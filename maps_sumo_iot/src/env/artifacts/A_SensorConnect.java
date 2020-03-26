// CArtAgO artifact code for project mAPS_SUMO

package artifacts;

import java.util.Map.Entry;

import br.pro.turing.javino.Javino;
import cartago.Artifact;
import cartago.OPERATION;
import control.DBControl;
import control.T_Javino;
import model.PSpace;
import model.Sensor;

public class A_SensorConnect extends Artifact {

	Thread thread;
	T_Javino tJavino;

	public String msg;

	private String rawMsg[];
	private Javino javino;
	private String port = "/dev/ttyACM0";
	private Sensor sensor;

	void init() {
		javino = new Javino();
		tJavino = new T_Javino(javino, port, this);
		thread = new Thread(tJavino);

	}

	@OPERATION
	void startConnection() {
		System.out.println("Iniciando conexao!!!!!!!!!!");
		thread.start();
	}

	public void receiveMsg() {
		System.out.println("recebi msg!");
		rawMsg = msg.split(",");
		for (int i = 0; i < rawMsg.length; i++) {

			if (Integer.valueOf(rawMsg[i]) < 30 && Integer.valueOf(rawMsg[i]) >= 1) {
				sensor = DBControl.getPSpace("pspace_".concat(String.valueOf(i))).psSensor;
				sensor.setValue(Float.valueOf(rawMsg[i]));
				System.out.println("Sensor(" + i + "): " + rawMsg[i]);
			}
		}
	}

	@OPERATION
	public synchronized void checkSensorChanges() {
		for (Entry<String, PSpace> entry : DBControl.hashPSpaces.entrySet()) {
			if (((entry.getValue().psSensor.getValue() - entry.getValue().psSensor.getOldValue()) > 10)
					|| (entry.getValue().psSensor.getOldValue() - entry.getValue().psSensor.getValue() > 10)) {
				entry.getValue().psSensor.setUsing(!entry.getValue().psSensor.isUsing());
				entry.getValue().setFree(!entry.getValue().isFree());
				System.out.println(entry.getKey() + " - " + entry.getValue().psSensor.getAgentName() + "("
						+ entry.getValue().psSensor.getValue() + "," + entry.getValue().psSensor.getOldValue() + ") - "
						+ "Signal has changed!!!!!!!!!");
				signal("statusChanged", entry.getKey());
			}
		}
	}

	@OPERATION
	void closeConnection() {

	}
}
