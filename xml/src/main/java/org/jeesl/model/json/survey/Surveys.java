package org.jeesl.model.json.survey;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonRootName(value="surveys")
public class Surveys implements Serializable
{
	public static final long serialVersionUID=1;
	
	@JsonProperty("survey")
	private List<Survey> survey;
	public List<Survey> getSurvey() {return survey;}
	public void setSurvey(List<Survey> survey) {this.survey = survey;}
	
	@Override public String toString()
	{
		StringBuffer sb = new StringBuffer();
		return sb.toString();
	}
}