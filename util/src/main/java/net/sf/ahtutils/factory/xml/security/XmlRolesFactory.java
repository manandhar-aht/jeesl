package net.sf.ahtutils.factory.xml.security;

import java.util.List;

import net.sf.ahtutils.interfaces.model.security.UtilsSecurityAction;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityCategory;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityRole;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityUsecase;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityView;
import net.sf.ahtutils.interfaces.model.security.UtilsUser;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.xml.security.Role;
import net.sf.ahtutils.xml.security.Roles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlRolesFactory <L extends UtilsLang,
								D extends UtilsDescription, 
								C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,
								R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,
								V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,
								U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,
								A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,
								USER extends UtilsUser<L,D,C,R,V,U,A,USER>>
{
	final static Logger logger = LoggerFactory.getLogger(XmlRolesFactory.class);
		
	private Roles q;
	
	public XmlRolesFactory(Roles q)
	{
		this.q=q;
	}

	public Roles build(List<R> roles){return build(roles,null);}
	public Roles build(List<R> roles,String type)
	{
		Role qRole = q.getRole().get(0);
		XmlRoleFactory<L,D,C,R,V,U,A,USER> f = new XmlRoleFactory<L,D,C,R,V,U,A,USER>(qRole);
		
		Roles xml = new Roles();
		xml.setType(type);
		for(R role : roles)
		{
			xml.getRole().add(f.build(role));
		}
		return xml;
		
	}
	
	public static Roles build(){return XmlRolesFactory.buildType(null);}
	public static Roles buildType(String type)
	{
		Roles xml = new Roles();
		xml.setType(type);
		return xml;
	}
}