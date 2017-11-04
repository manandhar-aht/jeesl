package org.jeesl.factory.xml.system.security;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

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
import net.sf.ahtutils.xml.security.Staff;
import net.sf.ahtutils.xml.status.Domain;

public class XmlStaffFactory<L extends UtilsLang,
							D extends UtilsDescription,
							C extends JeeslSecurityCategory<L,D>,
							R extends JeeslSecurityRole<L,D,C,V,U,A,USER>,
							V extends JeeslSecurityView<L,D,C,R,U,A>,
							U extends JeeslSecurityUsecase<L,D,C,R,V,A>,
							A extends JeeslSecurityAction<L,D,R,V,U,AT>,
							AT extends JeeslSecurityTemplate<L,D,C>,
							USER extends JeeslUser<R>,
							STAFF extends JeeslStaff<L,D,C,R,V,U,A,AT,USER,D1,D2>,
							D1 extends EjbWithId, D2 extends EjbWithId>
{
	final static Logger logger = LoggerFactory.getLogger(XmlStaffFactory.class);
		
	private String lang;
	private Staff q;
	
	public XmlStaffFactory(Staff q){this(null,q);}
	public XmlStaffFactory(String lang, Staff q)
	{
		this.lang=lang;
		this.q=q;
	}
	
	public static Staff build()
	{
		Staff xml = new Staff();

		return xml;
	}
	
	public Staff build(STAFF staff)
	{
		Staff xml = new Staff();
		
		if(q.isSetId()){xml.setId(staff.getId());}
		
		if(q.isSetUser())
		{
			XmlUserFactory<L,D,C,R,V,U,A,AT,USER> f = new XmlUserFactory<L,D,C,R,V,U,A,AT,USER>(q.getUser());
			xml.setUser(f.build(staff.getUser()));
		}
		
		if(q.isSetRole())
		{
			XmlRoleFactory<L,D,C,R,V,U,A,AT,USER> f = new XmlRoleFactory<L,D,C,R,V,U,A,AT,USER>(lang,q.getRole());
			xml.setRole(f.build(staff.getRole()));
		}
		
		if(q.isSetDomain())
		{
			Domain domain = new Domain();
			domain.setId(staff.getDomain().getId());
			xml.setDomain(domain);
		}
		
		return xml;
	}
	
	public static Map<Long,Staff> toMapId(List<Staff> staffs)
	{
		Map<Long,Staff> map = new Hashtable<Long,Staff>();
		for(Staff staff : staffs)
		{
			long id = staff.getId();
			if(!map.containsKey(id)){map.put(id,staff);}
		}
		return map;
	}
}