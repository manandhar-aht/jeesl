package org.jeesl.model.json.util.db.tuple;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JsonTuple implements Serializable
{
	public static final long serialVersionUID=1;
	
	@JsonProperty("sum")
	private Double sum;
	public Double getSum() {return sum;}
	public void setSum(Double sum) {this.sum = sum;}
	
	@JsonProperty("count")
	private Integer count;
	public Integer getCount() {return count;}
	public void setCount(Integer count) {this.count = count;}
}