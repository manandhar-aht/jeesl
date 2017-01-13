package org.jeesl.mail.freemarker;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import net.sf.ahtutils.exception.processing.UtilsDeveloperException;

import org.jeesl.mail.AbstractJeeslMailTest;
import org.jeesl.model.xml.system.io.mail.Mail;
import org.jeesl.model.xml.system.io.mail.Mails;
import org.jeesl.model.xml.system.io.mail.Template;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import freemarker.template.TemplateException;

public class TestFreemarkerEngine extends AbstractJeeslMailTest
{
	final static Logger logger = LoggerFactory.getLogger(TestFreemarkerEngine.class);
	
	private FreemarkerEngine fme;
	
	private Mails mails;
	
	@Before
	public void init()
	{	
		mails = new Mails();
		
		Mail mail = new Mail();
		mail.setCode("id");
		
		Template template = new Template();
		template.setLang("de");
		template.setType("html");
		
		mail.getTemplate().add(template);
		mails.getMail().add(mail);
		
		fme = new FreemarkerEngine(mails);
	}
	
	@After
	public void close()
	{
		fme = null;
	}
    
	@Ignore
    @Test(expected=UtilsDeveloperException.class)
    public void devException() throws SAXException, IOException, ParserConfigurationException, TemplateException
    {
    	fme.processXml("test");
    }
    
    @Test
    public void isAvailable() throws SAXException, IOException, ParserConfigurationException, TemplateException
    {
    	Assert.assertFalse(fme.isAvailable("null", "de", "txt"));
    	Assert.assertFalse(fme.isAvailable("id", "de", "txt"));
    	Assert.assertTrue(fme.isAvailable("id", "de", "html"));
    }
}