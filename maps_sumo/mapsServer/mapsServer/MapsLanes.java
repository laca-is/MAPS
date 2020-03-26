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

public class MapsLanes {
	
	/* 
	 * Cada via (rua) no SUMO possui uma ou mais faixas
	 * Esta classe retorna qual a faixa referente à vaga em que o veículo estacionará / deixará
	 */
	
	public String areaA(String spot){
		if(spot.equals("spotA0")){
			return "slot0-0l_0";
		}else if(spot.equals("spotA1")){
			return "slot0-1l_0";
		}else if(spot.equals("spotA2")){
			return "slot0-2l_0";
		}else if(spot.equals("spotA3")){
			return "slot0-3l_0";
		}else if(spot.equals("spotA4")){
			return "slot0-4l_0";
		}else if(spot.equals("spotA5")){
			return "slot0-5l_0";
		}else if(spot.equals("spotA6")){
			return "slot0-6l_0";
		}else if(spot.equals("spotA7")){
			return "slot0-7l_0";
		}else if(spot.equals("spotA8")){
			return "slot0-8l_0";
		}else if(spot.equals("spotA9")){
			return "slot0-9l_0";
		}
		return spot;
	}
	
	public String areaB(String spot){
		if(spot.equals("spotB0")){
			return "slot1-0l_0";
		}else if(spot.equals("spotB1")){
			return "slot1-1l_0";
		}else if(spot.equals("spotB2")){
			return "slot1-2l_0";
		}else if(spot.equals("spotB3")){
			return "slot1-3l_0";
		}else if(spot.equals("spotB4")){
			return "slot1-4l_0";
		}else if(spot.equals("spotB5")){
			return "slot1-5l_0";
		}else if(spot.equals("spotB6")){
			return "slot1-6l_0";
		}else if(spot.equals("spotB7")){
			return "slot1-7l_0";
		}else if(spot.equals("spotB8")){
			return "slot1-8l_0";
		}else if(spot.equals("spotB9")){
			return "slot1-9l_0";
		}else if(spot.equals("spotB10")){
			return "slot0-0r_0";
		}else if(spot.equals("spotB11")){
			return "slot0-1r_0";
		}else if(spot.equals("spotB12")){
			return "slot0-2r_0";
		}else if(spot.equals("spotB13")){
			return "slot0-3r_0";
		}else if(spot.equals("spotB14")){
			return "slot0-4r_0";
		}else if(spot.equals("spotB15")){
			return "slot0-5r_0";
		}else if(spot.equals("spotB16")){
			return "slot0-6r_0";
		}else if(spot.equals("spotB17")){
			return "slot0-7r_0";
		}else if(spot.equals("spotB18")){
			return "slot0-8r_0";
		}else if(spot.equals("spotB19")){
			return "slot0-9r_0";
		}
		return spot;
	}
	
	public String areaC(String spot){
		if(spot.equals("spotC0")){
			return "slot2-0l_0";
		}else if(spot.equals("spotC1")){
			return "slot2-1l_0";
		}else if(spot.equals("spotC2")){
			return "slot2-2l_0";
		}else if(spot.equals("spotC3")){
			return "slot2-3l_0";
		}else if(spot.equals("spotC4")){
			return "slot2-4l_0";
		}else if(spot.equals("spotC5")){
			return "slot2-5l_0";
		}else if(spot.equals("spotC6")){
			return "slot2-6l_0";
		}else if(spot.equals("spotC7")){
			return "slot2-7l_0";
		}else if(spot.equals("spotC8")){
			return "slot2-8l_0";
		}else if(spot.equals("spotC9")){
			return "slot2-9l_0";
		}else if(spot.equals("spotC10")){
			return "slot1-0r_0";
		}else if(spot.equals("spotC11")){
			return "slot1-1r_0";
		}else if(spot.equals("spotC12")){
			return "slot1-2r_0";
		}else if(spot.equals("spotC13")){
			return "slot1-3r_0";
		}else if(spot.equals("spotC14")){
			return "slot1-4r_0";
		}else if(spot.equals("spotC15")){
			return "slot1-5r_0";
		}else if(spot.equals("spotC16")){
			return "slot1-6r_0";
		}else if(spot.equals("spotC17")){
			return "slot1-7r_0";
		}else if(spot.equals("spotC18")){
			return "slot1-8r_0";
		}else if(spot.equals("spotC19")){
			return "slot1-9r_0";
		}
		return spot;
	}
	
