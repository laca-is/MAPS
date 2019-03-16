{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }
{ include("$moiseJar/asl/org-obedient.asl") }
// Agent sector
!startSector.

+!startSector<-
	
	.print("Hello!");
	joinWorkspace("maps_holo_org",WSP_ORG);
	.wait(replacedSector(S));	
	!setupEmergencyArtifacts.

+!setupEmergencyArtifacts : replacedSector(RSECTOR_NAME)<-
	
	.concat("A_PSControl_",RSECTOR_NAME,PSCONTROLNAME);
	.concat(RSECTOR_NAME,"_grp",GRPNAME);
	
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
	
	.my_name(Me);
	selectSector(Me,SECTOR_OBJ)[artifact_id(ART_SECTORCONTROL)];
	
	lookupArtifact(PSCONTROLNAME,ART_PSCONTROL);	
	focus(ART_PSCONTROL);
	
	adoptRole(r_head_sector)[artifact_id(ART_GROUP)];	
	adoptRole(r_body_sector)[artifact_id(ART_MAIN_GROUP)].
		
+usageUpdated(USAGE)[artifact_id(ART_ID)]<-
	.my_name(Me);
	updateUsage(Me,USAGE).
	
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
+!ping[source(AG)]<-
	.my_name(Me);
	.term2string(Me,MeString)
	.send(AG,tell,pong(MeString,0)).

+sectorFail : .my_name(Me) & sectorCode(SC) & nPS(NPS)<-
	.send(observer,tell,sectorFail(Me,SC));
	.drop_all_intentions.
	
//Plans to handle ps failures

+psFail(PS_CHILD,PSC_PRICE,PSC_TIME,PSC_LIST)<-
	
	.print("The ",PS_CHILD," failed! Choosing a parent to it...");
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
	-psFail(PS_CHILD,_,_,_); 
	+toTranslate(PS_CHILD).
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
	



