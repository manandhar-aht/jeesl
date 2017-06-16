package net.sf.ahtutils.factory.xml.status;

import org.jeesl.factory.xml.system.lang.XmlDescriptionsFactory;
import org.jeesl.factory.xml.system.lang.XmlLangsFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class XmlProcessFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlProcessFactory.class);
		
	private net.sf.ahtutils.xml.status.Process q;
	private String lang;
	
	public XmlProcessFactory(net.sf.ahtutils.xml.status.Process q){this(null,q);}
	public XmlProcessFactory(String lang, net.sf.ahtutils.xml.status.Process q)
	{
		this.lang=lang;
		this.q=q;
	}
	
	public <S extends UtilsStatus<S,L,D>, L extends UtilsLang, D extends UtilsDescription> net.sf.ahtutils.xml.status.Process build(S ejb){return build(ejb,null);}
	public <S extends UtilsStatus<S,L,D>, L extends UtilsLang, D extends UtilsDescription> net.sf.ahtutils.xml.status.Process build(S ejb, String group)
	{
		net.sf.ahtutils.xml.status.Process xml = new net.sf.ahtutils.xml.status.Process();
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
		
		return xml;
	}
	
	public static net.sf.ahtutils.xml.status.Process build(String code)
	{
		net.sf.ahtutils.xml.status.Process xml = new net.sf.ahtutils.xml.status.Process();
		xml.setCode(code);
		return xml;
	}
}