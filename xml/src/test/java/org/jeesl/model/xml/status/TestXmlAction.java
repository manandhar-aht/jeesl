package org.jeesl.model.xml.status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.ahtutils.xml.status.Action;
import net.sf.ahtutils.xml.status.TestXmlDescriptions;
import net.sf.ahtutils.xml.status.TestXmlLangs;

public class TestXmlAction extends AbstractXmlStatusTest<Action>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlAction.class);
	
	public TestXmlAction(){super(Action.class);}
	public static Action create(boolean withChildren){return (new TestXmlAction()).build(withChildren);}   
   
    
    public Action build(boolean withChilds)
    {
    	Action xml = new Action();
    	xml.setId(123);
    	xml.setCode("myCode");
    	xml.setVisible(true);
    	xml.setGroup("myGroup");
    	xml.setLabel("myLabel");
    	xml.setImage("test/green.png");
    	xml.setPosition(1);
    	
    	if(withChilds)
    	{
    		xml.setLangs(TestXmlLangs.create(false));
    		xml.setDescriptions(TestXmlDescriptions.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		TestXmlAction test = new TestXmlAction();
		test.saveReferenceXml();
    }
}