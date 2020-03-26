/*
 * SERVER MAPS
 * */

package it.polito.appeal.traci.examples;

import java.io.*;
import java.net.*;

public class TCPServer {
	public static void main(String args[]) {
		
		System.out.println("--- SERVER WAITING FOR CLIENTS ---");
		
		try {
			ServerSocket srvr = new ServerSocket(1234);
	        
			while(true){
				Socket skt = srvr.accept();
		        
		        System.out.println("\nClient has connected!");
		        
		        BufferedReader in = new BufferedReader(new InputStreamReader(skt.getInputStream()));
		        System.out.print("Received message - ");

		        while (!in.ready()) {}
		        String out = in.readLine();
		        System.out.println(out);
		        
		        if(out.equals("end")){
		        	skt.close();
		        	srvr.close();
		        }
			}
	     }catch(Exception e) {
	    	System.out.println("Server has disconnected!");
	     }
	}
}
