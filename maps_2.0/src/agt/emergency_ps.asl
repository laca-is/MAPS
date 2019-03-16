{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }
{ include("$moiseJar/asl/org-obedient.asl") }


//Agent ParkingSpace in MAPSHOLO Project

+mySector(SECTOR_AGENT) <-
	?myGroup(SECTOR_GROUP);	
	.my_name(Me);
	.concat("wsp_",SECTOR_AGENT,WSPNAME);
	
	.wait(2000);
	.print("Starting the ",Me);
		
	
	joinWorkspace(WSPNAME,WSP_SECTOR);		
	joinWorkspace("maps_holo_org",WSP_ORG);
		
	!setupPSArtifacts;	
	!assignEmergencyPSAgent(SECTOR_AGENT).
	
+!setupPSArtifacts : myGroup(SECTOR_GROUP) <-

	lookupArtifact("maps_holo_org",ART_ORG);
	focus(ART_ORG);
	
	lookupArtifact("maps_holo_grp",ART_MAIN_GROUP);
	focus(ART_MAIN_GROUP);
	
	lookupArtifact(SECTOR_GROUP,ART_GROUP);
	focus(ART_GROUP);
	
	adoptRole(r_pspace)[artifact_id(ART_GROUP)];	
	.
	
+!assignEmergencyPSAgent(SECTOR_AGENT) <-
	.send(SECTOR_AGENT,achieve,assignEmergencyPSAgent).
	
+!leavePSpace(DRIVERNAME,PS_NAME)[source(AG)] : psChildList(PSLIST) & .term2string(PS_NAME_T,PS_NAME) & .member(PS_NAME_T,PSLIST) <-	
	.print(PS_NAME,": Getting free!");
	-myDriver(DRIVERNAME,PS_NAME);
	.
	
+emergencyPSFail <-
	.print("Failling...");
	?mySector(SECTOR_AGENT);
	.send(SECTOR_AGENT,tell,emergencyPSFail).
	
	

	
	
	
	
	