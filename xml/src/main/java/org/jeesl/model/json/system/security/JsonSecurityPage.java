package org.jeesl.model.json.system.security;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value="page")
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonSecurityPage implements Serializable
{
	public static final long serialVersionUID=1;

	@JsonProperty("s1")
	private String s1;
	public String getS1() {return s1;}
	public void setS1(String s1) {this.s1 = s1;}
	
	@JsonProperty("s2")
	private String s2;
	public String getS2() {return s2;}
	public void setS2(String s2) {this.s2 = s2;}
	
	@JsonProperty("s3")
	private String s3;
	public String getS3() {return s3;}
	public void setS3(String s3) {this.s3 = s3;}
	
	@JsonProperty("public")
	private boolean accessPublic;
	public boolean isAccessPublic() {return accessPublic;}
	public void setAccessPublic(boolean accessPublic) {this.accessPublic = accessPublic;}
}