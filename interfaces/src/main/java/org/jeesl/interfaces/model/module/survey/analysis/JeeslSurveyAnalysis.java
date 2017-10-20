package org.jeesl.interfaces.model.module.survey.analysis;

import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyTemplate;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.with.parent.EjbWithParentAttributeResolver;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionVisible;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslSurveyAnalysis<L extends UtilsLang, D extends UtilsDescription,
					TEMPLATE extends JeeslSurveyTemplate<L,D,?,TEMPLATE,?,?,?,?,?,?>>
			extends EjbWithId,EjbWithParentAttributeResolver,EjbWithPositionVisible,EjbSaveable,
						EjbWithLang<L>//,,EjbWithDescription<D>
{
	public enum Attributes{template}
	
	TEMPLATE getTemplate();
	void setTemplate(TEMPLATE template);
}