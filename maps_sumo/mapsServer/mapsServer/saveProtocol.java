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

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class saveProtocol {

	public void saveProtocolFile(String protocol, String file){
		String fileName = "C:\\Users\\alexi\\Documents\\TCC\\TCC_2\\experimentosFinal\\" + file;
		
		try{
			FileWriter fileWriter =
	                new FileWriter(fileName, true);

	            BufferedWriter bufferedWriter =
	                new BufferedWriter(fileWriter);
	            bufferedWriter.write(protocol);
                bufferedWriter.newLine();
                bufferedWriter.close();
			
			} catch (IOException ex) {
				System.out.println(
		                "Error writing to file '"
		                + fileName + "'");
		            ex.printStackTrace();
			}
	}
	
}
