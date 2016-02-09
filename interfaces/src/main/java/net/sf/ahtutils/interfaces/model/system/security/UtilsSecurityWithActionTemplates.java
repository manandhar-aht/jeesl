package net.sf.ahtutils.interfaces.model.system.security;

import java.util.List;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface UtilsSecurityWithActionTemplates<L extends UtilsLang, D extends UtilsDescription, 
						 		   C extends UtilsSecurityCategory<L,D,C,R,V,U,A,AT,USER>,
						 		   R extends UtilsSecurityRole<L,D,C,R,V,U,A,AT,USER>,
						 		   V extends UtilsSecurityView<L,D,C,R,V,U,A,AT,USER>,
						 		   U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
						 		   A extends UtilsSecurityAction<L,D,C,R,V,U,A,AT,USER>,
						 		   AT extends UtilsSecurityActionTemplate<L,D,C,R,V,U,A,AT,USER>,
						 		   USER extends UtilsUser<L,D,C,R,V,U,A,AT,USER>>
	extends EjbWithId
{
	public List<AT> getTemplates();
	public void setTemplates(List<AT> actions);
}