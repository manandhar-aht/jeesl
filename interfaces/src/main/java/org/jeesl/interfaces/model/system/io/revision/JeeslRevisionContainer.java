package org.jeesl.interfaces.model.system.io.revision;

import org.hibernate.envers.RevisionType;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityView;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityTemplate;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityAction;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityUsecase;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityCategory;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslRevisionContainer <REV extends JeeslRevision,
										T extends EjbWithId,
										USER extends JeeslUser<?,?,?,?,?,?,?,?,USER>>
{					
	REV getInfo();
	
	RevisionType getType();
	
	USER getUser();
	void setUser(USER user);
	
	T getEntity();
}