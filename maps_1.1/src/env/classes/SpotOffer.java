package classes;

public class SpotOffer {
	
	private String location;
	private String spotAgentName;
	private int trust;
	private double price;
	
	
	public SpotOffer(String location, String spotAgent, int trust, double price) {
		
		this.location = location;
		this.trust = trust;
		this.price = price;
		this.spotAgentName = spotAgent;
	}
	
	public SpotOffer(){}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getTrust() {
		return trust;
	}

	public void setTrust(int trust) {
		this.trust = trust;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getSpotAgentName() {
		return spotAgentName;
	}

	public void setSpotAgentName(String spotAgentName) {
		this.spotAgentName = spotAgentName;
	}
	
	

}