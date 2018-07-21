package org.jeesl.model.json.db.tuple.t4;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class Json4Tuples <A extends EjbWithId, B extends EjbWithId, C extends EjbWithId, D extends EjbWithId> implements Serializable
{
	public static final long serialVersionUID=1;

	@JsonProperty("tuples")
	private List<Json4Tuple<A,B,C,D>> tuples;
	public List<Json4Tuple<A,B,C,D>> getTuples() {if(tuples==null){tuples = new ArrayList<Json4Tuple<A,B,C,D>>();} return tuples;}
	public void setTuples(List<Json4Tuple<A,B,C,D>> tuples) {this.tuples = tuples;}


	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		
		return sb.toString();
	}
}