// Agent driver in project mAPS
// Trust degree value is the image reputation that an agent owns (0-1000)

!arriveParking.

+!arriveParking : timeToArrive(TA)<- 
		.wait(TA); 
		!requestSpot;
		+arrivalParking.

+!requestSpot : myTrust(MT)
	<- .print("Arrived in the parking! Waiting for a spot...");
	   .send(manager,achieve,requestSpot(MT)).   

+!park(S)[source(AGENT)] : spotOk & arrivalParking & timeToSpend(TS)
	<- .print("Parking at the spot: ",S);
		+spot(S);
		.wait(TS);
		!leaveSpot.

+!leaveSpot : spot(S) <-
	.print("Leaving the parking...");	
	.send(manager,achieve,leaveSpot(S));
	-spot(S).



{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }

