package net.sf.ahtutils.factory.xml.status;

import java.util.ArrayList;
import java.util.List;

import org.jeesl.factory.xml.system.lang.XmlLangsFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.xml.status.Status;
import net.sf.ahtutils.xml.status.SubType;

public class XmlSubTypeFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlSubTypeFactory.class);
		
	private String lang;
	private SubType q;
	
	public XmlSubTypeFactory(SubType q){this(null,q);}
	public XmlSubTypeFactory(String lang,SubType q)
	{
		this.lang=lang;
		this.q=q;
	}
	
	public <S extends UtilsStatus<S,L,D>,L extends UtilsLang, D extends UtilsDescription> SubType build(S ejb){return build(ejb,null);}
	public <S extends UtilsStatus<S,L,D>,L extends UtilsLang, D extends UtilsDescription> SubType build(S ejb, String group)
	{
		SubType xml = new SubType();
		if(q.isSetId()){xml.setId(ejb.getId());}
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
		
		if(q.isSetLabel() && lang!=null)
		{
			if(ejb.getName()!=null)
			{
				if(ejb.getName().containsKey(lang)){xml.setLabel(ejb.getName().get(lang).getLang());}
				else
				{
					String msg = "No translation "+lang+" available in "+ejb;
					logger.warn(msg);
					xml.setLabel(msg);
				}
			}
			else
			{
				String msg = "No @name available in "+ejb;
				logger.warn(msg);
				xml.setLabel(msg);
			}
		}
		
		return xml;
	}
	
	public static SubType id()
	{
		SubType xml = new SubType();
		xml.setId(0);
		return xml;
	}
	
	public static SubType build(String code)
	{
		SubType xml = new SubType();
		xml.setCode(code);
		return xml;
	}
	
	public static SubType buildLabel(String code, String label)
	{
		SubType xml = new SubType();
		xml.setCode(code);
		xml.setLabel(label);
		return xml;
	}
	
	public static SubType transform(Status status)
	{
		SubType type = new SubType();
		type.setCode(status.getCode());
		type.setDescriptions(status.getDescriptions());
		type.setLangs(status.getLangs());
//		if(status.isSetParent()){type.setParent(status.getParent());}
		return type;
	}
	
	public static List<Long> toIds(List<SubType> list)
	{
		List<Long> result = new ArrayList<Long>();
		for(SubType type : list)
		{
			if(type.isSetId()){result.add(type.getId());}
		}
		return result;
	}
}