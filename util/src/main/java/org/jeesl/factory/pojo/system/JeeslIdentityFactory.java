package org.jeesl.factory.pojo.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jeesl.api.bean.JeeslSecurityBean;
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

	public I create(JeeslSecurityFacade<?,?,?,R,V,U,A,?,?,USER> fSecurity, USER user) {return create(fSecurity,null,user,null);}
	public I create(JeeslSecurityFacade<?,?,?,R,V,U,A,?,?,USER> fSecurity, JeeslSecurityBean<?,?,?,R,V,U,A,?,?,USER> bSecurity, USER user, ProcessingTimeTracker ptt)
	{		
		I identity = null;
		
		try
		{
			identity = cIdentity.newInstance();
			identity.setUser(user);
			
			List<R> roles = fSecurity.allRolesForUser(user);
			for(R r : roles){identity.allowRole(r);}
			if(ptt!=null) {ptt.tick(JeeslIdentityFactory.class.getSimpleName()+".allRolesForUser()");}
			
			if(bSecurity==null)
			{
				processViews(fSecurity,user,identity,ptt);
				processActions(fSecurity,user,identity,ptt);
			}
			else
			{
				processViews(bSecurity,roles,identity,ptt);
				processActions(bSecurity,roles,identity,ptt);
			}
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return identity;
	}
	
	private void processViews(JeeslSecurityFacade<?,?,?,R,V,U,A,?,?,USER> fSecurity, USER user, I identity, ProcessingTimeTracker ptt)
	{
		for(V v : fSecurity.allViewsForUser(user)){identity.allowView(v);}
		if(ptt!=null) {ptt.tick(JeeslIdentityFactory.class.getSimpleName()+".allViewsForUser("+JeeslSecurityFacade.class.getSimpleName()+")");}
	}
	private void processViews(JeeslSecurityBean<?,?,?,R,V,U,A,?,?,USER> bSecurity, List<R> roles, I identity, ProcessingTimeTracker ptt)
	{	
		Map<Long,V> map = new HashMap<Long,V>();
		for(R role : roles)
		{
			for(V rView : bSecurity.fViews(role))
			{
				map.put(rView.getId(), rView);
			}
			for(U u : bSecurity.fUsecases(role))
			{
				for(V uView : bSecurity.fViews(u))
				{
					map.put(uView.getId(), uView);
				}
			}
		}
		List<V> views = new ArrayList<V>(map.values());
		
		for(V v : views){identity.allowView(v);}
		if(ptt!=null) {ptt.tick(JeeslIdentityFactory.class.getSimpleName()+".allViewsForUser("+JeeslSecurityBean.class.getSimpleName()+")");}
	}
	
	private void processActions(JeeslSecurityFacade<?,?,?,R,V,U,A,?,?,USER> fSecurity, USER user, I identity, ProcessingTimeTracker ptt)
	{
		for(A a : fSecurity.allActionsForUser(user)){identity.allowAction(a);}
		if(ptt!=null) {ptt.tick(JeeslIdentityFactory.class.getSimpleName()+".allActionsForUser()");}
	}
	private void processActions(JeeslSecurityBean<?,?,?,R,V,U,A,?,?,USER> bSecurity, List<R> roles, I identity, ProcessingTimeTracker ptt)
	{
		Map<Long,A> actions = new HashMap<Long,A>();
		for(R r : roles)
		{
			for(A rAction : bSecurity.fActions(r))
			{
				actions.put(rAction.getId(), rAction);
			}
			for(U u : bSecurity.fUsecases(r))
			{
				for(A uAction : bSecurity.fActions(u))
				{
					actions.put(uAction.getId(), uAction);
				}
			}
		}
		for(A a : actions.values()){identity.allowAction(a);}
	}
}