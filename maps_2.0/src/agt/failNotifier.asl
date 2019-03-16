//{ include("$jacamoJar/templates/common-cartago.asl") }
//{ include("$jacamoJar/templates/common-moise.asl") }
//{ include("$moiseJar/asl/org-obedient.asl") }

// Agent failNotifier in project maps_holo
!start.

/* Plans */

+!start : true <- .print("Choosing a agent to fail!");
	?nPSpaces(NPS)
	.wait(15000);
	joinWorkspace("maps_holo_org",WSP_ORG);
	lookupArtifact("a_StructureInfo",ART_STINFO);
	focus(ART_STINFO);
	
	!startNotify.
	
+!startNotify <-
//.send(pspace_0_0,tell,fail);
//.send(sector_0,tell,sectorFail);
//.send(manager,tell,fail);
	

	.print("hi");
//	.random(A);
//	if(A < 0.1){
//		.print("Notifying the manager about its failure!");
//		//.send(manager,tell,fail);
//	}
//	if(A > 0.1 & A < 0.3 ){
//		.print("Notifying a sector about its failure!");
//	}
//	if(A > 0.3){
//		.print("Notifying a ps about its failure");
//		!notifyPS;
//		
//	};
//	.wait(50000);
//	!startNotify;
	.
	
+!notifyPS <-
	.random(R);	
	if(R > 0.5){
		S = 0;
	}
	else{
		S = 1;
	}
	?nPSpaces(NPS);
	P = math.floor((NPS-2)/R);	
	.concat("pspace_",S,"_",P,PS_AGENT);
	.print("Notifying the ",PS_AGENT," about its failure!");
	
	if(not fail(PS_AGENT)){
		.send(PS_AGENT,tell,fail);
		+fail(PS_AGENT);
	}
	.


