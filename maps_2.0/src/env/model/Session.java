package model;

import java.util.HashMap;
import java.util.LinkedList;

public class Session {

	private String driverName;
	private RDriver rDriver;
	private LinkedList<PSpace> psHistory;
	private PSpace currentPS;
	
	public LinkedList<Round> roundList;
	private int nUsedRounds;
	
	public HashMap<String, LinkedList<Round >> roundsSector;
	
	public Session(String driverName, RDriver rDriver) {
		this.driverName = driverName;
		this.rDriver = rDriver;
		psHistory = new LinkedList<PSpace>();
		roundList = new LinkedList<Round>();
		nUsedRounds = 0;
		roundsSector = new HashMap<>();
	}
	
	public void increaseRound(){
		nUsedRounds++;
	}
	
	public int getUsedRounds(){
		return this.nUsedRounds;
	}

	public boolean isOnPSHistory(String psName) {
		boolean isOn = false;

		for (PSpace ps : this.psHistory) {
			if (ps.getAgentName().equals(psName)) {
				isOn = true;
				break;
			}
		}
		return isOn;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public RDriver getrDriver() {
		return rDriver;
	}

	public void setrDriver(RDriver rDriver) {
		this.rDriver = rDriver;
	}

	public LinkedList<PSpace> getPsHistory() {
		return psHistory;
	}

	public PSpace getCurrentPS() {
		return currentPS;
	}

	public void setCurrentPS(PSpace currentPS) {
		this.currentPS = currentPS;
	}

}
