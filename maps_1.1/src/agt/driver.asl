// Agent driver in project mAPS_2

!setupAgent.

+!setupAgent <-
	+timeToSpend(math.random(5000));
	+myTrust(math.random(1000));
	+myBudget(math.random(30));
	!requestSpot.
	
+!requestSpot : myBudget(MB) & myTrust(MT) <-
	.wait(2000);
	focusWhenAvailable("a_Blackboard");
	focusWhenAvailable("a_Gate");
	.my_name(NAME);
	.print("My name is: ",NAME);
	requestSpot(NAME,REQUEST,S_PRICE,S_TRUST,S_LOCATION,S_AGENT);
	
	if(REQUEST = TRUE){
		if(S_PRICE < MB){
			.print("I accepted the proposed spot!");
			.send(S_AGENT,achieve,assignSpot);
			!goToSpot(S_LOCATION);
				
		}else{
			.print("I do not accepted the proposed spot!");
			!leaveParking;
		};	
	}else{
		.print("Parking lot is full!");
		!leaveParking;
	}.

+!goToSpot(L) : timeToSpend(TS) & myBudget(MB) <-
	
 	.print("Going to: ",L);
 	+spot(L); 	
 	.wait(TS);
 	//pay the spot
 	.send(manager,achieve,paymentSpot(MB,L));
 	!leaveSpot.
 	
+!leaveSpot : spot(L) <-

	if(isOpen = TRUE){ 
		.concat("spot",L,SPOTAGENT);
		.send(SPOTAGENT,achieve,freeDriverSpot);
		.print("Leaving the spot (",L,")");
		!leaveParking;	
	}.

+!leaveParking <-
	.print("Going away!");
	.send(manager,achieve,leaving).

{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }

