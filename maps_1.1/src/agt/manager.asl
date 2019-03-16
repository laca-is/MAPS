// Agent manager in project mAPS_2


!startParking.


+!startParking : true <-
	makeArtifact("a_Blackboard","artifacts.Blackboard",[],ArtId);
	focus(ArtId);
	
	makeArtifact("a_Gate","artifacts.Gate",[],ArtId2);
	focus(ArtId2);
	
	.print("The Smart Parking has been opened!").
	

+!paymentSpot(MONEY,LOCATION)[source(AG)] <-
	.print("Payment ($",MONEY,") has been received! (From agent: ",AG,")");
	freeSpot(LOCATION);
	openGate.
	
+!leaving[source(AG)] <-
	closeGate.


{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }

