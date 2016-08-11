package org.jeesl.model.xml.security;

import org.jeesl.UtilsXmlTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.security.Role;
import net.sf.ahtutils.xml.status.TestXmlDescriptions;
import net.sf.ahtutils.xml.status.TestXmlLangs;

public class TestXmlRole extends AbstractXmlSecurityTest<Role>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlRole.class);
	
	public TestXmlRole(){super(Role.class);}
	public static Role create(boolean withChildren){return (new TestXmlRole()).build(withChildren);}
    
    public Role build(boolean withChilds)
    {
    	Role xml = new Role();
    	xml.setId(123);
    	xml.setPosition(1);
    	xml.setVisible(true);
    	xml.setCode("myCode");
    	xml.setLabel("myLabel");
    	
    	if(withChilds)
    	{
    		xml.setLangs(TestXmlLangs.create(false));
    		xml.setDescriptions(TestXmlDescriptions.create(false));
    		xml.setActions(TestXmlActions.create(false));
    		xml.setViews(TestXmlViews.create(false));
    		xml.setUsecases(TestXmlUsecases.create(false));
    		xml.setStaffs(TestXmlStaffs.create(false));
    	}
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		TestXmlRole test = new TestXmlRole();
		test.saveReferenceXml();
    }
}