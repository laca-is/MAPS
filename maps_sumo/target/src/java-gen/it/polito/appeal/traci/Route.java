

/*
    THIS FILE IS GENERATED AUTOMATICALLY. DO NOT EDIT: CHANGES WILL BE OVERWRITTEN.
    File generated by traciObject.xslt.
*/

/*   
    Copyright (C) 2013 ApPeAL Group, Politecnico di Torino

    This file is part of TraCI4J.

    TraCI4J is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    TraCI4J is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with TraCI4J.  If not, see <http://www.gnu.org/licenses/>.
*/


package it.polito.appeal.traci;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**

	Representation of a route in the SUMO environment.
	@see <a href="http://sumo.dlr.de/wiki/Definition_of_Vehicles,_Vehicle_Types,_and_Routes#Vehicle_Types">SUMO documentation</a>
	@see <a href="http://sumo.dlr.de/wiki/TraCI/Route_Value_Retrieval">TraCI documentation</a>
	@author Enrico Gueli &lt;enrico.gueli@polito.it&gt;
	
*/
public class Route 
extends TraciObject<Route.Variable>
implements StepAdvanceListener
{

	/**
	 * Enumerates all the read queries. Each value can be used as an argument
	 * for {@link TraciObject#getReadQuery(Enum)}.
	 * 
	 * @author Enrico Gueli &lt;enrico.gueli@polito.it&gt;
	 */	
	public static enum Variable {
		
		/** 
		 * Query "ReadRoute"
		 * @see #queryReadRoute
		 */
		ROUTE,
		
	}
	
	Route (
		DataInputStream dis,
		DataOutputStream dos, 
		String id
		
			, Repository<Edge> repoEdge
	) {
		super(id, Variable.class);

		/*
		 * initialization of read queries
		 */
		
		addReadQuery(Variable.ROUTE, 
				new RouteQuery (dis, dos, 
				it.polito.appeal.traci.protocol.Constants.CMD_GET_ROUTE_VARIABLE, 
				id, 
				it.polito.appeal.traci.protocol.Constants.VAR_EDGES
				, repoEdge
				
				));
		

		/*
		 * initialization of change state queries
		 */
		
	
	}
	
	
	public void nextStep(double step) {
		
	}
	
	
	
	
	
	/**
	 * @return the instance of {@link ReadObjectVarQuery} relative to this query.
	 */
	public ReadObjectVarQuery<java.util.List<Edge>> queryReadRoute() {
		return (RouteQuery) getReadQuery(Variable.ROUTE);
	}
	
	
	/**
	 * Executes an instance of {@link ReadObjectVarQuery} relative to this query,
	 * and returns the corresponding value.
	 */
	public java.util.List<Edge> getRoute() throws IOException {
		return ((RouteQuery) getReadQuery(Variable.ROUTE)).get();
	}
	
}

