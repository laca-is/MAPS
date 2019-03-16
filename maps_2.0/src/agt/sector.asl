{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }
{ include("$moiseJar/asl/org-obedient.asl") }
// Agent sector
!startSector.

+!startSector : true <-
	
	.print("Hello!");
	.my_name(Me);
	.concat("wsp_",Me,WSPNAME);	
	
	joinWorkspace(WSPNAME,WSP_SECTOR);
	joinWorkspace("maps_holo_org",WSP_ORG);
	
	!setupSectorArtifacts.

+!setupSectorArtifacts <-
	
	.my_name(Me);
	.concat("A_PSControl_",Me,PSCONTROLNAME);
	.concat(Me,"_grp",GRPNAME);
	
	lookupArtifact("pFailScheme",ART_PSFAIL_SCH);
	focus(ART_PSFAIL_SCH);
	
	lookupArtifact("maps_holo_org",ART_ORG);
	focus(ART_ORG);
	
	lookupArtifact("maps_holo_grp",ART_MAIN_GROUP);
	focus(ART_MAIN_GROUP);
	
	lookupArtifact(GRPNAME,ART_GROUP);
	focus(ART_GROUP);
	
	lookupArtifact("a_BDConnection",ART_BDCONNECTION);
	focus(ART_BDCONNECTION);
	
	lookupArtifact("a_SessionControl",ART_SESSIONCONTROL);
	focus(ART_SESSIONCONTROL);
	
	lookupArtifact("a_StructureInfo",ART_STINFO);
	focus(ART_STINFO);
	
	lookupArtifact("a_SectorControl",ART_SECTORCONTROL);
	focus(ART_SECTORCONTROL);
	selectSector(Me,SECTOR_OBJ)[artifact_id(ART_SECTORCONTROL)];
	
	?nPS(NPS);
	makeArtifact(PSCONTROLNAME,"artifacts.A_PSControl",[Me,SECTOR_OBJ,NPS],ART_PSCONTROL)[wid(WSP_SECTOR)];
	focus(ART_PSCONTROL)[wid(WSP_SECTOR)];
	
	
	adoptRole(r_head_sector)[artifact_id(ART_GROUP)];
		
	adoptRole(r_body_sector)[artifact_id(ART_MAIN_GROUP)];
	.wait(500);
	
	setOwner(Me)[artifact_id(ART_GROUP)];

	
	
//	?formationStatus(Ok)[artifact_id(ART_GROUP)];
//	?formationStatus(Ok)[artifact_id(ART_MAIN_GROUP)];
	
	.
	
+?formationStatus(nok)[artifact_id(G)]
   <- .wait({+formationStatus(ok)[artifact_id(G)]}).
	
+!assignPSpace[source(PS_AGENT)] : sectorOk(COND) & COND = true<-

	registerPSpace(PS_AGENT,PS_PRICE,PS_TIME)[wid(WSP_SECTOR),artifact_id(ART_PSCONTROL)];
	.send(PS_AGENT,tell,[myPrice(PS_PRICE),myTime(PS_TIME)]);
	.
	
+nPSOK[artifact_id(ART_ID)] <-
	.print("Time to calculate PSUtility!");
	calculateUpdatePSUtility[artifact_id(ART_ID)];
	.
	
+usageUpdated(USAGE)[artifact_id(ART_ID)] <-
	.my_name(Me);
	updateUsage(Me,USAGE).
	
+!requestPSpace(DN)[source(AG)] : usage(USAGE) & USAGE < 100 & structure(STR) & STR = "HB" <-
	requestPSpace(DN,ISPS,PSNAME,PSPRICE,PSTIME);	
	if(ISPS = true){		
		.send(AG,achieve,receiveOffer(DN,PSNAME,PSPRICE,PSTIME));
	};
	.

+!requestPSpace(DN)[source(AG)] : usage(USAGE) & USAGE = 100 <-
	.my_name(Me);	
	setSectorFull;
	.send(AG,tell,sectorFull(Me)). 
	
+!acceptOffer(DN,PSNAME)[source(AG)]: structure(STR) & STR = "HB"<-
	.print("Accepting the ",PSNAME," offer from ",DN);
	assignPSpace(DN,PSNAME);	
	.send(AG,achieve,goDriver(DN,PSNAME));	
.
	
+!rejectOffer(DN,PSNAME)[source(AG)]: structure(STR) & STR = "HB"<-
	.print("Rejecting the ",PSNAME," offer from ",DN);
	rejectPSpace(DN,PSNAME)
.
	
+!requestPSpace(DN)[source(AG)] : (usage(USAGE) & USAGE < 100 & structure(STR) & STR = "BB") | (AG = "self") <-
	.wait(500);
	.my_name(Me);
	startRoundSector(DN,Me);
	//checks if the driver already owns a parking space. If so, it does not need request because the driver has one
	checkDriverPS(DN,ISDRIVERPS);
	
	if(ISDRIVERPS = false){
		checkRoundSector(DN,Me,ROUND_OK);
		if(ROUND_OK = true){	
			requestPSpace(DN,ISPS,PSNAME,PSPRICE,PSTIME);	
			if(ISPS = true){
				.my_name(Me);
				registerSectorOffer(DN, PSNAME, PSPRICE, PSTIME, Me);						
				.send(DN,achieve,receiveOffer(PSNAME,PSPRICE,PSTIME,Me));
			}
			else{
				.send(DN,tell,notAvailable(Me));
			}	
		}else{
			.print("Sector round finished!");
		}
					
	}
	else{
		.print("The driver owns another parking space!");	
	}
	.	
	
