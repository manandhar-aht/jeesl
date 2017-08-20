package org.jeesl.interfaces.model.system.constraint;

import org.jeesl.interfaces.model.system.with.status.JeeslWithType;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.EjbWithLangDescription;
import net.sf.ahtutils.interfaces.model.with.code.EjbWithNonUniqueCode;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPosition;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslConstraint<L extends UtilsLang, D extends UtilsDescription,
									SCOPE extends JeeslConstraintScope<L,D,SCOPE,CATEGORY,CONSTRAINT,TYPE>,
									CATEGORY extends UtilsStatus<CATEGORY,L,D>,
									CONSTRAINT extends JeeslConstraint<L,D,SCOPE,CATEGORY,CONSTRAINT,TYPE>,
									TYPE extends UtilsStatus<TYPE,L,D>>
			extends EjbWithId,EjbWithNonUniqueCode,
					EjbSaveable,EjbRemoveable,
					EjbWithPosition,
					EjbWithLangDescription<L,D>,
					JeeslWithType<L,D,TYPE>
{
	public static enum Attributes{scope,code};
	
	SCOPE getScope();
	void setScope(SCOPE scope);
}