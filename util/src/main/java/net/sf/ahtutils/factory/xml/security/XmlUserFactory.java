package net.sf.ahtutils.factory.xml.security;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityAction;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityCategory;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityRole;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityUsecase;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityView;
import net.sf.ahtutils.interfaces.model.system.security.UtilsUser;
import net.sf.ahtutils.xml.security.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlUserFactory<L extends UtilsLang,
							D extends UtilsDescription, 
							C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,
							R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,
							V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,
							U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,
							A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,
							USER extends UtilsUser<L,D,C,R,V,U,A,USER>>
{
	final static Logger logger = LoggerFactory.getLogger(XmlUserFactory.class);
		
	
	private User q;
	
	public XmlUserFactory(User q)
	{
		this.q=q;
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
	
	public User build(USER user)
	{
		User xml = new User();
		if(q.isSetId()){xml.setId(user.getId());}
		if(q.isSetFirstName()){xml.setFirstName(user.getFirstName());}
		if(q.isSetLastName()){xml.setLastName(user.getLastName());}
		
		return xml;
	}
}