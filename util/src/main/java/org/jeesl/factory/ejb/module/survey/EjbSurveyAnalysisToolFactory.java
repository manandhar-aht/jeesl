package org.jeesl.factory.ejb.module.survey;

import java.util.List;

import org.jeesl.interfaces.model.module.survey.analysis.JeeslSurveyAnalysisQuestion;
import org.jeesl.interfaces.model.module.survey.analysis.JeeslSurveyAnalysisTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class EjbSurveyAnalysisToolFactory<L extends UtilsLang, D extends UtilsDescription,

				AQ extends JeeslSurveyAnalysisQuestion<L,D,?,?>,
				AT extends JeeslSurveyAnalysisTool<L,D,?,?,AQ,ATT>,
				ATT extends UtilsStatus<ATT,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbSurveyAnalysisToolFactory.class);
	
	private final Class<AT> cAt;
    
	public EjbSurveyAnalysisToolFactory(final Class<AT> cAt)
	{       
        this.cAt = cAt;
	}
    
	public AT build(AQ analysisQuestion, ATT type, List<AT> list)
	{
		AT ejb = null;
		try
		{
			ejb = cAt.newInstance();
			ejb.setAnalysisQuestion(analysisQuestion);
			ejb.setType(type);
			if(list==null) {ejb.setPosition(1);}
			else {ejb.setPosition(list.size()+1);}
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
}