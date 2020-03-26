!arriveParking.

+!arriveParking <-

	.random(T);
	TIME2WAIT = 5000 * (T + 0.5);
	.wait(TIME2WAIT);

	focusWhenAvailable("A_StructureInfo");
	focusWhenAvailable("A_PSControl");
	.print("Requesting a PSpace!");
	
	!requestPSpace.
	
+!requestPSpace <-
	requestPSpace(IS_PS,PSNAME);
	if(IS_PS = true){
		.print("I received the ",PSNAME);
		.send(PSNAME,achieve,waitArrivalDriver);
	}
	else{
		.print("There is not pspace available now... Bye!");
	}.
	
	
+!parkAtPSpace <-
	.print("").
	


{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }

