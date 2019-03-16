{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }
{ include("$moiseJar/asl/org-obedient.asl") }

//Agent ParkingSpace in MAPSHOLO Project

+mySector(SECTOR_AGENT)[source(AG)] : not sectorFailed(S)<-

	?myGroup(SECTOR_GROUP);
	.my_name(Me);
	.concat("wsp_",SECTOR_AGENT,WSPNAME);	
	+myWorkspace(WSPNAME);
	.wait(2000);
	.print("Starting the ",Me);
	joinWorkspace(WSPNAME,WSP_SECTOR);		
	joinWorkspace("maps_holo_org",WSP_ORG);
		
	!setupPSArtifacts;
	
	!assignMe(SECTOR_AGENT).
	
+!setupPSArtifacts : myGroup(SECTOR_GROUP) <-

	lookupArtifact("maps_holo_org",ART_ORG);
	focus(ART_ORG);
	
	lookupArtifact("maps_holo_grp",ART_MAIN_GROUP);
	focus(ART_MAIN_GROUP);
	
	lookupArtifact("a_StructureInfo",ART_STRINFO);
	focus(ART_STRINFO);
	
	lookupArtifact(SECTOR_GROUP,ART_GROUP);
	focus(ART_GROUP);
	
	adoptRole(r_pspace)[artifact_id(ART_GROUP)];	
	.
	
+!assignMe(SECTOR_AGENT) <-
	.send(SECTOR_AGENT,achieve,assignPSpace).
	
+!gotDriver(PS_NAME)[source(AG)] : .my_name(Me) & .term2string(Me,MeStr) & PS_NAME = MeStr<-	
	.print("The ",AG," has got here!");
	?mySector(SECTOR);	
	.send(SECTOR,achieve,gotDriver(AG)).
	
+!gotDriver(PS_NAME)[source(AG)] : psChildList(PSLIST) & .term2string(PS_NAME_T,PS_NAME) & .member(PS_NAME_T,PSLIST) <-
	
	.print(PS_NAME,": The ",AG," has got here!");
	?mySector(SECTOR);	
	.send(SECTOR,achieve,gotDriver(AG)).
	
+!leavePSpace(DRIVERNAME,PS_NAME)[source(AG)] : .my_name(Me) & .term2string(Me,MeStr) & PS_NAME = MeStr <-	
	.print("Getting free!");
	-myDriver(DRIVERNAME,PS_NAME);
	.
	
+!leavePSpace(DRIVERNAME,PS_NAME)[source(AG)] : psChildList(PSLIST) & .term2string(PS_NAME_T,PS_NAME) & .member(PS_NAME_T,PSLIST) <-	
	.print(PS_NAME,": Getting free!");
	-myDriver(DRIVERNAME,PS_NAME);
	.
	
+psChild(PSC_NAME,PSC_PRICE,PSC_TIME,PSC_LIST) <-
	.print("I manage the ",PSC_NAME," now!");	
	.findall(P,psChild(P,_,_,_),PSLIST);
	
	if(psChildList(L)){
		-psChildList(L);
	}
	
	if(.list(PSC_LIST)){
		.union(PSC_LIST,PSLIST,NEWLIST);
		+psChildList(NEWLIST);
	}
	else{
		+psChildList(PSLIST);
	}.
	
	
+fail[source(AG)] : mySector(SECTOR_AGENT) & myPrice(PS_PRICE) & myTime(PS_TIME)<-	
	.print("Notifying",SECTOR_AGENT," about my fail!");	
	 .my_name(Me);
	 
	 if(psChildList(LIST)){
		.send(SECTOR_AGENT,tell,psFail(Me,PS_PRICE,PS_TIME,LIST));	 	
	 }
	 else{
	 	.print("Sending..");
	 	.send(SECTOR_AGENT,tell,psFail(Me,PS_PRICE,PS_TIME,_));
	 	
	 }.
	 
+!newSector(NEW_SECTOR) : statusSystem(STATUS) & STATUS = true & myGroup(OLD_GROUP) & mySector(OLD_SECTOR) & myWorkspace(OLD_WSP) <-
	
	+sectorFailed(OLD_SECTOR);	
	-myGroup(OLD_GROUP);
	-mySector(OLD_SECTOR);
	-myWorkspace(OLD_WSP);
	
	//quitWorkspace(OLD_WSP);	
	
	lookupArtifact(OLD_GROUP,ART_OLD_GROUP);	
	leaveRole(r_pspace)[artifact_id(ART_OLD_GROUP)];	
	stopFocus(ART_OLD_GROUP);
	
	.print("I got changed of sector! - ",NEW_SECTOR);
	
	.concat(NEW_SECTOR,"_grp",NEW_GROUP);
	.concat("wsp_",NEW_SECTOR,NEW_WSP);
	
	.print("New group: ",NEW_GROUP);
	.print("New workspace: ", NEW_WSP);
	
	+mySector(NEW_SECTOR);
	+myGroup(NEW_GROUP);
	+myWorkspace(NEW_WSP);
	
	lookupArtifact(NEW_GROUP,ART_GROUP);
	focus(ART_GROUP);
	
	joinWorkspace(NEW_WSP,WSP_SECTORNEW);
	adoptRole(r_pspace)[artifact_id(ART_GROUP)];
	.
	
+!newSector(NEW_SECTOR) : statusSystem(STATUS) & STATUS = false & myGroup(GROUP) & mySector(SECTOR)<-
	+sectorFailed(SECTOR);
	.print("Emergency plan!").
	
	
	

	
	
	
	
	