package model;

import java.util.LinkedList;

public class Sector {

	private String agentName;
	private Contract contract;
	private double usage;
	
	private boolean isHealthy;
	
	private LinkedList<Sector> sectorChildList;
	private Sector sectorParent;

	public Sector(String agentName, Contract contract) {
		this.agentName = agentName;
		this.contract = contract;
		this.usage = 0;
		this.isHealthy = true;
		sectorChildList = new LinkedList<Sector>();
		sectorParent = null;
	}
	
	public boolean isHealthy(){
		return this.isHealthy;
	}
	
	public void setHealthy(boolean healthy){
		this.isHealthy = healthy;
	}

	public Sector getSectorParent() {
		return sectorParent;
	}

	public void setSectorParent(Sector sectorParent) {
		this.sectorParent = sectorParent;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public Contract getContract() {
		return contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}

	public double getUsage() {
		return usage;
	}

	public void setUsage(double usage) {
		this.usage = usage;
	}

	public LinkedList<Sector> getSectorChildList() {
		return sectorChildList;
	}

	public void setSectorChildList(LinkedList<Sector> sectorChildList) {
		this.sectorChildList = sectorChildList;
	}

	@Override
	public String toString() {
		return "Sector: " + this.agentName + " - Usage: " + this.getUsage() + "\n\twPrice: "
				+ this.getContract().getwPrice() + " - wTime: " + this.getContract().getwTime();
	}

}
