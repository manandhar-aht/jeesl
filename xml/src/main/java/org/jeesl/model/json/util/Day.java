package org.jeesl.model.json.util;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value="day")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Day implements Serializable
{
	public static final long serialVersionUID=1;

	@JsonProperty("nr")
	private int nr;
	public int getNr() {return nr;}
	public void setNr(int nr) {this.nr = nr;}

	@JsonProperty("name")
	private String name;
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	
	@JsonProperty("code")
	private String code;
	public String getCode() {return code;}
	public void setCode(String code) {this.code = code;}

	@JsonProperty("weekend")
	private boolean weekend;
	public boolean isWeekend() {return weekend;}
	public void setWeekend(boolean weekend) {this.weekend = weekend;}
	
	@JsonProperty("today")
	private boolean today;
	public boolean isToday() {return today;}
	public void setToday(boolean today) {this.today = today;}
}