package org.jeesl.mail.smtp;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.Random;

import javax.activation.MimetypesFileTypeMap;
import javax.mail.MessagingException;

import net.sf.ahtutils.exception.processing.UtilsMailException;
import net.sf.ahtutils.exception.processing.UtilsProcessingException;
import net.sf.exlp.interfaces.util.ConfigKey;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.configuration.Configuration;
import org.jeesl.factory.xml.system.io.mail.XmlAttachmentFactory;
import org.jeesl.factory.xml.system.io.mail.XmlHeaderFactory;
import org.jeesl.factory.xml.system.io.mail.XmlMailFactory;
import org.jeesl.mail.JeeslMailTestBootstrap;
import org.jeesl.mail.XmlMailSender;
import org.jeesl.mail.freemarker.FreemarkerEngine;
import org.jeesl.model.xml.system.io.mail.Header;
import org.jeesl.model.xml.system.io.mail.Mail;
import org.jeesl.model.xml.system.io.mail.Mails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CliXmlMailer
{
	final static Logger logger = LoggerFactory.getLogger(CliXmlMailer.class);
	
	private XmlMailSender xmlMailer;
	
	private Configuration config;
	private String from,to;
	
	public CliXmlMailer(Configuration config) throws FileNotFoundException
	{
		this.config=config;
		
		Mails xmlMailsDefinition = JaxbUtil.loadJAXB("mails.ahtutils-mail.test/mails.xml", Mails.class);
		FreemarkerEngine fme = new FreemarkerEngine(xmlMailsDefinition);
		
		xmlMailer = new XmlMailSender(fme,config.getString(ConfigKey.netSmtpHost));
		from = config.getString("net.smtp.test.from");
		to = config.getString("net.smtp.test.to");
	}
	
	public void tlsAuthenticate()
	{
		xmlMailer.tlsPasswordAuthentication(config.getString(ConfigKey.netSmtpUser.toString()), config.getString(ConfigKey.netSmtpPwd.toString()));
	}
	
	public void sendMsg(int i) throws MessagingException, UnsupportedEncodingException, UtilsProcessingException, UtilsMailException
	{
		Mail container = new Mail();
		
		Header header = XmlHeaderFactory.create(CliXmlMailer.class.getSimpleName()+" "+i, from, to);
		Mail mail = XmlMailFactory.create(header, "test only");
		mail.setLang("de");
		mail.setCode("test");
		
		Mail mailContent = new Mail();
		mailContent.setExample("mYTest");
		mail.setMail(mailContent);
		
		byte[] data = new byte[10];
		Random rnd = new Random();
		rnd.nextBytes(data);
		
		String fName = "x.pdf";
		String mimeType = MimetypesFileTypeMap.getDefaultFileTypeMap().getContentType(fName);
		
		mail.getAttachment().add(XmlAttachmentFactory.create(fName, mimeType, data));
		
		container.setMail(mail);
		xmlMailer.send(JaxbUtil.toDocument(container));
	}
	
	public static void main (String[] args) throws Exception
	{
		Configuration config = JeeslMailTestBootstrap.init();
		
		CliXmlMailer cli = new CliXmlMailer(config);
		cli.tlsAuthenticate();
		cli.sendMsg(1);
	}
}