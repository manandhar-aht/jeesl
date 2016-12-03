package net.sf.ahtutils.util.query;

import java.util.Hashtable;
import java.util.Map;

import org.jeesl.util.query.xml.StatusQuery;

import net.sf.ahtutils.factory.xml.status.XmlScopeFactory;
import net.sf.ahtutils.factory.xml.utils.XmlTrafficLightsFactory;
import net.sf.ahtutils.xml.aht.Query;
import net.sf.ahtutils.xml.utils.TrafficLight;
import net.sf.ahtutils.xml.utils.TrafficLights;

public class UtilsQuery
{
	public static enum Key {exTrafficLights,exTrafficLight}
	
	private static Map<Key,Query> mQueries;
	
	public static Query get(Key key){return get(key,null);}
	public static Query get(Key key,String lang)
	{
		if(mQueries==null){mQueries = new Hashtable<Key,Query>();}
		if(!mQueries.containsKey(key))
		{
			Query q = new Query();
			switch(key)
			{
				case exTrafficLights: q.setTrafficLights(exTrafficLights());break;
				case exTrafficLight: q.setTrafficLight(exTrafficLight());break;
			}
			mQueries.put(key, q);
		}
		Query q = mQueries.get(key);
		q.setLang(lang);
		return q;
	}
	
	public static TrafficLights exTrafficLights()
	{		
		TrafficLights xml = XmlTrafficLightsFactory.build();
		xml.getTrafficLight().add(exTrafficLight());
		return xml;
	}
	
	public static TrafficLight exTrafficLight()
	{		
		TrafficLight xml = new TrafficLight();
		xml.setId(0);
		xml.setThreshold(0);
		xml.setScope(XmlScopeFactory.build(""));
		
		xml.setColorBackground("");
		xml.setColorText("");
		
		xml.setLangs(StatusQuery.langs());
		xml.setDescriptions(StatusQuery.descriptions());
		
		return xml;
	}
}
