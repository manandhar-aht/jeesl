package net.sf.ahtutils.controller.factory.xml.security;

import java.util.List;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityAction;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityCategory;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityRole;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityUsecase;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityView;
import net.sf.ahtutils.interfaces.model.system.security.UtilsUser;
import net.sf.ahtutils.xml.access.Roles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlAclRolesFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlAclRolesFactory.class);
		
	private Roles qProjectRoles;
	
	private String lang;
	
	public XmlAclRolesFactory(Roles qProjectRoles, String lang)
	{
		this.qProjectRoles=qProjectRoles;
		this.lang=lang;
	}
	
	public <L extends UtilsLang,
 			D extends UtilsDescription,
 			C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,
 			R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,
 			V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,
 			U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,
 			A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,
 			USER extends UtilsUser<L,D,C,R,V,U,A,USER>>
			Roles getProjectRoles(List<R> lRoles)
	{
		Roles roles = new Roles();
		
		if(qProjectRoles.isSetRole())
		{
			XmlRoleFactory<L,D,C,R,V,U,A,USER> f = new XmlRoleFactory<L,D,C,R,V,U,A,USER>(qProjectRoles.getRole().get(0),lang);
			for(R aclProjectRole : lRoles)
			{
				roles.getRole().add(f.create(aclProjectRole));
			}
		}
		return roles;
	}
}