	public String areaD(String spot){
		if(spot.equals("spotD0")){
			return "slot3-0l_0";
		}else if(spot.equals("spotD1")){
			return "slot3-1l_0";
		}else if(spot.equals("spotD2")){
			return "slot3-2l_0";
		}else if(spot.equals("spotD3")){
			return "slot3-3l_0";
		}else if(spot.equals("spotD4")){
			return "slot3-4l_0";
		}else if(spot.equals("spotD5")){
			return "slot3-5l_0";
		}else if(spot.equals("spotD6")){
			return "slot3-6l_0";
		}else if(spot.equals("spotD7")){
			return "slot3-7l_0";
		}else if(spot.equals("spotD8")){
			return "slot3-8l_0";
		}else if(spot.equals("spotD9")){
			return "slot3-9l_0";
		}else if(spot.equals("spotD10")){
			return "slot2-0r_0";
		}else if(spot.equals("spotD11")){
			return "slot2-1r_0";
		}else if(spot.equals("spotD12")){
			return "slot2-2r_0";
		}else if(spot.equals("spotD13")){
			return "slot2-3r_0";
		}else if(spot.equals("spotD14")){
			return "slot2-4r_0";
		}else if(spot.equals("spotD15")){
			return "slot2-5r_0";
		}else if(spot.equals("spotD16")){
			return "slot2-6r_0";
		}else if(spot.equals("spotD17")){
			return "slot2-7r_0";
		}else if(spot.equals("spotD18")){
			return "slot2-8r_0";
		}else if(spot.equals("spotD19")){
			return "slot2-9r_0";
		}
		return spot;
	}
	
	public String areaE(String spot){
		if(spot.equals("spotE0")){
			return "slot4-0l_0";
		}else if(spot.equals("spotE1")){
			return "slot4-1l_0";
		}else if(spot.equals("spotE2")){
			return "slot4-2l_0";
		}else if(spot.equals("spotE3")){
			return "slot4-3l_0";
		}else if(spot.equals("spotE4")){
			return "slot4-4l_0";
		}else if(spot.equals("spotE5")){
			return "slot4-5l_0";
		}else if(spot.equals("spotE6")){
			return "slot4-6l_0";
		}else if(spot.equals("spotE7")){
			return "slot4-7l_0";
		}else if(spot.equals("spotE8")){
			return "slot4-8l_0";
		}else if(spot.equals("spotE9")){
			return "slot4-9l_0";
		}else if(spot.equals("spotE10")){
			return "slot3-0r_0";
		}else if(spot.equals("spotE11")){
			return "slot3-1r_0";
		}else if(spot.equals("spotE12")){
			return "slot3-2r_0";
		}else if(spot.equals("spotE13")){
			return "slot3-3r_0";
		}else if(spot.equals("spotE14")){
			return "slot3-4r_0";
		}else if(spot.equals("spotE15")){
			return "slot3-5r_0";
		}else if(spot.equals("spotE16")){
			return "slot3-6r_0";
		}else if(spot.equals("spotE17")){
			return "slot3-7r_0";
		}else if(spot.equals("spotE18")){
			return "slot3-8r_0";
		}else if(spot.equals("spotE19")){
			return "slot3-9r_0";
		}
		return spot;
	}
	
	public String areaF(String spot){
		if(spot.equals("spotF0")){
			return "slot5-0l_0";
		}else if(spot.equals("spotF1")){
			return "slot5-1l_0";
		}else if(spot.equals("spotF2")){
			return "slot5-2l_0";
		}else if(spot.equals("spotF3")){
			return "slot5-3l_0";
		}else if(spot.equals("spotF4")){
			return "slot5-4l_0";
		}else if(spot.equals("spotF5")){
			return "slot5-5l_0";
		}else if(spot.equals("spotF6")){
			return "slot5-6l_0";
		}else if(spot.equals("spotF7")){
			return "slot5-7l_0";
		}else if(spot.equals("spotF8")){
			return "slot5-8l_0";
		}else if(spot.equals("spotF9")){
			return "slot5-9l_0";
		}else if(spot.equals("spotF10")){
			return "slot4-0r_0";
		}else if(spot.equals("spotF11")){
			return "slot4-1r_0";
		}else if(spot.equals("spotF12")){
			return "slot4-2r_0";
		}else if(spot.equals("spotF13")){
			return "slot4-3r_0";
		}else if(spot.equals("spotF14")){
			return "slot4-4r_0";
		}else if(spot.equals("spotF15")){
			return "slot4-5r_0";
		}else if(spot.equals("spotF16")){
			return "slot4-6r_0";
		}else if(spot.equals("spotF17")){
			return "slot4-7r_0";
		}else if(spot.equals("spotF18")){
			return "slot4-8r_0";
		}else if(spot.equals("spotF19")){
			return "slot4-9r_0";
		}
		return spot;
	}
	
