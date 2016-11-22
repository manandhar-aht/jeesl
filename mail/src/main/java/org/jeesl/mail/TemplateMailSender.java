package org.jeesl.mail;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.jeesl.mail.content.XmlMimeContentCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.mail.Mail;

public class TemplateMailSender extends AbstractMailSender
{
	final static Logger logger = LoggerFactory.getLogger(TemplateMailSender.class);
	

	public TemplateMailSender(String smtpHost){this(smtpHost,25);}
	public TemplateMailSender(String smtpHost, int smtpPort)
	{
		super(smtpHost,smtpPort);
	}

	public void send(Mail mail) throws MessagingException
	{
		Session session = buildSession();
		
		MimeMessage msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(mail.getHeader().getFrom().getEmailAddress().getEmail()));

		MimeMessageCreator mmc = new MimeMessageCreator(msg);
		try {
			mmc.createHeader(mail.getHeader());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		XmlMimeContentCreator mcc = new XmlMimeContentCreator(msg);
		mcc.createContent(mail);
		
		Transport.send(msg);
	}
}