package model;

import java.io.Serializable;

public class PSOffer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1260253635866744522L;
	private String psName;
	private int psMaxTime;
	private double psPrice;
	private String sectorName;

	public PSOffer(String psName, int psMaxTime, double psPrice, String sectorName) {
		this.psName = psName;
		this.psMaxTime = psMaxTime;
		this.psPrice = psPrice;
		this.sectorName = sectorName;
	}

	public PSOffer(String psName) {
		this.psName = psName;
	}

	public String getPsName() {
		return psName;
	}

	public void setPsName(String psName) {
		this.psName = psName;
	}

	public int getPsMaxTime() {
		return psMaxTime;
	}

	public void setPsMaxTime(int psMaxTime) {
		this.psMaxTime = psMaxTime;
	}

	public double getPsPrice() {
		return psPrice;
	}

	public void setPsPrice(double psPrice) {
		this.psPrice = psPrice;
	}

	public String getSectorName() {
		return sectorName;
	}

	public void setSectorName(String sectorName) {
		this.sectorName = sectorName;
	}

	@Override
	public String toString() {
		return "Parking Space: " + this.getPsName() + " (Sector: " + this.getSectorName() + ")\n\tPrice: $"
				+ this.getPsPrice() + " - Max. Time: " + this.getPsMaxTime() + "min.";
	}

}
