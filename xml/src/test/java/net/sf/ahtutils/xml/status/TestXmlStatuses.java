package net.sf.ahtutils.xml.status;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.jeesl.JeeslXmlTestBootstrap;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlStatuses extends AbstractXmlStatusTestOld
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlStatuses.class);
	
	@BeforeClass
	public static void initFiles()
	{
        fXml = new File(getXmlDir(dirSuffix),Statuses.class.getSimpleName()+".xml");
	}
    
    @Test
    public void xml() throws FileNotFoundException
    {
        Statuses actual = create(true);
        Statuses expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Statuses.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static Statuses create(boolean withChilds)
    {
        Statuses xml = new Statuses();
    	xml.setGroup("myGroup");

    	if(withChilds)
    	{
    		xml.getStatus().add(TestXmlStatus.create(false));
            xml.getStatus().add(TestXmlStatus.create(false));
    	}
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		JeeslXmlTestBootstrap.init();
			
		TestXmlStatuses.initFiles();
		TestXmlStatuses test = new TestXmlStatuses();
		test.save();
    }
}