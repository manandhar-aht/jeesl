package net.sf.ahtutils.factory.xml.audit;

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
import net.sf.ahtutils.interfaces.model.system.security.UtilsUser;
import net.sf.ahtutils.interfaces.model.with.EjbWithEmail;
import net.sf.ahtutils.xml.audit.User;

public class XmlUserFactory <L extends UtilsLang,
							D extends UtilsDescription, 
							C extends UtilsSecurityCategory<L,D,C,R,V,U,A,AT,USER>,
							R extends UtilsSecurityRole<L,D,C,R,V,U,A,AT,USER>,
							V extends UtilsSecurityView<L,D,C,R,V,U,A,AT,USER>,
							U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
							A extends UtilsSecurityAction<L,D,C,R,V,U,A,AT,USER>,
							AT extends UtilsSecurityActionTemplate<L,D,C,R,V,U,A,AT,USER>,
							USER extends UtilsUser<L,D,C,R,V,U,A,AT,USER>>
{
	final static Logger logger = LoggerFactory.getLogger(XmlUserFactory.class);
	
	public User build(USER user)
	{
		User xml = new User();
		xml.setFirstName(user.getFirstName());
		xml.setLastName(user.getLastName());
		
		
		
		return xml;
	}
	
	public User labelFirstLastEmail(USER user)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(user.getFirstName());
		sb.append(" ");
		sb.append(user.getLastName());
		if(user.getClass().isAssignableFrom(EjbWithEmail.class))
		{
			EjbWithEmail email = (EjbWithEmail)user;
			sb.append(" (").append(email.getEmail()).append(")");
		}
		
		User xml = new User();
		xml.setLabel(sb.toString());
		
		return xml;
	}
}