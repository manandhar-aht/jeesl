package net.sf.ahtutils.xml.report;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;

public class TestXmlLabels extends AbstractXmlReportTest<Labels>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlLabels.class);
	
	public TestXmlLabels(){super(Labels.class);}
	public static Labels create(boolean withChildren){return (new TestXmlLabels()).build(withChildren);}    
    
    public Labels build(boolean withChildren)
    {
    	Labels xml = new Labels();
   
    	if(withChildren)
    	{
    		xml.getLabel().add(TestXmlLabel.create(false));
    	}

    	return xml;
    }
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		TestXmlLabels test = new TestXmlLabels();
		test.saveReferenceXml();
    }
}