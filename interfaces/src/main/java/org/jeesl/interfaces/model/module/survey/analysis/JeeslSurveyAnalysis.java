package org.jeesl.interfaces.model.module.survey.analysis;

import org.jeesl.interfaces.model.module.survey.core.JeeslSurvey;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyScheme;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyScore;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyTemplate;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyTemplateVersion;
import org.jeesl.interfaces.model.module.survey.correlation.JeeslSurveyCorrelation;
import org.jeesl.interfaces.model.module.survey.correlation.JeeslSurveyDomain;
import org.jeesl.interfaces.model.module.survey.correlation.JeeslSurveyDomainPath;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyAnswer;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyData;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyMatrix;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyOption;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyOptionSet;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyQuestion;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveySection;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntity;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.parent.EjbWithParentAttributeResolver;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionVisible;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslSurveyAnalysis<L extends UtilsLang, D extends UtilsDescription,
					SURVEY extends JeeslSurvey<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,?,?,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT>,
					SS extends UtilsStatus<SS,L,D>,
					SCHEME extends JeeslSurveyScheme<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,?,?,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ>,
					TEMPLATE extends JeeslSurveyTemplate<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,?,?,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ>,
					VERSION extends JeeslSurveyTemplateVersion<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,?,?,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ>,
					SECTION extends JeeslSurveySection<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,?,?,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ>,
					QUESTION extends JeeslSurveyQuestion<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,?,?,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT>,
					QE extends UtilsStatus<QE,L,D>,
					SCORE extends JeeslSurveyScore<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,?,?,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ>,
					UNIT extends UtilsStatus<UNIT,L,D>,
					ANSWER extends JeeslSurveyAnswer<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,?,?,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ>,
					MATRIX extends JeeslSurveyMatrix<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,?,?,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ>,
					DATA extends JeeslSurveyData<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,?,?,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ>,
					OPTIONS extends JeeslSurveyOptionSet<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,?,?,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ>,
					OPTION extends JeeslSurveyOption<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,?,?,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ>,
					CORRELATION extends JeeslSurveyCorrelation<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,?,?,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT>,
					DOMAIN extends JeeslSurveyDomain<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,?,?,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT>,
					PATH extends JeeslSurveyDomainPath<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,?,?,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT>,
					DENTITY extends JeeslRevisionEntity<L,D,?,?,?,?,?,DENTITY,?,?,?>,
					ANALYSIS extends JeeslSurveyAnalysis<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT>,
					AQ extends JeeslSurveyAnalysisQuestion<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT>,
					AT extends JeeslSurveyAnalysisTool<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT>,
					ATT extends UtilsStatus<ATT,L,D>>
			extends EjbWithId,EjbWithParentAttributeResolver,EjbWithPositionVisible,EjbSaveable,
						EjbWithLang<L>//,,EjbWithDescription<D>
{
	public enum Attributes{template}
	
	TEMPLATE getTemplate();
	void setTemplate(TEMPLATE template);
}