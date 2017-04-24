package org.jeesl.interfaces.model.system.job;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslJobConsumer<L extends UtilsLang,D extends UtilsDescription,
							TEMPLATE extends JeeslJobTemplate<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,STATUS,CONSUMER>,
							CATEGORY extends UtilsStatus<CATEGORY,L,D>,
							TYPE extends UtilsStatus<TYPE,L,D>,
							JOB extends JeeslJob<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,STATUS,CONSUMER>,
							FEEDBACK extends UtilsStatus<FEEDBACK,L,D>,
							STATUS extends UtilsStatus<STATUS,L,D>,
							CONSUMER extends JeeslJobConsumer<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,STATUS,CONSUMER>
							>
		extends EjbWithId,EjbSaveable,EjbRemoveable,
					EjbWithLang<L>,EjbWithDescription<D>
{	

}