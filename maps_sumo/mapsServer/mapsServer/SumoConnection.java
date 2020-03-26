package mapsServer;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import it.polito.appeal.traci.Edge;
import it.polito.appeal.traci.Lane;
import it.polito.appeal.traci.Repository;
import it.polito.appeal.traci.Route;
import it.polito.appeal.traci.SumoTraciConnection;
import it.polito.appeal.traci.Vehicle;
import it.polito.appeal.traci.VehicleType;

public class SumoConnection implements Runnable{

	// Message queue
	BlockingQueue<Message> messageQueue = new LinkedBlockingQueue<Message>();
	// Drivers queue
	List<String> driverQueue = new ArrayList<String>();
	// Current drivers in the parking
	List<String> driverList = new ArrayList<String>();
	// Lanes
	MapsLanes mapsLanes = new MapsLanes();
	
	public void run(){
		// Communication between TraCI and SUMO
		SumoTraciConnection conn = new SumoTraciConnection( "C:\\Users\\alexi\\Documents\\TCC\\TCC_2\\teste\\parking.sumocfg", 8883);
		
		try {
			Message message = null;
			
			// Initiate SUMO graphics
			conn.runServer(true);

			// Get repositories from simulation
			Repository<VehicleType> types = conn.getVehicleTypeRepository();
			Repository<Route> routes = conn.getRouteRepository();
			Repository<Lane> lanes = conn.getLaneRepository();
			Repository<Edge> edges = conn.getEdgeRepository();
			
			// Infinity loop reading messages
			while (true){
				// Get message from poll
				message = messageQueue.poll();
				
				if(message != null){
					
					// Type 1: Vehicle entering the parking
					if(message.getType() == 1){
						
						// Verify if the driver is in the queue (enquanto não tem fila de espera implementada)
						if(driverQueue.contains(message.getDriver())){
							System.out.println("Driver " + message.getDriver() + " leaving queue and parking at: " + message.getSpot());
							driverQueue.remove(message.getDriver());
						}else{
							System.out.println("Driver " + message.getDriver() + " parking at: " + message.getSpot());
						}

						// Avoid duplicate vehicles (MAPS as vezes aloca um mesmo veículo em uma ou mais vagas. Feito tratamento interno.)
						if(!driverList.contains(message.getDriver())){
							
							// Cria query o qual define veículo a ser inserido
							conn.queryAddVehicle().setVehicleData(message.getDriver(), 
									types.getByID("car"),
									routes.getByID(message.getSpot()), 
									lanes.getByID("mainin_0"), 
									conn.getCurrentSimTime(),
									0,
									0);
							// Executa query
							conn.queryAddVehicle().run();
							// Adiciona veículo na lista interna
							driverList.add(message.getDriver());
						}else{
							System.out.println("Driver " + message.getDriver() + " already exists. Ignored.");
						}
						
						// Advance simulation step
				        conn.nextSimStep();
						
				        // Troca a cor do veículo
				        conn.getVehicleRepository().getByID(message.getDriver()).queryChangeColor().setValue(message.getColor());
				        conn.getVehicleRepository().getByID(message.getDriver()).queryChangeColor().run();
						
					// Type 2: Vehicle leaving the parking
					}else if(message.getType() == 2){
						System.out.println("Driver " + message.getDriver() + " leaving " + message.getSpot());
						
						// Get vehicle
						Vehicle vehicle = conn.getVehicleRepository().getByID(message.getDriver());
	
						// Remove vehicle from simulation
						if(vehicle != null){
							conn.queryRemoveVehicle().setVehicleData(vehicle, 1);
							conn.queryRemoveVehicle().run();
						}else{
							System.out.println("Veículo " + message.getDriver() + " inexistente!");
						}
						
						
						// Remove vehicle from current drivers list
						driverList.remove(message.getDriver());
						
						// Get the spot's lane
						String lane = mapsLanes.getLane(message.getSpot());
						
						// Add new vehicle in the exit route
						conn.queryAddVehicle().setVehicleData(message.getDriver() + "_out", 
								types.getByID("car"),
								routes.getByID(message.getSpot() + "_out"),
								lanes.getByID(lane),
								conn.getCurrentSimTime(),
								0,
								0);
						conn.queryAddVehicle().run();
						
						// Advance simulation step
				        conn.nextSimStep();
					// Type 3: Driver is in the queue
					}else if(message.getType() == 3){
						System.out.println("Driver " + message.getDriver() + " is in the queue.");
						driverQueue.add(message.getDriver());
						
						// Advance simulation step
				        conn.nextSimStep();
					}	
				}else{
					// Advance simulation step
			        conn.nextSimStep();
				}
			}			
		}
		catch(Exception e) {
			System.out.println("Server has disconnected!");
			e.printStackTrace();
		}
	}
}
