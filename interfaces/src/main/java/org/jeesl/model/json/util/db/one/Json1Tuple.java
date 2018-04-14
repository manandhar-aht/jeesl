package org.jeesl.model.json.util.db.one;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class Json1Tuple <T extends EjbWithId> implements Serializable
{
	public static final long serialVersionUID=1;

	@JsonProperty("ejb")
	private T ejb;
	public T getEjb() {return ejb;}
	public void setEjb(T ejb) {this.ejb=ejb;}
	
	@JsonProperty("id")
	private Long id;
	public Long getId() {return id;}
	public void setId(Long id) {this.id = id;}
	
	@JsonProperty("sum")
	private Double sum;
	public Double getSum() {return sum;}
	public void setSum(Double sum) {this.sum = sum;}
	
	@JsonProperty("count")
	private Integer count;
	public Integer getCount() {return count;}
	public void setCount(Integer count) {this.count = count;}
}