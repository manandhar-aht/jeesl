package org.jeesl.interfaces.model.module.survey.core;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;
import net.sf.ahtutils.model.interfaces.with.EjbWithRecord;

public interface JeeslSurveyTemplateVersion<L extends UtilsLang, D extends UtilsDescription,
					TEMPLATE extends JeeslSurveyTemplate<L,D,?,TEMPLATE,?,?,?,?,?,?>>
			extends EjbWithId,EjbWithRecord,EjbSaveable,EjbWithLang<L>,EjbWithDescription<D>
{
	enum Attributes {template,record}
	
	TEMPLATE getTemplate();
	void setTemplate(TEMPLATE template);
}