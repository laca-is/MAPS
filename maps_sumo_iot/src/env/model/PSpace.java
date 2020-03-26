package model;

public class PSpace {

	private String psName;
	private float price;
	private int maxTime;
	private boolean isFree;
	private double position;
	private String edge;
	
	public Sensor psSensor;

	public PSpace(String psName, float price, int maxTime, boolean isFree, double position, String edge) {
		this.psName = psName;
		this.price = price;
		this.maxTime = maxTime;
		this.isFree = isFree;
		this.position = position;
		this.edge = edge;
	}

	public String getPsName() {
		return psName;
	}

	public void setPsName(String psName) {
		this.psName = psName;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getMaxTime() {
		return maxTime;
	}

	public void setMaxTime(int maxTime) {
		this.maxTime = maxTime;
	}

	public boolean isFree() {
		return isFree;
	}

	public void setFree(boolean isFree) {
		this.isFree = isFree;
	}

	public double getPosition() {
		return position;
	}

	public void setPosition(double position) {
		this.position = position;
	}

	public String getEdge() {
		return edge;
	}

	public void setEdge(String edge) {
		this.edge = edge;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	@Override
	public String toString() {		
		return this.isFree
				? "FREE - " + this.getPsName() + " - R$" + this.getPrice() + " - Time: " + this.getMaxTime()
						+ "Position: " + this.position + "Edge: " + this.edge
				: "BUSY - " + this.getPsName() + " - R$" + this.getPrice() + " - Time: " + this.getMaxTime()
						+ "Position: " + this.position + "Edge: " + this.edge;
	}

}
