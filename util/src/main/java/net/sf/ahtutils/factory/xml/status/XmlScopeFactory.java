package net.sf.ahtutils.factory.xml.status;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.xml.status.Scope;

import org.jeesl.factory.xml.system.lang.XmlLangsFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlScopeFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlScopeFactory.class);
		
	private Scope q;
	private String lang;
	
	public XmlScopeFactory(Scope q){this(null,q);}
	public XmlScopeFactory(String lang, Scope q)
	{
		this.q=q;
		this.lang=lang;
	}
	
	public <S extends UtilsStatus<S,L,D>,L extends UtilsLang, D extends UtilsDescription> Scope build(S ejb)
	{
		Scope xml = new Scope();
		if(q.isSetCode()){xml.setCode(ejb.getCode());}
		if(q.isSetPosition()){xml.setPosition(ejb.getPosition());}
		if(q.isSetLangs())
		{
			XmlLangsFactory<L> f = new XmlLangsFactory<L>(q.getLangs());
			xml.setLangs(f.getUtilsLangs(ejb.getName()));
		}
		if(q.isSetDescriptions())
		{

		}
		
		if(q.isSetLabel() && lang!=null && ejb.getName()!=null && ejb.getName().containsKey(lang))
		{
			xml.setLabel(ejb.getName().get(lang).getLang());
		}
		
		return xml;
	}
	
	public static Scope build(String code)
	{
		Scope xml = new Scope();
		xml.setCode(code);
		return xml;
	}
}