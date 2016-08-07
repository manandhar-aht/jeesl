package org.jeesl.model.xml.qa;

import org.jeesl.UtilsXmlTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.qa.Test;
import net.sf.ahtutils.xml.status.TestXmlStatement;
import net.sf.ahtutils.xml.status.TestXmlStatus;

public class TestXmlTest extends AbstractXmlQaTest<net.sf.ahtutils.xml.qa.Test>
{
	final static Logger logger = LoggerFactory.getLogger(net.sf.ahtutils.xml.qa.Test.class);
	
	public TestXmlTest(){super(net.sf.ahtutils.xml.qa.Test.class);}
	public static net.sf.ahtutils.xml.qa.Test create(boolean withChildren){return (new TestXmlTest()).build(withChildren);}   
    
    public net.sf.ahtutils.xml.qa.Test build(boolean withChilds)
    {
    	net.sf.ahtutils.xml.qa.Test xml = new net.sf.ahtutils.xml.qa.Test();
    	xml.setId(123);
    	xml.setCode("myCode");
    	xml.setName("myName");
    	xml.setDuration(120);
    	
    	if(withChilds)
    	{
    		xml.setStatus(TestXmlStatus.create(false));
    		xml.setStatement(TestXmlStatement.create(false));
    		
    		xml.setReference(TestXmlReference.create(false));
    		xml.setDescription(TestXmlDescription.create(false));
    		xml.setPreCondition(TestXmlPreCondition.create(false));
    		xml.setSteps(TestXmlSteps.create(false));
    		xml.setExpected(TestXmlExpected.create(false));
    		
    		xml.setResults(TestXmlResults.create(false));
    		xml.setInfo(TestXmlInfo.create(false));
    		xml.setGroups(TestXmlGroups.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();	
		TestXmlTest test = new TestXmlTest();
		test.saveReferenceXml();
    }
}