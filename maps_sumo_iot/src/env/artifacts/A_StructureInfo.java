// CArtAgO artifact code for project mAPS_SUMO

package artifacts;

import cartago.*;

public class A_StructureInfo extends Artifact {
	void init() {
		defineObsProperty("builtSystem", false);
	}

	@OPERATION
	void setStatusSystem(boolean status){
		ObsProperty obsProp = getObsProperty("builtSystem");
		obsProp.updateValue(status);		
	}
}

