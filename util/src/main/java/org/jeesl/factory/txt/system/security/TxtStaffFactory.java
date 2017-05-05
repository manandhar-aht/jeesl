package org.jeesl.factory.txt.system.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityAction;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityActionTemplate;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityCategory;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityRole;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityUsecase;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityView;
import net.sf.ahtutils.interfaces.model.system.security.UtilsStaff;
import net.sf.ahtutils.interfaces.model.system.security.UtilsUser;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class TxtStaffFactory <L extends UtilsLang,
										 D extends UtilsDescription,
										 C extends UtilsSecurityCategory<L,D,C,R,V,U,A,AT,USER>,
										 R extends UtilsSecurityRole<L,D,C,R,V,U,A,AT,USER>,
										 V extends UtilsSecurityView<L,D,C,R,V,U,A,AT,USER>,
										 U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
										 A extends UtilsSecurityAction<L,D,C,R,V,U,A,AT,USER>,
										 AT extends UtilsSecurityActionTemplate<L,D,C,R,V,U,A,AT,USER>,
										 USER extends UtilsUser<L,D,C,R,V,U,A,AT,USER>,
										 STAFF extends UtilsStaff<L,D,C,R,V,U,A,AT,USER,D1,D2>,
										 D1 extends EjbWithId, D2 extends EjbWithId>
{
	final static Logger logger = LoggerFactory.getLogger(TxtStaffFactory.class);
    
	private final String localeCode;
	
    public TxtStaffFactory(final String localeCode)
    {
    	this.localeCode=localeCode;
    } 
    
    public String staff(STAFF staff)
    {
    	StringBuffer sb = new StringBuffer();
    	sb.append("Role:").append(staff.getRole().getName().get(localeCode).getLang());
    	sb.append(" User:").append(staff.getUser().getFirstName()).append(staff.getUser().getLastName());
    	sb.append(" Doamin:").append(staff.getDomain().toString());
    	return sb.toString();
    }
}