package org.jeesl.model.xml.jeesl;

import org.jeesl.model.xml.qa.TestXmlTest;
import org.jeesl.model.xml.survey.TestXmlAnswer;
import org.jeesl.model.xml.survey.TestXmlSurvey;
import org.jeesl.model.xml.survey.TestXmlSurveys;
import org.jeesl.model.xml.survey.TestXmlTemplate;
import org.jeesl.model.xml.survey.TestXmlTemplates;
import org.jeesl.model.xml.system.revision.TestXmlEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.ahtutils.xml.access.TestXmlRole;
import net.sf.ahtutils.xml.aht.Query;
import net.sf.ahtutils.xml.security.TestXmlCategory;
import net.sf.ahtutils.xml.security.TestXmlStaff;
import net.sf.ahtutils.xml.status.TestXmlLangs;
import net.sf.ahtutils.xml.status.TestXmlStatus;
import net.sf.ahtutils.xml.status.TestXmlType;
import net.sf.ahtutils.xml.utils.TestXmlTrafficLight;
import net.sf.ahtutils.xml.utils.TestXmlTrafficLights;

public class TestXmlQuery extends AbstractXmlJeeslTest<Query>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlQuery.class);
	
	public TestXmlQuery(){super(Query.class);}
	public static Query create(boolean withChildren){return (new TestXmlQuery()).build(withChildren);}  
    
    public Query build(boolean withChilds)
    {
    	Query xml = new Query();
    	xml.setLang("myLang");
    	
    	if(withChilds)
    	{
    		xml.setRole(TestXmlRole.create(false));
    		xml.setLangs(TestXmlLangs.create(false));
    		xml.setStatus(TestXmlStatus.create(false));
    		xml.setType(TestXmlType.create(false));
    		
    		xml.setTest(TestXmlTest.create(false));
    		
    		xml.setCategory(TestXmlCategory.create(false));
    		xml.setStaff(TestXmlStaff.create(false));
    		
    		xml.setEntity(TestXmlEntity.create(false));
    		
    		xml.setTemplates(TestXmlTemplates.create(false));
    		xml.setTemplate(TestXmlTemplate.create(false));
    		xml.setSurveys(TestXmlSurveys.create(false));
    		xml.setSurvey(TestXmlSurvey.create(false));
    		xml.setAnswer(TestXmlAnswer.create(false));
    		
    		xml.setTrafficLights(TestXmlTrafficLights.create(false));
    		xml.setTrafficLight(TestXmlTrafficLight.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		TestXmlQuery test = new TestXmlQuery();
		test.saveReferenceXml();
    }
}