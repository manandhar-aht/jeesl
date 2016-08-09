package org.jeesl.mail;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import net.sf.ahtutils.xml.mail.Bcc;
import net.sf.ahtutils.xml.mail.Cc;
import net.sf.ahtutils.xml.mail.EmailAddress;
import net.sf.ahtutils.xml.mail.From;
import net.sf.ahtutils.xml.mail.Header;
import net.sf.ahtutils.xml.mail.To;

public class MimeMessageCreator
{
	private MimeMessage msg;
	private String encoding;
	
	public MimeMessageCreator(MimeMessage msg)
	{
		this("ISO-8859-1",msg);
	}
	
	public MimeMessageCreator(String encoding, MimeMessage msg)
	{
		this.msg=msg;
		this.encoding=encoding;
	}
	
	public void createHeader(Header header) throws UnsupportedEncodingException, MessagingException
	{
		createFrom(header.getFrom());
		createTo(header.getTo());
		if(header.isSetCc()){createCc(header.getCc());}
		if(header.isSetBcc()){createBcc(header.getBcc());}
		
		msg.setSubject(header.getSubject(),encoding);
		msg.setSentDate(new Date());
	}
	
	private void createFrom(From from) throws UnsupportedEncodingException, MessagingException
	{
		InternetAddress fromAddress = create(from.getEmailAddress());
		
		msg.setFrom(fromAddress);
	}
	
	private void createTo(To to) throws UnsupportedEncodingException, MessagingException
	{
		Address[] addresses = new Address[to.getEmailAddress().size()];
		for(int i=0; i<addresses.length;i++)
		{
			EmailAddress xmlAddress = to.getEmailAddress().get(i);
			addresses[i] = create(xmlAddress);	
		}
		msg.setRecipients(Message.RecipientType.TO,addresses);
	}
	
	private void createCc(Cc cc) throws UnsupportedEncodingException, MessagingException
	{
		Address[] addresses = new Address[cc.getEmailAddress().size()];
		for(int i=0; i<addresses.length;i++)
		{
			EmailAddress xmlAddress = cc.getEmailAddress().get(i);
			addresses[i] = create(xmlAddress);	
		}
		msg.setRecipients(Message.RecipientType.CC,addresses);
	}
	
	private void createBcc(Bcc bcc) throws UnsupportedEncodingException, MessagingException
	{
		Address[] addresses = new Address[bcc.getEmailAddress().size()];
		for(int i=0; i<addresses.length;i++)
		{
			EmailAddress xmlAddress = bcc.getEmailAddress().get(i);
			addresses[i] = create(xmlAddress);	
		}
		msg.setRecipients(Message.RecipientType.BCC,addresses);
	}
	
	private InternetAddress create(EmailAddress xmlAddress) throws UnsupportedEncodingException
	{
		InternetAddress ia = new InternetAddress();
		ia.setAddress(xmlAddress.getEmail());
		if(xmlAddress.isSetName()){ia.setPersonal(xmlAddress.getName());}
		return ia;
	}
}