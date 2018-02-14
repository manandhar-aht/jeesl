package org.jeesl.api.bean.msg;

import org.jeesl.interfaces.model.system.constraint.JeeslConstraint;
import org.jeesl.interfaces.model.system.constraint.JeeslConstraintResolution;
import org.jeesl.interfaces.model.system.constraint.JeeslConstraintScope;
import org.jeesl.interfaces.model.system.constraint.algorithm.JeeslConstraintAlgorithm;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public interface JeeslConstraintMessageBean<L extends UtilsLang, D extends UtilsDescription,
											ALGCAT extends UtilsStatus<ALGCAT,L,D>,
											ALGO extends JeeslConstraintAlgorithm<L,D,ALGCAT>,
											SCOPE extends JeeslConstraintScope<L,D,SCOPE,CONCAT,CONSTRAINT,LEVEL,TYPE,RESOLUTION>,
											CONCAT extends UtilsStatus<CONCAT,L,D>,
											CONSTRAINT extends JeeslConstraint<L,D,SCOPE,CONCAT,CONSTRAINT,LEVEL,TYPE,RESOLUTION>,
											LEVEL extends UtilsStatus<LEVEL,L,D>,
											TYPE extends UtilsStatus<TYPE,L,D>,
											RESOLUTION extends JeeslConstraintResolution<L,D,SCOPE,CONCAT,CONSTRAINT,LEVEL,TYPE,RESOLUTION>>
				extends JeeslFacesMessageBean
{
	<FID extends Enum<FID>> void show(FID fId, CONSTRAINT constraint);
	<FID extends Enum<FID>, SID extends Enum<SID>, CID extends Enum<CID>> void show(FID fId, SID sId, CID cId);
}