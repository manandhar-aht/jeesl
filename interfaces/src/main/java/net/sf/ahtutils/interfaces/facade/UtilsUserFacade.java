package net.sf.ahtutils.interfaces.facade;

import java.util.List;

import net.sf.ahtutils.interfaces.model.security.UtilsSecurityAction;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityCategory;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityRole;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityUsecase;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityView;
import net.sf.ahtutils.interfaces.model.security.UtilsUser;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public interface UtilsUserFacade <L extends UtilsLang,
										D extends UtilsDescription,
										C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,
										R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,
										V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,
										U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,
										A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,
										USER extends UtilsUser<L,D,C,R,V,U,A,USER>>
	extends UtilsFacade
{	
	USER load(Class<USER> cUser, USER user);
	List<USER> likeNameFirstLast(Class<USER> cUser, String query);
}