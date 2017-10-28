package org.jeesl.interfaces.model.module.survey.question;

import java.io.Serializable;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPosition;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslSurveyValidationAlgorithm
			extends Serializable,EjbWithId,EjbSaveable,EjbRemoveable,
					EjbWithPosition
{
	public enum Attributes{validation}

}