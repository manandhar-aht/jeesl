package  org.jeesl.factory.ejb.system.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

public class EjbStaffFactory <L extends UtilsLang, D extends UtilsDescription,
						C extends JeeslSecurityCategory<L,D>,
						R extends JeeslSecurityRole<L,D,C,R,V,U,A,AT,USER>,
						V extends JeeslSecurityView<L,D,C,R,U,A>,
						U extends JeeslSecurityUsecase<L,D,C,R,V,A>,
						A extends JeeslSecurityAction<L,D,C,R,V,U,A,AT,USER>,
						AT extends JeeslSecurityTemplate<L,D,C,R,V,U,A,AT,USER>,
						USER extends JeeslUser<L,D,C,R,V,U,A,AT,USER>,
						STAFF extends JeeslStaff<L,D,C,R,V,U,A,AT,USER,D1,D2>,
						D1 extends EjbWithId, D2 extends EjbWithId>
	extends AbstractEjbSecurityFactory<L,D,C,R,V,U,A,AT,USER>
{
	final static Logger logger = LoggerFactory.getLogger(EjbStaffFactory.class);
	
	final Class<STAFF> cStaff;
	
    public static <L extends UtilsLang, D extends UtilsDescription,
					C extends JeeslSecurityCategory<L,D>,
					R extends JeeslSecurityRole<L,D,C,R,V,U,A,AT,USER>,
					V extends JeeslSecurityView<L,D,C,R,U,A>,
					U extends JeeslSecurityUsecase<L,D,C,R,V,A>,
					A extends JeeslSecurityAction<L,D,C,R,V,U,A,AT,USER>,
					AT extends JeeslSecurityTemplate<L,D,C,R,V,U,A,AT,USER>,
					USER extends JeeslUser<L,D,C,R,V,U,A,AT,USER>,
					STAFF extends JeeslStaff<L,D,C,R,V,U,A,AT,USER,D1,D2>,
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
					C extends JeeslSecurityCategory<L,D>,
					R extends JeeslSecurityRole<L,D,C,R,V,U,A,AT,USER>,
					V extends JeeslSecurityView<L,D,C,R,U,A>,
					U extends JeeslSecurityUsecase<L,D,C,R,V,A>,
					A extends JeeslSecurityAction<L,D,C,R,V,U,A,AT,USER>,
					AT extends JeeslSecurityTemplate<L,D,C,R,V,U,A,AT,USER>,
					USER extends JeeslUser<L,D,C,R,V,U,A,AT,USER>,
					STAFF extends JeeslStaff<L,D,C,R,V,U,A,AT,USER,D1,D2>,
					D1 extends EjbWithId, D2 extends EjbWithId>
    	List<USER> toUsers(List<STAFF> staffs)
	{
    	Set<USER> set = new HashSet<USER>();
    	for(STAFF staff : staffs){set.add(staff.getUser());}
    	return new ArrayList<USER>(set);
	}
    
    public static <L extends UtilsLang,
			D extends UtilsDescription,
			C extends JeeslSecurityCategory<L,D>,
			R extends JeeslSecurityRole<L,D,C,R,V,U,A,AT,USER>,
			V extends JeeslSecurityView<L,D,C,R,U,A>,
			U extends JeeslSecurityUsecase<L,D,C,R,V,A>,
			A extends JeeslSecurityAction<L,D,C,R,V,U,A,AT,USER>,
			AT extends JeeslSecurityTemplate<L,D,C,R,V,U,A,AT,USER>,
			USER extends JeeslUser<L,D,C,R,V,U,A,AT,USER>,
			STAFF extends JeeslStaff<L,D,C,R,V,U,A,AT,USER,D1,D2>,
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
    
    public static <L extends UtilsLang, D extends UtilsDescription,
			C extends JeeslSecurityCategory<L,D>,
			R extends JeeslSecurityRole<L,D,C,R,V,U,A,AT,USER>,
			V extends JeeslSecurityView<L,D,C,R,U,A>,
			U extends JeeslSecurityUsecase<L,D,C,R,V,A>,
			A extends JeeslSecurityAction<L,D,C,R,V,U,A,AT,USER>,
			AT extends JeeslSecurityTemplate<L,D,C,R,V,U,A,AT,USER>,
			USER extends JeeslUser<L,D,C,R,V,U,A,AT,USER>,
			STAFF extends JeeslStaff<L,D,C,R,V,U,A,AT,USER,D1,D2>,
			D1 extends EjbWithId, D2 extends EjbWithId>
		List<D1> toDomains(List<STAFF> staffs)
	{
		Set<D1> set = new HashSet<D1>();
		for(STAFF staff : staffs)
		{
			if(!set.contains(staff.getDomain())){set.add(staff.getDomain());}
		}
		return new ArrayList<D1>(set);
	}
    
    public static <L extends UtilsLang, D extends UtilsDescription,
			C extends JeeslSecurityCategory<L,D>,
			R extends JeeslSecurityRole<L,D,C,R,V,U,A,AT,USER>,
			V extends JeeslSecurityView<L,D,C,R,U,A>,
			U extends JeeslSecurityUsecase<L,D,C,R,V,A>,
			A extends JeeslSecurityAction<L,D,C,R,V,U,A,AT,USER>,
			AT extends JeeslSecurityTemplate<L,D,C,R,V,U,A,AT,USER>,
			USER extends JeeslUser<L,D,C,R,V,U,A,AT,USER>,
			STAFF extends JeeslStaff<L,D,C,R,V,U,A,AT,USER,D1,D2>,
			D1 extends EjbWithId, D2 extends EjbWithId>
		Set<D1> toDomainSet(List<STAFF> staffs)
	{
		Set<D1> set = new HashSet<D1>();
		for(STAFF staff : staffs)
		{
			if(!set.contains(staff.getDomain())){set.add(staff.getDomain());}
		}
		return set;
	}
}