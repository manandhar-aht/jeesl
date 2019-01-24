package org.jeesl.factory.json.system.io.db;

import java.sql.Timestamp;
import java.util.Date;

import org.jeesl.model.json.JsonFlatFigure;
import org.jeesl.model.xml.system.io.db.Query;

public class JsonDbPgStatConnectionFactory 
{
	public static JsonFlatFigure build(int number, Object[] array)
	{
		Timestamp tsTransaction = null;
		Timestamp tsQuery = null;
		Timestamp tsState = null;
		Boolean waiting = null;
		String state = null;
		String query = null;
		
		if(array[0]!=null){tsTransaction = (Timestamp)array[0];}
		if(array[1]!=null){tsQuery = (Timestamp)array[1];}
		if(array[2]!=null){tsState = (Timestamp)array[2];}
		if(array[3]!=null){state = (String)array[3];}
		if(array[4]!=null){query = (String)array[4];}
        
		return build(number,tsTransaction,tsQuery,tsState,waiting,state,query);
	}
	
	public static JsonFlatFigure build(int number, Timestamp tsTransaction, Timestamp tsQuery, Timestamp tsState,Boolean waiting,String state,String query)
	{
		JsonFlatFigure json = new JsonFlatFigure();
		json.setC1(number);
		
		if(tsTransaction!=null){json.setDate1(new Date(tsTransaction.getTime()));}
		if(tsQuery!=null){json.setDate2(new Date(tsQuery.getTime()));}
		if(tsState!=null){json.setDate3(new Date(tsState.getTime()));}
		
		if(waiting!=null){json.setB1(waiting);}
		
		if(state!=null){json.setG1(state);}
		if(query!=null){json.setG2(query);}
		
		return json;
	}
}