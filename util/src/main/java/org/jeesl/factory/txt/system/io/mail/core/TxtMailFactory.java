package org.jeesl.factory.txt.system.io.mail.core;

import org.jeesl.model.xml.system.io.mail.Mail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TxtMailFactory
{
	final static Logger logger = LoggerFactory.getLogger(TxtMailFactory.class);
		
	public static String debug(Mail mail)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(mail.getHeader().getSubject());
		return sb.toString();
	}
}