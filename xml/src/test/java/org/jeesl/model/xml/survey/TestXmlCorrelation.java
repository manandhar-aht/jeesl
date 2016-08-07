package org.jeesl.model.xml.survey;

import org.jeesl.UtilsXmlTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.survey.Correlation;

public class TestXmlCorrelation extends AbstractXmlSurveyTest<Correlation>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlCorrelation.class);
	
	public TestXmlCorrelation(){super(Correlation.class);}
	public static Correlation create(boolean withChildren){return (new TestXmlCorrelation()).build(withChildren);}   

    public Correlation build(boolean withChilds)
    {
    	Correlation xml = new Correlation();
    	xml.setId(123);
    	xml.setType("myType");
    	if(withChilds)
    	{    		
    		xml.getCorrelation().add(TestXmlCorrelation.create(false));
    		xml.getCorrelation().add(TestXmlCorrelation.create(false));
    	}
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		TestXmlCorrelation test = new TestXmlCorrelation();
		test.saveReferenceXml();
    }
}