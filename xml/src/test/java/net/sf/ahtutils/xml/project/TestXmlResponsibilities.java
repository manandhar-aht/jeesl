package net.sf.ahtutils.xml.project;

import org.jeesl.UtilsXmlTestBootstrap;
import org.jeesl.model.xml.security.TestXmlRoles;
import org.jeesl.model.xml.security.TestXmlUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
		UtilsXmlTestBootstrap.init();
		TestXmlResponsibilities test = new TestXmlResponsibilities();
		test.saveReferenceXml();
    }
}