package org.jeesl.model.json.system.json;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value="page")
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonSecurityPage implements Serializable
{
	public static final long serialVersionUID=1;

	@JsonProperty("l1")
	private String l1;
	public String getL1() {return l1;}
	public void setL1(String l1) {this.l1 = l1;}
	
	@JsonProperty("public")
	private boolean accessPublic;
	public boolean isAccessPublic() {return accessPublic;}
	public void setAccessPublic(boolean accessPublic) {this.accessPublic = accessPublic;}
}