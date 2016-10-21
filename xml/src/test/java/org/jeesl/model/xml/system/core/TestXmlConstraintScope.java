package org.jeesl.model.xml.system.core;

import org.jeesl.JeeslXmlTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.status.TestXmlDescription;
import net.sf.ahtutils.xml.status.TestXmlDescriptions;
import net.sf.ahtutils.xml.status.TestXmlLang;
import net.sf.ahtutils.xml.status.TestXmlLangs;
import net.sf.ahtutils.xml.system.ConstraintScope;

public class TestXmlConstraintScope extends AbstractXmlSystemTest<ConstraintScope>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlConstraintScope.class);
	
	public TestXmlConstraintScope(){super(ConstraintScope.class);}
	public static ConstraintScope create(boolean withChildren){return (new TestXmlConstraintScope()).build(withChildren);}
        
    public ConstraintScope build(boolean withChilds)
    {
    	ConstraintScope xml = new ConstraintScope();
    	xml.setId(123);
    	xml.setCode("myCode");
    	xml.setCategory("myCategory");
    	
    	if(withChilds)
    	{
    		xml.setLangs(TestXmlLangs.create(false));
    		xml.setDescriptions(TestXmlDescriptions.create(false));
    		
    		xml.getConstraint().add(TestXmlConstraint.create(false));xml.getConstraint().add(TestXmlConstraint.create(false));
    		
    		xml.setLang(TestXmlLang.create(false));
    		xml.setDescription(TestXmlDescription.create(false));
    	}
    	
    	return xml;
    }
    
	public static void main(String[] args)
    {
		JeeslXmlTestBootstrap.init();
		TestXmlConstraintScope test = new TestXmlConstraintScope();
		test.saveReferenceXml();
    }
}