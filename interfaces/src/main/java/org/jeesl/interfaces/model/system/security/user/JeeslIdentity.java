package org.jeesl.interfaces.model.system.security.user;

import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityView;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityTemplate;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityAction;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityUsecase;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityCategory;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public interface JeeslIdentity <L extends UtilsLang, D extends UtilsDescription,
								C extends JeeslSecurityCategory<L,D>,
								R extends JeeslSecurityRole<L,D,C,R,V,U,A,AT,USER>,
								V extends JeeslSecurityView<L,D,C,R,U,A>,
								U extends JeeslSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
								A extends JeeslSecurityAction<L,D,C,R,V,U,A,AT,USER>,
								AT extends JeeslSecurityTemplate<L,D,C,R,V,U,A,AT,USER>,
								USER extends JeeslUser<L,D,C,R,V,U,A,AT,USER>>
{	
	USER getUser();
	void setUser(USER user);
		
	boolean isLoggedIn();
	void setLoggedIn(boolean loggedIn);
	
	boolean hasUsecase(String usecaseCode);
	boolean hasView(String code);
	boolean hasView(V view);
	boolean hasRole(String code);
	boolean hasAction(String code);
	
	int sizeAllowedUsecases();
	int sizeAllowedViews();
	int sizeAllowedRoles();
	int sizeAllowedActions();
	
	void allowView(V view);
	void allowRole(R role);
	void allowAction(A action);
}