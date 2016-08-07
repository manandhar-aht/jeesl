package net.sf.ahtutils.xml.security;

import org.jeesl.UtilsXmlTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.access.TestXmlViews;
import net.sf.ahtutils.xml.status.TestXmlDescriptions;
import net.sf.ahtutils.xml.status.TestXmlLangs;

public class TestXmlCategory extends AbstractXmlSecurityTest<Category>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlCategory.class);
	
	public TestXmlCategory(){super(Category.class);}
	public static Category create(boolean withChildren){return (new TestXmlCategory()).build(withChildren);}
    
    public Category build(boolean withChilds)
    {
    	Category xml = new Category();
    	xml.setPosition(1);
    	xml.setVisible(true);
    	xml.setDocumentation(true);
    	xml.setCode("myCode");
    	xml.setLabel("myLabel");
    	
    	if(withChilds)
    	{
    		xml.setLangs(TestXmlLangs.create(false));
    		xml.setDescriptions(TestXmlDescriptions.create(false));
    		xml.setRoles(TestXmlRoles.create(false));
    		xml.setActions(TestXmlActions.create(false));
    		xml.setTemplates(TestXmlTemplates.create(false));
    		xml.setViews(TestXmlViews.create(false));
    		xml.setUsecases(TestXmlUsecases.create(false));
    		xml.getStaffs().add(TestXmlStaffs.create(false));xml.getStaffs().add(TestXmlStaffs.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		TestXmlCategory test = new TestXmlCategory();
		test.saveReferenceXml();
    }
}