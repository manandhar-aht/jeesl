package org.jeesl.interfaces.model.system.io.revision.tracker;

import org.jeesl.interfaces.model.system.security.user.JeeslUser;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithRecord;
import net.sf.ahtutils.model.interfaces.with.EjbWithUser;

public interface JeeslActionTracker <USER extends JeeslUser<?>>
		extends EjbWithId,EjbWithRecord,EjbWithUser<USER>
{
	
}