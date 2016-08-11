package org.jeesl.model.xml.mail;

import org.jeesl.UtilsXmlTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.mail.Mail;

public class TestXmlMail extends AbstractXmlMailTest<Mail>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlMail.class);
	
	public TestXmlMail(){super(Mail.class);}
	public static Mail create(boolean withChildren){return (new TestXmlMail()).build(withChildren);}
	
    public Mail build(boolean withChilds)
    {
    	Mail xml = new Mail();
    	xml.setId(123);
    	xml.setCode("myCode");
    	xml.setLang("myLang");
    	xml.setType("myType");
    	xml.setMsgId("myMsgID");
    	xml.setDir("myDir");
    	xml.setExample("myExample");
    	xml.setTest(true);
    	
    	if(withChilds)
    	{
    		xml.setMail(TestXmlMail.create(false));
    		xml.setHeader(TestXmlHeader.create(false));
    		xml.getTemplate().add(TestXmlTemplate.create(false));
    		xml.getTemplate().add(TestXmlTemplate.create(false));
    		xml.getAttachment().add(TestXmlAttachment.create(false));xml.getAttachment().add(TestXmlAttachment.create(false));
    		xml.getImage().add(TestXmlImage.create(false));xml.getImage().add(TestXmlImage.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		TestXmlMail test = new TestXmlMail();
		test.saveReferenceXml();
    }
}