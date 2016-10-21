package net.sf.ahtutils.xml.status;

import org.jeesl.JeeslXmlTestBootstrap;
import org.jeesl.model.xml.system.status.AbstractXmlStatusTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlTranslation extends AbstractXmlStatusTest<Translation>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlTranslations.class);
	
	public TestXmlTranslation(){super(Translation.class);}
	public static Translation create(boolean withChildren){return (new TestXmlTranslation()).build(withChildren);}   
    
    public Translation build(boolean withChilds)
    {
    	Translation xml = new Translation();
    	xml.setKey("myKey");

    	if(withChilds)
    	{
    		xml.setDescriptions(TestXmlDescriptions.create(false));
    		xml.setLangs(TestXmlLangs.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		JeeslXmlTestBootstrap.init();
		TestXmlTranslation test = new TestXmlTranslation();
		test.saveReferenceXml();
    }
}