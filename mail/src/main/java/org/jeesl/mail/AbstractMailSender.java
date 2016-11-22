package org.jeesl.mail;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import org.jdom2.Namespace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.mail.EmailAddress;

public abstract class AbstractMailSender
{
	final static Logger logger = LoggerFactory.getLogger(AbstractMailSender.class);
	
	private String smtpHost,smtpUser,smtpPassword;
	private int smtpPort;
	
	private boolean tlsPwd,plainPwd;
	private boolean smtpDebug; public void setSmtpDebug(boolean smtpDebug) {this.smtpDebug = smtpDebug;}

	protected List<EmailAddress> alwaysBcc; public void addBcc(EmailAddress bcc){alwaysBcc.add(bcc);}
	protected EmailAddress overrideOnlyTo; public void setOverrideOnlyTo(EmailAddress overrideOnlyTo) {this.overrideOnlyTo = overrideOnlyTo;}
	
	protected Namespace nsMail;
	
	public AbstractMailSender(String smtpHost, int smtpPort)
	{
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
	
	
	public void debugSettings()
	{
		logger.info("Host: "+smtpHost);
		logger.info("User: "+smtpUser);
		logger.info("Pwd: "+smtpPassword);
	}
	
	protected Session buildSession()
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
}