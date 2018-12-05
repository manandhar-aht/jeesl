package org.jeesl.model.json.system.io.ssi;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value="version")
public class Version implements Serializable
{
	public static final long serialVersionUID=1;

	@JsonProperty("appVersion")
	private String appVersion;
	public String getAppVersion() {return appVersion;}
	public void setAppVersion(String appVersion) {this.appVersion = appVersion;}
	
	@JsonProperty("handheldVersion")
	private String handheldVersion;
	public String getHandheldVersion() {return handheldVersion;}
	public void setHandheldVersion(String handheldVersion) {this.handheldVersion = handheldVersion;}
}