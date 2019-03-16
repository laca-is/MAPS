
!wait_start.

+!wait_start : true <-
	
	focusWhenAvailable("a_Blackboard");
	!register.


+!register : spotGroup(SG) & price(P) & trust(T) <-
		.my_name(NAME);
		registerSpot(NAME,P,T);			
		.print("SPOT: ",NAME).
		
+!assignSpot[source(DRIVER)] <-
	
	+driverAgent(DRIVER);
	.print("The (",DRIVER,") is here!").
	
+!freeDriverSpot: driverAgent(DRIVER) <-
	-driverAgent(DRIVER);
	.print("The (",DRIVER,") has left!").
	
		




{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }

