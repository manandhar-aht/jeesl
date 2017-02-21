package org.jeesl.model.json.survey;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.jeesl.interfaces.model.survey.JeeslSimpleSurveySection;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value="section")
public class Section implements Serializable,JeeslSimpleSurveySection
{
	public static final long serialVersionUID=1;

	@JsonProperty("id")
	private Long id;
	@Override public long getId() {return id;}
	@Override public void setId(long id) {this.id = id;}
	public boolean isSetId() {return id!=null;}

	@JsonProperty("code")
	private String code;
	public String getCode() {return code;}
	public void setCode(String code) {this.code = code;}
	public boolean isSetCode() {return code!=null;}
	
	@JsonProperty("name")
	private String name;
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	public boolean isSetName() {return name!=null;}
	
	@JsonProperty("question")
	private List<Question> questions;
	public List<Question> getQuestions() {if(questions==null){questions = new ArrayList<Question>();} return questions;}
	public void setQuestions(List<Question> questions) {this.questions = questions;}
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		
		return sb.toString();
	}
}