package org.jeesl.model.xml.mail;

import org.jeesl.UtilsXmlTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.mail.Header;

public class TestXmlHeader extends AbstractXmlMailTest<Header>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlHeader.class);
	
	public TestXmlHeader(){super(Header.class);}
	public static Header create(boolean withChildren){return (new TestXmlHeader()).build(withChildren);}
	
    public Header build(boolean withChilds)
    {
    	Header xml = new Header();
    	xml.setSubject("mySubject");
    	
    	if(withChilds)
    	{
    		xml.setFrom(TestXmlFrom.create(false));
    		xml.setTo(TestXmlTo.create(false));
    		xml.setCc(TestXmlCc.create(false));
    		xml.setBcc(TestXmlBcc.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		TestXmlHeader test = new TestXmlHeader();
		test.saveReferenceXml();
    }
}