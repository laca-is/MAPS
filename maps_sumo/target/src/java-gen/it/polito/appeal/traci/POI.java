

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

	Represents a POI (Point-of-Interest) in the SUMO environment.
	@see <a href="http://sumo.dlr.de/wiki/Simulation/Shapes#POI_.28Point_of_interest.29_Definitions">SUMO documentation</a>
	@see <a href="http://sumo.dlr.de/wiki/TraCI/POI_Value_Retrieval">TraCI documentation</a>
	@author Enrico Gueli &lt;enrico.gueli@polito.it&gt;
	
*/
public class POI 
extends TraciObject<POI.Variable>
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
		 * Query "ReadType"
		 * @see #queryReadType
		 */
		TYPE,
		
		/** 
		 * Query "ReadColor"
		 * @see #queryReadColor
		 */
		COLOR,
		
		/** 
		 * Query "ReadPosition"
		 * @see #queryReadPosition
		 */
		POSITION,
		
	}
	
	
	private final ChangeColorQuery csqvar_ChangeColor;
	
	private final ChangeObjectVarQuery.ChangeStringQ csqvar_ChangeType;
	
	private final ChangePositionQuery csqvar_ChangePosition;
	POI (
		DataInputStream dis,
		DataOutputStream dos, 
		String id
		
	) {
		super(id, Variable.class);

		/*
		 * initialization of read queries
		 */
		
		addReadQuery(Variable.TYPE, 
				new ReadObjectVarQuery.StringQ (dis, dos, 
				it.polito.appeal.traci.protocol.Constants.CMD_GET_POI_VARIABLE
	, 
				id, 
				it.polito.appeal.traci.protocol.Constants.VAR_TYPE
				
				));
		
		addReadQuery(Variable.COLOR, 
				new ReadObjectVarQuery.ColorQ (dis, dos, 
				it.polito.appeal.traci.protocol.Constants.CMD_GET_POI_VARIABLE
	, 
				id, 
				it.polito.appeal.traci.protocol.Constants.VAR_COLOR
				
				));
		
		addReadQuery(Variable.POSITION, 
				new ReadObjectVarQuery.PositionQ (dis, dos, 
				it.polito.appeal.traci.protocol.Constants.CMD_GET_POI_VARIABLE
	, 
				id, 
				it.polito.appeal.traci.protocol.Constants.VAR_POSITION
				
				));
		

		/*
		 * initialization of change state queries
		 */
		
		csqvar_ChangeColor = new ChangeColorQuery(dis, dos
		, it.polito.appeal.traci.protocol.Constants.CMD_SET_POI_VARIABLE
	
		, id
		, it.polito.appeal.traci.protocol.Constants.VAR_COLOR)
		{
			@Override
			void pickResponses(java.util.Iterator<it.polito.appeal.traci.protocol.ResponseContainer> responseIterator)
					throws TraCIException {
				super.pickResponses(responseIterator);
				
				queryReadColor().setObsolete();
				
			}
		};
		
		csqvar_ChangeType = new ChangeObjectVarQuery.ChangeStringQ(dis, dos
		, it.polito.appeal.traci.protocol.Constants.CMD_SET_POI_VARIABLE
	
		, id
		, it.polito.appeal.traci.protocol.Constants.VAR_TYPE)
		{
			@Override
			void pickResponses(java.util.Iterator<it.polito.appeal.traci.protocol.ResponseContainer> responseIterator)
					throws TraCIException {
				super.pickResponses(responseIterator);
				
				queryReadType().setObsolete();
				
			}
		};
		
		csqvar_ChangePosition = new ChangePositionQuery(dis, dos
		
		, id
		)
		{
			@Override
			void pickResponses(java.util.Iterator<it.polito.appeal.traci.protocol.ResponseContainer> responseIterator)
					throws TraCIException {
				super.pickResponses(responseIterator);
				
				queryReadPosition().setObsolete();
				
			}
		};
		
	
	}
	
	
	public void nextStep(double step) {
		
	}
	
	
	
	
	
	/**
	 * @return the instance of {@link ReadObjectVarQuery} relative to this query.
	 */
	public ReadObjectVarQuery<java.lang.String> queryReadType() {
		return (ReadObjectVarQuery.StringQ) getReadQuery(Variable.TYPE);
	}
	
	
	/**
	 * Executes an instance of {@link ReadObjectVarQuery} relative to this query,
	 * and returns the corresponding value.
	 */
	public java.lang.String getType() throws IOException {
		return ((ReadObjectVarQuery.StringQ) getReadQuery(Variable.TYPE)).get();
	}
	
	/**
	 * @return the instance of {@link ReadObjectVarQuery} relative to this query.
	 */
	public ReadObjectVarQuery<java.awt.Color> queryReadColor() {
		return (ReadObjectVarQuery.ColorQ) getReadQuery(Variable.COLOR);
	}
	
	
	/**
	 * Executes an instance of {@link ReadObjectVarQuery} relative to this query,
	 * and returns the corresponding value.
	 */
	public java.awt.Color getColor() throws IOException {
		return ((ReadObjectVarQuery.ColorQ) getReadQuery(Variable.COLOR)).get();
	}
	
	/**
	 * @return the instance of {@link ReadObjectVarQuery} relative to this query.
	 */
	public ReadObjectVarQuery<java.awt.geom.Point2D> queryReadPosition() {
		return (ReadObjectVarQuery.PositionQ) getReadQuery(Variable.POSITION);
	}
	
	
	/**
	 * Executes an instance of {@link ReadObjectVarQuery} relative to this query,
	 * and returns the corresponding value.
	 */
	public java.awt.geom.Point2D getPosition() throws IOException {
		return ((ReadObjectVarQuery.PositionQ) getReadQuery(Variable.POSITION)).get();
	}
	
	/**
	 * @return the instance of {@link ChangeColorQuery} relative to this query.
	 */
	public ChangeColorQuery queryChangeColor() {
		return csqvar_ChangeColor;
	}
	
	
	/**
	 * Execute an instance of ChangeColorQuery set to the given value.
	 * 
	 * This setter method is equivalent to queryChangeColor().setValue(value).run().
	 */
	public void changeColor(java.awt.Color value) throws IOException {
		ChangeColorQuery q = csqvar_ChangeColor;
		q.setValue(value);
		q.run();
	}
	
	/**
	 * @return the instance of {@link ChangeObjectVarQuery.ChangeStringQ} relative to this query.
	 */
	public ChangeObjectVarQuery.ChangeStringQ queryChangeType() {
		return csqvar_ChangeType;
	}
	
	
	/**
	 * Execute an instance of ChangeObjectVarQuery.ChangeStringQ set to the given value.
	 * 
	 * This setter method is equivalent to queryChangeType().setValue(value).run().
	 */
	public void changeType(String value) throws IOException {
		ChangeObjectVarQuery.ChangeStringQ q = csqvar_ChangeType;
		q.setValue(value);
		q.run();
	}
	
	/**
	 * @return the instance of {@link ChangePositionQuery} relative to this query.
	 */
	public ChangePositionQuery queryChangePosition() {
		return csqvar_ChangePosition;
	}
	
	
	/**
	 * Execute an instance of ChangePositionQuery set to the given value.
	 * 
	 * This setter method is equivalent to queryChangePosition().setValue(value).run().
	 */
	public void changePosition(java.awt.geom.Point2D value) throws IOException {
		ChangePositionQuery q = csqvar_ChangePosition;
		q.setValue(value);
		q.run();
	}
	
}

