package org.jeesl.factory.xml.module.survey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.survey.Survey;
import net.sf.ahtutils.xml.survey.Surveys;

public class XmlSurveysFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlSurveysFactory.class);
	
	public static Surveys build(Survey survey)
	{
		Surveys xml = new Surveys();
		xml.getSurvey().add(survey);
		return xml;
	}
}