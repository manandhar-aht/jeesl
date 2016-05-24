package net.sf.ahtutils.xml.qa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.ahtutils.xml.security.TestXmlStaff;
import net.sf.ahtutils.xml.survey.TestXmlSurvey;

public class TestXmlQa extends AbstractXmlQaTest<Qa>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlQa.class);
	
	public TestXmlQa(){super(Qa.class);}
	public static Qa create(boolean withChildren){return (new TestXmlQa()).build(withChildren);}  
    
    public Qa build(boolean withChilds)
    {
    	Qa xml = new Qa();
    	xml.setId(123);
    	xml.setClient("myClient");
    	xml.setDeveloper("myDeveloper");
    	
    	if(withChilds)
    	{
    		xml.getCategory().add(TestXmlCategory.create(false));xml.getCategory().add(TestXmlCategory.create(false));
    		xml.getStaff().add(TestXmlStaff.create(false));xml.getStaff().add(TestXmlStaff.create(false));
    		xml.setSurvey(TestXmlSurvey.create(false));
    		xml.setGroups(TestXmlGroups.create(false));
    		xml.getChecklist().add(TestXmlChecklist.create(false));xml.getChecklist().add(TestXmlChecklist.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		TestXmlQa test = new TestXmlQa();
		test.saveReferenceXml();
    }
}