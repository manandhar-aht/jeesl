package org.jeesl.factory.xml.system.io.report;

import org.jeesl.interfaces.model.system.io.report.JeeslIoReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.report.Footer;

public class XmlFooterFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlFooterFactory.class);
		
	public static Footer build(JeeslIoReport.FooterOrientation orientation, String  value)
	{
		Footer xml = new Footer();
		xml.setOrientation(orientation.toString());
		xml.setValue(value);
		return xml;
	}
}