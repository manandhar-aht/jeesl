package org.jeesl.model.json.survey;

import java.io.Serializable;

import org.jeesl.interfaces.model.survey.JeeslSimpleSurvey;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value="survey")
public class Survey implements Serializable,JeeslSimpleSurvey
{
	public static final long serialVersionUID=1;

	@JsonProperty("id")
	private Long id;
	@Override public long getId() {return id;}
	@Override public void setId(long id) {this.id = id;}

	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		
		return sb.toString();
	}
}