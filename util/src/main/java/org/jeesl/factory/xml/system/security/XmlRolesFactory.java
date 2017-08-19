package org.jeesl.factory.xml.system.security;

import java.util.List;

import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityView;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityTemplate;
import org.jeesl.interfaces.model.system.security.user.UtilsUser;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityAction;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityUsecase;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityCategory;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.xml.security.Role;
import net.sf.ahtutils.xml.security.Roles;

public class XmlRolesFactory <L extends UtilsLang, D extends UtilsDescription, 
								C extends JeeslSecurityCategory<L,D,C,R,V,U,A,AT,USER>,
								R extends JeeslSecurityRole<L,D,C,R,V,U,A,AT,USER>,
								V extends JeeslSecurityView<L,D,C,R,V,U,A,AT,USER>,
								U extends JeeslSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
								A extends JeeslSecurityAction<L,D,C,R,V,U,A,AT,USER>,
								AT extends JeeslSecurityTemplate<L,D,C,R,V,U,A,AT,USER>,
								USER extends UtilsUser<L,D,C,R,V,U,A,AT,USER>>
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
		XmlRoleFactory<L,D,C,R,V,U,A,AT,USER> f = new XmlRoleFactory<L,D,C,R,V,U,A,AT,USER>(qRole);
		
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