package org.jeesl.model.json.system.json;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value="pages")
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonSecurityPages implements Serializable
{
	public static final long serialVersionUID=1;

	
}