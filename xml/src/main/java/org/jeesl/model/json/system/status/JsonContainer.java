package org.jeesl.model.json.system.status;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value="container")
public class JsonContainer implements Serializable
{
	public static final long serialVersionUID=1;

	@JsonProperty("status")
	private List<JsonStatus> status;
	public List<JsonStatus> getStatus() {if(status==null){status = new ArrayList<JsonStatus>();}return status;}
	public void setStatus(List<JsonStatus> status) {this.status = status;}
}