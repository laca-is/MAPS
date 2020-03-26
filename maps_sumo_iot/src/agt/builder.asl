
!buildSystem.

+!buildSystem <-
	.print("Building the system...");
	
	makeArtifact("A_SUMOConnect","artifacts.A_SUMOConnect",[],_);
	makeArtifact("A_PSControl","artifacts.A_PSControl",[],_);
	
	makeArtifact("A_SensorConnect","artifacts.A_SensorConnect",[],ART_SENSOR);
	focus(ART_SENSOR);
	
	
	makeArtifact("A_StructureInfo","artifacts.A_StructureInfo",[],ART_STR);
	focus(ART_STR);
	
	setStatusSystem(true);
	.print("Buit!");
	
	.wait(7000);	
	startConnection;
	!!checkSensors.
	
+!checkSensors <-
	checkSensorChanges;
	.wait(1000);
	!checkSensors.
	
	


{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }

// uncomment the include below to have an agent compliant with its organisation
//{ include("$moiseJar/asl/org-obedient.asl") }
