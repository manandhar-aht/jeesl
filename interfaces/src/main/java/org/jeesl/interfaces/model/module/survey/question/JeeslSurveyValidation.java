package org.jeesl.interfaces.model.module.survey.question;

import java.io.Serializable;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.with.parent.EjbWithParentAttributeResolver;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionVisible;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslSurveyValidation<QUESTION extends JeeslSurveyQuestion<?,?,?,?,?,?,?,?,?,?,?>,
										VALGORITHM extends JeeslSurveyValidationAlgorithm<?,?>>
			extends Serializable,EjbWithId,EjbSaveable,EjbRemoveable,
					EjbWithPositionVisible,EjbWithParentAttributeResolver
{
	public enum Attributes{question}
	
	QUESTION getQuestion();
	void setQuestion(QUESTION question);
	
	VALGORITHM getAlgorithm();
	void setAlgorithm(VALGORITHM algorithm);
	
	String getConfig();
	void setConfig(String config);
}