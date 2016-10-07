package org.jeesl.model.xml.security;

import org.jeesl.UtilsXmlTestBootstrap;
import org.jeesl.model.xml.navigation.TestXmlNavigation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.security.View;
import net.sf.ahtutils.xml.status.TestXmlDescriptions;
import net.sf.ahtutils.xml.status.TestXmlLangs;

public class TestXmlView extends AbstractXmlSecurityTest<View>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlView.class);
	
	public TestXmlView(){super(View.class);}
	public static View create(boolean withChildren){return (new TestXmlView()).build(withChildren);}
    
    public View build(boolean withChilds)
    {
    	View xml = new View();
    	xml.setId(123);
    	xml.setPosition(1);
    	xml.setVisible(true);
    	xml.setCode("myCode");
    	xml.setLabel("myLabel");
    	
    	if(withChilds)
    	{
    		xml.setLangs(TestXmlLangs.create(false));
    		xml.setDescriptions(TestXmlDescriptions.create(false));
    		xml.setNavigation(TestXmlNavigation.create(false));
    	}
    	return xml;
    }
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		TestXmlView test = new TestXmlView();
		test.saveReferenceXml();
    }
}