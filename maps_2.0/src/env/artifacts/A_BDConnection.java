package artifacts;

import java.io.OutputStream;
import java.util.Date;

import cartago.Artifact;
import cartago.OPERATION;
import cartago.OpFeedbackParam;
import control.DBControl;
import model.DriverProfile;
import model.PSPreferences;
import model.PSpace;
import model.RDriver;

public class A_BDConnection extends Artifact{

	
	
	void init() {
		
	}
	
		@OPERATION
	public void startCountingTime(String dName){
		DBControl.getDriver(dName).setTimeIn(new Date());
	}
	
	@OPERATION
	public void finishCountingTime(String dName){
		DBControl.getDriver(dName).setTimeOff(new Date());
	}
	
	@OPERATION
	public void calcDriverPrice2Pay(String dName, String psName, OpFeedbackParam<Double> price){
		RDriver rDriver = DBControl.getDriver(dName);
		PSpace ps = DBControl.getPSpace(psName);
		
		double stayedTime = rDriver.getTimeOff().getTime() - rDriver.getTimeIn().getTime();
		System.out.println("Stayed Time: " + stayedTime);
		stayedTime = stayedTime / 1000;
		System.out.println("Stayed Time: " + stayedTime);
		//stayedTime = stayedTime / 1000;
		//System.out.println("Stayed Time: " + stayedTime);
		stayedTime = stayedTime / 60;
		System.out.println("Stayed Time: " + stayedTime);
		
		double finalPrice = stayedTime * (ps.getContract().getPsPrice());
		System.out.println("Price: " + finalPrice);
		
		
		price.set(finalPrice);
		
	}
	
	@OPERATION
	public void addDriver(String dName, double dPrice, double wPrice, int dTime, double wTime, OutputStream addressDriver){
		
		PSPreferences psPref = new PSPreferences(dPrice, wPrice, dTime, wTime);
		DriverProfile dProfile = new DriverProfile(0, dName, psPref);
		
		RDriver rDriver = new RDriver(dProfile, psPref, addressDriver);
		DBControl.addCurrentDriver(rDriver);
		System.out.println(dName + " inserido com sucesso!");
	}
	
	@OPERATION
	public void addDriverSimulation(String dName, double dPrice, double wPrice, int dTime, double wTime){
		
		PSPreferences psPref = new PSPreferences(dPrice, wPrice, dTime, wTime);
		DriverProfile dProfile = new DriverProfile(0, dName, psPref);
		
		RDriver rDriver = new RDriver(dProfile, psPref, null);
		DBControl.addCurrentDriver(rDriver);
		System.out.println(dName + " inserido com sucesso!");
	}
	
	@OPERATION
	public void removeDriver(String agentName){
		DBControl.removeDriver(agentName);
	}
	
	@OPERATION
	public void isDriver(String dName, OpFeedbackParam<Boolean> isDriver) {
		isDriver.set(DBControl.isOn(dName));
	}
	
	@OPERATION
	public void getDriver(String dName){
		
	}
	
	
}
