package org.jeesl.model.json.system.io.cms;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value="CmsStatusTable")
public class JsonCmsStatusTable implements Serializable
{
	public static final long serialVersionUID=1;
	
	@JsonProperty("className")
	private String className;
	public String getClassName() {return className;}
	public void setClassName(String className) {this.className = className;}

//	@JsonProperty("graphic")
//	private boolean graphic;
//	public boolean isGraphic() {return graphic;}
//	public void setGraphic(boolean graphic) {this.graphic = graphic;}
}