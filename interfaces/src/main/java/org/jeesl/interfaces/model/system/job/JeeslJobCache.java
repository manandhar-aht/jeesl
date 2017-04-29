package org.jeesl.interfaces.model.system.job;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslJobCache<L extends UtilsLang,D extends UtilsDescription,
							TEMPLATE extends JeeslJobTemplate<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,STATUS,ROBOT,CACHE>,
							CATEGORY extends UtilsStatus<CATEGORY,L,D>,
							TYPE extends UtilsStatus<TYPE,L,D>,
							JOB extends JeeslJob<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,STATUS,ROBOT,CACHE>,
							FEEDBACK extends UtilsStatus<FEEDBACK,L,D>,
							STATUS extends UtilsStatus<STATUS,L,D>,
							ROBOT extends JeeslJobRobot<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,STATUS,ROBOT,CACHE>,
							CACHE extends JeeslJobCache<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,STATUS,ROBOT,CACHE>
							>
		extends EjbWithId,EjbSaveable,EjbRemoveable
{	

}