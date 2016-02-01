package net.sf.ahtutils.interfaces.model.util;

import java.util.Date;

import net.sf.ahtutils.interfaces.model.security.UtilsSecurityAction;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityCategory;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityRole;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityUsecase;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityView;
import net.sf.ahtutils.interfaces.model.security.UtilsUser;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface UtilsAuditTrail<L extends UtilsLang,
								 D extends UtilsDescription,
								 C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,
								 R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,
								 V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,
								 U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,
								 A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,
								 USER extends UtilsUser<L,D,C,R,V,U,A,USER>,
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