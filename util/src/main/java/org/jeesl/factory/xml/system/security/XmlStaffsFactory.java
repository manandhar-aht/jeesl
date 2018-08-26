package org.jeesl.factory.xml.system.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.security.Staff;
import net.sf.ahtutils.xml.security.Staffs;

public class XmlStaffsFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlStaffsFactory.class);
			
	public static Staffs build() {return new Staffs();}
	
	public static Staffs build(Staff staff)
	{
		Staffs xml = build();
		xml.getStaff().add(staff);
		return xml;
	}
}