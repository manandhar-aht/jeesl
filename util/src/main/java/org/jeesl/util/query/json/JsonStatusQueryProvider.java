package org.jeesl.util.query.json;

import java.util.Hashtable;
import java.util.Map;

import org.jeesl.model.json.system.status.JsonStatus;

import net.sf.ahtutils.xml.aht.Query;

public class JsonStatusQueryProvider
{
	public static enum Key {StatusExport,Langs,extractType,statusLabel,typeLabel,categoryLabel}
	
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

			}
			mQueries.put(key, q);
		}
		Query q = mQueries.get(key);
		q.setLang(lang);
		return q;
	}
	
	public static JsonStatus statusExport()
	{				
		JsonStatus xml = new JsonStatus();
		xml.setId(Long.valueOf(0));
		xml.setCode("");
		return xml;
	}
}