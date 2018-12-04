package org.jeesl.model.json.survey;

import java.io.Serializable;

import org.jeesl.model.json.system.status.JsonType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonRootName(value="answer")
public class Condition implements Serializable
{
	public static final long serialVersionUID=1;

	@JsonProperty("id")
	private Long id;
	public Long getId() {return id;}
	public void setId(Long id) {this.id = id;}

	@JsonProperty("question")
	private Question question;
	public Question getQuestion() {return question;}
	public void setQuestion(Question question) {this.question = question;}

	@JsonProperty("trigger")
	private Question trigger;
	public Question getTrigger() {return trigger;}
	public void setTrigger(Question trigger) {this.trigger = trigger;}
	
	@JsonProperty("option")
	private Option option;
	public Option getOption() {return option;}
	public void setOption(Option option) {this.option = option;}
	
	@JsonProperty("option")
	private JsonType type;
	public JsonType getType() {return type;}
	public void setType(JsonType type) {this.type = type;}
	
	@Override public String toString()
	{
		StringBuffer sb = new StringBuffer();	
		return sb.toString();
	}
}