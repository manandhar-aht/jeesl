package org.jeesl.model.json.system.io.ssi;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value="dataUpdate")
public class DataUpdate implements Serializable
{
	public static final long serialVersionUID=1;

	@JsonProperty("success")
	private boolean success;
	public boolean isSuccess() {return success;}
	public void setSuccess(boolean success) {this.success = success;}
}