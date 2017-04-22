package org.jeesl.factory.json.system.status;

import org.jeesl.model.json.system.status.JsonStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class JsonStatusFactory<S extends UtilsStatus<S,L,D>,L extends UtilsLang, D extends UtilsDescription>
{
	final static Logger logger = LoggerFactory.getLogger(JsonStatusFactory.class);
		
	private final JsonStatus q;
	
	public JsonStatusFactory(String localeCode, JsonStatus q)
	{
		this.q=q;
	}
		
	public JsonStatus build(S ejb)
	{
		JsonStatus json = new JsonStatus();
	
//		if(q.isSetId()){xml.setId(ejb.getId());}
		if(q.getCode()!=null){json.setCode(ejb.getCode());}
	
		return json;
	}
}