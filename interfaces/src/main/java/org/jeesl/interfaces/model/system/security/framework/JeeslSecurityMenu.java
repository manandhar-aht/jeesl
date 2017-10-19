package org.jeesl.interfaces.model.system.security.framework;

import org.jeesl.interfaces.model.system.security.user.JeeslUser;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPosition;

public interface JeeslSecurityMenu<L extends UtilsLang, D extends UtilsDescription,
								   C extends JeeslSecurityCategory<L,D,C,R,V,U,A,AT,USER>,
								   R extends JeeslSecurityRole<L,D,C,R,V,U,A,AT,USER>,
								   V extends JeeslSecurityView<L,D,C,R,V,U,A,AT,USER>,
								   U extends JeeslSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
								   A extends JeeslSecurityAction<L,D,C,R,V,U,A,AT,USER>,
								   AT extends JeeslSecurityTemplate<L,D,C,R,V,U,A,AT,USER>,
								   M extends JeeslSecurityMenu<L,D,C,R,V,U,A,AT,M,USER>,
								   USER extends JeeslUser<L,D,C,R,V,U,A,AT,USER>>
			extends EjbSaveable,EjbWithPosition
{
	public static final String extractId = "securityMenu";
	public static final String keyRoot = "root";
	
	M getParent();
	void setParent(M menu);
	
	V getView();
	void setView(V view);
}