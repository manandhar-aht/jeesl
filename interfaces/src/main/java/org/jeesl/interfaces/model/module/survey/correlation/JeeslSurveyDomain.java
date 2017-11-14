package org.jeesl.interfaces.model.module.survey.correlation;

import java.io.Serializable;

import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntity;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPosition;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslSurveyDomain<L extends UtilsLang, D extends UtilsDescription,
									PATH extends JeeslSurveyDomainPath<L,D,?,PATH,DENTITY>,
									DENTITY extends JeeslRevisionEntity<L,D,?,?,?>
									>
			extends Serializable,EjbWithId,EjbSaveable,
					EjbWithPosition,EjbWithLang<L>//,EjbWithDescription<D>
{
	DENTITY getEntity();
	void setEntity(DENTITY entity);
}