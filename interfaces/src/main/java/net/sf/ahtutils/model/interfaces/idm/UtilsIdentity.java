package net.sf.ahtutils.model.interfaces.idm;

import net.sf.ahtutils.interfaces.model.security.UtilsSecurityAction;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityCategory;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityRole;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityUsecase;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityView;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public interface UtilsIdentity <L extends UtilsLang,
								D extends UtilsDescription,
								C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,
								R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,
								V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,
								U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,
								A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,
								USER extends UtilsUser<L,D,C,R,V,U,A,USER>>
{	
	USER getUser();
	void setUser(USER user);
		
	boolean hasUsecase(String usecaseCode);
	boolean hasView(String code);
	boolean hasRole(String code);
	boolean hasAction(String code);
	
	int sizeAllowedUsecases();
	int sizeAllowedViews();
	int sizeAllowedRoles();
	int sizeAllowedActions();
	
	void allowView(V v);
	void allowRole(R r);
	void allowAction(A a);
}