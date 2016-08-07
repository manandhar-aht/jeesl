package net.sf.ahtutils.xml.mail;

import org.jeesl.UtilsXmlTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
		UtilsXmlTestBootstrap.init();
		TestXmlMails test = new TestXmlMails();
		test.saveReferenceXml();
    }
}