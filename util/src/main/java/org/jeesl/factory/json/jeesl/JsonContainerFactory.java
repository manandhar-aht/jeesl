package org.jeesl.factory.json.jeesl;

import java.util.List;

import org.jeesl.factory.json.system.status.JsonStatusFactory;
import org.jeesl.model.json.system.status.JsonContainer;
import org.jeesl.model.json.system.status.JsonStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

@SuppressWarnings("rawtypes")
public class JsonContainerFactory
{
	final static Logger logger = LoggerFactory.getLogger(JsonContainerFactory.class);
	
	private final JsonStatusFactory jfStatus;
	
	public JsonContainerFactory(String localeCode, JsonStatus query)
	{
		jfStatus = new JsonStatusFactory(localeCode,query);
	}
	
	@SuppressWarnings("unchecked")
	public <S extends UtilsStatus<S,L,D>,L extends UtilsLang, D extends UtilsDescription> JsonContainer build(List<S> list)
	{
		JsonContainer xml = build();
		for(S s : list){xml.getStatus().add(jfStatus.build(s));}
		return xml;
	}
		
    public static JsonContainer build()
    {
    	JsonContainer xml = new JsonContainer();
    	return xml;
    }
}