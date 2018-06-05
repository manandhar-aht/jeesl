package org.jeesl.model.json.db.tuple.one;

import org.jeesl.model.json.db.tuple.AbstractJsonTuple;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class Json1Tuple <T extends EjbWithId> extends AbstractJsonTuple
{
	public static final long serialVersionUID=1;

	@JsonIgnore
	private T ejb;
	public T getEjb() {return ejb;}
	public void setEjb(T ejb) {this.ejb=ejb;}
	
	@JsonProperty("id")
	private Long id;
	public Long getId() {return id;}
	public void setId(Long id) {this.id = id;}
}