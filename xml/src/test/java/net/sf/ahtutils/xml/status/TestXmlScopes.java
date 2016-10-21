package net.sf.ahtutils.xml.status;

import org.jeesl.JeeslXmlTestBootstrap;
import org.jeesl.model.xml.system.status.AbstractXmlStatusTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlScopes extends AbstractXmlStatusTest<Scopes>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlScopes.class);
	
	public TestXmlScopes(){super(Scopes.class);}
	public static Scopes create(boolean withChildren){return (new TestXmlScopes()).build(withChildren);} 
    
    public Scopes build(boolean withChilds)
    {
    	Scopes xml = new Scopes();
    	xml.setGroup("myGroup");
    	xml.setSize(3);
    	
    	if(withChilds)
    	{
    		xml.getScope().add(TestXmlScope.create(false));
    		xml.getScope().add(TestXmlScope.create(false));
    	}
    	return xml;
    }
	
	public static void main(String[] args)
    {
		JeeslXmlTestBootstrap.init();	
		TestXmlScopes test = new TestXmlScopes();
		test.saveReferenceXml();
    }
}