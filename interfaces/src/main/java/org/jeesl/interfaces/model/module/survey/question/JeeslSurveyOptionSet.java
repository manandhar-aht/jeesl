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
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPosition;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;
import net.sf.ahtutils.model.interfaces.with.EjbWithVisible;

public interface JeeslSurveyOptionSet<L extends UtilsLang, D extends UtilsDescription,
					TEMPLATE extends JeeslSurveyTemplate<L,D,?,?,?,TEMPLATE,VERSION,?,?,SECTION,QUESTION,?,?,?,ANSWER,MATRIX,DATA,OPTIONS,OPTION,?,?>,
					VERSION extends JeeslSurveyTemplateVersion<L,D,?,?,?,TEMPLATE,VERSION,?,?,SECTION,QUESTION,?,?,?,ANSWER,MATRIX>,
					SECTION extends JeeslSurveySection<L,D,?,?,?,TEMPLATE,VERSION,?,?,SECTION,QUESTION,?,?,?,ANSWER,MATRIX,DATA,OPTIONS,OPTION,?>,
					QUESTION extends JeeslSurveyQuestion<L,D,SECTION,QUESTION,?,?,?,ANSWER,MATRIX,DATA,OPTIONS,OPTION,?,?>,
					ANSWER extends JeeslSurveyAnswer<L,D,?,?,?,TEMPLATE,VERSION,?,?,SECTION,QUESTION,?,?,?,ANSWER,MATRIX,DATA,OPTIONS,OPTION,?>,
					MATRIX extends JeeslSurveyMatrix<L,D,?,?,?,TEMPLATE,VERSION,?,?,SECTION,QUESTION,?,?,?,ANSWER,MATRIX,DATA,OPTIONS,OPTION,?>,
					DATA extends JeeslSurveyData<L,D,?,?,?,TEMPLATE,VERSION,?,?,SECTION,QUESTION,?,?,?,ANSWER,MATRIX,DATA,OPTIONS,OPTION,?>,
					OPTIONS extends JeeslSurveyOptionSet<L,D,TEMPLATE,VERSION,SECTION,QUESTION,ANSWER,MATRIX,DATA,OPTIONS,OPTION>,
					OPTION extends JeeslSurveyOption<L,D>>
			extends EjbWithId,EjbSaveable,EjbRemoveable,EjbWithCode,EjbWithPosition,EjbWithVisible,
					EjbWithLang<L>
{
	public enum Attributes{template}
	
	TEMPLATE getTemplate();
	void setTemplate(TEMPLATE template);
	
	List<OPTION> getOptions();
	void setOptions(List<OPTION> options);
}