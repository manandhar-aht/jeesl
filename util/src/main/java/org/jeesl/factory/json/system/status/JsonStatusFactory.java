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
	
	private final String localeCode;
	private final JsonStatus q;
	
	public JsonStatusFactory(String localeCode, JsonStatus q)
	{
		this.localeCode=localeCode;
		this.q=q;
	}
		
	public JsonStatus build(S ejb)
	{
		JsonStatus json = new JsonStatus();
	
		if(q.getId()!=null){json.setId(ejb.getId());}
		if(q.isSetCode()){json.setCode(ejb.getCode());}
		if(q.isSetLabel() && ejb.getName().containsKey(localeCode)){json.setLabel(ejb.getName().get(localeCode).getLang());}
		if(q.isSetDescription() && ejb.getDescription().containsKey(localeCode)){json.setDescription(ejb.getDescription().get(localeCode).getLang());}
	
		return json;
	}
}