
{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }
{ include("$moiseJar/asl/org-obedient.asl") }

getInfoDriver(DN,DP,DWP,DT,DWT) :- driverName(DN) & driverPrice(DP) & driverWPrice(DWP) & driverTime(DT) & driverWTime(DWT).
obs_hb(TM,TS) :- structure(ST) & ST = "HB" & timeoutManager(TA) & timeoutSector(TS).
obs_bb(TS) :- structure(ST) & ST = "BB" & timeoutSector(TS).
+!startObserver(NET,SIMU)[source(AG)]<-
	+simulation(SIMU); +network(NET);
		
	for(play(AGENT,ROLE,__)){
		.term2string(ROLE,ROLE_S);
		if(ROLE_S = "r_body_sector"){
			+sector(AGENT);
		}
	}
	
	!startObserverArtifacs(NET,SIMU);
	.
	
	
+!startObserverArtifacs(NET,SIMU) <-

	lookupArtifact("a_BDConnection",ART_BDCONNECTION);
	focus(ART_BDCONNECTION);
	
	lookupArtifact("a_SessionControl",ART_SESSIONCONTROL);
	focus(ART_SESSIONCONTROL);
	
	lookupArtifact("a_StructureInfo",ART_STRINFO);
	focus(ART_STRINFO);
	
	lookupArtifact("a_QueueControl",ART_QUEUECONTROL);
	focus(ART_QUEUECONTROL);
	
	lookupArtifact("a_SectorControl",ART_SECTORCONTROL);
	focus(ART_SECTORCONTROL);	
	
    if(NET = true){
		.print("Checking the network!");
		lookupArtifact("a_DriverConnection",ART_DRIVERCONNECTION);
		focus(ART_DRIVERCONNECTION);
	}else{
		.print("The system is running through simulation!");
		lookupArtifact("a_Simulation",ART_SIMULATION);
		focus(ART_SIMULATION);
	}.
	

+isRequest(DN,DP,DPW,DT,DTW,DA): structure(ST) & network(NET) & statusSystem(STATUS) & STATUS = true<-
	
	if(NET = true){
		addDriver(DN,DP,DPW,DT,DTW,DA)[artifact_id(ART_BDCONNECTION)];
		jacamo.create_agent(DN,"idriver.asl");	
	}
	else{
		addDriverSimulation(DN,DP,DPW,DT,DTW)[artifact_id(ART_BDCONNECTION)];
	}
	generateSession(DN)[artifact_id(ART_SESSIONCONTROL)];
	isFull(ISFULL);
	if(ISFULL = false){				
		.send(DN,tell,[driverPrice(DP),priceWeight(DPW),driverTime(DT),timeWeight(DTW),driverName(DN)]);	
	}
	else{
		addDriverQueue(DN);		
		!notifyQueueInfo(DN);
	}
	.
	
+isRequest(DN,DP,DPW,DT,DTW,DA): statusSystem(STATUS) & STATUS = false <-
	.print("System is closed to receive new requests!");
	addDriver(DN,DP,DPW,DT,DTW,DA)[artifact_id(ART_BDCONNECTION)];
	?network(NET);
	if(NET = true){
		sendUnavailableInfo(DN,"System is closed to receive new requests!");	
	}
	.
	
+isLeavingRequest(DN)<-	
	.send(DN,tell,timeToLeave).
	
+roundsOver(DN) : structure(ST) & ST = "BB" <-
	.send(DN,tell,roundsOver).
	
+yellowAlert <-
	.print("Yellow Alert!").

+orangeAlert(SECTOR_NAME) <-	
	.print("Orange alert! System is closed to new requests! Emergency Plan running!");	
	jacamo.create_agent("emergency_sector","emergency_sector.asl");		
	.send("emergency_sector",tell,replacedSector(SECTOR_NAME));
	assignSectorSTR("emergency_sector")[artifact_id(ART_STRINFO)];
	registerSector("emergency_sector",_,_)[artifact_id(ART_SECTORCONTROL)];
	.
	
+redAlert <-
	.print("Red Alert! System is broken! Suspending the system!");
	.drop_all_desires;
	.drop_all_intentions;
	.
	
+!timeoutDriver[source(DN)] : network(NET) & NET = true <-
	sendUnavailableInfo(DN,"Timeout reached!").

