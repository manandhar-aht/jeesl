package org.jeesl.model.json.db.tuple.t3;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class Json3Tuples <A extends EjbWithId, B extends EjbWithId, C extends EjbWithId> implements Serializable
{
	public static final long serialVersionUID=1;

	@JsonProperty("tuples")
	private List<Json3Tuple<A,B,C>> tuples;
	public List<Json3Tuple<A,B,C>> getTuples() {if(tuples==null){tuples = new ArrayList<Json3Tuple<A,B,C>>();} return tuples;}
	public void setTuples(List<Json3Tuple<A,B,C>> tuples) {this.tuples = tuples;}


	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		
		return sb.toString();
	}
}