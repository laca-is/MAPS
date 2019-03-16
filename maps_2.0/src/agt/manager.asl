{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }
{ include("$moiseJar/asl/org-obedient.asl") }
//Agent manager

!startManager.

+!startManager <-
	
	.print("Hello!");
	joinWorkspace("maps_holo_org",WSP_ORG);
	+wPrice(0.5); +wTime(0.5);
	 
	!setupManagerArtifacts.
	
+!setupManagerArtifacts : wPrice(WP) & wTime(WT)<-
	
	lookupArtifact("maps_holo_org",ART_ORG);
	focus(ART_ORG);
	
	lookupArtifact("maps_holo_grp",ART_MAIN_GROUP);
	focus(ART_MAIN_GROUP);
	
	lookupArtifact("a_SectorControl",ART_SECTORCONTROL);
	focus(ART_SECTORCONTROL);	
	
	lookupArtifact("a_StructureInfo",ART_STINFO);
	focus(ART_STINFO);
	setManagerWeights(WP,WT);
	
	lookupArtifact("a_SessionControl",ART_SESSIONCONTROL);
	focus(ART_SESSIONCONTROL);
	
	adoptRole(r_manager)[artifact_id(ART_MAIN_GROUP)];
	.
	
+!requestPSpace(DN)[source(DRIVER)] <-
	.print("Requesting a PSpace!");
	startRound(DN);
	for(sector(S,H)){		 
		.send(S,achieve,requestPSpace(DN));
	};	
	
	.
	
		
+!receiveOffer(DNAME,PSNAME,PSPRICE,PSTIME)[source(SECTOR)] <-
	.print("I received a offer from...",SECTOR,"PS: ",PSNAME);
	registerOffer(DNAME,PSNAME,PSPRICE,PSTIME,SECTOR);
	.
	
+offersCompleted(DN) <-
	.print("Time to send the offers to the ",DN);
	calculatePSUtilityManager(DN);
	selectOffer(DN,ISPS,PSNAME,PSPRICE,PSTIME,SECTORNAME);
	if(ISPS = true){		 
		.send(DN,achieve,receiveOffer(PSNAME,PSPRICE,PSTIME,SECTORNAME));
	}
	else{
		.print("To do when in the first round there is no pspace!!!");
	}
	.
	
+roundsOver(DN) <-
	.print("The rounds are over to the ",DN);	 
	.send(DN,tell,roundsOver);	 
	.send(observer,achieve,checkQueue).

+!acceptOffer(PSNAME,SECTORNAME)[source(AG)] <-
//	getManagerUtility(PSNAME,MANAGER_UTILITY);
//	addSystemUtility(MANAGER_UTILITY);	 
	.send(SECTORNAME,achieve,acceptOffer(AG,PSNAME)).

+!rejectOffer(PSNAME,SECTORNAME)[source(AG)] <-
	 
	.send(SECTORNAME,achieve,rejectOffer(AG,PSNAME));
	checkRound(AG,ROUND_OK);
	if(ROUND_OK = true){
		.print("The ",AG," rejected the offer! Ofering another...");
		selectOffer(AG,ISPS2,PSNAME2, PSPRICE2, PSTIME2, SECTORNAME2);		
		if(ISPS2 = false){
			for(sector(S)[artifact_id(ART_STINFO)]){				 
				.send(S,achieve,requestPSpace(AG));
			}
		}
		else{			 
			.send(AG,achieve,receiveOffer(PSNAME2,PSPRICE2,PSTIME2,SECTORNAME2));
		}	
	}
	else{
		.print("The rounds are over to the ",AG);
		.send(AG,tell,roundsOver);
		!finishDriverSession(AG);	
	}
.

+!goDriver(DN,PSNAME)[source(AG)]: network(NET) <-
	.send(DN,achieve,goDriver(PSNAME,AG));
	if(NET = true){
		.send(observer,achieve,notifyRDriver(DN,PSNAME,AG));
	}.
	
+!leavePSpace(PSNAME,SECTORNAME)[source(AG)] <-	 
	.send(SECTORNAME,achieve,leavePSpace(AG,PSNAME)).

+!notifyPaymentDriver(DRIVERNAME,PRICE)[source(AG)] : network(NET) <-
	 
	.send(DRIVERNAME,tell,finalPrice(PRICE));
	if(NET = true){
		.send(observer,achieve,notifyPaymentDriver(DRIVERNAME,PRICE));
	};
	!finishDriverSession(DRIVERNAME);
	.send(observer,achieve,checkQueue).
	
+!finishDriverSession(DN) <-
	finishSession(DN);
	removeDriver(DN);
	.kill_agent(DN).
	
+!ping[source(AG)] <-
	.send(AG,tell,pong(manager,_)).
	
+fail[source(AG)]<-
	.print("I have a fail! Notifying about my failure!... System now is a Body-Body Arch!");	
	.send(observer,tell,managerFail).
		
	


