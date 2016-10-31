package org.jeesl.model.json;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JsonFlatFigures implements Serializable
{
	public static final long serialVersionUID=1;

	@JsonProperty("figures")
	private List<JsonFlatFigure> figures;
	public List<JsonFlatFigure> getFigures() {if(figures==null){figures = new ArrayList<JsonFlatFigure>();} return figures;}
	public void setFigures(List<JsonFlatFigure> figures) {this.figures = figures;}


	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		
		return sb.toString();
	}
}