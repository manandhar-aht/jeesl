package org.jeesl.util.query.xml;

import java.util.Hashtable;
import java.util.Map;

import net.sf.ahtutils.xml.aht.Query;
import net.sf.ahtutils.xml.symbol.Graphic;

public class GraphicQuery
{
	public static enum Key {GraphicExport}
	
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
				case GraphicExport: q.setGraphic(exportGraphic());break;
			}
			mQueries.put(key, q);
		}
		Query q = mQueries.get(key);
		q.setLang(lang);
		return q;
	}
	
	private static Graphic exportGraphic()
	{
		Graphic g = new Graphic();
		g.setId(0);
		return g;
	}	
}