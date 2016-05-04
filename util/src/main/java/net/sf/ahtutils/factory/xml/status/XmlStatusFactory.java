package net.sf.ahtutils.factory.xml.status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.xml.status.Parent;
import net.sf.ahtutils.xml.status.Status;

public class XmlStatusFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlStatusFactory.class);
		
	private Status q;
	private String lang;
	
	public XmlStatusFactory(Status q){this(null,q);}
	public XmlStatusFactory(String lang,Status q)
	{
		this.lang=lang;
		this.q=q;
	}
	
	public <S extends UtilsStatus<S,L,D>, L extends UtilsLang, D extends UtilsDescription> Status build(S ejb){return build(ejb,null);}
	public <S extends UtilsStatus<S,L,D>, L extends UtilsLang, D extends UtilsDescription> Status build(S ejb, String group)
	{
		Status xml = new Status();
		xml.setGroup(group);
		if(q.isSetId()){xml.setId(ejb.getId());}
		if(q.isSetCode()){xml.setCode(ejb.getCode());}
		if(q.isSetPosition()){xml.setPosition(ejb.getPosition());}
		if(q.isSetStyle()){xml.setStyle(ejb.getStyle());}
		if(q.isSetImage()){xml.setImage(ejb.getImage());}
		if(q.isSetVisible()){xml.setVisible(ejb.isVisible());}
		
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
		
		if(q.isSetParent() && ejb.getParent()!=null)
		{
			Parent parent = new Parent();
			parent.setCode(ejb.getParent().getCode());
			xml.setParent(parent);
		}
		
		return xml;
	}
	
	public static <E extends Enum<E>> Status build(E code)
	{
		return create(code.toString());
	}
	
	public static Status create(String code)
	{
		Status xml = new Status();
		xml.setCode(code);
		return xml;
	}
	
	public static Status buildLabel(String code, String label)
	{
		Status xml = new Status();
		xml.setCode(code);
		xml.setLabel(label);
		return xml;
	}
	
	public static Status id()
	{
		Status xml = new Status();
		xml.setId(0);
		return xml;
	}
}