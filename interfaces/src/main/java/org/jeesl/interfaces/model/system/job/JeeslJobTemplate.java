package org.jeesl.interfaces.model.system.job;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.code.EjbWithCode;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslJobTemplate<L extends UtilsLang,D extends UtilsDescription,
									TEMPLATE extends JeeslJobTemplate<L,D,TEMPLATE,CATEGORY,TYPE,JOB,STATUS>,
									CATEGORY extends UtilsStatus<CATEGORY,L,D>,
									TYPE extends UtilsStatus<TYPE,L,D>,
									JOB extends JeeslJob<L,D,TEMPLATE,CATEGORY,TYPE,JOB,STATUS>,
									STATUS extends UtilsStatus<STATUS,L,D>
									>
		extends EjbWithId,EjbSaveable,EjbRemoveable,
				EjbWithCode,EjbWithLang<L>,EjbWithDescription<D>
{	
	
	public static enum Status{queue,spooling,sent,failed};
	public static enum Attributes{category,status,recordCreation,recordSpool};
	
	CATEGORY getCategory();
	void setCategory(CATEGORY category);
	
	TYPE getType();
	void setType(TYPE type);
}