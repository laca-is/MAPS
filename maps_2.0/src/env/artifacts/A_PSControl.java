// CArtAgO artifact code for project maps_holo

package artifacts;

import java.util.Collections;
import java.util.LinkedList;

import cartago.Artifact;
import cartago.OPERATION;
import cartago.ObsProperty;
import cartago.OpFeedbackParam;
import control.DBControl;
import model.PSValues;
import model.PSpace;
import model.Sector;

public class A_PSControl extends Artifact {

	private LinkedList<PSpace> psList;
	private Sector selfSector;
	private int nPS, nPSOk = 0, nUsedPS;
	private double usage;

	void init(String sectorName, Sector self, int nPS) {
		this.psList = new LinkedList<PSpace>();
		this.selfSector = self;
		this.nPS = nPS;
		defineObsProperty("usage", 0);
	}

	@OPERATION
	public void registerPSpace(String psAgentName, OpFeedbackParam<Double> psPrice, OpFeedbackParam<Integer> psTime) {

		nPSOk++;
		PSpace pSpace = new PSpace(psAgentName, this.selfSector);
		pSpace.generateContract();
		pSpace.calculatePSUtility(this.selfSector.getContract().getwPrice(), this.selfSector.getContract().getwTime());
		// System.out.println(pSpace);
		// System.out.println("PSUtility ("+ psAgentName + ") - " +
		// pSpace.getPsUtility());
		psList.add(pSpace);
		DBControl.addPSpace(pSpace);
		psPrice.set(pSpace.getContract().getPsPrice());
		psTime.set(pSpace.getContract().getMaxTime());

		if (nPSOk == nPS)
			signal("nPSOK");
	}

	@OPERATION
	public void calculateUpdatePSUtility() {
		Collections.sort(this.psList);

		System.out.println(this.selfSector);
		for (PSpace ps : psList) {
			System.out.println(ps.getAgentName() + " - Utility: " + ps.getPsUtility() + "\nPrice: $"
					+ ps.getContract().getPsPrice() + " - Time: " + ps.getContract().getMaxTime() + "min.");
		}
	}

	@OPERATION
	public void requestPSpace(String dName, OpFeedbackParam<Boolean> isPS, OpFeedbackParam<String> psName,
			OpFeedbackParam<Double> psPrice, OpFeedbackParam<Integer> psTime) {

		isPS.set(false);

		for (PSpace ps : psList) {
			if (ps.getStatus().equals(PSValues.FREE) && !ps.isOnHistoryNegociation(dName)) {
				psName.set(ps.getAgentName());
				psPrice.set(ps.getContract().getPsPrice());
				psTime.set(ps.getContract().getMaxTime());

				ps.setDriverName(dName);
				ps.setStatus(PSValues.NEGOCIATION);
				ps.insertDriverInNegociation(dName);
				isPS.set(true);
				nUsedPS++;
				updateUsage();
				break;
			}
		}
	}

	private void updateUsage() {
		
		usage = (100 * nUsedPS) / nPS;
		ObsProperty ob = getObsProperty("usage");
		ob.updateValue(usage);
		signal("usageUpdated", usage);
		System.out.println("Lucas: " + selfSector.getAgentName() + " - " + usage + "%");
	}

	@OPERATION
	public void assignPSpace(String dName, String psName) {
		PSpace ps = getPSpace(psName);
		ps.setDriverName(dName);
		ps.setStatus(PSValues.OCCUPIED);
		ps.clearHistoryNegociation();
	}

	@OPERATION
	public void freePSpace(String dName, String psName) {
		PSpace ps = getPSpace(psName);
		ps.setStatus(PSValues.FREE);
		nUsedPS--;
		updateUsage();
		ps.setDriverName("");
		ps.clearHistoryNegociation();
	}

	@OPERATION
	public void rejectPSpace(String dName, String psName) {
		PSpace ps = getPSpace(psName);
		ps.setStatus(PSValues.FREE);
		nUsedPS--;
		updateUsage();
		ps.setDriverName("");
	}

	private PSpace getPSpace(String psName) {
		PSpace pSpace = null;
		for (PSpace ps : psList) {
			if (ps.getAgentName().equals(psName)) {
				pSpace = ps;
				break;
			}
		}
		return pSpace;
	}

	@OPERATION
	public void setFailedPS(String psName) {
		PSpace ps = getPSpace(psName);
		ps.setHealthy(false);
	}

	@OPERATION
	public void setNewPSParent(String psChildName, OpFeedbackParam<String> psParentName) {

		PSpace psChild = getPSpace(psChildName);
		PSpace psParent = findChildLessPS(psChildName);

		if (psParent == null) {
			System.out.println("Alarm signal!!!!!!!!!!!!");
			signal("all_PSBroken");
		} else {
			if (!psChild.getPsChilds().isEmpty()) {
				for (PSpace psgrandchild : psChild.getPsChilds()) {
					psgrandchild.setPsParent(psParent);
					psParent.addChild(psgrandchild);
				}
			}
			psChild.setPsParent(psParent);
			psParent.addChild(psChild);
			psParentName.set(psParent.getAgentName());
		}
	}
	
	@OPERATION
	public void translatePS(String psChildName, OpFeedbackParam<String> psParentName) {
		
		PSpace ps = getPSpace(psChildName);
		
		if(ps.getPsParent() != null)
			psParentName.set(ps.getPsParent().getAgentName());
			else
				psParentName.set(ps.getAgentName());
		
		
//		for (PSpace ps : psList) {
//			if (ps.getPsChilds().size() > 0) {
//				for (PSpace psChild : ps.getPsChilds()) {
//					if (psChild.getAgentName().equals(psChildName)) {
//						psParentName.set(ps.getAgentName());
//						return;
//					}
//				}
//			}
//		}
	}

	private PSpace findChildLessPS(String psName) {
		int nChild = psList.size();
		PSpace psChildLess = null;

		for (PSpace ps : psList) {
			if (ps.getPsChilds().size() < nChild && ps.isHealthy() && !ps.getAgentName().equals(psName)) {
				nChild = ps.getPsChilds().size();
				psChildLess = ps;
			}
		}
		return psChildLess;
	}
	
	@OPERATION
	public void assignEmergencyPSAGent(String psAgent){
		PSpace psEMS = new PSpace(psAgent);
		psEMS.setHealthy(true);
		psEMS.setStatus(PSValues.OCCUPIED);
		
		for(PSpace ps : psList){
			psEMS.addChild(ps);
			ps.setPsParent(psEMS);
		}
	
		psList.addFirst(psEMS);
		
	}
	
	@OPERATION
	public void addNewParkingSpaces(Object[] psArray){
		PSpace ps;
		for(Object str_ps : psArray){
			ps = DBControl.getPSpace((String)str_ps);
			ps.setSectorName(selfSector);
			ps.calculatePSUtility(selfSector.getContract().getwPrice(), selfSector.getContract().getwTime());
			psList.add(ps);
			System.out.println(ps.getAgentName() + "added!");
		}
		
	}
	
	@OPERATION
	public void renameAgent(String psName,String psCloneName){
		PSpace ps = getPSpace(psName);
		ps.setAgentName(psCloneName);		
	}


}
