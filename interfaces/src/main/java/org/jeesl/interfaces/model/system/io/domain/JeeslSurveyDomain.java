package org.jeesl.interfaces.model.system.io.domain;

import java.io.Serializable;

import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntity;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPosition;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslSurveyDomain<L extends UtilsLang,
									ENTITY extends JeeslRevisionEntity<L,?,?,?,?>
									>
			extends Serializable,EjbWithId,EjbSaveable,
					EjbWithPosition,EjbWithLang<L>//,EjbWithDescription<D>
{
	ENTITY getEntity();
	void setEntity(ENTITY entiy);
}