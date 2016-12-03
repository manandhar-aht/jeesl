package org.jeesl.util.query.xml;

import java.util.Hashtable;
import java.util.Map;

import org.jeesl.factory.xml.system.status.XmlTypeFactory;

import net.sf.ahtutils.xml.aht.Query;
import net.sf.ahtutils.xml.symbol.Graphic;
import net.sf.exlp.xml.io.Data;
import net.sf.exlp.xml.io.File;

public class SymbolQuery
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
		File f = new File();
		f.setData(new Data());
		
		Graphic g = new Graphic();
		g.setId(0);
		
		g.setType(XmlTypeFactory.create(""));
		g.setFile(f);
		
		return g;
	}	
}