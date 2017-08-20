package org.jeesl.api.facade.system;

import org.jeesl.interfaces.model.system.constraint.JeeslConstraint;
import org.jeesl.interfaces.model.system.constraint.JeeslConstraintScope;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public interface JeeslSystemConstraintFacade <L extends UtilsLang, D extends UtilsDescription,
									SCOPE extends JeeslConstraintScope<L,D,SCOPE,CATEGORY,CONSTRAINT,TYPE>,
									CATEGORY extends UtilsStatus<CATEGORY,L,D>,
									CONSTRAINT extends JeeslConstraint<L,D,SCOPE,CATEGORY,CONSTRAINT,TYPE>,
									TYPE extends UtilsStatus<TYPE,L,D>>
			extends UtilsFacade
{	
	CONSTRAINT fSystemConstraint(SCOPE scope, String code) throws UtilsNotFoundException;
}