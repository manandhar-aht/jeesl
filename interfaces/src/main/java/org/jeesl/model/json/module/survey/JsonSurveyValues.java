package org.jeesl.model.json.module.survey;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class JsonSurveyValues implements Serializable
{
	public static final long serialVersionUID=1;

	public JsonSurveyValues(){}

	private  List<JsonSurveyValue> values;
	public List<JsonSurveyValue> getValues() {if(values==null) {values = new ArrayList<JsonSurveyValue>();}return values;}
	public void setValues(List<JsonSurveyValue> values) {this.values = values;}

	@Override public String toString()
	{
		StringBuffer sb = new StringBuffer();
		
		return sb.toString();
	}
}