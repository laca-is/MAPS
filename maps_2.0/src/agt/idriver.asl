{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }
{ include("$moiseJar/asl/org-obedient.asl") }

getInfoDriver(DP,DPW,DT,DTW,DN) :- driverPrice(DP) & priceWeight(DPW) & driverTime(DT) & timeWeight(DTW) & driverName(DN).
getInfoPS(PS,SECTOR) :- myPS(PS) & mySector(SECTOR).

!startIDriver.

+!startIDriver <-
	?timeToArrive(TA);
	.wait(TA);
	joinWorkspace("maps_holo_org",WSP_ORG);

	lookupArtifact("a_StructureInfo",ART_STINFO);
	focus(ART_STINFO);

	lookupArtifact("a_IDriverTools",ART_IDTOOLS);
	focus(ART_IDTOOLS);
	
	?simulation(SIMU);
	if(SIMU = true){
		!requestPSSimulation;
	}.

+driverName(DN) : structure(ST)<-		
	!requestPSpace(ST);
	.
	
+driverName(DN) <-
	.wait(structure(ST));	
	!requestPSpace(ST);
	.
	
+!checkTimeoutOffer <-
	.wait(30000);	
	if(gotPS){}
	else{
		.my_name(Me);
		.print("Time out! Bye bye!");
		.send(observer,tell,timeoutDriver);
		.drop_all_intentions;
	}.
	
+!requestPSpace(STR) : STR = "HB" & getInfoDriver(DP,DPW,DT,DTW,DN) <-
	.print("Hi, I`m the ",DN," - My Pref.: ($",DP,"_",DPW,") - (",DT,"min._",DTW,")");			
	.send(manager,achieve,requestPSpace(DN));
	!!checkTimeoutOffer.	

+!receiveOffer(PSNAME,PSPRICE,PSTIME,SECTORNAME)[source(AG)] : not gotPS & not roundsOver <-
	+receivedOffer;
	.print("I received a offer: ",PSNAME,"(",SECTORNAME,") - $",PSPRICE," - ",PSTIME,"min.");
	.print("Calculating my Utility to this offer!...");
	
	?getInfoDriver(DP,DPW,DT,DTW,DN);
	?threshold(THRESHOLD);
	.my_name(Me);
	
	calculateIDriverUtility(DPW,DTW,DP,DT,PSPRICE,PSTIME,IDUTILITY);
	
	if(IDUTILITY >= THRESHOLD){
		+gotPS;
		.print("I will accept the offer! (",PSNAME,") - UT: ",IDUTILITY);
		.send(AG,achieve,acceptOffer(PSNAME,SECTORNAME));
	}
	else{
		.print("I will reject the offer! (",PSNAME,") - UT: ",IDUTILITY);
		.send(AG,achieve,rejectOffer(PSNAME,SECTORNAME));
	}	
.

+!receiveOffer(PSNAME,PSPRICE,PSTIME,SECTORNAME)[source(AG)] : gotPS <-
	.print("I've got a parking space! Thank you!'").

+!goDriver(PSNAME,SECTORNAME) : simulation(SIMU)<-
	
	.concat("wsp_",SECTORNAME,WSPNAME);
	.concat(SECTORNAME,"_grp",GRPNAME);
	
	lookupArtifact("maps_holo_org",ART_ORG);
	focus(ART_ORG);
	
	lookupArtifact("maps_holo_grp",ART_MAIN_GROUP);
	focus(ART_MAIN_GROUP);
	
	lookupArtifact(GRPNAME,ART_GROUP);
	focus(ART_GROUP);
	
	adoptRole(r_idriver)[artifact_id(ART_GROUP)];
	.wait(500);
	
	joinWorkspace(WSPNAME,WSP_SECTOR);		
	.print("Going to the (",PSNAME," - ",SECTORNAME,")");
	
	+myPS(PSNAME); +mySector(SECTORNAME);
	
	translateAgentAddress(PSNAME,DEST_PS,_);	
	.send(DEST_PS,achieve,gotDriver(PSNAME));
	
	if(SIMU = true){
		?getInfoDriver(_,_,DT,_,_);
		.wait(DT * 50);
		+timeToLeave;
	}
.
	
+!requestPSpace(STR) : STR = "BB" & getInfoDriver(DP,DPW,DT,DTW,DN) <-
	for(sector(S,H)){
		.wait(500);
		if(H == true){
			.send(S,achieve,requestPSpace(DN));
		};	
	}.
		
+!requestPSSimulation : driverPrice(DP) & priceWeight(DPW) & driverTime(DT) & timeWeight(DTW)<-
	lookupArtifact("a_Simulation",ART_SIMULATION);
	focus(ART_SIMULATION);
	.my_name(Me);	
	requestPS(Me, DP, DPW, DT, DTW);
	.

+timeToLeave : getInfoPS(PS,SECTOR) & structure(STR) & STR = "HB" <-
	.send(manager,achieve,leavePSpace(PS,SECTOR)).
	
+timeToLeave : getInfoPS(PS,SECTOR) & structure(STR) & STR = "BB" <-
	.my_name(Me);
	translateAgentAddress(SECTOR,DEST_SECTOR,_);
	.send(DEST_SECTOR,achieve,leavePSpace(Me,PS)).

+finalPrice(PRICE) : getInfoPS(PS,SECTOR) <-
	.print("Final Price: $",PRICE," - ",PS,"(",SECTOR,")").

+waitingQueue <-
	.print("Waiting my turn...").

+roundsOver <-			
	.print("Rounds are over! - Bye bye!").
	
+structureChanged : structure(ST) & not gotPS <-
	.print("Structure changed... Changing my mind!");
	.drop_all_intentions;
	!requestPSpace(ST).