package net.sf.ahtutils.interfaces.model.system.security;

import java.util.List;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.with.EjbWithParent;
import net.sf.ahtutils.interfaces.model.with.code.EjbWithCode;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionVisible;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface UtilsSecurityUsecase<L extends UtilsLang,
 									  D extends UtilsDescription, 
 									  C extends UtilsSecurityCategory<L,D,C,R,V,U,A,AT,USER>,
 									  R extends UtilsSecurityRole<L,D,C,R,V,U,A,AT,USER>,
 									  V extends UtilsSecurityView<L,D,C,R,V,U,A,AT,USER>,
 									  U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
 									  A extends UtilsSecurityAction<L,D,C,R,V,U,A,AT,USER>,
 									 AT extends UtilsSecurityActionTemplate<L,D,C,R,V,U,A,AT,USER>,
 									  USER extends UtilsUser<L,D,C,R,V,U,A,AT,USER>>
			extends EjbWithCode,EjbSaveable,EjbWithPositionVisible,EjbWithParent,
					EjbWithLang<L>,EjbWithDescription<D>,
					UtilsSecurityWithCategory<L,D,C,R,V,U,A,AT,USER>,
					UtilsSecurityWithViews<L,D,C,R,V,U,A,AT,USER>,
					UtilsSecurityWithActions<L,D,C,R,V,U,A,AT,USER>
{
	public static final String extractId = "securityUsecases";
	
	public List<R> getRoles();
	public void setRoles(List<R> roles);
	
	public Boolean getDocumentation();
	public void setDocumentation(Boolean documentation);
}