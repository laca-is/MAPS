package artifacts;

import cartago.Artifact;
import cartago.OPERATION;
import cartago.ObsProperty;

public class Gate extends Artifact{
	ObsProperty property;

	void init() {		
		defineObsProperty("isOpen",false);
		property = getObsProperty("isOpen");
	}

	@OPERATION
	public void openGate(){
		property.updateValue(true);
		System.out.println("Opening gate!");
	}
	
	@OPERATION
	public void closeGate(){
		property.updateValue(false);
		System.out.println("Closing gate!");
	}
}
