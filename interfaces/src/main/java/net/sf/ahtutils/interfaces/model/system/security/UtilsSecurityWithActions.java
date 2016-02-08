package net.sf.ahtutils.interfaces.model.system.security;

import java.util.List;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public interface UtilsSecurityWithActions<L extends UtilsLang,
						 		   D extends UtilsDescription, 
						 		   C extends UtilsSecurityCategory<L,D,C,R,V,U,A,AT,USER>,
						 		   R extends UtilsSecurityRole<L,D,C,R,V,U,A,AT,USER>,
						 		   V extends UtilsSecurityView<L,D,C,R,V,U,A,AT,USER>,
						 		   U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
						 		   A extends UtilsSecurityAction<L,D,C,R,V,U,A,AT,USER>,
						 		  AT extends UtilsSecurityActionTemplate<L,D,C,R,V,U,A,AT,USER>,
						 		   USER extends UtilsUser<L,D,C,R,V,U,A,AT,USER>>
{
	public List<A> getActions();
	public void setActions(List<A> actions);
}