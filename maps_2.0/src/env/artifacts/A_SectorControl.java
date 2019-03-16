package artifacts;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;

import cartago.Artifact;
import cartago.OPERATION;
import cartago.ObsProperty;
import cartago.OpFeedbackParam;
import control.ContractGenerator;
import control.DBControl;
import model.PSpace;
import model.Sector;

public class A_SectorControl extends Artifact {

	private HashMap<String, Sector> hashSector;
	private Sector sector;
	public ObsProperty obsSector;

	void init() {
		hashSector = new HashMap<String, Sector>();
		defineObsProperty("getSector", sector);
	}

	@OPERATION
	public void setNewSectorParent(String sectorName, OpFeedbackParam<String> sectorParentName) {
		Sector sectorChild = DBControl.getSector(sectorName);
		Sector sectorParent = findSectorChildLess(sectorName);

		if (sectorParent == null) {
			signal("systemAlert");
		} else {
			if (!sectorChild.getSectorChildList().isEmpty()) {
				for (Sector sectorGrandson : sectorChild.getSectorChildList()) {
					sectorParent.getSectorChildList().add(sectorGrandson);
					sectorGrandson.setSectorParent(sectorParent);
				}
				sectorChild.setSectorParent(sectorParent);
				sectorParent.getSectorChildList().add(sectorChild);
				sectorParentName.set(sectorParent.getAgentName());
			}
		}
	}

	private Sector findSectorChildLess(String sectorName) {
		Sector sectorChildLess = null;
		int nChilds = hashSector.size() - 1;
		for (Entry<String, Sector> s : hashSector.entrySet()) {
			if (s.getValue().isHealthy() && !s.getValue().getAgentName().equals(sectorName)
					&& s.getValue().getSectorChildList().size() < nChilds) {
				nChilds = s.getValue().getSectorChildList().size();
				sectorChildLess = s.getValue();
			}
		}
		return sectorChildLess;
	}

	@OPERATION
	public void translateSector(String sectorChildName, OpFeedbackParam<String> sectorParentName){
		Sector sectorChild = hashSector.get(sectorChildName);
		
		if(sectorChild.getSectorParent() == null)
			sectorParentName.set(sectorChildName);			
		else
			sectorParentName.set(sectorChild.getSectorParent().getAgentName());
	}
	

	@OPERATION
	public void registerSector(String sectorName, OpFeedbackParam<Double> wPrice, OpFeedbackParam<Double> wTime) {
		sector = new Sector(sectorName, ContractGenerator.generateSectorContract());
		hashSector.put(sectorName, sector);
		DBControl.insertSector(sectorName, sector);
	}

	@OPERATION
	public void selectSector(String sectorName, OpFeedbackParam<Sector> feedbackSector) {
		feedbackSector.set(hashSector.get(sectorName));
	}
	
	@OPERATION
	public void selectHealthySector(String sectorName, OpFeedbackParam<String> sectorHealthy){
		for(Entry<String, Sector> s : hashSector.entrySet()){
			if(s.getValue().isHealthy() && !s.getValue().getAgentName().equals(sectorName)){
				sectorHealthy.set(s.getKey());
				System.out.println("The Healthy sector is: " + s.getKey());
				break;
			}
		}
		
	}
	
//	@OPERATION
//	public void transferSectorhood(String oldSector, String newSector){
//		System.out.println("Transfering sectorhood...!");
//		for(Entry<String, PSpace> ps : DBControl.getHashPSpaces().entrySet()){
//			if(ps.getValue().getSectorName().equals(oldSector))
//				ps.getValue().setSectorName(hashSector.get(newSector));
//				ps.getValue().setContract(hashSector.get(newSector).getContract());				
//		}
//		
//	}
	
	@OPERATION
	public void getSectorPSList(String sectorName, OpFeedbackParam<String[]> psList){
		LinkedList<String> list = new LinkedList<>();
		
		for(Entry<String, PSpace> ps : DBControl.getHashPSpaces().entrySet()){
			if(ps.getValue().getSectorName().getAgentName().equals(sectorName)){
				list.add(ps.getKey());
			}
		}
		
		String[] x = new String[list.size()];
		for(int i = 0; i < x.length; i++){
			x[i] = list.get(i);
		}
		psList.set(x);
		
	}
	
	@OPERATION
	public void renameSector(String sectorName, String newName){
		hashSector.get(sectorName).setAgentName(newName);
	}

	@OPERATION
	public void updateUsage(String sectorName, double usage) {
		hashSector.get(sectorName).setUsage(usage);
		System.out.println(sectorName + " updated its usage to " + usage + "%");
	}

	@OPERATION
	public void isFull(OpFeedbackParam<Boolean> isFull) {
		isFull.set(true);
		for (Entry<String, Sector> entry : hashSector.entrySet()) {
			if (entry.getValue().getUsage() < 100) {
				isFull.set(false);
				break;
			}
		}
	}

}
