package net.sf.ahtutils.controller.util.query;

import java.util.Hashtable;
import java.util.Map;

import net.sf.ahtutils.xml.aht.Query;
import net.sf.ahtutils.xml.status.Description;
import net.sf.ahtutils.xml.status.Descriptions;
import net.sf.ahtutils.xml.status.Lang;
import net.sf.ahtutils.xml.status.Langs;
import net.sf.ahtutils.xml.status.Parent;
import net.sf.ahtutils.xml.status.Scope;
import net.sf.ahtutils.xml.status.Scopes;
import net.sf.ahtutils.xml.status.Status;
import net.sf.ahtutils.xml.status.Type;

public class StatusQuery
{
	public static enum Key {StatusExport,Langs,extractType,statusLabel,typeLabel}
	
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
				case StatusExport: q.setStatus(statusExport());break;
				case Langs: q.setLangs(langs());break;
				case extractType: q.setType(extractType());break;
				case statusLabel: q.setStatus(statusLabel());break;
				case typeLabel: q.setType(typeLabel());break;
			}
			mQueries.put(key, q);
		}
		Query q = mQueries.get(key);
		q.setLang(lang);
		return q;
	}
	
	public static Status statusExport()
	{
		Parent parent = new Parent();
		parent.setId(0);
		parent.setCode("");
		parent.setPosition(0);
		
		Status xml = new Status();
		xml.setCode("");
		xml.setImage("");
		xml.setStyle("");
		xml.setPosition(0);
		xml.setVisible(true);
		xml.setLangs(langs());
		xml.setDescriptions(descriptions());
		xml.setParent(parent);
		return xml;
	}
	
	public static Status statusLabel()
	{		
		Status xml = new Status();
		xml.setId(0);
		xml.setCode("");
		xml.setLabel("");
		return xml;
	}
	
	public static Type typeLabel()
	{		
		Type xml = new Type();
		xml.setId(0);
		xml.setCode("");
		xml.setLabel("");
		return xml;
	}
	
	public static Scopes scopes()
	{
		Scope scope = new Scope();
		scope.setCode("");
		scope.setLabel("");
		
		Scopes scopes = new Scopes();
		scopes.getScope().add(scope);
		return scopes;
	}
	
	public static Descriptions descriptions()
	{
		Descriptions xml = new Descriptions();
		
		Description d = new Description();
		d.setKey("");
		d.setValue("");
		
		xml.getDescription().add(d);
		
    	return xml;
	}
	
	public static Langs langs()
	{
		Langs xml = new Langs();
		
		Lang l = new Lang();
		l.setKey("");
		l.setTranslation("");
		
		xml.getLang().add(l);
		
    	return xml;
	}
	
	public static Type extractType()
	{
		Type xml = new Type();
		xml.setCode("");
		xml.setLangs(langs());
		return xml;
	}
}
