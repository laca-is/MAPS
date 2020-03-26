package control;

import it.polito.appeal.traci.SumoTraciConnection;

public class T_TraCI_SUMO implements Runnable {

	SumoTraciConnection conn;
	int cont = 0;

	public T_TraCI_SUMO(SumoTraciConnection connection) {
		conn = connection;
	}

	public void run() {
		startSimulation();
	}

	public void startSimulation() {
		while (true) {
			try {
				cont++;
				conn.do_timestep();
				Thread.sleep(20);
//
//				if (cont == 300)
//					conn.do_job_set(Vehicle.changeTarget("v2", "gneE12"));

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
	
	

}
