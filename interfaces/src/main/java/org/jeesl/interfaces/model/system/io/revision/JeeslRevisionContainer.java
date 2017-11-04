package org.jeesl.interfaces.model.system.io.revision;

import org.hibernate.envers.RevisionType;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslRevisionContainer <REV extends JeeslRevision,
										T extends EjbWithId,
										USER extends JeeslUser<?>>
{					
	REV getInfo();
	
	RevisionType getType();
	
	USER getUser();
	void setUser(USER user);
	
	T getEntity();
}