package org.jeesl.model.xml.project;

import org.jeesl.JeeslXmlTestBootstrap;
import org.jeesl.model.xml.system.security.TestXmlRoles;
import org.jeesl.model.xml.system.security.TestXmlUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.project.Responsibilities;

public class TestXmlResponsibilities extends AbstractXmlProjectTest<Responsibilities>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlResponsibilities.class);
	
	public TestXmlResponsibilities(){super(Responsibilities.class);}
	public static Responsibilities create(boolean withChildren){return (new TestXmlResponsibilities()).build(withChildren);}
    
    public Responsibilities build(boolean withChilds)
    {
    	Responsibilities xml = new Responsibilities();

    	if(withChilds)
    	{
    		xml.setRoles(TestXmlRoles.create(false));
    		xml.getUser().add(TestXmlUser.create(false));xml.getUser().add(TestXmlUser.create(false));
    	}
    	return xml;
    }
	
	public static void main(String[] args)
    {
		JeeslXmlTestBootstrap.init();
		TestXmlResponsibilities test = new TestXmlResponsibilities();
		test.saveReferenceXml();
    }
}