package org.jeesl.factory.xml.system.io.attribute;

import org.jeesl.interfaces.model.module.attribute.JeeslAttributeOption;
import org.jeesl.model.xml.system.io.attribute.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public class XmlOptionFactory <L extends UtilsLang, D extends UtilsDescription,
									
									OPTION extends JeeslAttributeOption<L,D,?>>
{
	final static Logger logger = LoggerFactory.getLogger(XmlOptionFactory.class);
	
	private final String localeCode;
	
	public XmlOptionFactory(String localeCode)
	{
		this.localeCode=localeCode;
	}
	
	public static Option build(){return new Option();}

	
	public Option build(OPTION option)
	{
		Option xml = build();
		
		xml.setCode(option.getCode());
		xml.setLabel(option.getName().get(localeCode).getLang());
		
		return xml;
	}
}