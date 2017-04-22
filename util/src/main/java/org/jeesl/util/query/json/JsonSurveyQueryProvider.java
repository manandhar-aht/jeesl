package org.jeesl.util.query.json;

import java.util.Date;

import org.jeesl.model.json.survey.Survey;

public class JsonSurveyQueryProvider
{
	public static Survey survey()
	{				
		Survey json = new Survey();
		json.setId(Long.valueOf(1));
		json.setName("");
		json.setDateStart(new Date());
		json.setDateEnd(new Date());
		json.setStatus(JsonStatusQueryProvider.statusLabel());
		return json;
	}
}