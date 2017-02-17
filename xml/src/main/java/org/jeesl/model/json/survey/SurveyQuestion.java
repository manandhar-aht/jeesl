package org.jeesl.model.json.survey;

import java.io.Serializable;

import org.jeesl.interfaces.model.survey.JeeslSimpleSurveyQuestion;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value = "question")
public class SurveyQuestion implements Serializable,JeeslSimpleSurveyQuestion
{
	public static final long serialVersionUID=1;

	@JsonProperty("id")
	private Long id;
	@Override public long getId() {return id;}
	@Override public void setId(long id) {this.id = id;}

	@JsonProperty("question")
	private String question;
	public String getQuestion() {return question;}
	public void setQuestion(String question) {this.question = question;}
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		
		return sb.toString();
	}
}