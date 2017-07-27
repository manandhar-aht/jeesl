package org.jeesl.model.xml.jeesl;

import org.jeesl.JeeslXmlTestBootstrap;
import org.jeesl.model.xml.system.security.TestXmlUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlQuerySecurity extends AbstractXmlJeeslTest<QuerySecurity>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlQuerySecurity.class);
	
	public TestXmlQuerySecurity(){super(QuerySecurity.class);}
	public static QuerySecurity create(boolean withChildren){return (new TestXmlQuerySecurity()).build(withChildren);}
    
    public QuerySecurity build(boolean withChilds)
    {
		QuerySecurity xml = new QuerySecurity();
	        	
	    	if(withChilds)
	    	{
	    		xml.setUser(TestXmlUser.create(false));
	    	}
	    	
	    	return xml;
    }
	
	public static void main(String[] args)
    {
		JeeslXmlTestBootstrap.init();
		TestXmlQuerySecurity test = new TestXmlQuerySecurity();
		test.saveReferenceXml();
    }
}