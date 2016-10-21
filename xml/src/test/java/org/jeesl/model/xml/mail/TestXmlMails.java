package org.jeesl.model.xml.mail;

import org.jeesl.JeeslXmlTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.mail.Mails;

public class TestXmlMails extends AbstractXmlMailTest<Mails>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlMails.class);
	
	public TestXmlMails(){super(Mails.class);}
	public static Mails create(boolean withChildren){return (new TestXmlMails()).build(withChildren);}
	
    public Mails build(boolean withChilds)
    {
    	Mails xml = new Mails();
    	xml.setDir("myDir");
    	
    	if(withChilds)
    	{
    		xml.getMail().add(TestXmlMail.create(false));
    	}
    	    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		JeeslXmlTestBootstrap.init();
		TestXmlMails test = new TestXmlMails();
		test.saveReferenceXml();
    }
}