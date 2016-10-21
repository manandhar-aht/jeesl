package net.sf.ahtutils.xml.status;

import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.jeesl.UtilsXmlTestBootstrap;
import org.jeesl.model.xml.system.status.AbstractXmlStatusTest;
import org.jeesl.model.xml.system.status.TestXmlAction;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlUnit extends AbstractXmlStatusTest<Unit>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlUnit.class);
	
	public TestXmlUnit(){super(Unit.class);}
	public static Unit create(boolean withChildren){return (new TestXmlUnit()).build(withChildren);}   
    
    public Unit build(boolean withChilds)
    {
    	Unit xml = new Unit();
    	xml.setCode("myCode");
    	xml.setVisible(true);
    	xml.setGroup("myGroup");
    	xml.setLabel("myLabel");
    	xml.setImage("test/green.png");
    	xml.setPosition(1);
    	
    	if(withChilds)
    	{
    		xml.setLangs(TestXmlLangs.create(false));
    		xml.setDescriptions(TestXmlDescriptions.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		TestXmlUnit test = new TestXmlUnit();
		test.saveReferenceXml();
    }
}