package org.jeesl.model.json.db.tuple;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class AbstractJsonTuple implements Serializable
{
	public static final long serialVersionUID=1;
	
	@JsonProperty("sum")
	private Double sum;
	public Double getSum() {return sum;}
	public void setSum(Double sum) {this.sum = sum;}
	
	@JsonProperty("count")
	private Long count;
	public Long getCount() {return count;}
	public void setCount(Long count) {this.count = count;}
}