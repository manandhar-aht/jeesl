package org.jeesl.model.json.util.db;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class JsonTuples <T extends EjbWithId> implements Serializable
{
	public static final long serialVersionUID=1;

	@JsonProperty("tuples1")
	private List<JsonTuple1<T>> tuples1;
	public List<JsonTuple1<T>> getTuples1() {if(tuples1==null){tuples1 = new ArrayList<JsonTuple1<T>>();} return tuples1;}
	public void setTuples1(List<JsonTuple1<T>> tuples1) {this.tuples1 = tuples1;}


	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		
		return sb.toString();
	}
}