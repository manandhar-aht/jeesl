package net.sf.ahtutils.interfaces.model.util;

import java.util.Date;

import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityView;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityTemplate;
import org.jeesl.interfaces.model.system.security.user.UtilsUser;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityAction;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityUsecase;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityCategory;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface UtilsAuditTrail<L extends UtilsLang,
								 D extends UtilsDescription,
								 C extends JeeslSecurityCategory<L,D,C,R,V,U,A,AT,USER>,
								 R extends JeeslSecurityRole<L,D,C,R,V,U,A,AT,USER>,
								 V extends JeeslSecurityView<L,D,C,R,V,U,A,AT,USER>,
								 U extends JeeslSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
								 A extends JeeslSecurityAction<L,D,C,R,V,U,A,AT,USER>,
								 AT extends JeeslSecurityTemplate<L,D,C,R,V,U,A,AT,USER>,
								 USER extends UtilsUser<L,D,C,R,V,U,A,AT,USER>,
								 TY extends UtilsStatus<TY,L,D>>
					extends EjbWithId
{
	public USER getUser();
	public void setUser(USER user);
	
	public TY getType();
	public void setType(TY type);
	
	public Date getRecord();
	public void setRecord(Date record);
}