+!acceptOffer(PSNAME,_)[source(AG)] : structure(STR) & STR = "BB" & network(NET)<-
	.print("Accepting the ",PSNAME," offer from ",AG);
	assignPSpace(AG,PSNAME);
	assignPSpaceSession(AG,PSNAME);
	
	.my_name(Me);
	
	translateAgentAddress(PSNAME,DEST_PS,_);		
	.send(DEST_PS,tell,myDriver(PSNAME,AG));	
	.send(AG,achieve,goDriver(PSNAME,Me));
	if(NET = true){		
		.send(observer,achieve,notifyRDriver(AG,PSNAME,Me));
	};
.
	
+!rejectOffer(PSNAME,_)[source(AG)] : structure(STR) & STR = "BB"<-
	.print("The ",AG," reject the offer - ",PSNAME,"Ofering another PS!");	
	rejectPSpace(AG,PSNAME);
	!requestPSpace(AG).		
	
	
+!gotDriver(DRIVER)[source(PSAGENT)] <-
	startCountingTime(DRIVER);
	.print("Starting to counting the time!").

+!leavePSpace(DRIVERNAME,PSNAME)[source(AG)] <-
	finishCountingTime(DRIVERNAME);
	
	translateAgentAddress(PSNAME,DEST_PS,_);	
	.send(DEST_PS,achieve,leavePSpace(DRIVERNAME,PSNAME));
	
	freePSpace(DRIVERNAME,PSNAME);
	calcDriverPrice2Pay(DRIVERNAME,PSNAME,PRICE);
	!notifyPaymentDriver(DRIVERNAME,PRICE).

+!notifyPaymentDriver(DRIVERNAME,PRICE) : structure(ST) & ST = "HB" <-	
	.send(manager,achieve,notifyPaymentDriver(DRIVERNAME,PRICE)).
	
+!notifyPaymentDriver(DRIVERNAME,PRICE) : structure(ST) & ST = "BB" & network(NET) <-
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
	
//Plans to handle the failures (self)
+!ping[source(AG)] : sectorCode(SC)<-
	.my_name(Me);
	.term2string(Me,MeString)
	.send(AG,tell,pong(MeString,SC)).

+sectorChild(SECTOR_CHILD)[source(AG)] : newParkingSpaces(PSLIST)<-
	.print("I have now the ", SECTOR_CHILD," parking spaces!");
	addNewParkingSpaces(PSLIST);
	calculateUpdatePSUtility.
	
+sectorFail : .my_name(Me) & sectorCode(SC) & nPS(NPS)<-
	.print("Failing...");
	!notifySectorFail.
	
+!notifySectorFail : .my_name(Me) & sectorCode(SC) & nPS(NPS)<-
	.send(observer,tell,sectorFail(Me,SC));
	.drop_all_intentions.
	
//Plans to handle ps failures

+psFail(PS_CHILD,PSC_PRICE,PSC_TIME,PSC_LIST)<-
	
	.print("The ",PS_CHILD," failed! Choosing a parent to it...");
	+toTranslate(PS_CHILD);
	setFailedPS(PS_CHILD);
	!setPSParent;
	!notifyPSParent.
	//commitMission("m_psFail")[artifact_id(ART_PSFAIL_SCH)].
	
+emergencyPSFail <-
	.print("My emergency ps failed! So do I...");
	+sectorFail.

+!setPSParent: psFail(PS_CHILD,_,_,_) <-
	setNewPSParent(PS_CHILD,PS_PARENT);
	.print("The ",PS_CHILD," has a new parent! - ",PS_PARENT)
	.print("Setting up a new parent!").
	
+!notifyPSParent : psFail(PS_CHILD,PSC_PRICE,PSC_TIME,PSC_LIST)<-
	translatePS(PS_CHILD,PS_PARENT);
	.print("Notifying the ",PS_PARENT," about its child: ", PS_CHILD);
	.send(PS_PARENT,tell,psChild(PS_CHILD,PSC_PRICE,PSC_TIME,PSC_LIST)); 
	-psFail(PS_CHILD,_,_,_). 
//	leaveMission("m_psFail")[artifact_id(ART_PSFAIL_SCH)].
	
	
+all_PSBroken[artifact_id(ART_ID)] : sectorCode(SC)<-
	.print("All my PS are broken!");
	.my_name(Me);
	.send(observer,tell,all_PSBroken(SC));	
	.drop_all_intentions.
	
+structureChanged : structure(ST)<-
	.print("Structure changed... Changing my mind!");
	.print("Arch: ",ST).


+!assignEmergencyPSAgent[source(PS_AGENT)] <-
	.print("Assigning the Emergency Agent!");
	assignEmergencyPSAGent(PS_AGENT);	
	.findall(P,toTranslate(P),PSCHILDLIST);
	.send(PS_AGENT,tell,psChildList(PSCHILDLIST)).
	
	



