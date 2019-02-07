package org.jeesl.model.json.survey.validation;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonRootName(value="validation")
public class Validation implements Serializable
{
	public static final long serialVersionUID=1;

	@JsonProperty("id")
	private Long id;
	public Long getId() {return id;}
	public void setId(Long id) {this.id = id;}
	
	@JsonProperty("algorithm")
	private ValidationAlgorithm algorithm;
	public ValidationAlgorithm getAlgorithm() {return algorithm;}
	public void setAlgorithm(ValidationAlgorithm algorithm) {this.algorithm = algorithm;}
	
	@JsonProperty("config")
	private String config;
	public String getConfig() {return config;}
	public void setConfig(String config) {this.config = config;}
	
	@JsonProperty("message")
	private String message;
	public String getMessage() {return message;}
	public void setMessage(String message) {this.message = message;}
	
	
	@Override public String toString()
	{
		StringBuffer sb = new StringBuffer();	
		return sb.toString();
	}
}