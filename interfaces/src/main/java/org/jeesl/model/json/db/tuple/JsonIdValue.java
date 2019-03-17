package org.jeesl.model.json.db.tuple;

import java.io.Serializable;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class JsonIdValue extends AbstractJsonTuple implements Serializable,EjbWithId
{
	public static final long serialVersionUID=1;
	
	public enum Field{sum,count}
	
	private long id;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	private double d1;

	public double getD1() {
		return d1;
	}

	public void setD1(double d1) {
		this.d1 = d1;
	}
}