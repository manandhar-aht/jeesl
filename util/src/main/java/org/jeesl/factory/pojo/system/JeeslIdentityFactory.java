package org.jeesl.factory.pojo.system;

import org.jeesl.api.facade.system.JeeslSecurityFacade;
import org.jeesl.controller.monitor.ProcessingTimeTracker;
import org.jeesl.factory.builder.system.SecurityFactoryBuilder;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityAction;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityUsecase;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityView;
import org.jeesl.interfaces.model.system.security.user.JeeslIdentity;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JeeslIdentityFactory <I extends JeeslIdentity<R,V,U,A,USER>,
								   R extends JeeslSecurityRole<?,?,?,V,U,A,USER>,
								   V extends JeeslSecurityView<?,?,?,R,U,A>,
								   U extends JeeslSecurityUsecase<?,?,?,R,V,A>,
								   A extends JeeslSecurityAction<?,?,R,V,U,?>,
								   USER extends JeeslUser<R>>
{

	final static Logger logger = LoggerFactory.getLogger(JeeslIdentityFactory.class);
	
	private final SecurityFactoryBuilder<?,?,?,R,V,U,A,?,?,USER> fbSecurity;
	final Class<I>  cIdentity;

	public JeeslIdentityFactory(SecurityFactoryBuilder<?,?,?,R,V,U,A,?,?,USER> fbSecurity,final Class<I> cIdentity)
	{
		this.fbSecurity=fbSecurity;
		this.cIdentity=cIdentity;
	} 

	public static <I extends JeeslIdentity<R,V,U,A,USER>,
	   			   R extends JeeslSecurityRole<?,?,?,V,U,A,USER>,
	   			   V extends JeeslSecurityView<?,?,?,R,U,A>,
	   			   U extends JeeslSecurityUsecase<?,?,?,R,V,A>,
	   			   A extends JeeslSecurityAction<?,?,R,V,U,?>,
	   			USER extends JeeslUser<R>>
	JeeslIdentityFactory<I,R,V,U,A,USER> factory(SecurityFactoryBuilder<?,?,?,R,V,U,A,?,?,USER> fbSecurity,final Class<I> cIdentity)
	{
		return new JeeslIdentityFactory<I,R,V,U,A,USER>(fbSecurity,cIdentity);
	}

	public I create(JeeslSecurityFacade<?,?,?,R,V,U,A,?,?,USER> fSecurity, USER user) {return create(fSecurity,user,null);}
	public I create(JeeslSecurityFacade<?,?,?,R,V,U,A,?,?,USER> fSecurity, USER user, ProcessingTimeTracker ptt)
	{		
		I identity = null;
		
		try
		{
			identity = cIdentity.newInstance();
			identity.setUser(user);
			
			for(A a : fSecurity.allActionsForUser(user)){identity.allowAction(a);}
			if(ptt!=null) {ptt.tick(JeeslIdentityFactory.class.getSimpleName()+".allActionsForUser()");}
			
			for(R r : fSecurity.allRolesForUser(user)){identity.allowRole(r);}
			if(ptt!=null) {ptt.tick(JeeslIdentityFactory.class.getSimpleName()+".allRolesForUser()");}
			
			for(V v : fSecurity.allViewsForUser(user)){identity.allowView(v);}
			if(ptt!=null) {ptt.tick(JeeslIdentityFactory.class.getSimpleName()+".allViewsForUser()");}
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return identity;
	}
	
	public I build()
	{		
		I identity = null;
		
		try
		{
			identity = cIdentity.newInstance();
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return identity;
	}
}