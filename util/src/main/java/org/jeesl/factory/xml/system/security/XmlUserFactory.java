package org.jeesl.factory.xml.system.security;

import org.jeesl.model.xml.jeesl.QuerySecurity;
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
import net.sf.ahtutils.xml.security.User;

public class XmlUserFactory<L extends UtilsLang, D extends UtilsDescription, 
							C extends UtilsSecurityCategory<L,D,C,R,V,U,A,AT,USER>,
							R extends UtilsSecurityRole<L,D,C,R,V,U,A,AT,USER>,
							V extends UtilsSecurityView<L,D,C,R,V,U,A,AT,USER>,
							U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
							A extends UtilsSecurityAction<L,D,C,R,V,U,A,AT,USER>,
							AT extends UtilsSecurityActionTemplate<L,D,C,R,V,U,A,AT,USER>,
							USER extends UtilsUser<L,D,C,R,V,U,A,AT,USER>>
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