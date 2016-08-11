package org.jeesl.model.xml.qa;

import org.jeesl.UtilsXmlTestBootstrap;
import org.jeesl.model.xml.security.TestXmlStaff;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.qa.Group;

public class TestXmlGroup extends AbstractXmlQaTest<Group>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlGroup.class);
	
	public TestXmlGroup(){super(Group.class);}
	public static Group create(boolean withChildren){return (new TestXmlGroup()).build(withChildren);} 
    
    public Group build(boolean withChilds)
    {
    	Group xml = new Group();
    	xml.setId(123);
    	xml.setPosition(1);
    	xml.setName("myName");
    	
    	if(withChilds)
    	{
    		xml.setDescription(net.sf.ahtutils.xml.status.TestXmlDescription.create(false));
    		xml.getStaff().add(TestXmlStaff.create(false));xml.getStaff().add(TestXmlStaff.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();	
		TestXmlGroup test = new TestXmlGroup();
		test.saveReferenceXml();
    }
}