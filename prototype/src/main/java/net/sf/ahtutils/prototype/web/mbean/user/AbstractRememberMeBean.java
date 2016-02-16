package net.sf.ahtutils.prototype.web.mbean.user;

import java.io.Serializable;

import net.sf.ahtutils.interfaces.facade.UtilsUserFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityAction;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityActionTemplate;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityCategory;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityRole;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityUsecase;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityView;
import net.sf.ahtutils.interfaces.model.system.security.UtilsUser;
import net.sf.ahtutils.prototype.web.mbean.admin.AbstractAdminBean;

public class AbstractRememberMeBean <L extends UtilsLang,D extends UtilsDescription,
										C extends UtilsSecurityCategory<L,D,C,R,V,U,A,AT,USER>,
										R extends UtilsSecurityRole<L,D,C,R,V,U,A,AT,USER>,
										V extends UtilsSecurityView<L,D,C,R,V,U,A,AT,USER>,
										U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
										A extends UtilsSecurityAction<L,D,C,R,V,U,A,AT,USER>,
										AT extends UtilsSecurityActionTemplate<L,D,C,R,V,U,A,AT,USER>,
										USER extends UtilsUser<L,D,C,R,V,U,A,AT,USER>>
		extends AbstractAdminBean<L,D>
		implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private boolean dummy; public boolean isDummy() {return dummy;} public void setDummy(boolean dummy) {this.dummy = dummy;}
	
	protected void preSelect(UtilsUserFacade<L,D,C,R,V,U,A,AT,USER> fUser)
	{
		
	}
}