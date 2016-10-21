package net.sf.ahtutils.xml.status;

import org.jeesl.JeeslXmlTestBootstrap;
import org.jeesl.model.xml.security.TestXmlUser;
import org.jeesl.model.xml.system.status.AbstractXmlStatusTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlTracked extends AbstractXmlStatusTest<Tracked>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlTracked.class);
	
	public TestXmlTracked(){super(Tracked.class);}
	public static Tracked create(boolean withChildren){return (new TestXmlTracked()).build(withChildren);} 
    
    public Tracked build(boolean withChilds)
    {
    	Tracked xml = new Tracked();
    	xml.setRecord(TestXmlTracked.getDefaultXmlDate());
    	
    	if(withChilds)
    	{
    		xml.setUser(TestXmlUser.create(false));
    		
    		xml.setStatus(TestXmlStatus.create(false));
    		xml.setStatement(TestXmlStatement.create(false));
    		xml.setDeclaration(TestXmlDeclaration.create(false));
    	}
    	
    	return xml;
    }
    
	public static void main(String[] args)
    {
		JeeslXmlTestBootstrap.init();
		TestXmlTracked test = new TestXmlTracked();
		test.saveReferenceXml();
    }
}