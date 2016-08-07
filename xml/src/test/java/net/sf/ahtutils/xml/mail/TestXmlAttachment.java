package net.sf.ahtutils.xml.mail;

import org.jeesl.UtilsXmlTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlAttachment extends AbstractXmlMailTest<Attachment>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlAttachment.class);
	
	public TestXmlAttachment(){super(Attachment.class);}
	public static Attachment create(boolean withChildren){return (new TestXmlAttachment()).build(withChildren);}
	
    public Attachment build(boolean withChilds)
    {
    	Attachment xml = new Attachment();
    	
    	if(withChilds)
    	{
    		xml.setData("myBinaryData".getBytes());
    		xml.setFile(new net.sf.exlp.xml.io.File());
    	}
    	    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		TestXmlAttachment test = new TestXmlAttachment();
		test.saveReferenceXml();
    }
}