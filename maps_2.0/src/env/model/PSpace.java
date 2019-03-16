package model;

import java.text.DecimalFormat;
import java.util.LinkedList;

import control.ContractGenerator;

public class PSpace implements Comparable<PSpace> {

	private String agentName;
	
	private PSValues status;
	private Contract contract;
	
	private String driverName; // my costumer
	private LinkedList<String> dNegociationHist; 

	private PSRequest psRequest;
	private LinkedList<PSpace> psChilds;
	private PSpace psParent;
	private Sector mySector;

	private double psUtilitySector;
	private double psUtilityManager;
	
	private boolean isHealthy;

	public double getPsUtility() {
		return psUtilitySector;
	}
	
	public double getPsUtilityManager(){
		return psUtilityManager;
	}

	private void setPsUtility(double utility) {
		this.psUtilitySector = utility;
	}
	
	private void setPsUtilityManager(double utility){
		this.psUtilityManager = utility;
	}
	
	public void calculatePSUtilityManager(double wPrice, double wTime){
		double utilityP, utilityS;
		DecimalFormat df = new DecimalFormat("#.00");

		utilityP = ((this.contract.getPsPrice() - PSValues.MIN_PRICE.getValue())
				/ (PSValues.MAX_PRICE.getValue() - PSValues.MIN_PRICE.getValue())) * wPrice;
		utilityS = (this.contract.getMaxTime() - PSValues.MIN_TIME.getValue());
		utilityS = (utilityS / (PSValues.MAX_TIME.getValue() - PSValues.MIN_TIME.getValue())) * wTime;
		
		

		// System.out.println("UtilityP - " + utilityP);
		// System.out.println("UtilityS - " + utilityS);
		this.setPsUtilityManager(Double.valueOf(df.format(utilityP + utilityS)));
	}

	public void calculatePSUtility(double wPrice, double wTime) {

		// System.out.println("WPRICE: " + wPrice + " WTIME: " + wTime);
		double utilityP, utilityS;
		DecimalFormat df = new DecimalFormat("#.00");

		utilityP = ((this.contract.getPsPrice() - PSValues.MIN_PRICE.getValue())
				/ (PSValues.MAX_PRICE.getValue() - PSValues.MIN_PRICE.getValue())) * wPrice;
		utilityS = (this.contract.getMaxTime() - PSValues.MIN_TIME.getValue());
		utilityS = (utilityS / (PSValues.MAX_TIME.getValue() - PSValues.MIN_TIME.getValue())) * wTime;

		// System.out.println("UtilityP - " + utilityP);
		// System.out.println("UtilityS - " + utilityS);
		this.setPsUtility(Double.valueOf(df.format(utilityP + utilityS)));
	}

	public PSpace(String agentName, Sector sector) {
		this.agentName = agentName;
		this.status = PSValues.FREE;		
		this.mySector = sector;
		psChilds = new LinkedList<PSpace>();
		dNegociationHist = new LinkedList<>();
		isHealthy = true;
	}
	
	public void generateContract(){
		this.contract = ContractGenerator.generatePSContract();
	}

	public PSpace(String agentName) {
		this.agentName = agentName;
		this.status = PSValues.FREE;
		psChilds = new LinkedList<PSpace>();
		dNegociationHist = new LinkedList<>();
		isHealthy = true;
	}
	
	public void insertDriverInNegociation(String dName){
		dNegociationHist.add(dName);
	}
	
	public void removeDriverInNegociation(String dName){
		dNegociationHist.remove(dName);
	}
	
	public void clearHistoryNegociation(){
		dNegociationHist.clear();
	}
	
	public boolean isOnHistoryNegociation(String dName){
		boolean isOn = false;
		
		for(String st : dNegociationHist){
			if(st.equals(dName)){
				isOn = true;
				break;
			}
		}
		return isOn;
	}

	public void addChild(PSpace pSpaceChild) {
		this.psChilds.add(pSpaceChild);
	}

	public Sector getSectorName() {
		return mySector;
	}

	public void setSectorName(Sector sectorName) {
		this.mySector = sectorName;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public PSValues getStatus() {
		return status;
	}

	public void setStatus(PSValues status) {
		this.status = status;
	}

	public Contract getContract() {
		return contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public PSRequest getPsRequest() {
		return psRequest;
	}

	public void setPsRequest(PSRequest psRequest) {
		this.psRequest = psRequest;
	}

	public LinkedList<PSpace> getPsChilds() {
		return psChilds;
	}

	public void setPsChilds(LinkedList<PSpace> psChilds) {
		this.psChilds = psChilds;
	}

	public PSpace getPsParent() {
		return psParent;
	}

	public void setPsParent(PSpace psParent) {
		this.psParent = psParent;
	}

	public boolean isHealthy() {
		return isHealthy;
	}

	public void setHealthy(boolean isHealthy) {
		this.isHealthy = isHealthy;
	}

	public int compareTo(PSpace ps) {
		return (this.getPsUtility() > ps.getPsUtility() ? -1 : this.getPsUtility() < ps.getPsUtility() ? +1 : 0);
	}

	@Override
	public String toString() {

		return this.getAgentName() + " - Driver: " + this.getDriverName() + "\nPrice: "
				+ this.getContract().getPsPrice() + " - Time: " + this.getContract().getMaxTime() + "\nChilds: " + this.getPsChilds();
	}

}
