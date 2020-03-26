/*   
    Copyright (C) 2016 ApPeAL Group, Politecnico di Torino

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

package mapsServer;

import java.io.*;
import java.util.Random;

public class CreateAgents {

	public static int getRandom(int[] array) {
	    int rnd = new Random().nextInt(array.length);
	    return array[rnd];
	}
	
	public static void createAgent(){
        String fileName = "C:\\Users\\alexi\\Documents\\TCC\\TCC_2\\experimentos\\agents4-MeioSegundo.txt";

        try {
            FileWriter fileWriter =
                new FileWriter(fileName);

            BufferedWriter bufferedWriter =
                new BufferedWriter(fileWriter);
            
            int trust, TS,
            	// Number of agents to be created
            	nAgents = 15,
            	// Vehicles starts to arrive after 1 second. (TA > 0)
            	TA = 1000; 
            
            // Vector that contains "times to spend", in milliseconds
            int[] vecTS = new int[]{ 30000, 25000, 60000, 37000, 20000, 20000, 120000, 60000, 12000, 12000},
                  vecTS2 = new int[]{ 20000, 20000, 25000, 30000, 35000, 60000, 60000, 120000, 120000 };
            
            Random rand = new Random();

            for (int i = 1;  i < nAgents+1 ; i++){
            	
            	// Select a random trust between 500 and 50
            	trust = 50 + rand.nextInt((500 - 50) + 1);
            	// Select a random time to spend
            	TS = getRandom(vecTS2);
            	
            	bufferedWriter.write("\tagent d" + i + ": driver.asl {");
                bufferedWriter.newLine();
                bufferedWriter.write("\t\t beliefs: myTrust(" + trust + ")");
                bufferedWriter.newLine();
                bufferedWriter.write("\t\t\t\t timeToSpend(" + TS + ")");
                bufferedWriter.newLine();
                bufferedWriter.write("\t\t\t\t timeToArrive(" + TA + ")");
                bufferedWriter.newLine();
                bufferedWriter.write("\t}");;
                bufferedWriter.newLine();
                
                // Next agent in 0.50 seconds
                TA += 500;
                System.out.println("Agent d" + i + " created. Trust: " + trust + ". Time to spend: " + TS + " Time to arrive: " + TA);
            }

            bufferedWriter.close();
        }
        catch(IOException ex) {
            System.out.println(
                "Error writing to file '"
                + fileName + "'");
            ex.printStackTrace();
        }
	}
	
	public static void main(String[] args) {
		createAgent();    
	}

}
