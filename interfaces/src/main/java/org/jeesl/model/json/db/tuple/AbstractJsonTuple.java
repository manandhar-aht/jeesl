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
	
	
	@JsonProperty("sum1")
	private Double sum1;
	public Double getSum1() {return sum1;}
	public void setSum1(Double sum1) {this.sum1 = sum1;}
	
	@JsonProperty("sum2")
	private Double sum2;
	public Double getSum2() {return sum2;}
	public void setSum2(Double sum2) {this.sum2 = sum2;}
	
	@JsonProperty("count")
	private Long count;
	public Long getCount() {return count;}
	public void setCount(Long count) {this.count = count;}
}