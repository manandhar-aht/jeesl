package net.sf.ahtutils.interfaces.model.system.security;

import org.jeesl.interfaces.model.system.with.code.EjbWithCode;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.date.EjbWithValidUntil;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithUser;

public interface UtilsRememberMe <L extends UtilsLang, D extends UtilsDescription,
							C extends UtilsSecurityCategory<L,D,C,R,V,U,A,AT,USER>,
							R extends UtilsSecurityRole<L,D,C,R,V,U,A,AT,USER>,
							V extends UtilsSecurityView<L,D,C,R,V,U,A,AT,USER>,
							U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
							A extends UtilsSecurityAction<L,D,C,R,V,U,A,AT,USER>,
							AT extends UtilsSecurityActionTemplate<L,D,C,R,V,U,A,AT,USER>,
							USER extends UtilsUser<L,D,C,R,V,U,A,AT,USER>>
		extends EjbWithId,EjbSaveable,EjbRemoveable,
				EjbWithCode,EjbWithValidUntil,
				EjbWithUser<L,D,C,R,V,U,A,AT,USER>
{	

}