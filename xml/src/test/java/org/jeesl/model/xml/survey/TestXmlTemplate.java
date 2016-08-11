package org.jeesl.model.xml.survey;

import org.jeesl.UtilsXmlTestBootstrap;
import org.jeesl.model.xml.status.TestXmlCategory;
import org.jeesl.model.xml.text.TestXmlRemark;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.status.TestXmlDescription;
import net.sf.ahtutils.xml.status.TestXmlStatus;
import net.sf.ahtutils.xml.survey.Template;

public class TestXmlTemplate extends AbstractXmlSurveyTest<Template>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlTemplate.class);
	
	public TestXmlTemplate(){super(Template.class);}
	public static Template create(boolean withChildren){return (new TestXmlTemplate()).build(withChildren);}   

    public Template build(boolean withChilds)
    {
    	Template xml = new Template();
    	xml.setId(123);
    	xml.setCode("myCode");
    	
    	if(withChilds)
    	{
    		xml.setCategory(TestXmlCategory.create(false));
    		xml.setStatus(TestXmlStatus.create(false));
    		
    		xml.setRemark(TestXmlRemark.create(false));
    		xml.setDescription(TestXmlDescription.create(false));
    		
    		xml.getSection().add(TestXmlSection.create(false));xml.getSection().add(TestXmlSection.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		TestXmlTemplate test = new TestXmlTemplate();
		test.saveReferenceXml();
    }
}