// CArtAgO artifact code for project mAPS_2

package artifacts;

import java.util.HashMap;

import cartago.Artifact;
import cartago.OPERATION;
import cartago.OpFeedbackParam;
import classes.Spot;
import classes.SpotOffer;

public class Blackboard extends Artifact {
	
	
	HashMap <String,Spot> spotHash; 
	BlackBoard_Controller bController;
	
	
	
	void init() {
		spotHash = new HashMap<String, Spot>();
		bController = new BlackBoard_Controller();
	}

	
	@OPERATION
	void registerSpot(String agentName, double spotPrice,int spotTrust){
		
		Spot spot = new Spot(agentName,spotPrice,spotTrust);
		spotHash.put(agentName, spot);
		//System.out.println(spot);
	}
	@OPERATION
	void requestSpot(String driverAgentName, OpFeedbackParam<Object > isAccepted, OpFeedbackParam<Object> price, 
				OpFeedbackParam<Object> trust, OpFeedbackParam<Object> location, OpFeedbackParam<Object > spotAgent){
		
		SpotOffer offer = bController.getCheaperSpot(spotHash);
		
		if(offer != null){
			isAccepted.set(true);
			price.set(offer.getPrice());
			trust.set(offer.getTrust());
			location.set(offer.getLocation());
			spotAgent.set(offer.getSpotAgentName());
		}
		else
			isAccepted.set(false);	
	}
	
	@OPERATION
	void assignSpot(String driverAgentName, String spotAgentName){
		spotHash.get(spotAgentName).setSpotAgent(driverAgentName);
		
	}
	
	@OPERATION
	void freeSpot(String spotLocation){
		
		String spotAgentName = "spot".concat(spotLocation);		
		bController.freeSpot(spotHash, spotAgentName);
		System.out.println("The Spot (" + spotAgentName + ") has been cleaned!");
	}
	
	@SuppressWarnings("unused")
	private void printSpots(){
		spotHash.forEach((k,v) -> {
			System.out.println("Spot: " + k + "\nInfo: "+(Spot)v);
		});
	}
}

