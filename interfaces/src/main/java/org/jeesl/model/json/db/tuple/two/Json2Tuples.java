package org.jeesl.model.json.db.tuple.two;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class Json2Tuples <X extends EjbWithId, Y extends EjbWithId> implements Serializable
{
	public static final long serialVersionUID=1;

	@JsonProperty("tuples")
	private List<Json2Tuple<X,Y>> tuples;
	public List<Json2Tuple<X,Y>> getTuples() {if(tuples==null){tuples = new ArrayList<Json2Tuple<X,Y>>();} return tuples;}
	public void setTuples(List<Json2Tuple<X,Y>> tuples) {this.tuples = tuples;}


	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		
		return sb.toString();
	}
}