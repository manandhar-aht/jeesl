package net.sf.ahtutils.factory.xml.status;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.xml.status.Gender;

import org.jeesl.factory.xml.system.lang.XmlLangsFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlGenderFactory <S extends UtilsStatus<S,L,D>,L extends UtilsLang, D extends UtilsDescription>
{
	final static Logger logger = LoggerFactory.getLogger(XmlGenderFactory.class);
		
	private static boolean errorPrinted = false;
	
	private String localeCode;
	private Gender q;
	
	public XmlGenderFactory(String localeCode, Gender q)
	{
		this.localeCode=localeCode;
		this.q=q;
	}
	
	public Gender build(S ejb){return build(ejb,null);}
	public Gender build(S ejb, String group)
	{
		Gender xml = new Gender();
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
		if(q.isSetLabel() && localeCode!=null)
		{
			if(ejb.getName()!=null)
			{
				if(ejb.getName().containsKey(localeCode)){xml.setLabel(ejb.getName().get(localeCode).getLang());}
				else
				{
					String msg = "No translation "+localeCode+" available in "+ejb;
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
		else if(q.isSetLabel() && localeCode==null)
		{
			logger.warn("Should render label, but localeCode is null");
			if(!errorPrinted)
			{
				logger.warn("This StackTrace will only shown once!");
				for (StackTraceElement ste : Thread.currentThread().getStackTrace())
				{
				    System.err.println(ste);
				}
				errorPrinted=true;
			}
		}
		return xml;
	}
	
	public static Gender female() {return build("female");}
	public static Gender male() {return build("male");}
	public static Gender build(String code){return build(code,null);}
	public static Gender build(String code,String label)
	{
		Gender xml = new Gender();
		xml.setCode(code);
		xml.setLabel(label);
		return xml;
	}
}