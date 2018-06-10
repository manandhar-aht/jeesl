package org.jeesl.model.json.db.tuple.one;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class Json1Tuples <T extends EjbWithId> implements Serializable
{
	public static final long serialVersionUID=1;

	@JsonProperty("tuples")
	private List<Json1Tuple<T>> tuples;
	public List<Json1Tuple<T>> getTuples() {if(tuples==null){tuples = new ArrayList<Json1Tuple<T>>();} return tuples;}
	public void setTuples(List<Json1Tuple<T>> tuples) {this.tuples = tuples;}

	private boolean ejbLoaded;
	public boolean isEjbLoaded() {return ejbLoaded;}
	public void setEjbLoaded(boolean ejbLoaded) {this.ejbLoaded = ejbLoaded;}
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		return sb.toString();
	}
}