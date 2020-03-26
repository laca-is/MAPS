/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package it.polito.appeal.traci.examples;

import it.polito.appeal.traci.SumoTraciConnection;
import it.polito.appeal.traci.Vehicle;

import java.util.Collection;

/**
 *
 * @author alexi
 */
public class Teste {
    public static void main(String[] args) {
		SumoTraciConnection conn = new SumoTraciConnection(
				//"C:\\Users\\alexi\\Documents\\TCC\\sumo\\sumo-0.25.0\\docs\\tutorial\\city_mobil\\park10.sumocfg",  // config file
				//"C:\\Users\\alexi\\Documents\\TCC\\TCC_2\\teste\\parkingTeste.sumocfg",
				"C:\\Users\\alexi\\Documents\\TCC\\TCC_2\\teste\\parking.sumocfg",
				8883                                  // random seed
				);
		try {
			conn.runServer();
			
			System.out.println(conn.getVehicleRepository().getAll());
			
			// the first two steps of this simulation have no vehicles.
			
			for (int i = 1 ; i < 55 ; i ++){
				conn.nextSimStep();
			}
			
			Collection<Vehicle> vehicles = conn.getVehicleRepository().getAll().values();
			
			System.out.println(conn.getVehicleRepository().getAll());
			System.out.println(conn.getVehicleRepository().getIDs());
			
			for(Vehicle v : vehicles){
				System.out.println("Vehicle " + v
						+ " will traverse these edges: "
						+ v.getCurrentRoute());
			}
			
			conn.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
