package org.jeesl.factory.xml.system.symbol;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.symbol.Size;

public class XmlSizeFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlSizeFactory.class);
		
	public static <E extends Enum<E>> Size build(E group, int value){return build(group.toString(),value);}
	public static Size build(String group, int value)
	{
		Size xml = new Size();
		xml.setGroup(group);
		xml.setValue(value);
		return xml;
	}
}