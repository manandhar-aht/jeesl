package net.sf.ahtutils.factory.xml.status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.xml.aht.Query;
import net.sf.ahtutils.xml.status.Status;
import net.sf.ahtutils.xml.status.Type;

public class XmlTypeFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlTypeFactory.class);
		
	private String lang;
	private Type q;
	
	public XmlTypeFactory(Query q){this(q.getLang(),q.getType());}
	public XmlTypeFactory(Type q){this(null,q);}
	public XmlTypeFactory(String lang,Type q)
	{
		this.lang=lang;
		this.q=q;
	}
	
	public <S extends UtilsStatus<S,L,D>,L extends UtilsLang, D extends UtilsDescription> Type build(S ejb){return build(ejb,null);}
	public <S extends UtilsStatus<S,L,D>,L extends UtilsLang, D extends UtilsDescription> Type build(S ejb, String group)
	{
		Type xml = new Type();
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
			XmlDescriptionsFactory<D> f = new XmlDescriptionsFactory<D>(q.getDescriptions());
			xml.setDescriptions(f.create(ejb.getDescription()));
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
	
	public static Type build(String code,String label){return create(null,code,label);}
	
	public static <E extends Enum<E>> Type build(E code)
	{
		return create(code.toString());
	}
	public static Type create(String code){return create(null,code);}
	public static Type create(String key, String code){return create(key,code,null);}
	public static Type create(String key, String code, String label)
	{
		Type xml = new Type();
		xml.setKey(key);
		xml.setCode(code);
		xml.setLabel(label);
		return xml;
	}
	
	public static Type id()
	{
		Type xml = new Type();
		xml.setId(0);
		return xml;
	}
	
	public static Type build(Status status)
	{
		Type type = new Type();
		type.setCode(status.getCode());
		type.setDescriptions(status.getDescriptions());
		type.setLangs(status.getLangs());
		if(status.isSetParent()){type.setParent(status.getParent());}
		return type;
	}
	
	public static Type buildLabel(String code, String label)
	{
		Type type = new Type();
		type.setCode(code);
		type.setLabel(label);
		return type;
	}
}