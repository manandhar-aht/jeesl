package org.jeesl.mail.freemarker;

import net.sf.ahtutils.xml.mail.Mail;
import net.sf.ahtutils.xml.mail.Mails;

import org.jeesl.factory.xml.mail.XmlMailFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FreemarkerConfigBuilder
{
	final static Logger logger = LoggerFactory.getLogger(FreemarkerConfigBuilder.class);

	@SuppressWarnings("unused")
	private Mails mails;
	
	public FreemarkerConfigBuilder(Mails mails)
	{
		this.mails=mails;
	}
	
	public Mail build(String id, String lang, String type)
	{
		Mail mail = XmlMailFactory.create(id, lang, type);
		return mail;
	}
}
