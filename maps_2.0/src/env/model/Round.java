package model;

import java.util.Date;
import java.util.LinkedList;

public class Round {
	
	private Date timeIn;
	private Date timeOut;
	private LinkedList<PSpace> psHistoryRound;
	
	public Round(Date timeIn) {
		this.timeIn = timeIn;
		this.timeOut = new Date(timeIn.getTime() + PSValues.TIMEOUTROUND.getValue());		
		this.psHistoryRound = new LinkedList<PSpace>();
	}

	public Date getTimeIn() {
		return timeIn;
	}

	public void setTimeIn(Date timeIn) {
		this.timeIn = timeIn;
	}

	public Date getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(Date timeOut) {
		this.timeOut = timeOut;
	}

	public LinkedList<PSpace> getPsHistoryRound() {
		return psHistoryRound;
	}
	
	
	
	

}
