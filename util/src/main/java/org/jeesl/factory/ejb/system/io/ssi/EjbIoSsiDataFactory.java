package org.jeesl.factory.ejb.system.io.ssi;

import org.jeesl.interfaces.model.system.io.ssi.JeeslIoSsiData;
import org.jeesl.interfaces.model.system.io.ssi.JeeslIoSsiMapping;

import com.fasterxml.jackson.core.JsonProcessingException;

import net.sf.exlp.util.io.JsonUtil;

public class EjbIoSsiDataFactory <MAPPING extends JeeslIoSsiMapping<?,?>,
									DATA extends JeeslIoSsiData<MAPPING>>
{
	private final Class<DATA> cData;

	public EjbIoSsiDataFactory(final Class<DATA> cData)
	{
        this.cData = cData;
	}
	
	public DATA build(MAPPING mapping, String code, Object json)
	{
		DATA ejb = null;
		try
		{
			ejb = cData.newInstance();
			ejb.setMapping(mapping);
			ejb.setCode(code);
			ejb.setJson(JsonUtil.toString(json));
	       
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		catch (JsonProcessingException e) {e.printStackTrace();}
		return ejb;
	}    
}