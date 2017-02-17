package org.jeesl.model.json.survey;

import java.io.Serializable;

import org.jeesl.interfaces.model.survey.JeeslSimpleSurveySection;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value="section")
public class SurveySection implements Serializable,JeeslSimpleSurveySection
{
	public static final long serialVersionUID=1;

	@JsonProperty("id")
	private Long id;
	@Override public long getId() {return id;}
	@Override public void setId(long id) {this.id = id;}

	@JsonProperty("name")
	private String name;
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		
		return sb.toString();
	}
}