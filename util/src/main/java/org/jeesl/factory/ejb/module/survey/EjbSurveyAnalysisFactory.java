package org.jeesl.factory.ejb.module.survey;

import org.jeesl.interfaces.model.module.survey.analysis.JeeslSurveyAnalysis;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public class EjbSurveyAnalysisFactory<L extends UtilsLang, D extends UtilsDescription,
				TEMPLATE extends JeeslSurveyTemplate<L,D,?,TEMPLATE,?,?,?,?,?,ANALYSIS>,
				ANALYSIS extends JeeslSurveyAnalysis<L,D,TEMPLATE>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbSurveyAnalysisFactory.class);
	
	private final Class<ANALYSIS> cAnalysis;
    
	public EjbSurveyAnalysisFactory(final Class<ANALYSIS> cAnalysis)
	{       
        this.cAnalysis = cAnalysis;
	}
    
	public ANALYSIS build(TEMPLATE template)
	{
		ANALYSIS ejb = null;
		try
		{
			ejb = cAnalysis.newInstance();
			ejb.setTemplate(template);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
}