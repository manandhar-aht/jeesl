package net.sf.ahtutils.factory.xml.status;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.xml.status.Level;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlLevelFactory <S extends UtilsStatus<S,L,D>,L extends UtilsLang, D extends UtilsDescription>
{
	final static Logger logger = LoggerFactory.getLogger(XmlLevelFactory.class);
		
	private Level q;
	
	public XmlLevelFactory(Level q)
	{
		this.q=q;
	}
	
	public Level build(S ejb){return build(ejb,null);}
	public Level build(S ejb, String group)
	{
		Level xml = new Level();
		if(q.isSetCode()){xml.setCode(ejb.getCode());}
		if(q.isSetPosition()){xml.setPosition(ejb.getPosition());}
		xml.setGroup(group);
		
		if(q.isSetLangs())
		{
			XmlLangsFactory<L> f = new XmlLangsFactory<L>(q.getLangs());
			xml.setLangs(f.getUtilsLangs(ejb.getName()));
		}
		if(q.isSetDescriptions())
		{

		}
		
		return xml;
	}
	
	public static Level build(String code)
	{
		Level xml = new Level();
		xml.setCode(code);
		return xml;
	}
	
	public static Level build(String code, String label)
	{
		Level xml = build(code);
		xml.setLabel(label);
		return xml;
	}
}