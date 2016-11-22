package org.jeesl.factory.xml.mail;

import javax.mail.Message;
import javax.mail.MessagingException;

import net.sf.ahtutils.xml.mail.Header;
import net.sf.ahtutils.xml.mail.Mail;
import net.sf.ahtutils.xml.mail.Template;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlMailFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlEmailAddressFactory.class);
	
	public static Mail build(String code)
	{
		Mail xml = new Mail();
		xml.setCode(code);
		return xml;
	}
	
    public static Mail create(String code, String lang, String type)
    {
    	Template template = new Template();
    	template.setLang(lang);
    	template.setType(type);
    	
    	Mail mail = new Mail();
    	mail.setCode(code);
    	mail.getTemplate().add(template);
    	
    	return mail;
    }
    
    public static Mail create(Header header, String content)
    {   	   	
    	Mail mail = new Mail();
    	mail.setHeader(header);
    	mail.setExample(content);
    	return mail;
    }
    
    public Mail build(Message message) throws MessagingException
    {
    	Mail mail = new Mail();
    	mail.setId(message.getMessageNumber());
    	mail.setMsgId(message.getHeader("Message-ID")[0]);
    	
    	XmlHeaderFactory f = new XmlHeaderFactory();
    	mail.setHeader(f.build(message));
    	
    	return mail;
    }
}