package org.jeesl.model.json.util.query;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value="filters")
public class JsonFilters implements Serializable
{
	public static final long serialVersionUID=1;

	@JsonProperty("fqcn")
	private String fqcn;
	public String getFqcn() {return fqcn;}
	public void setFqcn(String fqcn) {this.fqcn = fqcn;}
	
	@JsonProperty("type")
	private String type;
	public String getType() {return type;}
	public void setType(String type) {this.type = type;}

	@JsonProperty("ids")
	private List<Long> ids;
	public List<Long> getIds() {return ids;}
	public void setIds(List<Long> ids) {this.ids = ids;}
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		
		return sb.toString();
	}
}