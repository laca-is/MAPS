{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }
{ include("$moiseJar/asl/org-obedient.asl") }


getInfoSystem(ST,NET,SIMU,NS,NPS) :- structure(ST) & network(NET) & simulation(SIMU) & nSectors(NS) & nPSpaces(NPS).
!start.


////nSectors(2),
//				 structure(0), //0 - head-body / 1 - body-body
//				 network(true),
//				 simulation(false)

+!setupWorkspaces : nSectors(NS) <-
	
	
	for (.range(I,0,NS-1) ) {	
		.concat("wsp_sector_",I,WSP_SECTOR);				
		.print("Creating (",WSP_SECTOR,") Workspace");		
		createWorkspace(WSP_SECTOR);
	}.

+!setupArtifacts : getInfoSystem(ST,NET,SIMU,NS,NPS) <-


	//makeartifact  addressControl

	makeArtifact("a_SectorControl","artifacts.A_SectorControl",[],ART_SECTORCONTROL);
	focus(ART_SECTORCONTROL);
	
	makeArtifact("a_BDConnection","artifacts.A_BDConnection",[],__);
	makeArtifact("a_SessionControl","artifacts.A_SessionControl",[NS],__);
	makeArtifact("a_IDriverTools","artifacts.A_IDriverTools",[],__);
	makeArtifact("a_QueueControl","artifacts.A_QueueControl",[],__);
	makeArtifact("a_StructureInfo","artifacts.A_StructureInfo",[ST,NET,SIMU,NS,NPS],ART_STRINFO);
	focus(ART_STRINFO);
	
	if(NET = true){
		makeArtifact("a_DriverConnection","artifacts.A_DriverConnection",[],ART_DRIVERCONNECTION);		
	}
	else{
		makeArtifact("a_Simulation","artifacts.A_Simulation",[],ART_SIMULATION);
	}
	.
	
+!setupManager : structure(ST)[artifact_id(ART)] <-
	if(ST = "HB"){
		jacamo.create_agent("manager","manager.asl");
	}.
	

+!setupSectors : getInfoSystem(ST,NET,SIMU,NS,NPS) <-
	
	for (.range(I,0,NS-1) ) {
		
		.concat("sector_",I,SECTOR_AGENT);		
		jacamo.create_agent(SECTOR_AGENT,"sector.asl");		
		assignSectorSTR(SECTOR_AGENT)[artifact_id(ART_STRINFO)];
		registerSector(SECTOR_AGENT,WPRICE,WTIME)[artifact_id(ART_SECTORCONTROL)];
		.send(SECTOR_AGENT,tell,[nPS(NPS),wPrice(WPRICE),wTime(WTIME),sectorCode(I)]);
		
		.concat("sector_",I,"_grp",SECTOR_GROUP);
		createGroup(SECTOR_GROUP, sectorGroup, GrArtId);
		.wait(500);
		//setParentGroup(maps_holo_grp)[artifact_id(GrArtId)];
		debug(inspector_gui(on))[artifact_id(GrArtId)];
		addScheme("pFailScheme")[artifact_id(GrArtId)];
		
		
		for(.range(J,0,NPS-1)){
			.concat("pspace_",I,"_",J,PS_AGENT);
			jacamo.create_agent(PS_AGENT,"pspace.asl");
			.send(PS_AGENT,tell,[myGroup(SECTOR_GROUP),mySector(SECTOR_AGENT)]);
					
		}
		.wait(500);
		.send(SECTOR_AGENT,tell,sectorOk(true));
	}
	.
+!setupPSpaces <-
	.print("All the PSAgents will be created!").

+!setupObserver : getInfoSystem(ST,NET,SIMU,__,__)  <-
	.print("Setting up the Observer!");
	.send(observer,achieve,startObserver(NET,SIMU));
	.

+!start : true <- 
	.print("hello world.");	
	.print("Got here!");
.

+!checkNewRequests <-
	checkNewRequests;
	.wait(500);
	!checkNewRequests;
	.


