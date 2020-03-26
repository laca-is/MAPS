/*   
    Copyright (C) 2013 ApPeAL Group, Politecnico di Torino

    This file is part of TraCI4J.

    TraCI4J is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    TraCI4J is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with TraCI4J.  If not, see <http://www.gnu.org/licenses/>.
*/

package it.polito.appeal.traci.examples;

import it.polito.appeal.traci.Lane;
import it.polito.appeal.traci.Repository;
import it.polito.appeal.traci.Route;
import it.polito.appeal.traci.SumoTraciConnection;
import it.polito.appeal.traci.Vehicle;
import it.polito.appeal.traci.VehicleType;

import java.awt.Color;
/**
 * This code runs an instance of SUMO, queries the map bounds and does ten
 * simulation steps. For each step, it prints which and how many vehicles are
 * active.
 * <p>
 * The specified configuration file is relative to the TraCI4J package's base
 * directory.
 * 
 * @author Enrico Gueli &lt;enrico.gueli@polito.it&gt;
 * 
 */
public class GetVehicleInfo {
	
	public static void main(String[] args) {
		
		SumoTraciConnection conn = new SumoTraciConnection(
				"C:\\Users\\alexi\\Documents\\TCC\\TCC_2\\teste\\parking.sumocfg",  // config file
				8883                                  // random seed
				);
		try {
			Repository<Vehicle> vehicles;
			Repository<VehicleType> types;
			Repository<Route> routes;
			Repository<Lane> lanes;
			
			// Inicializa SUMO com interface gráfica
			conn.runServer(true);
			
			// the first two steps of this simulation have no vehicles.
			conn.nextSimStep();
			
			int i = 0;
			while (i < 100){
				conn.nextSimStep();
				
				vehicles = conn.getVehicleRepository();
				types = conn.getVehicleTypeRepository();
				routes = conn.getRouteRepository();
				lanes = conn.getLaneRepository();
				
				//System.out.println("STEP:["+ conn.getCurrentSimTime() +"]" + "\nVEHICLES: " + vehicles + "\nTYPES: " + types + "\nROUTES: " + routes + "\nLANES: " + lanes);
				//System.out.println(types.getIDs());
				System.out.println(routes.getIDs());
				//System.out.println(lanes.getIDs());
				
				if (i == 10){
					//conn.getVehicleRepository().getByID("a1").changeColor(color);
					/*System.out.println(types.getByID("DEFAULT_VEHTYPE"));
					System.out.println(routes.getByID("!35"));
					System.out.println(lanes.getByID("footmainin_0"));
					System.out.println(lanes.getIDs());*/
					
					conn.queryAddVehicle().setVehicleData("spotA0", 
							types.getByID("car"),
							routes.getByID("spotA0"), 
							lanes.getByID("footmainin_0"), 
							0, 
							0,
							0);
					conn.queryAddVehicle().run();
				}
								
				System.out.println("Vehicle " + vehicles.getAll().values());
				
				i++;
			}
			conn.close();
		}
		catch(Exception e) {
			System.out.println("Server has disconnected!");
			e.printStackTrace();
		}
	}
}
