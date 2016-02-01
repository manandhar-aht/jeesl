package net.sf.ahtutils.model.interfaces.with;

import java.util.List;

import net.sf.ahtutils.interfaces.model.security.UtilsSecurityAction;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityCategory;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityRole;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityUsecase;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityView;
import net.sf.ahtutils.interfaces.model.security.UtilsUser;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.util.UtilsAuditTrail;

public interface EjbWithAuditTrails <L extends UtilsLang,
									D extends UtilsDescription,
									C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,
									R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,
									V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,
									U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,
									A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,
									USER extends UtilsUser<L,D,C,R,V,U,A,USER>,
									T extends UtilsAuditTrail<L,D,C,R,V,U,A,USER,TY>,
									TY extends UtilsStatus<TY,L,D>>
{
	List<T> getAuditTrails();
	void setAuditTrails(List<T> auditTrails);
}
