package org.jeesl.model.xml.dev.srs;

import org.jeesl.JeeslXmlTestBootstrap;
import org.openfuxml.factory.xml.ofx.content.structure.XmlSectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlChapter extends AbstractXmlSrsTest<Chapter>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlChapter.class);
	
	public TestXmlChapter(){super(Chapter.class);}
	public static Chapter create(boolean withChildren){return (new TestXmlChapter()).build(withChildren);}
    
    public Chapter build(boolean withChildren)
    {
    	Chapter xml = new Chapter();
    	xml.setTitle("Functional Requirements");
    	
    	if(withChildren && false)
    	{
    		xml.getContent().add(TestXmlChapter.create(false));
    		xml.getContent().add(XmlSectionFactory.build("de"));
    		xml.getContent().add(XmlSectionFactory.build("en"));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		JeeslXmlTestBootstrap.init();
		TestXmlChapter test = new TestXmlChapter();
		test.saveReferenceXml();
    }
}