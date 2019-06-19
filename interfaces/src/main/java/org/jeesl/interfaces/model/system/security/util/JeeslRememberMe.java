package org.jeesl.interfaces.model.system.security.util;

import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.jeesl.interfaces.model.system.with.code.EjbWithCode;
import org.jeesl.interfaces.model.util.date.EjbWithValidUntil;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithUser;

public interface JeeslRememberMe <USER extends JeeslUser<?>>
		extends EjbWithId,EjbSaveable,EjbRemoveable,
				EjbWithCode,EjbWithValidUntil,
				EjbWithUser<USER>
{	

}