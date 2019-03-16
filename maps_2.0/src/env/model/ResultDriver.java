package model;

import java.util.Date;

public class ResultDriver {
	
	public String driverName;
	public long timeIn;
	public long timeOff;
	public boolean isTimeout;
	public boolean getPS;
	public boolean roundsOver;
	public double driverUtility;
	
	public ResultDriver(String driverName){
		timeIn = new Date().getTime();
		isTimeout = false;
		getPS = false;
		roundsOver = false;
		driverUtility = 0;
		this.driverName = driverName;
	}

}
