package org.jeesl.factory.xml.system.io.attribute;

import org.jeesl.interfaces.model.module.attribute.JeeslAttributeCriteria;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeData;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeOption;
import org.jeesl.model.xml.system.io.attribute.Attribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public class XmlAttributeFactory <L extends UtilsLang, D extends UtilsDescription,
									CRITERIA extends JeeslAttributeCriteria<L,D,?,?>,
									OPTION extends JeeslAttributeOption<L,D,CRITERIA>,
									DATA extends JeeslAttributeData<CRITERIA,OPTION,?>>
{
	final static Logger logger = LoggerFactory.getLogger(XmlAttributeFactory.class);
	
	private final XmlOptionFactory<L,D,OPTION> xfOption;
	
	public XmlAttributeFactory(String localeCode)
	{
		xfOption = new XmlOptionFactory<L,D,OPTION>(localeCode);
	}
	
	public static Attribute build(){return new Attribute();}
	
	public Attribute build(DATA data)
	{
		Attribute xml = build();
			
		if(data.getCriteria().getType().getCode().equals(JeeslAttributeCriteria.Types.selectOne.toString()) && data.getValueOption()!=null) {xml.setOption(xfOption.build(data.getValueOption()));}
		
		return xml;
	}
}