package org.jeesl.util.query.xml;

import java.util.Hashtable;
import java.util.Map;

import org.jeesl.factory.xml.system.status.XmlCategoryFactory;
import org.jeesl.factory.xml.system.status.XmlTypeFactory;
import org.jeesl.model.xml.system.revision.Attribute;
import org.jeesl.model.xml.system.revision.Entity;

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
		xml.setLangs(XmlStatusQuery.langs());
		xml.setDescriptions(XmlStatusQuery.descriptions());
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
		xml.setUi(true);
		xml.setBean(true);
		xml.setConstruction(true);
		
		xml.setType(XmlTypeFactory.create(""));
		
		xml.setLangs(XmlStatusQuery.langs());
		xml.setDescriptions(XmlStatusQuery.descriptions());
		xml.setRemark(XmlRemarkFactory.build(""));
		
		return xml;
	}
}