package  net.sf.ahtutils.factory.ejb.security;

import net.sf.ahtutils.interfaces.model.security.UtilsSecurityAction;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityCategory;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityRole;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityUsecase;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityView;
import net.sf.ahtutils.interfaces.model.security.UtilsStaff;
import net.sf.ahtutils.interfaces.model.security.UtilsUser;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class EjbStaffFactory <L extends UtilsLang,
						D extends UtilsDescription,
						C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,
						R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,
						V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,
						U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,
						A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,
						USER extends UtilsUser<L,D,C,R,V,U,A,USER>,
						STAFF extends UtilsStaff<L,D,C,R,V,U,A,USER,DOMAIN>,
						DOMAIN extends EjbWithId>
{
	final static Logger logger = LoggerFactory.getLogger(EjbStaffFactory.class);
	
	final Class<STAFF> cStaff;
	
    public static <L extends UtilsLang,
					D extends UtilsDescription,
					C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,
					R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,
					V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,
					U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,
					A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,
					USER extends UtilsUser<L,D,C,R,V,U,A,USER>,
					STAFF extends UtilsStaff<L,D,C,R,V,U,A,USER,DOMAIN>,
					DOMAIN extends EjbWithId>
    	EjbStaffFactory<L,D,C,R,V,U,A,USER,STAFF,DOMAIN> factory(final Class<STAFF> cStaff)
    {
        return new EjbStaffFactory<L,D,C,R,V,U,A,USER,STAFF,DOMAIN>(cStaff);
    }
    
    public EjbStaffFactory(final Class<STAFF> cStaff)
    {
        this.cStaff = cStaff;
    } 
    
    public STAFF build(USER user, R role, DOMAIN domain)
    {
    	STAFF ejb = null;
    	
    	try
    	{
			ejb = cStaff.newInstance();
			ejb.setUser(user);
			ejb.setRole(role);
			ejb.setDomain(domain);
		}
    	catch (InstantiationException e) {e.printStackTrace();}
    	catch (IllegalAccessException e) {e.printStackTrace();}
    	
    	return ejb;
    }
}