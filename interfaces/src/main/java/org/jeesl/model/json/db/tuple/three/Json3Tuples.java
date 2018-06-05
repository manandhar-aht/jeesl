package org.jeesl.model.json.db.tuple.three;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class Json3Tuples <X extends EjbWithId, Y extends EjbWithId, Z extends EjbWithId> implements Serializable
{
	public static final long serialVersionUID=1;

	@JsonProperty("tuples")
	private List<Json3Tuple<X,Y,Z>> tuples;
	public List<Json3Tuple<X,Y,Z>> getTuples() {if(tuples==null){tuples = new ArrayList<Json3Tuple<X,Y,Z>>();} return tuples;}
	public void setTuples(List<Json3Tuple<X,Y,Z>> tuples) {this.tuples = tuples;}


	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		
		return sb.toString();
	}
}