+!checkRequests <-	
	.print("Waiting for new requests...");
	checkNewRequests;
	!!startRequestLoopChecking.

+!startRequestLoopChecking <-
	checkNewRequests;
	.wait(5000);
	!!startRequestLoopChecking.
		
+!observeAgents <-
	.print("Observing the agents!");
	!!startLoopObserving.


+!notifyRDriver(DN,PSNAME,SECTOR) : network(NET) & NET = true <-
	.print("Notifying the ",DN," about its PSpace through Network!");
	sendPSInfo(DN,PSNAME).
	
+!notifyQueueInfo(DN) : network(NET) & NET = true  <-
	.print("Notifying the ",DN," about its Waiting Queue through Network!");
	.send(DN,tell,waitingQueue);
	sendQueueInfo(DN).

+!notifyQueueInfo(DN) : simulation(SIMU) & SIMU = true  <-
	.print("Notifying the ",DN," about its Waiting Queue (SIMULATION)");
	.send(DN,tell,waitingQueue).

+!notifyPaymentDriver(DRIVERNAME,PRICE) : network(NET) & NET = true <-
	.print("Sending the final price to the ",DRIVERNAME," through Network!");
	sendPSPaymentInfo(DRIVERNAME,PRICE);
	.
	
+!checkQueue[source(AG)] <- 
	isEmptyQueue(ISEMPTY);
	if(ISEMPTY = false){
		.print("Getting a driver from Queue");
		getDriverQueue(DN,DP,DPW,DT,DTW);		
		.send(DN,tell,[driverPrice(DP),priceWeight(DPW),driverTime(DT),timeWeight(DTW),driverName(DN)]);
	}.
	
	
+!startLoopObserving : obs_hb(TM,TS) <-
	!sendPingManager;
	.wait(20000);
	!sendPingSector;
	.wait(10000);
	!checkPong.
	
+!startLoopObserving : obs_bb(TS) <-
	!sendPingSector.
	
+!sendPingSector  <-
	for(sector(S,H)){
		if(H = true){
			.send(S,achieve,ping);
			+ping(S);		
		}
	}.
	
+!sendPingManager<-
	.send(manager,achieve,ping);
	+ping(manager).
	
+!checkPong <-
	for(ping(AG)){
		if(pong(AG,CODE)){
			.print(AG," ok!");
			-pong(AG);
		}
		else{
			.print(AG, " failed!!!");
			if(AG == "manager"){
				+managerFail;
			}
			else{
				+sectorFail(AG,CODE)
			}	
		}
		-ping(AG); 
		
	};
	!startLoopObserving.
	  
+managerFail <-
	//normal mode
	
	setStructure("BB");
	.kill_agent(manager).
		
+sectorFail(SECTOR_NAME,SECTOR_CODE) <-
	.print("The ",SECTOR_NAME," failed!");
	
	setSectorHealthy(SECTOR_NAME,false);	
	.wait(500);
	selectHealthySector(SECTOR_NAME,SECTOR_HEALTHY);	
	.print("Notifying the Parking Spaces of ", SECTOR_NAME, "about their new Sector! (",SECTOR_HEALTHY,")");
	
	//receive the old sector ps list
	getSectorPSList(SECTOR_NAME,PSLIST);
	
	for(.member(PS_AGENT,PSLIST) ) {
        .send(PS_AGENT,achieve,newSector(SECTOR_HEALTHY));
     };
    .send(SECTOR_HEALTHY,tell,[newParkingSpaces(PSLIST),sectorChild(SECTOR_NAME)]).
	
	
+emergencySectorFail <-
	.print("The ",SECTOR_NAME," failed!");
	suspendSystem.

+all_PSBroken(SC)[source(AG)] <-
	.print("Closing ",AG," to new requests!");
	.concat("emergency_ps_",SC,PSNAME);
	
	setSectorHealthy(AG,false);	
	
	
	.concat("sector_",SC,SECTOR_AGENT);
	.concat(SECTOR_AGENT,"_grp",SECTOR_GROUP);
	
	jacamo.create_agent(PSNAME,"emergency_ps.asl");
	.send(PSNAME,tell,[myGroup(SECTOR_GROUP),mySector(SECTOR_AGENT)]).


	
	
	
	
		
