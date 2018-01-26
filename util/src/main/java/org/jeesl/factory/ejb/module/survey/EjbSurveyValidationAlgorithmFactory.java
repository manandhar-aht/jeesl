package org.jeesl.factory.ejb.module.survey;

import java.util.List;

import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyValidationAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbSurveyValidationAlgorithmFactory<VALGORITHM extends JeeslSurveyValidationAlgorithm<?,?>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbSurveyValidationAlgorithmFactory.class);
	
	final Class<VALGORITHM> cValgorithm;
    
	public EjbSurveyValidationAlgorithmFactory(final Class<VALGORITHM> cValgorithm)
	{       
        this.cValgorithm = cValgorithm;
	}
	    
	public VALGORITHM build(List<VALGORITHM> list)
	{
		VALGORITHM ejb = null;
		try
		{
			ejb = cValgorithm.newInstance();
			if(list==null) {ejb.setPosition(1);}
			else {ejb.setPosition(list.size()+1);}
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
}