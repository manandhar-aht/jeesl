package org.jeesl.interfaces.model.system.constraint;

import java.io.Serializable;

import org.jeesl.interfaces.model.system.with.code.EjbWithCode;
import org.jeesl.interfaces.model.system.with.status.JeeslWithCategory;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.EjbWithLangDescription;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPosition;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionParent;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslConstraintScope<L extends UtilsLang, D extends UtilsDescription,
									SCOPE extends JeeslConstraintScope<L,D,SCOPE,CATEGORY,CONSTRAINT,LEVEL,TYPE,RESOLUTION>,
									CATEGORY extends UtilsStatus<CATEGORY,L,D>,
									CONSTRAINT extends JeeslConstraint<L,D,SCOPE,CATEGORY,CONSTRAINT,LEVEL,TYPE,RESOLUTION>, LEVEL extends UtilsStatus<LEVEL,L,D>,
									TYPE extends UtilsStatus<TYPE,L,D>,
									RESOLUTION extends JeeslConstraintResolution<L,D,SCOPE,CATEGORY,CONSTRAINT,LEVEL,TYPE,RESOLUTION>>
			extends Serializable,EjbPersistable,EjbWithPosition,EjbWithId,EjbWithCode,
					EjbSaveable,EjbRemoveable,
					JeeslWithCategory<L,D,CATEGORY>,
					EjbWithPositionParent,
					EjbWithLangDescription<L,D>
{
	public enum Attributes{category}
}