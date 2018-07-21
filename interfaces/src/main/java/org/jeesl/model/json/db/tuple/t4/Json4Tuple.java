package org.jeesl.model.json.db.tuple.t4;

import org.jeesl.model.json.db.tuple.t3.Json3Tuple;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class Json4Tuple <A extends EjbWithId, B extends EjbWithId, C extends EjbWithId, D extends EjbWithId> extends Json3Tuple<A,B,C>
{
	public static final long serialVersionUID=1;

	@JsonIgnore
	private D ejb4;
	public D getEjb4() {return ejb4;}
	public void setEjb4(D ejb4) {this.ejb4=ejb4;}
		
	@JsonProperty("id4")
	private Long id4;
	public Long getId4() {return id4;}
	public void setId4(Long id4) {this.id4 = id4;}
}