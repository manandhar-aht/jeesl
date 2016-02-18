package net.sf.ahtutils.xml.report;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;

public class TestXmlFile extends AbstractXmlReportTest<File>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlFile.class);
	
	public TestXmlFile(){super(File.class);}
	public static File create(boolean withChildren){return (new TestXmlFile()).build(withChildren);}    
    
    public File build(boolean withChildren)
    {
    	File xml = new File();
    	xml.setValue("myFileName");
    	return xml;
    }
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		TestXmlFile test = new TestXmlFile();
		test.saveReferenceXml();
    }
}