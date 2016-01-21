package net.sf.ahtutils.xml.report;

import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.ahtutils.xml.status.TestXmlDescriptions;
import net.sf.ahtutils.xml.status.TestXmlLangs;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlXlsColumn extends AbstractXmlReportTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlXlsColumn.class);
	
	@BeforeClass public static void initFiles(){setXmlFile(dirSuffix,XlsColumn.class);}
    
    @Test
    public void testReport() throws FileNotFoundException
    {
    	XlsColumn test = create(true);
    	XlsColumn ref = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), XlsColumn.class);
    	assertJaxbEquals(ref, test);
    }
    
    public static XlsColumn create(boolean withChildren)
    {
    	XlsColumn xml = new XlsColumn();
    	
    	xml.setColumn("A");
    	xml.setLabel("myLabel");
    	xml.setRequired(true);
    	xml.setExample("myExample");
    	xml.setAutoWidth(false);
    	xml.setWidth(2);
    	
    	if(withChildren)
    	{
    		xml.setLangs(TestXmlLangs.create(false));
    		xml.setDescriptions(TestXmlDescriptions.create(false));
    		xml.setXlsTransformation(TestXmlXlsTransformation.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestXmlXlsColumn.initJaxb();
		TestXmlXlsColumn.initFiles();
		TestXmlXlsColumn test = new TestXmlXlsColumn();
		test.save();
    }
}