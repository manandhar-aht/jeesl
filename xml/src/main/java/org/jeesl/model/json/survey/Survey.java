package org.jeesl.model.json.survey;

import java.io.Serializable;
import java.util.Date;

import org.jeesl.interfaces.model.survey.JeeslSimpleSurvey;
import org.jeesl.model.json.system.status.JsonStatus;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonRootName(value="survey")
public class Survey implements Serializable,JeeslSimpleSurvey
{
	public static final long serialVersionUID=1;

	@JsonProperty("id")
	private long id;
	public long getId() {return id;}
	public void setId(long id) {this.id = id;}

	@JsonProperty("template")
	private Template template;
	public Template getTemplate() {return template;}
	public void setTemplate(Template template) {this.template = template;}
	
	@JsonProperty("name")
	private String name;
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	
	@JsonProperty("dateStart")
	private Date dateStart;
	public Date getDateStart() {return dateStart;}
	public void setDateStart(Date dateStart) {this.dateStart = dateStart;}

	@JsonProperty("dateEnd")
	private Date dateEnd;
	public Date getDateEnd() {return dateEnd;}
	public void setDateEnd(Date dateEnd) {this.dateEnd = dateEnd;}
	
	@JsonProperty("status")
	private JsonStatus status;
	public JsonStatus getStatus() {return status;}
	public void setStatus(JsonStatus status) {this.status = status;}
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		
		return sb.toString();
	}
}