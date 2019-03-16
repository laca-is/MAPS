package classes;

import artifacts.ISensorService;

public class Spot {
	
	private String spotAgent;
	private String driverAgent;
	private String location;
	private double price;
	private int trust;
	private boolean available;
	private ISensorService sensor;
	
	
	public Spot(String spotAgent, double price, int trust){
		
		this.setSpotAgent(spotAgent);		
		this.setDriverAgent(driverAgent);
		this.location = generateLocation(spotAgent);
		this.setPrice(price);
		this.setTrust(trust);
		this.setAvailable(true);
		sensor = new DistanceSensor();
	}
	
	private String generateLocation(String spotAgent){
		
		return spotAgent.substring(4);
		
	}
	
	public boolean getStatusSensor(){
		
		if((double)sensor.read() >  0.5)			
			return true;
			else
				return false;
		
	}
	
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getTrust() {
		return trust;
	}
	public void setTrust(int trust) {
		this.trust = trust;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}


	public String getDriverAgent() {
		return driverAgent;
	}


	public void setDriverAgent(String driverAgent) {
		this.driverAgent = driverAgent;
	}
	
	public String getLocation() {
		return location;
	}


	public void setLocation(String location) {
		this.location = location;
	}


	public String getSpotAgent() {
		return spotAgent;
	}


	public void setSpotAgent(String spotAgent) {
		this.spotAgent = spotAgent;
	}
	
	@Override
	public String toString() {
		System.out.println("Spot (" + this.location+") \nPrice: " + 
				this.getPrice()+"$\nTrust: "+this.getTrust()
					+"\nDriver: " + this.getDriverAgent());
		return "";
	}


	
	
	
	

}
