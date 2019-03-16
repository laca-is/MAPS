// CArtAgO artifact code for project maps_holo

package artifacts;

import java.util.Date;
import java.util.LinkedList;

import cartago.Artifact;
import cartago.OPERATION;
import cartago.ObsProperty;
import cartago.OpFeedbackParam;
import control.DBControl;
import model.PSValues;
import model.PSpace;
import model.ResultDriver;
import model.Sector;

public class A_StructureInfo extends Artifact {

	private int nHealthSectors;
	public LinkedList<ResultDriver> resultDriver = new LinkedList<>();
	public LinkedList<Double> utilitySystem = new LinkedList<>();
	public int nMensagens = 0;
	

	void init(String st, boolean network, boolean simulation, int nSectors, int nPS) {

		defineObsProperty("structure", st);
		defineObsProperty("simulation", simulation);
		defineObsProperty("network", network);
		defineObsProperty("timeoutManager", 20000);
		defineObsProperty("timeoutSector", 30000);
		defineObsProperty("timeoutPS", 30000);
		defineObsProperty("nSectors", nSectors);
		defineObsProperty("nPS", nPS);
		defineObsProperty("threshold", ((double) PSValues.DRIVERTHRESHOLD.getValue()) / 10);

		nHealthSectors = nSectors;
		defineObsProperty("statusSystem", true);
	}

	@OPERATION
	void setStatusSystem(boolean status) {
		ObsProperty st = getObsProperty("statusSystem");
		st.updateValue(status);

		signal("statusSystem", status);
	}

	@OPERATION
	void setStructure(String structure) {
		ObsProperty st = getObsProperty("structure");
		st.updateValue(structure);
		signal("structureChanged");
	}

	@OPERATION
	void setNSectors(int nSectors) {
		ObsProperty st = getObsProperty("nSectors");
		st.updateValue(nSectors);
	}

	@OPERATION
	void assignSectorSTR(String sectorName) {
		// sector,sectorName,healthy
		// healthy indicates the health of sector (if true - sector is ok / if
		// false - sector is on emergency status)
		defineObsProperty("sector", sectorName, true);
	}
	
	@OPERATION
	void suspendSystem(){
		signal("redAlert");
	}

	@OPERATION
	void setSectorHealthy(String sectorName, Boolean isHealthy) {
		ObsProperty st = getObsPropertyByTemplate("sector", sectorName, Boolean.TRUE);
		st.updateValues(sectorName, isHealthy);
		DBControl.getSector(sectorName).setHealthy(isHealthy);
		
		signal("yellowAlert");
		nHealthSectors--;
		checkSystemHealth(sectorName);
		
		
	}

	private void checkSystemHealth(String sectorName) {
		if (nHealthSectors < 1) {
			updateObsProperty("statusSystem", false);
			signal("orangeAlert",sectorName);
		}
	}

	
	@OPERATION
	void setTimeout(String agent, int timeout) {

		ObsProperty prop;
		switch (agent) {
		case "manager":
			prop = getObsProperty("timeoutManager");
			break;
		case "sector":
			prop = getObsProperty("timeoutSector");
			break;
		case "pspace":
			prop = getObsProperty("timeoutPS");
			break;
		default:
			prop = getObsProperty("timeoutManager");
			break;
		}
		prop.updateValue(timeout);
	}

	@OPERATION
	void translateAgentAddress(String sender, OpFeedbackParam<String> receiver, OpFeedbackParam<Boolean> changeAgentType) {

		char type = sender.charAt(0);
		changeAgentType.set(false);
		
		switch (type) {
		case 'm':
			changeAgentType.set(true);
			break;
		case 'p':
			receiver.set(translatePS(sender));
			break;
		case 's':
			receiver.set(translateSector(sender));
			break;
		default:
			System.out.println("Wrong type of agent!");
			break;
		}
	}
	
	private String translatePS(String sender){
		PSpace ps = DBControl.getPSpace(sender);
		return ps.getPsParent() == null ? ps.getAgentName() : ps.getPsParent().getAgentName();
	}
	
	private String translateSector(String sender){
		Sector sector = DBControl.getSector(sender);
		return sector.getSectorParent() == null ? sector.getAgentName() : sector.getSectorParent().getAgentName();
	}
	
	
	//********** Methods to static usage *****************
	@OPERATION
	public void getCurrentTime(OpFeedbackParam<Long> time){
		time.set(new Date().getTime());
	}
	
	@OPERATION
	public void startStatisticDriver(String driverName){
		ResultDriver result = new ResultDriver(driverName);
		resultDriver.add(result);
	}
	
	@OPERATION
	public void setDriverUtility(String driverName, double utility){
		ResultDriver rd = getResultDriver(driverName);
		rd.driverUtility = utility;
	}
	
	@OPERATION
	public void setDriverRoundOver(String driverName){
		ResultDriver rd = getResultDriver(driverName);
		rd.roundsOver = true;
	}
	

	@OPERATION
	public void setDriverTimeOut(String driverName){
		ResultDriver rd = getResultDriver(driverName);
		rd.isTimeout = true;
	}
	
	
	@OPERATION
	public void finishStatisticDriver(String driverName, double utility, boolean getPS, boolean roundsOver, boolean timeout){
		ResultDriver result = getResultDriver(driverName);
		result.timeOff = new Date().getTime();
		result.driverUtility = utility;
		result.getPS = getPS;
		result.roundsOver = roundsOver;
		result.isTimeout = timeout;
	}
	
	@OPERATION
	public void addSystemUtility(double utility){
		utilitySystem.add(utility);
	}
	
	private ResultDriver getResultDriver(String driverName){
		ResultDriver rd = null;
		
		for(ResultDriver r : resultDriver){
			if(r.driverName.equals(driverName)){
				rd = r;
				break;
			}
		}
		
		return rd;
	}

	@OPERATION
	public void generateReport(){
		double avgSystem = 0;
		double avgdrivers = 0, avgTime = 0;
		int timeouts = 0, roundsOver = 0, getPS = 0;
		
		for(Double d : utilitySystem){
			avgSystem+= d;
		}
		avgSystem = avgSystem / utilitySystem.size();
		
		for(ResultDriver rd : resultDriver){
			avgdrivers += rd.driverUtility;
			
			if(rd.getPS)
				avgTime = (rd.timeOff - rd.timeIn) + avgTime;
			
			
			if(rd.getPS)
				getPS++;
			if(rd.isTimeout)
				timeouts++;
			if(rd.roundsOver)
				roundsOver++;
		}
		
		avgdrivers = avgdrivers / resultDriver.size();
		avgTime = avgTime / getPS;
		
		System.out.println("System Utility: " + avgSystem);
		System.out.println("Drivers Utility: " + avgdrivers);
		System.out.println("Driver PS" + getPS);
		System.out.println("Rounds Over: " + roundsOver);
		System.out.println("Timeout: " + timeouts);
		System.out.println("AVG Assigning time: " + avgTime);
		System.out.println("Mensagens: " + this.nMensagens);
	}
}
