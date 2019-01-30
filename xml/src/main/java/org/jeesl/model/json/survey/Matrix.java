package org.jeesl.model.json.survey;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonRootName(value="matrix")
public class Matrix implements Serializable
{
	public static final long serialVersionUID=1;

	@JsonProperty("cells")
	private List<Cell> cells;
	public List<Cell> getCells() {return cells;}
	public void setCells(List<Cell> cells) {this.cells = cells;}


	@Override public String toString()
	{
		StringBuffer sb = new StringBuffer();	
		return sb.toString();
	}
}