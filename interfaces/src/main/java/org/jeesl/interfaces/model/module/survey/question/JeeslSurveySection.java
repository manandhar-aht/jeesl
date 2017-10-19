package org.jeesl.interfaces.model.module.survey.question;

import java.util.List;

import org.jeesl.interfaces.model.module.survey.analysis.JeeslSurveyAnalysis;
import org.jeesl.interfaces.model.module.survey.analysis.JeeslSurveyAnalysisQuestion;
import org.jeesl.interfaces.model.module.survey.analysis.JeeslSurveyAnalysisTool;
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
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntity;
import org.jeesl.interfaces.model.system.with.code.EjbWithCode;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.EjbWithLevel;
import net.sf.ahtutils.interfaces.model.with.EjbWithRemark;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPosition;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;
import net.sf.ahtutils.model.interfaces.with.EjbWithVisible;

public interface JeeslSurveySection<L extends UtilsLang, D extends UtilsDescription,
					TEMPLATE extends JeeslSurveyTemplate<L,D,?,TEMPLATE,?,?,?,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,?>,
					SECTION extends JeeslSurveySection<L,D,TEMPLATE,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>,
					QUESTION extends JeeslSurveyQuestion<L,D,SECTION,QUESTION,QE,SCORE,UNIT,OPTIONS,OPTION,?>,
					QE extends UtilsStatus<QE,L,D>,
					SCORE extends JeeslSurveyScore<L,D,?,?,?,TEMPLATE,?,?,?,SECTION,QUESTION,QE,SCORE>,
					UNIT extends UtilsStatus<UNIT,L,D>,
					ANSWER extends JeeslSurveyAnswer<L,D,QUESTION,ANSWER,MATRIX,DATA,OPTION>,
					MATRIX extends JeeslSurveyMatrix<L,D,?,?,?,TEMPLATE,?,?,?,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>,
					DATA extends JeeslSurveyData<L,D,?,?,?,TEMPLATE,?,?,?,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>,
					OPTIONS extends JeeslSurveyOptionSet<L,D,TEMPLATE,OPTION>,
					OPTION extends JeeslSurveyOption<L,D>,
					CORRELATION extends JeeslSurveyCorrelation<L,D,?,?,?,TEMPLATE,?,?,?,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>
					
>
			extends EjbWithId,EjbWithVisible,EjbSaveable,EjbRemoveable,EjbWithCode,EjbWithRemark,EjbWithPosition,EjbWithLevel,
					EjbWithLang<L>,EjbWithDescription<D>
{
	public enum Attributes{template,visible,position}
	
	TEMPLATE getTemplate();
	void setTemplate(TEMPLATE template);
	
	SECTION getSection();
	void setSection(SECTION section);
	
	List<SECTION> getSections();
	void setSections(List<SECTION> sections);
	
	List<QUESTION> getQuestions();
	void setQuestions(List<QUESTION> questions);
	
	String getColumnClasses();
	void setColumnClasses(String columnClasses);
	
	Double getScoreLimit();
	void setScoreLimit(Double scoreLimit);
	
	Double getScoreNormalize();
	void setScoreNormalize(Double scoreNormalize);
}