package org.jeesl.factory.xml.dev.qa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.qa.Checklist;
import net.sf.ahtutils.xml.security.Staff;

public class XmlChecklistFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlChecklistFactory.class);
	
	public static Checklist build(Staff staff)
	{
		Checklist xml = new Checklist();
		xml.setStaff(staff);
		return xml;
	}
}