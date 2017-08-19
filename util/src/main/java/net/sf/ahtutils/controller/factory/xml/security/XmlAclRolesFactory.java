package net.sf.ahtutils.controller.factory.xml.security;

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
import net.sf.ahtutils.xml.access.Roles;

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
 			C extends JeeslSecurityCategory<L,D,C,R,V,U,A,AT,USER>,
 			R extends JeeslSecurityRole<L,D,C,R,V,U,A,AT,USER>,
 			V extends JeeslSecurityView<L,D,C,R,V,U,A,AT,USER>,
 			U extends JeeslSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
 			A extends JeeslSecurityAction<L,D,C,R,V,U,A,AT,USER>,
 			AT extends JeeslSecurityTemplate<L,D,C,R,V,U,A,AT,USER>,
 			USER extends UtilsUser<L,D,C,R,V,U,A,AT,USER>>
			Roles getProjectRoles(List<R> lRoles)
	{
		Roles roles = new Roles();
		
		if(qProjectRoles.isSetRole())
		{
			XmlRoleFactory<L,D,C,R,V,U,A,AT,USER> f = new XmlRoleFactory<L,D,C,R,V,U,A,AT,USER>(qProjectRoles.getRole().get(0),lang);
			for(R aclProjectRole : lRoles)
			{
				roles.getRole().add(f.create(aclProjectRole));
			}
		}
		return roles;
	}
}