package org.jeesl.interfaces.model.module.survey.analysis;

import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyQuestion;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.with.parent.EjbWithParentAttributeResolver;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslSurveyAnalysisQuestion<L extends UtilsLang, D extends UtilsDescription,
					QUESTION extends JeeslSurveyQuestion<L,D,?,QUESTION,?,?,?,?,?,?>,
					ANALYSIS extends JeeslSurveyAnalysis<L,D,?>
								>
			extends EjbWithId,EjbWithParentAttributeResolver,EjbSaveable,
					EjbWithLang<L>
{
	public enum Attributes{analysis,question}
	
	ANALYSIS getAnalysis();
	void setAnalysis(ANALYSIS analysis);
	
	QUESTION getQuestion();
	void setQuestion(QUESTION question);
}