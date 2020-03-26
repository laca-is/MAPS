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

import java.awt.Color;

public class Message {
	private int type;
	private String driver;
	private String spot;
	private String protocol;
	private Color color;
	
	public Message(String message) {
		this.type = setType(message);
		this.driver = setDriver(message);
		this.spot = setSpot(message);
		this.protocol = setProtocol(message);
		this.color = setColor(message);
	}
	
	public Color getColor(){
		return this.color;
	}
	
	public int getType(){
		return this.type;
	}
	
	public String getDriver(){
		return this.driver;
	}
	
	public String getSpot(){
		return this.spot;
	}
	
	public String getProtocol(){
		return this.protocol;
	}
	
	public String setProtocol(String message){
		return message;
	}
	
	// Transforma tipo do protocolo em tipo da mensagem
	public int setType(String message){
		int type;
		
		if(message.contains("PS")){
			type = 1;
		}else if(message.contains("LS")){
			type = 2;
		}else{
			type = 3;
		}
		
		return type;
	}
	
	// Retorna cor baseado na reputação
	public Color getColorTrust(int trust){
		
		if(trust <= 100){
			return new Color(255, 0, 0);
		}else if((trust > 100) && (trust <= 200)){
			return new Color(0, 255, 0);
		}else if((trust > 200) && (trust <= 300)){
			return new Color(0, 0, 255);
		}else if((trust > 300) && (trust <= 400)){
			return new Color(255, 255, 0);
		}else{
			return new Color(255, 255, 255);
		}
	}
	
	// Define cor do agente
	public Color setColor(String message){
		
		int i = message.indexOf("TR");
		
		if(i != -1){
			int trust = Integer.parseInt(message.substring(i + 2, message.length()));
			return getColorTrust(trust);
		}else{
			return new Color(255, 0, 255);
		}
		
	}
	
	// Define ID do agente
	public String setDriver(String message){
		String driver = "";
		
		int iPS = message.indexOf("PS"),
		    iLS = message.indexOf("LS"),
		    iQU = message.indexOf("QU");
		
		if(iPS != -1){
			driver = message.substring(1, iPS);
		}else if (iLS != -1){
			driver = message.substring(1, iLS);
		}else{
			driver = message.substring(1, iQU);
		}
		
		return driver;
	}

	// Define vaga referente ao protocolo
	public String setSpot(String message){
		String spot = "";
		
		int iPS = message.indexOf("PS"),
		    iLS = message.indexOf("LS"),
		    iTR = message.indexOf("TR");
		
		if(iTR != -1){
			if(iPS != -1){
				spot = message.substring(iPS+2, iTR);
			}else if(iLS != -1){
				spot = message.substring(iLS+2, iTR);
			}else{
				spot = "";
			}
		}else{
			if(iPS != -1){
				spot = message.substring(iPS+2, message.length());
			}else if(iLS != -1){
				spot = message.substring(iLS+2, message.length());
			}else{
				spot = "";
			}
		}
		
		return spot;
	}
}
