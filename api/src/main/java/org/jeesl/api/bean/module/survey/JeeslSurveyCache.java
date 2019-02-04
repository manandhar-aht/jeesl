package org.jeesl.api.bean.module.survey;

import java.util.List;

import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyTemplate;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyCondition;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyQuestion;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveySection;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyValidation;

public interface JeeslSurveyCache<TEMPLATE extends JeeslSurveyTemplate<?,?,?,TEMPLATE,?,?,?,SECTION,?,?>,
					SECTION extends JeeslSurveySection<?,?,TEMPLATE,SECTION,QUESTION>,
					QUESTION extends JeeslSurveyQuestion<?,?,SECTION,CONDITION,VALIDATION,?,?,?,?,?,?>,
					CONDITION extends JeeslSurveyCondition<QUESTION,?,?>,
					VALIDATION  extends JeeslSurveyValidation<?,?,QUESTION,?>
>
{	
	List<SECTION> getSections(TEMPLATE template);
	List<QUESTION> getQuestions(SECTION section);
	List<CONDITION> getConditions(QUESTION question);
	List<VALIDATION> getValidations(QUESTION question);
}