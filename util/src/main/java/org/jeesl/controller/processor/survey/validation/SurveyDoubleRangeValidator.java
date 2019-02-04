package org.jeesl.controller.processor.survey.validation;

import org.jeesl.interfaces.controller.processor.SurveyValidator;
import org.jeesl.interfaces.model.json.module.survey.SurveyValidatorConfiguration;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyAnswer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SurveyDoubleRangeValidator <ANSWER extends JeeslSurveyAnswer<?,?,?,?,?,?>> implements SurveyValidator<ANSWER>
{
	final static Logger logger = LoggerFactory.getLogger(SurveyDoubleRangeValidator.class);
	public static final long serialVersionUID=1;

	@Override
	public void init(SurveyValidatorConfiguration config)
	{
		logger.info("NYI");
	}
	
	@Override public String toString()
	{
		StringBuffer sb = new StringBuffer();	
		return sb.toString();
	}

	@Override
	public boolean validate(ANSWER answer) {
		// TODO Auto-generated method stub
		return false;
	}

	
}