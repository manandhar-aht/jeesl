package org.jeesl.model.xml.security;

import org.jeesl.UtilsXmlTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.security.Usecase;
import net.sf.ahtutils.xml.status.TestXmlDescriptions;
import net.sf.ahtutils.xml.status.TestXmlLangs;

public class TestXmlUsecase extends AbstractXmlSecurityTest<Usecase>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlUsecase.class);
	
	public TestXmlUsecase(){super(Usecase.class);}
	public static Usecase create(boolean withChildren){return (new TestXmlUsecase()).build(withChildren);}
    
    public Usecase build(boolean withChilds)
    {
    	Usecase xml = new Usecase();
    	xml.setId(123);
    	xml.setPosition(1);
    	xml.setVisible(true);
    	xml.setCode("myCode");
    	xml.setLabel("myLabel");
    	
    	if(withChilds)
    	{
    		xml.setLangs(TestXmlLangs.create(false));
    		xml.setDescriptions(TestXmlDescriptions.create(false));

    	}
    	return xml;
    }
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		TestXmlUsecase test = new TestXmlUsecase();
		test.saveReferenceXml();
    }
}