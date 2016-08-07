package org.jeesl.model.xml.survey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.test.AbstractXmlTest;

public abstract class AbstractXmlSurveyTest <T extends Object> extends AbstractXmlTest<T>
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlSurveyTest.class);
	
	public AbstractXmlSurveyTest(java.lang.Class<T> cXml)
	{
		super(cXml,"survey");
	}
}