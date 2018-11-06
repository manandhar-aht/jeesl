package org.jeesl.model.json.system.security;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value="user")
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonSecurityUser implements Serializable
{
	public static final long serialVersionUID=1;

	@JsonProperty("email")
	private String email;
	public String getEmail() {return email;}
	public void setEmail(String email) {this.email = email;}
	
	@JsonProperty("password")
	private String password;
	public String getPassword() {return password;}
	public void setPassword(String password) {this.password = password;}
}