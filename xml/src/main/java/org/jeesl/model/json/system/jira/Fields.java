package org.jeesl.model.json.system.jira;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value="fields")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Fields implements Serializable
{
	public static final long serialVersionUID=1;

	@JsonProperty("status")
	private Status status;
	public Status getStatus() {return status;}
	public void setStatus(Status status) {this.status = status;}
}