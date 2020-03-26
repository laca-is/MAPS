// CArtAgO artifact code for project mAPS_SUMO

package artifacts;

import cartago.Artifact;
import cartago.OPERATION;
import control.T_TraCI_SUMO;
import de.tudresden.sumo.cmd.Vehicle;
import de.tudresden.ws.container.SumoStopFlags;
import it.polito.appeal.traci.SumoTraciConnection;

public class A_SUMOConnect extends Artifact {

	static String sumo_bin = "sumo-gui";
	static String config_file = "scenario/unicamp.sumocfg";
	static double step_length = 0.1;
	SumoTraciConnection conn;

	Thread threadTraCI;
	T_TraCI_SUMO clientTraCI;

	void init() {
		startTraciSUMO();
		clientTraCI = new T_TraCI_SUMO(conn);
		threadTraCI = new Thread(clientTraCI);
		threadTraCI.start();
	}

	public void startTraciSUMO() {

		try {
			conn = new SumoTraciConnection(sumo_bin, config_file);
			conn.addOption("step-length", step_length + "");
			conn.addOption("start", "true");

			conn.runServer();
			conn.setOrder(1);

			conn.printSumoError(true);
			conn.printSumoOutput(true);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@OPERATION
	public void addVehicle(String agentName, String edge, double position) {
		try {
			conn.do_job_set(Vehicle.add(agentName, "DEFAULT_VEHTYPE", "route2", 1, 0.0, 1.0, Byte.valueOf("0")));
			conn.do_job_set(Vehicle.setStop(agentName, edge, position, (byte) 0, 9999999.1,
					new SumoStopFlags(true, false, false, false, false)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@OPERATION
	public void removeVehicle(String agentName) {

		try {
			conn.do_job_set(Vehicle.resume(agentName));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
