package org.jeesl.factory.xml.system.io.report;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.report.Size;
import net.sf.ahtutils.xml.status.Type;

public class XmlSizeFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlSizeFactory.class);
	
	public static <E extends Enum<E>> Size build(E code, Type type, int value)
	{
		Size xml = new Size();
		xml.setCode(code.toString());
		xml.setType(type);
		xml.setValue(value);
		return xml;
	}
}