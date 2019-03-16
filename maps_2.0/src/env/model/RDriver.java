package model;

import java.io.OutputStream;
import java.util.Date;

public class RDriver {
	
	private DriverProfile dProfile;
	private PSPreferences dPreferences;
	
	private OutputStream dOutput;
	
	private Date timeIn;
	private Date timeOff;
	
	public RDriver(DriverProfile dProfile, PSPreferences dPreferences, OutputStream dOutput) {
		this.dProfile = dProfile;
		this.dPreferences = dPreferences;
		this.dOutput = dOutput;
	}

	public DriverProfile getdProfile() {
		return dProfile;
	}

	public void setdProfile(DriverProfile dProfile) {
		this.dProfile = dProfile;
	}

	public PSPreferences getdPreferences() {
		return dPreferences;
	}

	public void setdPreferences(PSPreferences dPreferences) {
		this.dPreferences = dPreferences;
	}

	public OutputStream getdOutput() {
		return dOutput;
	}

	public void setdOutput(OutputStream dOutput) {
		this.dOutput = dOutput;
	}

	public Date getTimeIn() {
		return timeIn;
	}

	public void setTimeIn(Date timeIn) {
		this.timeIn = timeIn;
	}

	public Date getTimeOff() {
		return timeOff;
	}

	public void setTimeOff(Date timeOff) {
		this.timeOff = timeOff;
	}
	
	
	
	 
	

}
