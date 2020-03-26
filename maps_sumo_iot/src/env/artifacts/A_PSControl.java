// CArtAgO artifact code for project mAPS_SUMO

package artifacts;

import java.util.Map.Entry;

import cartago.Artifact;
import cartago.OPERATION;
import cartago.OpFeedbackParam;
import control.DBControl;
import model.PSpace;
import model.Sensor;

public class A_PSControl extends Artifact {

	private PSpace ps;

	void init() {
	}

	@OPERATION
	void registerPSpace(String psName, float price, int time, String edge, double position) {
		ps = new PSpace(psName, price, time, true, position, edge);
		ps.psSensor = new Sensor(psName, false, 0, 0);
		DBControl.hashPSpaces.put(psName, ps);

	}

	@OPERATION
	void printAllPSpaces() {
		for (Entry<String, PSpace> entry : DBControl.hashPSpaces.entrySet())
			System.out.println(entry.getValue());

	}

	@OPERATION
	void requestPSpace(OpFeedbackParam<Boolean> isPSpace, OpFeedbackParam<String> psName) {

		isPSpace.set(false);

		for (Entry<String, PSpace> entry : DBControl.hashPSpaces.entrySet()) {
			if (entry.getValue().isFree()) {

				entry.getValue().setFree(false);
				isPSpace.set(true);
				psName.set(entry.getValue().getPsName());
				break;
			}
		}

	}

	@OPERATION
	void freePSpace(String psName) {
		DBControl.hashPSpaces.get(psName).setFree(true);
	}
}
