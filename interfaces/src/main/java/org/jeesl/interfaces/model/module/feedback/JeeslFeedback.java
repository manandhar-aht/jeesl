package org.jeesl.interfaces.model.module.feedback;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.EjbWithEmail;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithRecord;

public interface JeeslFeedback<L extends UtilsLang, D extends UtilsDescription,
								USER extends EjbWithEmail,
								TYPE extends UtilsStatus<TYPE,L,D>>
						extends EjbWithId,EjbWithRecord
{	
	TYPE getType();
	void setType(TYPE type);
	
	USER getUser();
	void setUser(USER user);
}