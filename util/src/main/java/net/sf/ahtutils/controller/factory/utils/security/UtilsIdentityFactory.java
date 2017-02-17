package net.sf.ahtutils.controller.factory.utils.security;

import org.jeesl.api.facade.module.JeeslSecurityFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.system.security.UtilsIdentity;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityAction;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityActionTemplate;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityCategory;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityRole;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityUsecase;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityView;
import net.sf.ahtutils.interfaces.model.system.security.UtilsUser;

public class UtilsIdentityFactory <I extends UtilsIdentity<L,D,C,R,V,U,A,AT,USER>,
								   L extends UtilsLang,
								   D extends UtilsDescription,
								   C extends UtilsSecurityCategory<L,D,C,R,V,U,A,AT,USER>,
								   R extends UtilsSecurityRole<L,D,C,R,V,U,A,AT,USER>,
								   V extends UtilsSecurityView<L,D,C,R,V,U,A,AT,USER>,
								   U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
								   A extends UtilsSecurityAction<L,D,C,R,V,U,A,AT,USER>,
								   AT extends UtilsSecurityActionTemplate<L,D,C,R,V,U,A,AT,USER>,
								   USER extends UtilsUser<L,D,C,R,V,U,A,AT,USER>>
{

	final static Logger logger = LoggerFactory.getLogger(UtilsIdentityFactory.class);

	final Class<I>  clIdentity;
	final Class<L>  clLang;
	final Class<D>  clDescription;
	final Class<C>  clCategory;
	final Class<R>  clRole;
	final Class<V>  clView;
	final Class<U> 	clUsecase;
	final Class<A> 	clAction;
	final Class<USER>  cUser;

	public UtilsIdentityFactory(final Class<I> clIdentity,
								final Class<L> clLang,
								final Class<D> clDescription,
								final Class<C> clCategory,
								final Class<R> clRole,
								final Class<V> clView,
								final Class<U> clUsecase,
								final Class<A> clAction,
								final Class<USER> cUser)
	{
		this.clIdentity=clIdentity;
		this.clLang=clLang;
		this.clDescription=clDescription;
		this.clCategory=clCategory;
		this.clRole=clRole;
		this.clView=clView;
		this.clUsecase=clUsecase;
		this.clAction=clAction;
		this.cUser = cUser;
	} 

	public static <I extends UtilsIdentity<L,D,C,R,V,U,A,AT,USER>,
	   			   L extends UtilsLang,
	   			   D extends UtilsDescription,
	   			   C extends UtilsSecurityCategory<L,D,C,R,V,U,A,AT,USER>,
	   			   R extends UtilsSecurityRole<L,D,C,R,V,U,A,AT,USER>,
	   			   V extends UtilsSecurityView<L,D,C,R,V,U,A,AT,USER>,
	   			   U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
	   			   A extends UtilsSecurityAction<L,D,C,R,V,U,A,AT,USER>,
	   			AT extends UtilsSecurityActionTemplate<L,D,C,R,V,U,A,AT,USER>,
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
			identity = clIdentity.newInstance();
			identity.setUser(user);
			
			for(A a : fSecurity.allActionsForUser(cUser, user)){identity.allowAction(a);}		
			for(R r : fSecurity.allRolesForUser(cUser,user)){identity.allowRole(r);}
			for(V v : fSecurity.allViewsForUser(cUser,user)){identity.allowView(v);}
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return identity;
	}
}