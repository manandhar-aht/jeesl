package org.jeesl.model.json.survey;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonRootName(value="matrix")
public class Cell implements Serializable
{
	public static final long serialVersionUID=1;

	@JsonProperty("id")
	private Long id;
	public Long getId() {return id;}
	public void setId(Long id) {this.id = id;}

	@JsonProperty("row")
	private Long row;
	public Long getRow() {return row;}
	public void setRow(Long row) {this.row = row;}

	@JsonProperty("column")
	private Long column;
	public Long getColumn() {return column;}
	public void setColumn(Long column) {this.column = column;}
	
	@JsonProperty("answer")
	private Answer answer;
	public Answer getAnswer() {return answer;}
	public void setAnswer(Answer answer) {this.answer = answer;}

	@Override public String toString()
	{
		StringBuffer sb = new StringBuffer();	
		return sb.toString();
	}
}