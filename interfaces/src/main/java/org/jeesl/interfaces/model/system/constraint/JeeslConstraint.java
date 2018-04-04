package org.jeesl.interfaces.model.system.constraint;

import org.jeesl.interfaces.model.system.with.status.JeeslWithLevel;
import org.jeesl.interfaces.model.system.with.status.JeeslWithType;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.EjbWithLangDescription;
import net.sf.ahtutils.interfaces.model.with.code.EjbWithNonUniqueCode;
import net.sf.ahtutils.interfaces.model.with.parent.EjbWithParentAttributeResolver;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPosition;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslConstraint<L extends UtilsLang, D extends UtilsDescription,
									SCOPE extends JeeslConstraintScope<L,D,SCOPE,CATEGORY,CONSTRAINT,LEVEL,TYPE,RESOLUTION>,
									CATEGORY extends UtilsStatus<CATEGORY,L,D>,
									CONSTRAINT extends JeeslConstraint<L,D,SCOPE,CATEGORY,CONSTRAINT,LEVEL,TYPE,RESOLUTION>,
									LEVEL extends UtilsStatus<LEVEL,L,D>,
									TYPE extends UtilsStatus<TYPE,L,D>,
									RESOLUTION extends JeeslConstraintResolution<L,D,SCOPE,CATEGORY,CONSTRAINT,LEVEL,TYPE,RESOLUTION>>
			extends EjbWithId,EjbWithNonUniqueCode,
					EjbSaveable,EjbRemoveable,
					EjbWithPosition,
					EjbWithParentAttributeResolver,
					EjbWithLangDescription<L,D>,
					JeeslWithType<TYPE>,
					JeeslWithLevel<L,D,LEVEL>
{
	public static enum Attributes{scope,code};
	
	SCOPE getScope();
	void setScope(SCOPE scope);
}