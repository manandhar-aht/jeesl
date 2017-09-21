package org.jeesl.util.query.json;

import org.jeesl.model.json.system.status.JsonStatus;

public class JsonStatusQueryProvider
{
	
	public static JsonStatus statusExport()
	{				
		JsonStatus xml = new JsonStatus();
		xml.setId(Long.valueOf(0));
		xml.setCode("");
		return xml;
	}
	
	public static JsonStatus statusLabel()
	{				
		JsonStatus xml = new JsonStatus();
//		xml.setId(Long.valueOf(0));
		xml.setCode("");
		xml.setLabel("");
		return xml;
	}
}