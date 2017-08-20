package org.jeesl.factory.ejb.system.constraint;

import org.jeesl.interfaces.model.system.constraint.JeeslConstraint;
import org.jeesl.interfaces.model.system.constraint.JeeslConstraintScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class EjbConstraintScopeFactory <L extends UtilsLang, D extends UtilsDescription,
										SCOPE extends JeeslConstraintScope<L,D,SCOPE,CATEGORY,CONSTRAINT,TYPE>,
										CATEGORY extends UtilsStatus<CATEGORY,L,D>,
										CONSTRAINT extends JeeslConstraint<L,D,SCOPE,CATEGORY,CONSTRAINT,TYPE>,
										TYPE extends UtilsStatus<TYPE,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbConstraintScopeFactory.class);
	
	private final Class<SCOPE> cScope;

	public EjbConstraintScopeFactory(final Class<SCOPE> cScope)
	{
        this.cScope = cScope;
	}
 
	public SCOPE build(CATEGORY category)
	{
		SCOPE ejb = null;
		try
		{
			ejb = cScope.newInstance();
			ejb.setPosition(0);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
}