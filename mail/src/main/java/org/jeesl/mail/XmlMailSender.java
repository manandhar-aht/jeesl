package org.jeesl.mail;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import net.sf.ahtutils.exception.processing.UtilsMailException;
import net.sf.ahtutils.exception.processing.UtilsProcessingException;
import net.sf.ahtutils.xml.mail.Bcc;
import net.sf.ahtutils.xml.mail.EmailAddress;
import net.sf.ahtutils.xml.mail.Header;
import net.sf.ahtutils.xml.mail.Mail;
import net.sf.exlp.util.xml.JDomUtil;

import org.jdom2.Content;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jeesl.mail.content.FreemarkerMimeContentCreator;
import org.jeesl.mail.content.XmlMimeContentCreator;
import org.jeesl.mail.freemarker.FreemarkerEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlMailSender
{
	final static Logger logger = LoggerFactory.getLogger(XmlMailSender.class);
	
	private String smtpHost,smtpUser,smtpPassword;
	private int smtpPort;
	
	private boolean tlsPwd,plainPwd;
	private boolean smtpDebug;
	
	private FreemarkerEngine fme;
	private List<EmailAddress> alwaysBcc;
	private EmailAddress overrideOnlyTo;
	
	public void setOverrideOnlyTo(EmailAddress overrideOnlyTo) {this.overrideOnlyTo = overrideOnlyTo;}
	public void addBcc(EmailAddress bcc){alwaysBcc.add(bcc);}
	private Namespace nsMail;
	
	@Deprecated
	public XmlMailSender(String smtpHost)
	{
		this(null,smtpHost);
	}
	public XmlMailSender(FreemarkerEngine fme,String smtpHost){this(fme,smtpHost,25);}
	public XmlMailSender(FreemarkerEngine fme,String smtpHost, int smtpPort)
	{
		this.fme=fme;
		this.smtpHost=smtpHost;
		this.smtpPort=smtpPort;
		alwaysBcc = new ArrayList<EmailAddress>();
		overrideOnlyTo = null;
		
		tlsPwd = false;
		plainPwd = false;
		smtpDebug = false;
		
		smtpUser=null;
		smtpPassword=null;
		
		nsMail = Namespace.getNamespace("http://ahtutils.aht-group.com/mail");
	}
	
	public void tlsPasswordAuthentication(String smtpUser, String smtpPassword)
	{
		this.smtpUser=smtpUser;
		this.smtpPassword=smtpPassword;
		tlsPwd = true;
	}
	
	public void plainPasswordAuthentication(String smtpUser, String smtpPassword)
	{
		this.smtpUser=smtpUser;
		this.smtpPassword=smtpPassword;
		plainPwd = true;
	}
	
	private Session buildSession()
	{
		Properties props = System.getProperties();
		props.put("mail.smtp.host", smtpHost);
		props.put("mail.smtp.port", smtpPort);
		props.put("mail.smtp.auth", "false");
		Session session;
		
		if(tlsPwd)
		{
			props.put("mail.transport.protocol","smtp");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.tls", "true");
			props.put("mail.smtp.user", smtpUser);
			props.put("mail.password", smtpPassword);
			
			Authenticator auth = new Authenticator()
			{
				@Override public PasswordAuthentication getPasswordAuthentication()
				{
					return new PasswordAuthentication(smtpUser,smtpPassword);
				}
			};
			session = Session.getInstance(props, auth);
		}
		else if(plainPwd)
		{
			props.put("mail.transport.protocol","smtp");
			props.put("mail.smtp.auth", "true");
//			props.put("mail.smtp.starttls.enable", "true");
//			props.put("mail.smtp.tls", "true");
			props.put("mail.smtp.user", smtpUser);
			props.put("mail.password", smtpPassword);
			
			Authenticator auth = new Authenticator()
			{
				@Override public PasswordAuthentication getPasswordAuthentication()
				{
					return new PasswordAuthentication(smtpUser,smtpPassword);
				}
			};
			session = Session.getInstance(props, auth);
		}
		else
		{
			session = Session.getInstance(props, null);
		}		
		session.setDebug(smtpDebug);
		return session;
	}
	
	public void send(Mail mail) throws MessagingException, UnsupportedEncodingException
	{
		Session session = buildSession();
		
		MimeMessage msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(mail.getHeader().getFrom().getEmailAddress().getEmail()));

		MimeMessageCreator mmc = new MimeMessageCreator(msg);
		mmc.createHeader(mail.getHeader());
				
		XmlMimeContentCreator mcc = new XmlMimeContentCreator(msg);
		mcc.createContent(mail);
		
		Transport.send(msg);
	}
	
	@Deprecated
	public void send(FreemarkerEngine fme, String lang, Document doc) throws UnsupportedEncodingException, MessagingException, UtilsProcessingException, UtilsMailException
	{
		this.fme=fme;
		send(lang, doc);
	}
	
	@Deprecated
	public void send(String lang, Document doc) throws UnsupportedEncodingException, MessagingException, UtilsProcessingException, UtilsMailException
	{
		send(doc,lang);
	}
	public void send(Document doc) throws UnsupportedEncodingException, MessagingException, UtilsProcessingException, UtilsMailException
	{
		send(doc,null);
	}
	
	private void send(Document doc, String lang) throws UnsupportedEncodingException, MessagingException, UtilsProcessingException, UtilsMailException
	{
		Session session = buildSession();
		
		MimeMessage message = new MimeMessage(session);
		MimeMessageCreator mmc = new MimeMessageCreator(message);
		
		Header header = getHeader(doc.getRootElement());
		if(overrideOnlyTo!=null)
		{
			header.setBcc(null);
			header.setCc(null);
			header.getTo().getEmailAddress().clear();
			header.getTo().getEmailAddress().add(overrideOnlyTo);
		}
		else
		{
			if(!header.isSetBcc()){header.setBcc(new Bcc());}
			header.getBcc().getEmailAddress().addAll(alwaysBcc);
		}
		mmc.createHeader(header);
		
		Mail mail = getMailAndDetachAtt(doc.getRootElement());
		
		if(!mail.isSetLang())
		{
			if(lang!=null){mail.setLang(lang);}
			else{mail.setLang("de");}
			logger.warn("No @lang is set in this mail! Setting to "+mail.getLang());
		}
		
		FreemarkerMimeContentCreator mcc = new FreemarkerMimeContentCreator(message, fme);
		mcc.createContent(doc,mail);
		
		Transport transport = session.getTransport("smtp");
		transport.connect();
		transport.sendMessage(message, message.getAllRecipients());
		transport.close();
	}
	
	private Header getHeader(Element root) throws UtilsProcessingException
	{
		logger.trace("Parsing header");
		for(Object o: root.getContent())
		{
			Element e = (Element)o;
			if(e.getName().equals("header"))
			{
				logger.warn("This should be avaoided, see UTILS-200");
				return JDomUtil.toJaxb(e, Header.class);
			}
		}
		Element mail = root.getChild("mail", nsMail);
		if(mail!=null)
		{
			Element header = mail.getChild("header", nsMail);
			if(header!=null){return JDomUtil.toJaxb(header, Header.class);}
		}
		logger.info(mail.toString());
		throw new UtilsProcessingException("No <header> (or <mail><header/></mail>) Element found");
	}
	
	private Mail getMailAndDetachAtt(Element root) throws UtilsProcessingException
	{
		logger.trace("Parsing Mail");
		for(Content content: root.getContent())
		{
			Element e = (Element)content;
			if(e.getName().equals("mail"))
			{
//				for(Element att : e.getChildren("attachment", nsMail))
				{
		//			att.detach();
				}
				return JDomUtil.toJaxb(e, Mail.class);
			}
		}
		throw new UtilsProcessingException("No <mail> Element found");
	}
	
	public void setSmtpDebug(boolean smtpDebug) {this.smtpDebug = smtpDebug;}
	
	public void debugSettings()
	{
		logger.info("Host: "+smtpHost);
		logger.info("User: "+smtpUser);
		logger.info("Pwd: "+smtpPassword);
	}
}