package org.jeesl.model.json.module.ts;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonRootName(value="statistic")
public class TsStatistic implements Serializable
{
	public static final long serialVersionUID=1;

	@JsonProperty("Entity")
	private String entity;
	public String getEntity() {return entity;}
	public void setEntity(String entity) {this.entity = entity;}
	
	@JsonProperty("Interval")
	private String interval;
	public String getInterval() {return interval;}
	public void setInterval(String interval) {this.interval = interval;}

	@JsonProperty("Scope")
	private String scope;

	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}

	@JsonProperty("Count")
	private int count;
	public int getCount() {return count;}
	public void setCount(int count) {this.count = count;}
	
	@Override public String toString()
	{
		StringBuffer sb = new StringBuffer();	
		return sb.toString();
	}
}