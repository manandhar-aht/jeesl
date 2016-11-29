package org.jeesl.report.prototype;

import java.util.ArrayList;
import java.util.List;

import org.jeesl.model.json.JsonFlatFigures;

public abstract class AbstractJeeslReport
{
	protected String localeCode;
	
	protected JsonFlatFigures flats; public JsonFlatFigures getFlats() {return flats;}
	protected List<String> headers; public List<String> getHeaders() {return headers;}

	public AbstractJeeslReport(String localeCode)
	{
		this.localeCode=localeCode;
		
		buildHeaders();
	}
	
	private void buildHeaders()
	{
		headers = new ArrayList<String>();
	}
}