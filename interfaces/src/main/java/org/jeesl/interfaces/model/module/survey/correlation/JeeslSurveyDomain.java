package org.jeesl.interfaces.model.module.survey.correlation;

import java.io.Serializable;

import org.jeesl.interfaces.model.module.survey.analysis.JeeslSurveyAnalysis;
import org.jeesl.interfaces.model.module.survey.analysis.JeeslSurveyAnalysisQuestion;
import org.jeesl.interfaces.model.module.survey.analysis.JeeslSurveyAnalysisTool;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntity;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPosition;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslSurveyDomain<L extends UtilsLang, D extends UtilsDescription,
									DOMAIN extends JeeslSurveyDomain<L,D,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT>,
									PATH extends JeeslSurveyDomainPath<L,D,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT>,
									DENTITY extends JeeslRevisionEntity<L,D,?,?,?,?,?,DENTITY,?,?,?>,
									ANALYSIS extends JeeslSurveyAnalysis<L,D,?>,
									AQ extends JeeslSurveyAnalysisQuestion<L,D,?,ANALYSIS>,
									AT extends JeeslSurveyAnalysisTool<L,D,?,AQ,ATT>,
									ATT extends UtilsStatus<ATT,L,D>>
			extends Serializable,EjbWithId,EjbSaveable,
					EjbWithPosition//,EjbWithLang<L>//,EjbWithDescription<D>
{
	DENTITY getEntity();
	void setEntity(DENTITY entity);
}