package net.sf.ahtutils.xml.qa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.ahtutils.xml.security.TestXmlStaff;

public class TestXmlChecklist extends AbstractXmlQaTest<Checklist>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlChecklist.class);
	
	public TestXmlChecklist(){super(Checklist.class);}
	public static Checklist create(boolean withChildren){return (new TestXmlChecklist()).build(withChildren);}  
    
    public Checklist build(boolean withChilds)
    {
    	Checklist xml = new Checklist();
    	
    	if(withChilds)
    	{
    		xml.setStaff(TestXmlStaff.create(false));
    		xml.getCategory().add(TestXmlCategory.create(false));xml.getCategory().add(TestXmlCategory.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		TestXmlChecklist test = new TestXmlChecklist();
		test.saveReferenceXml();
    }
}