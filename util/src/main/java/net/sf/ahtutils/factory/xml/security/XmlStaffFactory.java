package net.sf.ahtutils.factory.xml.security;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
import net.sf.ahtutils.xml.security.Staff;
import net.sf.ahtutils.xml.status.Domain;

public class XmlStaffFactory<L extends UtilsLang,
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
			XmlUserFactory<L,D,C,R,V,U,A,USER> f = new XmlUserFactory<L,D,C,R,V,U,A,USER>(q.getUser());
			xml.setUser(f.build(staff.getUser()));
		}
		
		if(q.isSetRole())
		{
			XmlRoleFactory<L,D,C,R,V,U,A,USER> f = new XmlRoleFactory<L,D,C,R,V,U,A,USER>(lang,q.getRole());
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