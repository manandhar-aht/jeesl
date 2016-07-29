package org.jeesl.util.query.xml;

import java.util.Hashtable;
import java.util.Map;

import org.jeesl.model.xml.system.revision.Attribute;
import org.jeesl.model.xml.system.revision.Entity;

import net.sf.ahtutils.controller.util.query.StatusQuery;
import net.sf.ahtutils.factory.xml.status.XmlCategoryFactory;
import net.sf.ahtutils.factory.xml.status.XmlTypeFactory;
import net.sf.ahtutils.factory.xml.text.XmlRemarkFactory;
import net.sf.ahtutils.xml.aht.Query;

public class RevisionQuery
{
	public static enum Key {exEntity}
	
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
				case exEntity: q.setEntity(exEntity());break;
			}
			mQueries.put(key, q);
		}
		Query q = mQueries.get(key);
		q.setLang(lang);
		return q;
	}
	
	public static Entity exEntity()
	{		
		Entity xml = new Entity();
		xml.setCode("");
		xml.setPosition(0);
		xml.setVisible(true);
		xml.setCategory(XmlCategoryFactory.create(""));
		xml.setLangs(StatusQuery.langs());
		xml.setDescriptions(StatusQuery.descriptions());
		xml.setRemark(XmlRemarkFactory.build(""));
		xml.getAttribute().add(exAttribute());
		return xml;
	}
	
	public static Attribute exAttribute()
	{		
		Attribute xml = new Attribute();
		xml.setCode("");
		xml.setPosition(0);
		xml.setXpath("");
		
		xml.setWeb(true);
		xml.setPrint(true);
		xml.setName(true);
		xml.setEnclosure(true);
		
		xml.setType(XmlTypeFactory.create(""));
		
		xml.setLangs(StatusQuery.langs());
		xml.setDescriptions(StatusQuery.descriptions());
		
		return xml;
	}
}