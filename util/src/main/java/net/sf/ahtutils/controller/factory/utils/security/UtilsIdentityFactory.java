package net.sf.ahtutils.controller.factory.utils.security;

import net.sf.ahtutils.interfaces.facade.UtilsSecurityFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.system.security.UtilsIdentity;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityAction;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityCategory;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityRole;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityUsecase;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityView;
import net.sf.ahtutils.interfaces.model.system.security.UtilsUser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UtilsIdentityFactory <I extends UtilsIdentity<L,D,C,R,V,U,A,USER>,
								   L extends UtilsLang,
								   D extends UtilsDescription,
								   C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,
								   R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,
								   V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,
								   U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,
								   A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,
								   USER extends UtilsUser<L,D,C,R,V,U,A,USER>>
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
	final Class<USER>  clUser;

	public UtilsIdentityFactory(final Class<I> clIdentity,
								final Class<L> clLang,
								final Class<D> clDescription,
								final Class<C> clCategory,
								final Class<R> clRole,
								final Class<V> clView,
								final Class<U> clUsecase,
								final Class<A> clAction,
								final Class<USER> clUser)
	{
		this.clIdentity=clIdentity;
		this.clLang=clLang;
		this.clDescription=clDescription;
		this.clCategory=clCategory;
		this.clRole=clRole;
		this.clView=clView;
		this.clUsecase=clUsecase;
		this.clAction=clAction;
		this.clUser = clUser;
	} 

	public static <I extends UtilsIdentity<L,D,C,R,V,U,A,USER>,
	   			   L extends UtilsLang,
	   			   D extends UtilsDescription,
	   			   C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,
	   			   R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,
	   			   V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,
	   			   U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,
	   			   A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,
	   			USER extends UtilsUser<L,D,C,R,V,U,A,USER>>
	UtilsIdentityFactory<I,L,D,C,R,V,U,A,USER> factory(final Class<I> clIdentity,
													 final Class<L> clLang,
													 final Class<D> clDescription,
													 final Class<C> clCategory,
													 final Class<R> clRole,
													 final Class<V> clView,
													 final Class<U> clUsecase,
													 final Class<A> clAction,
													 final Class<USER> clUser)
	{
		return new UtilsIdentityFactory<I,L,D,C,R,V,U,A,USER>(clIdentity,clLang,clDescription,clCategory,clRole,clView,clUsecase,clAction,clUser);
	}

	public I create(UtilsSecurityFacade<L,D,C,R,V,U,A,USER> fSecurity, USER user)
	{		
		I identity = null;
		
		try
		{
			identity = clIdentity.newInstance();
			identity.setUser(user);
			
			for(A a : fSecurity.allActionsForUser(clUser, user)){identity.allowAction(a);}		
			for(R r : fSecurity.allRolesForUser(clUser,user)){identity.allowRole(r);}
			
			if(logger.isTraceEnabled()){logger.info("Views");}
			for(V v : fSecurity.allViewsForUser(clUser,user))
			{
				if(logger.isTraceEnabled()){logger.info("\t"+v.toString());}
				identity.allowView(v);
			}
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return identity;
	}
}