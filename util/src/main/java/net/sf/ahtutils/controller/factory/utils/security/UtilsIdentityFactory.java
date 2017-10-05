package net.sf.ahtutils.controller.factory.utils.security;

import org.jeesl.api.facade.system.JeeslSecurityFacade;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityView;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityTemplate;
import org.jeesl.interfaces.model.system.security.user.JeeslIdentity;
import org.jeesl.interfaces.model.system.security.user.UtilsUser;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityAction;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityUsecase;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityCategory;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public class UtilsIdentityFactory <I extends JeeslIdentity<L,D,C,R,V,U,A,AT,USER>,
								   L extends UtilsLang,  D extends UtilsDescription,
								   C extends JeeslSecurityCategory<L,D,C,R,V,U,A,AT,USER>,
								   R extends JeeslSecurityRole<L,D,C,R,V,U,A,AT,USER>,
								   V extends JeeslSecurityView<L,D,C,R,V,U,A,AT,USER>,
								   U extends JeeslSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
								   A extends JeeslSecurityAction<L,D,C,R,V,U,A,AT,USER>,
								   AT extends JeeslSecurityTemplate<L,D,C,R,V,U,A,AT,USER>,
								   USER extends UtilsUser<L,D,C,R,V,U,A,AT,USER>>
{

	final static Logger logger = LoggerFactory.getLogger(UtilsIdentityFactory.class);

	final Class<I>  cIdentity;
	final Class<L>  cL;
	final Class<D>  cD;
	final Class<C>  clCategory;
	final Class<R>  clRole;
	final Class<V>  clView;
	final Class<U> 	clUsecase;
	final Class<A> 	clAction;
	final Class<USER>  cUser;

	public UtilsIdentityFactory(final Class<I> cIdentity,
								final Class<L> cL, final Class<D> cD,
								final Class<C> clCategory,
								final Class<R> clRole,
								final Class<V> clView,
								final Class<U> clUsecase,
								final Class<A> clAction,
								final Class<USER> cUser)
	{
		this.cIdentity=cIdentity;
		this.cL=cL;
		this.cD=cD;
		this.clCategory=clCategory;
		this.clRole=clRole;
		this.clView=clView;
		this.clUsecase=clUsecase;
		this.clAction=clAction;
		this.cUser = cUser;
	} 

	public static <I extends JeeslIdentity<L,D,C,R,V,U,A,AT,USER>,
	   			   L extends UtilsLang, D extends UtilsDescription,
	   			   C extends JeeslSecurityCategory<L,D,C,R,V,U,A,AT,USER>,
	   			   R extends JeeslSecurityRole<L,D,C,R,V,U,A,AT,USER>,
	   			   V extends JeeslSecurityView<L,D,C,R,V,U,A,AT,USER>,
	   			   U extends JeeslSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
	   			   A extends JeeslSecurityAction<L,D,C,R,V,U,A,AT,USER>,
	   			AT extends JeeslSecurityTemplate<L,D,C,R,V,U,A,AT,USER>,
	   			USER extends UtilsUser<L,D,C,R,V,U,A,AT,USER>>
	UtilsIdentityFactory<I,L,D,C,R,V,U,A,AT,USER> factory(final Class<I> clIdentity,
													 final Class<L> clLang,
													 final Class<D> clDescription,
													 final Class<C> clCategory,
													 final Class<R> clRole,
													 final Class<V> clView,
													 final Class<U> clUsecase,
													 final Class<A> clAction,
													 final Class<USER> clUser)
	{
		return new UtilsIdentityFactory<I,L,D,C,R,V,U,A,AT,USER>(clIdentity,clLang,clDescription,clCategory,clRole,clView,clUsecase,clAction,clUser);
	}

	public I create(JeeslSecurityFacade<L,D,C,R,V,U,A,AT,USER> fSecurity, USER user)
	{		
		I identity = null;
		
		try
		{
			identity = cIdentity.newInstance();
			identity.setUser(user);
			
			for(A a : fSecurity.allActionsForUser(cUser,user)){identity.allowAction(a);}
			
			logger.info("Roles");
			for(R r : fSecurity.allRolesForUser(cUser,user))
			{
				identity.allowRole(r);
			}
			for(V v : fSecurity.allViewsForUser(cUser,user)){identity.allowView(v);}
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