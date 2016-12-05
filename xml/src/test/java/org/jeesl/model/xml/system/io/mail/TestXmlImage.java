package org.jeesl.model.xml.system.io.mail;

import org.jeesl.JeeslXmlTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.mail.Image;

public class TestXmlImage extends AbstractXmlMailTest<Image>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlImage.class);
	
	public TestXmlImage(){super(Image.class);}
	public static Image create(boolean withChildren){return (new TestXmlImage()).build(withChildren);}
	
    public Image build(boolean withChilds)
    {
    	Image xml = new Image();
    	
    	xml.setCid("myCid");
    	if(withChilds)
    	{
    		xml.setData("myBinaryData".getBytes());
    		xml.setFile(new net.sf.exlp.xml.io.File());
    	}
    	    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		JeeslXmlTestBootstrap.init();	
		TestXmlImage test = new TestXmlImage();
		test.saveReferenceXml();
    }
}