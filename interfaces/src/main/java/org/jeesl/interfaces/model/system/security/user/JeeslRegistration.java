package org.jeesl.interfaces.model.system.security.user;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslRegistration <L extends UtilsLang, D extends UtilsDescription,
							USER extends JeeslUser<L,D,?,?,?,?,?,?,USER>,
							REGSTATUS extends UtilsStatus<REGSTATUS,L,D>>
		extends EjbWithId,EjbSaveable,EjbRemoveable
{	
	USER getUser();
	void setUser(USER user);
	
	REGSTATUS getStatus();
	void setStatus(REGSTATUS status);
}