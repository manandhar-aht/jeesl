package org.jeesl.model.xml.module.survey;

import org.jeesl.JeeslXmlTestBootstrap;
import org.jeesl.model.xml.system.status.TestXmlDescription;
import org.jeesl.model.xml.text.TestXmlRemark;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.survey.Section;

public class TestXmlSection extends AbstractXmlSurveyTest<Section>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlSection.class);
	
	public TestXmlSection(){super(Section.class);}
	public static Section create(boolean withChildren){return (new TestXmlSection()).build(withChildren);}   

    public Section build(boolean withChilds)
    {
    	Section xml = new Section();
    	xml.setId(123);
    	xml.setCode("myCode");
    	xml.setPosition(2);
    	
    	if(withChilds)
    	{
    		xml.setRemark(TestXmlRemark.create(false));
    		xml.setDescription(TestXmlDescription.create(false));
    		xml.getQuestion().add(TestXmlQuestion.create(false));xml.getQuestion().add(TestXmlQuestion.create(false));
    		xml.getSection().add(TestXmlSection.create(false));xml.getSection().add(TestXmlSection.create(false));
    	}
    	
    	return xml;
    }

	public static void main(String[] args)
    {
		JeeslXmlTestBootstrap.init();
		TestXmlSection test = new TestXmlSection();
		test.saveReferenceXml();
    }
}