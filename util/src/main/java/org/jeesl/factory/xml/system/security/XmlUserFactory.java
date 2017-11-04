package org.jeesl.factory.xml.system.security;

import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityView;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityTemplate;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityAction;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityUsecase;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityCategory;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.jeesl.model.xml.jeesl.QuerySecurity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.xml.security.User;

public class XmlUserFactory<L extends UtilsLang, D extends UtilsDescription, 
							C extends JeeslSecurityCategory<L,D>,
							R extends JeeslSecurityRole<L,D,C,V,U,A,USER>,
							V extends JeeslSecurityView<L,D,C,R,U,A>,
							U extends JeeslSecurityUsecase<L,D,C,R,V,A>,
							A extends JeeslSecurityAction<L,D,R,V,U,AT>,
							AT extends JeeslSecurityTemplate<L,D,C,R,V,U,A,AT,USER>,
							USER extends JeeslUser<L,D,C,R,V,U,A,AT,USER>>
{
	final static Logger logger = LoggerFactory.getLogger(XmlUserFactory.class);
		
	private final User q;
	
	public XmlUserFactory(QuerySecurity query){this(query.getUser());}
	public XmlUserFactory(User q)
	{
		this.q=q;
	}
	
	public User build(USER user)
	{
		User xml = new User();
		if(q.isSetId()){xml.setId(user.getId());}
		if(q.isSetFirstName()){xml.setFirstName(user.getFirstName());}
		if(q.isSetLastName()){xml.setLastName(user.getLastName());}
		if(q.isSetName())
		{
			StringBuilder sb = new StringBuilder();
			sb.append(user.getFirstName());
			sb.append(" ");
			sb.append(user.getLastName());
			xml.setName(sb.toString().trim());
		}
		
		return xml;
	}
	
	public static User create(String firstName, String lastName)
	{
		User xml = new User();
		xml.setFirstName(firstName);
		xml.setLastName(lastName);
		return xml;
	}
	
	public static User build(String firstName, String lastName, String email)
	{
		User xml = new User();
		xml.setFirstName(firstName);
		xml.setLastName(lastName);
		xml.setEmail(email);
		return xml;
	}
}