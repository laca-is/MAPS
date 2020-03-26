package mapsServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
	public static void main(String[] args) {
	
		//saveProtocol save = new saveProtocol();
		
		// Start simulation in a Thread
		SumoConnection st = new SumoConnection();
		Thread threadSumo = new Thread(st);
		threadSumo.start();
		
		// Creates a server to communicate with MAPS (Port 1234)
		try {
			ServerSocket srvr = new ServerSocket(1234);
			
			// Infinity loop waiting for connections
			while (true){
				Socket skt = srvr.accept();
				
				BufferedReader in = new BufferedReader(new InputStreamReader(skt.getInputStream()));
	
				while (!in.ready()) {}
				// Read protocol
		        String protocol = in.readLine();
		        
		        // Saves protocol
		        //save.saveProtocolFile(protocol, "protocolMiddleware.txt");
		        
		        // Creates message from protocol
		        Message message = new Message(protocol);
		        
		        // Send the message to SUMO
		        st.messageQueue.put(message);
		        
		        // 
		        if(protocol.equals("end")){
		        	skt.close();
		        	srvr.close();
		        }
			}
		}
		catch(Exception e) {
			System.out.println("Server has disconnected!");
			e.printStackTrace();
		}
	}
}