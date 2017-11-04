package org.jeesl.factory.txt.system.security;

import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityView;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityTemplate;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.jeesl.interfaces.model.system.security.util.JeeslStaff;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityAction;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityUsecase;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityCategory;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class TxtStaffFactory <L extends UtilsLang,
										 D extends UtilsDescription,
										 C extends JeeslSecurityCategory<L,D>,
										 R extends JeeslSecurityRole<L,D,C,V,U,A,USER>,
										 V extends JeeslSecurityView<L,D,C,R,U,A>,
										 U extends JeeslSecurityUsecase<L,D,C,R,V,A>,
										 A extends JeeslSecurityAction<L,D,R,V,U,AT>,
										 AT extends JeeslSecurityTemplate<L,D,C,R,V,U,A,AT,USER>,
										 USER extends JeeslUser<R>,
										 STAFF extends JeeslStaff<L,D,C,R,V,U,A,AT,USER,D1,D2>,
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