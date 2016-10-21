package org.jeesl.model.xml.system.core;

import org.jeesl.JeeslXmlTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.status.TestXmlDescription;
import net.sf.ahtutils.xml.status.TestXmlDescriptions;
import net.sf.ahtutils.xml.status.TestXmlLang;
import net.sf.ahtutils.xml.status.TestXmlLangs;
import net.sf.ahtutils.xml.status.TestXmlType;
import net.sf.ahtutils.xml.system.Constraint;

public class TestXmlConstraint extends AbstractXmlSystemTest<Constraint>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlConstraint.class);
	
	public TestXmlConstraint(){super(Constraint.class);}
	public static Constraint create(boolean withChildren){return (new TestXmlConstraint()).build(withChildren);}
        
    public Constraint build(boolean withChilds)
    {
    	Constraint xml = new Constraint();
    	xml.setCode("myCode");
    	
    	if(withChilds)
    	{
    		xml.setType(TestXmlType.create(false));
    		xml.setLangs(TestXmlLangs.create(false));
    		xml.setDescriptions(TestXmlDescriptions.create(false));
    		xml.getConstraintAttribute().add(TestXmlConstraintAttribute.create(false));xml.getConstraintAttribute().add(TestXmlConstraintAttribute.create(false));
    		xml.setConstraintSolution(TestXmlConstraintSolution.create(false));
    		xml.setLang(TestXmlLang.create(false));
    		xml.setDescription(TestXmlDescription.create(false));
    	}
    	return xml;
    }
    
	public static void main(String[] args)
    {
		JeeslXmlTestBootstrap.init();
		TestXmlConstraint test = new TestXmlConstraint();
		test.saveReferenceXml();
    }
}