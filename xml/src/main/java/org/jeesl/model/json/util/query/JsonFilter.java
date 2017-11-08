package org.jeesl.model.json.util.query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value="filter")
public class JsonFilter implements Serializable
{
	public static final long serialVersionUID=1;
	public enum Type{idList}
	
	@JsonProperty("filters")
	private List<JsonFilters> filters;
	public List<JsonFilters> getFilters() {if(filters==null) {filters = new ArrayList<JsonFilters>();}return filters;}
	public void setFilters(List<JsonFilters> filters) {this.filters = filters;}


	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		
		return sb.toString();
	}
}