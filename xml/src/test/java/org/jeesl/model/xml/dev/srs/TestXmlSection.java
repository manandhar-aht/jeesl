package org.jeesl.model.xml.dev.srs;

import org.jeesl.JeeslXmlTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TestXmlSection extends AbstractXmlSrsTest<Section>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlSection.class);
	
	public TestXmlSection(){super(Section.class);}
	public static Section create(boolean withChildren){return (new TestXmlSection()).build(withChildren);}
    
    public Section build(boolean withChildren)
    {
    	Section xml = new Section();
    	xml.setTitle("Functional Requirements");
    	
    	if(withChildren)
    	{
    		xml.getSection().add(TestXmlSection.create(false));
    		xml.getSection().add(TestXmlSection.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		JeeslXmlTestBootstrap.init();
		TestXmlSection test = new TestXmlSection();
		test.saveReferenceXml();
    }
}