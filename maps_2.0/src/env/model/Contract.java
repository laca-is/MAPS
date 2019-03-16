package model;

public class Contract {

	//Sector Values
	private double wPrice;
	private double wTime;
		
	//PSValues
	private double psPrice;
	private int maxTime;
	
	public Contract(){}
	
	public Contract(double wPrice, double wTime) {
		this.wTime = wTime;
		this.wPrice = wPrice;
	}
	
	public Contract(double psPrice, int maxTime) {
		this.maxTime = maxTime;
		this.psPrice = psPrice;
	}

	public int getMaxTime() {
		return maxTime;
	}

	public void setMaxTime(int maxTime) {
		this.maxTime = maxTime;
	}

	public double getPsPrice() {
		return psPrice;
	}

	public void setPsPrice(double psPrice) {
		this.psPrice = psPrice;
	}

	public double getwTime() {
		return wTime;
	}
	public void setwTime(double wTime) {
		this.wTime = wTime;
	}
	public double getwPrice() {
		return wPrice;
	}
	public void setwPrice(double wPrice) {
		this.wPrice = wPrice;
	}
		
}
