package artifacts;

import java.util.HashMap;

import classes.Spot;
import classes.SpotOffer;

public class BlackBoard_Controller {
	
	
	protected SpotOffer getCheaperSpot(HashMap<String, Spot> spotHash) {

		Spot maxSpot = new Spot("minimum_spot", 1000, 1001),minSpot = null;
		SpotOffer offer = null;
			for (String s : spotHash.keySet()) {
				if (spotHash.get(s).isAvailable() && spotHash.get(s).getStatusSensor()) {
					Spot spot = spotHash.get(s);
					if(spot.getPrice() < maxSpot.getPrice()){
						minSpot = spot;						
					}
				}
			}
			
		if(minSpot != null){
			offer = new SpotOffer(minSpot.getLocation(),minSpot.getSpotAgent(),minSpot.getTrust(),minSpot.getPrice());
			spotHash.get(minSpot.getSpotAgent()).setAvailable(false);
			return offer;
		}
		else
			return null;
		
	}
	
	protected void freeSpot(HashMap<String,Spot> spotHash,String spotAgentName){
		
		Spot spot = spotHash.get(spotAgentName);
		
		spot.setAvailable(true);		
		spot.setPrice(spotHash.get(spotAgentName).getPrice() + 1);
		spot.setDriverAgent("");
		
		
	}

}
