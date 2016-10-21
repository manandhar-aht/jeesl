package org.jeesl.model.xml.system.util;

import org.jeesl.UtilsXmlTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.access.TestXmlCategory;
import net.sf.ahtutils.xml.utils.Utils;

public class TestXmlUtils extends AbstractXmlUtilsTest<Utils>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlUtils.class);
	
	public TestXmlUtils(){super(Utils.class);}
	public static Utils create(boolean withChildren){return (new TestXmlUtils()).build(withChildren);}
    
    public Utils build(boolean withChilds)
    {
    	Utils xml = new Utils();
        	
    	if(withChilds)
    	{
    		xml.getCategory().add(TestXmlCategory.create(false));xml.getCategory().add(TestXmlCategory.create(false));  		
    		xml.getProperty().add(TestXmlProperty.create(false));xml.getProperty().add(TestXmlProperty.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		TestXmlUtils test = new TestXmlUtils();
		test.saveReferenceXml();
    }
}