package net.sf.ahtutils.xml.mail;

import org.jeesl.UtilsXmlTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlCc extends AbstractXmlMailTest<Cc>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlCc.class);
	
	public TestXmlCc(){super(Cc.class);}
	public static Cc create(boolean withChildren){return (new TestXmlCc()).build(withChildren);}
	
    public Cc build(boolean withChilds)
    {
    	Cc xml = new Cc();

    	if(withChilds)
    	{
    		xml.getEmailAddress().add(TestXmlEmailAddress.create(false));
    		xml.getEmailAddress().add(TestXmlEmailAddress.create(false));
    	}
    	    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		TestXmlCc test = new TestXmlCc();
		test.saveReferenceXml();
    }
}