package net.sf.ahtutils.factory.xml.status;

import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.util.comparator.xml.status.LangComparator;
import net.sf.ahtutils.xml.status.Lang;
import net.sf.ahtutils.xml.status.Langs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlLangsFactory <L extends UtilsLang>
{
	final static Logger logger = LoggerFactory.getLogger(XmlLangsFactory.class);
		
	private static Comparator<Lang> comparator = LangComparator.factory(LangComparator.Type.key);
	
	private Langs q;
	
	public XmlLangsFactory(Langs q)
	{
		this.q=q;
	}
	
	public Langs getUtilsLangs(Map<String,L> mapLangs)
	{
		Langs xml = new Langs();
		
		if(q.isSetLang())
		{
			XmlLangFactory<L> f = new XmlLangFactory<L>(q.getLang().get(0));
			for(L ahtLang : mapLangs.values())
			{
				xml.getLang().add(f.getUtilsLang(ahtLang));
			}
		}
		Collections.sort(xml.getLang(), comparator);
		return xml;
	}
	
	public static Langs build()
	{
		return new Langs();
	}
	
	public static Langs build(String[] langs)
	{
		Langs xml = build();
		for(String lang : langs)
		{
			xml.getLang().add(XmlLangFactory.create(lang,""));
		}
		return xml;
	}
}