// CArtAgO artifact code for project maps_holo

package artifacts;

import cartago.Artifact;
import cartago.OPERATION;
import cartago.OpFeedbackParam;
import control.DBControl;
import model.RDriver;

public class A_QueueControl extends Artifact {

	void init() {

	}

	@OPERATION
	public void addDriverQueue(String dName) {
		DBControl.addIntoQueue(dName);
		System.out.println("Drivers in Queue: (" + DBControl.numDriversinQueue() + "): " + DBControl.driversInQueue());
	}

	@OPERATION
	public void isEmptyQueue(OpFeedbackParam<Boolean> isEmpty) {
		isEmpty.set(DBControl.isEmptyQueue());
	}

	@OPERATION
	public void getDriverQueue(OpFeedbackParam<String> dName, OpFeedbackParam<Double> dPrice,
			OpFeedbackParam<Double> dPriceWeight, OpFeedbackParam<Integer> dTime, OpFeedbackParam<Double> dTimeWeight) {
		String driver = DBControl.pullQueue();
		RDriver rDriver = DBControl.getDriver(driver);
		
		dName.set(rDriver.getdProfile().getDriverName());
		
		dPrice.set(rDriver.getdPreferences().getPsPrice());
		dPriceWeight.set(rDriver.getdPreferences().getPriceWeight());
		
		dTime.set(rDriver.getdPreferences().getPsTime());
		dTimeWeight.set(rDriver.getdPreferences().getTimeWeight());
		
		System.out.println("Drivers in Queue: (" + DBControl.numDriversinQueue() + "): " + DBControl.driversInQueue());

		
		
	}
}
