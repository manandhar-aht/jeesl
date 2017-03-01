package org.jeesl.interfaces.model.system.job;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslJob<L extends UtilsLang,D extends UtilsDescription,
							TEMPLATE extends JeeslJobTemplate<L,D,TEMPLATE,CATEGORY,TYPE,JOB,STATUS>,
							CATEGORY extends UtilsStatus<CATEGORY,L,D>,
							TYPE extends UtilsStatus<TYPE,L,D>,
							JOB extends JeeslJob<L,D,TEMPLATE,CATEGORY,TYPE,JOB,STATUS>,
							STATUS extends UtilsStatus<STATUS,L,D>
							>
		extends EjbWithId,EjbSaveable,EjbRemoveable
{	
	
	public static enum Status{queue,spooling,sent,failed};
	public static enum Attributes{category,status,recordCreation,recordSpool};
	
	TEMPLATE getTemplate();
	void setTemplate(TEMPLATE template);
	
	STATUS getStatus();
	void setStatus(STATUS category);
}