	public String areaG(String spot){
		if(spot.equals("spotG0")){
			return "slot6-0l_0";
		}else if(spot.equals("spotG1")){
			return "slot6-1l_0";
		}else if(spot.equals("spotG2")){
			return "slot6-2l_0";
		}else if(spot.equals("spotG3")){
			return "slot6-3l_0";
		}else if(spot.equals("spotG4")){
			return "slot6-4l_0";
		}else if(spot.equals("spotG5")){
			return "slot6-5l_0";
		}else if(spot.equals("spotG6")){
			return "slot6-6l_0";
		}else if(spot.equals("spotG7")){
			return "slot6-7l_0";
		}else if(spot.equals("spotG8")){
			return "slot6-8l_0";
		}else if(spot.equals("spotG9")){
			return "slot6-9l_0";
		}else if(spot.equals("spotG10")){
			return "slot5-0r_0";
		}else if(spot.equals("spotG11")){
			return "slot5-1r_0";
		}else if(spot.equals("spotG12")){
			return "slot5-2r_0";
		}else if(spot.equals("spotG13")){
			return "slot5-3r_0";
		}else if(spot.equals("spotG14")){
			return "slot5-4r_0";
		}else if(spot.equals("spotG15")){
			return "slot5-5r_0";
		}else if(spot.equals("spotG16")){
			return "slot5-6r_0";
		}else if(spot.equals("spotG17")){
			return "slot5-7r_0";
		}else if(spot.equals("spotG18")){
			return "slot5-8r_0";
		}else if(spot.equals("spotG19")){
			return "slot5-9r_0";
		}
		return spot;
	}
	
	public String areaH(String spot){
		if(spot.equals("spotH0")){
			return "slot7-0l_0";
		}else if(spot.equals("spotH1")){
			return "slot7-1l_0";
		}else if(spot.equals("spotH2")){
			return "slot7-2l_0";
		}else if(spot.equals("spotH3")){
			return "slot7-3l_0";
		}else if(spot.equals("spotH4")){
			return "slot7-4l_0";
		}else if(spot.equals("spotH5")){
			return "slot7-5l_0";
		}else if(spot.equals("spotH6")){
			return "slot7-6l_0";
		}else if(spot.equals("spotH7")){
			return "slot7-7l_0";
		}else if(spot.equals("spotH8")){
			return "slot7-8l_0";
		}else if(spot.equals("spotH9")){
			return "slot7-9l_0";
		}else if(spot.equals("spotH10")){
			return "slot6-0r_0";
		}else if(spot.equals("spotH11")){
			return "slot6-1r_0";
		}else if(spot.equals("spotH12")){
			return "slot6-2r_0";
		}else if(spot.equals("spotH13")){
			return "slot6-3r_0";
		}else if(spot.equals("spotH14")){
			return "slot6-4r_0";
		}else if(spot.equals("spotH15")){
			return "slot6-5r_0";
		}else if(spot.equals("spotH16")){
			return "slot6-6r_0";
		}else if(spot.equals("spotH17")){
			return "slot6-7r_0";
		}else if(spot.equals("spotH18")){
			return "slot6-8r_0";
		}else if(spot.equals("spotH19")){
			return "slot6-9r_0";
		}
		return spot;
	}
	
	public String areaI(String spot){
		if(spot.equals("spotI0")){
			return "slot7-0r_0";
		}else if(spot.equals("spotI1")){
			return "slot7-1r_0";
		}else if(spot.equals("spotI2")){
			return "slot7-2r_0";
		}else if(spot.equals("spotI3")){
			return "slot7-3r_0";
		}else if(spot.equals("spotI4")){
			return "slot7-4r_0";
		}else if(spot.equals("spotI5")){
			return "slot7-5r_0";
		}else if(spot.equals("spotI6")){
			return "slot7-6r_0";
		}else if(spot.equals("spotI7")){
			return "slot7-7r_0";
		}else if(spot.equals("spotI8")){
			return "slot7-8r_0";
		}else if(spot.equals("spotI9")){
			return "slot7-9r_0";
		}
		return spot;
	}
	
	
	
	public String getLane(String spot){
		
		// Gets the parking area
		String area = spot.substring(spot.indexOf("t")+1, spot.indexOf("t")+2);

		switch (area) {
			case "A":
				return areaA(spot);
			case "B":
				return areaB(spot);
			case "C":
				return areaC(spot);
			case "D":
				return areaD(spot);
			case "E":
				return areaE(spot);
			case "F":
				return areaF(spot);
			case "G":
				return areaG(spot);
			case "H":
				return areaH(spot);
			case "I":
				return areaI(spot);
			default:
				return "";
		}
			
	}
	
	/*
	public static void main(String[] args){
		getLane("spotB10");
	}*/

}
