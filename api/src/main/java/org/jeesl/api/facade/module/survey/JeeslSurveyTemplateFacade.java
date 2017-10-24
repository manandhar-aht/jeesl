package org.jeesl.api.facade.module.survey;

import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyScheme;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyScore;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyTemplate;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyTemplateVersion;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyOption;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyOptionSet;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyQuestion;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveySection;

import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public interface JeeslSurveyTemplateFacade <L extends UtilsLang, D extends UtilsDescription,
									SCHEME extends JeeslSurveyScheme<L,D,TEMPLATE,SCORE>,
									TEMPLATE extends JeeslSurveyTemplate<L,D,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,OPTIONS,?>,
									VERSION extends JeeslSurveyTemplateVersion<L,D,TEMPLATE>,
									TS extends UtilsStatus<TS,L,D>,
									TC extends UtilsStatus<TC,L,D>,
									SECTION extends JeeslSurveySection<L,D,TEMPLATE,SECTION,QUESTION>,
									QUESTION extends JeeslSurveyQuestion<L,D,SECTION,QE,SCORE,UNIT,OPTIONS,OPTION,?>,
									QE extends UtilsStatus<QE,L,D>,
									SCORE extends JeeslSurveyScore<L,D,SCHEME,QUESTION>,
									UNIT extends UtilsStatus<UNIT,L,D>,
									OPTIONS extends JeeslSurveyOptionSet<L,D,TEMPLATE,OPTION>,
									OPTION extends JeeslSurveyOption<L,D>>
	extends UtilsFacade
{	
	
}