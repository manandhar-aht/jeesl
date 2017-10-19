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
										L extends UtilsLang,
										D extends UtilsDescription,
										C extends JeeslSecurityCategory<L,D>,
										R extends JeeslSecurityRole<L,D,C,R,V,U,A,AT,USER>,
										V extends JeeslSecurityView<L,D,C,R,V,U,A,AT,USER>,
										U extends JeeslSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
										A extends JeeslSecurityAction<L,D,C,R,V,U,A,AT,USER>,
										AT extends JeeslSecurityTemplate<L,D,C,R,V,U,A,AT,USER>,
										USER extends JeeslUser<L,D,C,R,V,U,A,AT,USER>>
{					
	REV getInfo();
	
	RevisionType getType();
	
	USER getUser();
	void setUser(USER user);
	
	T getEntity();
}