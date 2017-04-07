package  net.sf.ahtutils.factory.ejb.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

public class EjbStaffFactory <L extends UtilsLang,
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
	extends AbstractEjbSecurityFactory<L,D,C,R,V,U,A,AT,USER>
{
	final static Logger logger = LoggerFactory.getLogger(EjbStaffFactory.class);
	
	final Class<STAFF> cStaff;
	
    public static <L extends UtilsLang,
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
    	EjbStaffFactory<L,D,C,R,V,U,A,AT,USER,STAFF,D1,D2> factory(final Class<STAFF> cStaff)
    {
        return new EjbStaffFactory<L,D,C,R,V,U,A,AT,USER,STAFF,D1,D2>(cStaff);
    }
    
    public EjbStaffFactory(final Class<STAFF> cStaff)
    {
    	super(null,null);
        this.cStaff = cStaff;
    } 
    
    public STAFF build(USER user, R role, D1 domain)
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
    
    public static <L extends UtilsLang,
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
    	List<USER> toUsers(List<STAFF> staffs)
	{
    	Set<USER> set = new HashSet<USER>();
    	for(STAFF staff : staffs){set.add(staff.getUser());}
    	return new ArrayList<USER>(set);
	}
    
    public static <L extends UtilsLang,
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
		Map<D1,List<USER>> toMapDomainUsers(List<STAFF> staffs)
	{
    	Map<D1,List<USER>> map = new HashMap<D1,List<USER>>();
		for(STAFF staff : staffs)
		{
			if(!map.containsKey(staff.getDomain())){map.put(staff.getDomain(), new ArrayList<USER>());}
			if(!map.get(staff.getDomain()).contains(staff.getUser())){map.get(staff.getDomain()).add(staff.getUser());}
		}
		return map;
	}
}