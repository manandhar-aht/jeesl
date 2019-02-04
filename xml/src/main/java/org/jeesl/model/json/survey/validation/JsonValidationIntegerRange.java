package org.jeesl.model.json.survey.validation;

import org.jeesl.interfaces.model.json.module.survey.SurveyValidatorConfiguration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonRootName(value="intRage")
public class JsonValidationIntegerRange implements SurveyValidatorConfiguration
{
	public static final long serialVersionUID=1;

	@JsonProperty("min")
	private Integer min;
	public Integer getMin() {return min;}
	public void setMin(Integer min) {this.min = min;}

	@JsonProperty("max")
	private Integer max;
	public Integer getMax() {return max;}
	public void setMax(Integer max) {this.max = max;}

	@Override public String toString()
	{
		StringBuffer sb = new StringBuffer();	
		return sb.toString();
	}
}