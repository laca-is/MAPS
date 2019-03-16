// CArtAgO artifact code for project maps_holo

package artifacts;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;

import cartago.Artifact;
import cartago.OPERATION;
import cartago.OpFeedbackParam;
import control.DBControl;
import model.PSValues;
import model.PSpace;
import model.Round;
import model.Session;

public class A_SessionControl extends Artifact {

	public HashMap<String, Session> hashSessions;

	private Session session;
	private int nSectors;
	private PSpace psOffer;
	private double wPrice, wTime;

	void init(int nSectors) {
		hashSessions = new HashMap<String, Session>();
		this.nSectors = nSectors;
		
	}
	
	@OPERATION
	public void setSectorFull(){
		this.nSectors--;
	}
	
	@OPERATION
	public void setSectorAvailable(){
		this.nSectors++;
	}

	@OPERATION
	public void generateSession(String dName) {
		Session s = new Session(dName, DBControl.getDriver(dName));
		s.setCurrentPS(null);
		hashSessions.put(dName, s);

	}

	@OPERATION
	public void finishSession(String dName) {
		hashSessions.remove(dName);
	}

	@OPERATION
	public void startRound(String dName) {

		if (hashSessions.get(dName).roundList.size() < PSValues.MAXROUNDS.getValue()) {
			Round r = new Round(new Date());
			hashSessions.get(dName).roundList.add(r);
		} else {
			signal("roundsOver", dName);
		}
	}

	@OPERATION
	public void setManagerWeights(double wPrice, double wTime) {
		this.wPrice = wPrice;
		this.wTime = wTime;
	}

	@OPERATION
	public void registerOffer(String dName, String psName, double psPrice, int psTime, String sector) {

		session = hashSessions.get(dName);
		Round rLast = session.roundList.getLast();
		PSpace ps = DBControl.getPSpace(psName);

		session.getPsHistory().add(ps);
		rLast.getPsHistoryRound().add(ps);
		rLast.setTimeIn(new Date());

		if (rLast.getTimeIn().after(rLast.getTimeOut())) {
			signal("timeoutSector", dName);
		}

		if (rLast.getPsHistoryRound().size() >= this.nSectors) {
			signal("offersCompleted", dName);
		}
	}

	@OPERATION
	public void selectOffer(String dName, OpFeedbackParam<Boolean> isPS, OpFeedbackParam<String> psName,
			OpFeedbackParam<Double> psPrice, OpFeedbackParam<Integer> psTime, OpFeedbackParam<String> sectorName) {

		session = hashSessions.get(dName);
		Round rLast = session.roundList.getLast();

		if (session.getUsedRounds() < PSValues.MAXROUNDS.getValue()) {
			if (rLast.getPsHistoryRound().isEmpty()) {
				isPS.set(false);
			} else {
				isPS.set(true);
				psOffer = rLast.getPsHistoryRound().getFirst();
				psName.set(psOffer.getAgentName());
				psPrice.set(psOffer.getContract().getPsPrice());
				psTime.set(psOffer.getContract().getMaxTime());
				sectorName.set(psOffer.getSectorName().getAgentName());
				rLast.getPsHistoryRound().removeFirst();
				session.increaseRound();
			}
		} else {
			signal("roundsOver", dName);
		}
	}

	@OPERATION
	public void checkRound(String dName, OpFeedbackParam<Boolean> isRoundOk) {
		System.out.println("Teste de Round!!!!!!!!" + hashSessions.get(dName).getUsedRounds());
		if (hashSessions.get(dName).getUsedRounds() < PSValues.MAXROUNDS.getValue()) {
			isRoundOk.set(true);
		} else {
			isRoundOk.set(false);
		}

	}

	@OPERATION
	private void calculatePSUtilityManager(String dName) {
		Round rLast = hashSessions.get(dName).roundList.getLast();
		for (PSpace ps : rLast.getPsHistoryRound()) {
			ps.calculatePSUtilityManager(wPrice, wTime);
		}
		Collections.sort(rLast.getPsHistoryRound());
	}
	

	@OPERATION
	public void checkDriverPS(String dName, OpFeedbackParam<Boolean> isPSDriver){
		isPSDriver.set(hashSessions.get(dName).getCurrentPS() == null ? false : true);
	}
	
	/*
	 * *****************************************************************************************************
	 Above are the HB methods
	 Bellow are the BB methos
	 ********************************************************************************************************
	 */
	
	
	@OPERATION
	public void startRoundSector(String dName, String sectorName) {
		session = hashSessions.get(dName);
		
		if(!session.roundsSector.containsKey(sectorName))
			session.roundsSector.put(sectorName, new LinkedList<>());		
		
		session.roundsSector.get(sectorName).add(new Round(new Date()));
	}
	
	@OPERATION
	public synchronized void checkRoundSector(String dName, String sectorName, OpFeedbackParam<Boolean> isRoundOk) {
		session = hashSessions.get(dName);
		boolean roundsOver = true;
		
		if(session.roundsSector.get(sectorName).size() <= PSValues.MAXROUNDS.getValue())
			isRoundOk.set(true);
		else
			isRoundOk.set(false);
		
		for(Entry<String, LinkedList<Round>> entry : session.roundsSector.entrySet()){
			if(entry.getValue().size() <= PSValues.MAXROUNDS.getValue()){
				roundsOver = false;				
			}
		}
		
		if(roundsOver){
			//System.out.println(sectorName + " over reacted the round!!!!!!!!!!!!!! - " + session.roundsSector.size());
			signal("roundsOver",dName);
		}
		
	}
	
	@OPERATION
	public void registerSectorOffer(String dName, String psName, double psPrice, double psTime, String sectorName) {
		
		session = hashSessions.get(dName);
		Round rLast = session.roundsSector.get(sectorName).getLast();
		PSpace ps = DBControl.getPSpace(psName);
		rLast.getPsHistoryRound().add(ps);
		session.getPsHistory().add(ps);
		session.increaseRound();
	}

	@OPERATION
	public void rejectOfferSector(String dName, String sectorName) {
		session = hashSessions.get(dName);
		Round rLast = session.roundsSector.get(sectorName).getLast(); 
		rLast.setTimeOut(new Date());
		session.roundsSector.get(sectorName).add(new Round(new Date()));		
	}

	@OPERATION
	public void assignPSpaceSession(String dName, String psName) {
		session = hashSessions.get(dName);
		session.setCurrentPS(DBControl.getPSpace(psName));
	}
	
	//METHOD STATISTIC
	@OPERATION
	public void getManagerUtility(String psName, OpFeedbackParam<Double> managerUt){
		managerUt.set(DBControl.getPSpace(psName).getPsUtilityManager());
	}
	
	@OPERATION
	public void getSectorUtility(String psName, OpFeedbackParam<Double> sectorUt){
		sectorUt.set(DBControl.getPSpace(psName).getPsUtility());
	}
}
