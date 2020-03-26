!register.


+!register: .my_name(Me)<-
	
	focusWhenAvailable("A_StructureInfo");
	focusWhenAvailable("A_PSControl");
	focusWhenAvailable("A_SensorConnect");
	focusWhenAvailable("A_SUMOConnect");
	
	
	?price(P);
	?maxTime(T);
	?edge(E);
	?position(POS);
	
	
	.print("hello!");
	.wait(500);
	
	.print("Going to register...");
	.print("Price (R$ ",P,") - Time (",T,"min.)");	
	registerPSpace(Me,P,T,E,POS).
	
	
+!waitArrivalDriver[source(AG_DRIVER)] <-
	.print("Waiting the ",AG_DRIVER," to park!");
	+waitingDriver(AG_DRIVER).
	
	

+!freePSpace[source(AG_DRIVER)] <-
	.print("Getting free!").
	
+statusChanged(PS_NAME) : .my_name(Me) & .term2string(Me,MeStr) & PS_NAME = MeStr & waitingDriver(DRIVER) & not parked(DRIVER)<-
	.print("My sensor has changed!");
	.print("The ",DRIVER," arrived!");
	-waitingDriver(DRIVER);
	
	?edge(E);
	?position(POS);
	addVehicle(DRIVER,E,POS);
	.wait(5000);
	+parked(DRIVER).
	
+statusChanged(PS_NAME) : .my_name(Me) & .term2string(Me,MeStr) & PS_NAME = MeStr & not waitingDriver(DRIVER) & parked(DRIVER)<-
	.print("My sensor has changed!");
	removeVehicle(DRIVER);
	.print("The ",DRIVER," left!");
	-parked(DRIVER).
	

