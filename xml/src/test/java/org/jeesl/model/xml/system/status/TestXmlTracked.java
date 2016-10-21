package org.jeesl.model.xml.system.status;

import org.jeesl.JeeslXmlTestBootstrap;
import org.jeesl.model.xml.security.TestXmlUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.status.TestXmlDeclaration;
import net.sf.ahtutils.xml.status.Tracked;

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