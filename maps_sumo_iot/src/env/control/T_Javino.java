package control;

import artifacts.A_SensorConnect;
import br.pro.turing.javino.Javino;

public class T_Javino implements Runnable {

	public Javino javino;
	public String port;
	public A_SensorConnect sensorConnect;
	
	public T_Javino(Javino javino, String port, A_SensorConnect sensorConnect) {
		this.javino = javino;
		this.port = port;
		this.sensorConnect = sensorConnect;
	}

	@Override
	public void run() {
		while (javino.listenArduino(port)) {
			try{
			this.sensorConnect.msg = javino.getData();
			this.sensorConnect.receiveMsg();
			}catch(Exception e){
				System.err.println("Error to get the sensor data!");
				e.printStackTrace();
			}
			
		}

	}

}
