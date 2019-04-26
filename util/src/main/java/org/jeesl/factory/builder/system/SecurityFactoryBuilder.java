package org.jeesl.factory.builder.system;

import org.jeesl.factory.builder.AbstractFactoryBuilder;
import org.jeesl.factory.ejb.system.security.EjbSecurityActionFactory;
import org.jeesl.factory.ejb.system.security.EjbSecurityActionTemplateFactory;
import org.jeesl.factory.ejb.system.security.EjbSecurityAreaFactory;
import org.jeesl.factory.ejb.system.security.EjbSecurityCategoryFactory;
import org.jeesl.factory.ejb.system.security.EjbSecurityMenuFactory;
import org.jeesl.factory.ejb.system.security.EjbSecurityRoleFactory;
import org.jeesl.factory.ejb.system.security.EjbSecurityUsecaseFactory;
import org.jeesl.factory.ejb.system.security.EjbSecurityUserFactory;
import org.jeesl.factory.ejb.system.security.EjbSecurityViewFactory;
import org.jeesl.factory.ejb.system.security.EjbStaffFactory;
import org.jeesl.factory.json.system.security.JsonPageFactory;
import org.jeesl.factory.json.system.security.JsonPagesFactory;
import org.jeesl.factory.txt.system.security.TxtStaffFactory;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityAction;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityArea;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityCategory;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityMenu;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityTemplate;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityUsecase;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityView;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.jeesl.interfaces.model.system.security.util.JeeslStaff;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class SecurityFactoryBuilder<L extends UtilsLang, D extends UtilsDescription,
									C extends JeeslSecurityCategory<L,D>,
									R extends JeeslSecurityRole<L,D,C,V,U,A,USER>,
									V extends JeeslSecurityView<L,D,C,R,U,A>,
									U extends JeeslSecurityUsecase<L,D,C,R,V,A>,
									A extends JeeslSecurityAction<L,D,R,V,U,AT>,
									AT extends JeeslSecurityTemplate<L,D,C>,
									M extends JeeslSecurityMenu<V,M>,
									AR extends JeeslSecurityArea<L,D,V>,
									USER extends JeeslUser<R>
//,I extends JeeslIdentity<R,V,U,A,USER>
>
				extends AbstractFactoryBuilder<L,D>
{
	final static Logger logger = LoggerFactory.getLogger(SecurityFactoryBuilder.class);
        
	private final Class<C> cCategory; public Class<C> getClassCategory(){return cCategory;}
	private final Class<R> cRole; public Class<R> getClassRole(){return cRole;}
	private final Class<V> cView; public Class<V> getClassView(){return cView;}
	private final Class<U> cUsecase; public Class<U> getClassUsecase(){return cUsecase;}
    private final Class<A> cAction; public Class<A> getClassAction(){return cAction;}
    private final Class<AT> cTemplate; public Class<AT> getClassTemplate(){return cTemplate;}
    private final Class<M> cMenu; public Class<M> getClassMenu(){return cMenu;}
    private final Class<AR> cArea; public Class<AR> getClassArea(){return cArea;}
    private final Class<USER> cUser; public Class<USER> getClassUser(){return cUser;}
	
	public SecurityFactoryBuilder(final Class<L> cL, final Class<D> cD,
									final Class<C> cCategory,
									final Class<R> cRole,
									final Class<V> cView,
									final Class<U> cUsecase,
									final Class<A> cAction,
									final Class<AT> cTemplate,
									final Class<M> cMenu,
									final Class<AR> cArea,
									final Class<USER> cUser)
	{		
		super(cL,cD);
		this.cCategory=cCategory;
		this.cRole=cRole;
		this.cView=cView;
		this.cUsecase=cUsecase;
		this.cAction=cAction;
		this.cTemplate=cTemplate;
		this.cMenu=cMenu;
		this.cArea=cArea;
		this.cUser=cUser;
	}
	
	public EjbSecurityCategoryFactory<C> ejbCategory()
	{
		return new EjbSecurityCategoryFactory<C>(cCategory);
	}
	
	public EjbSecurityRoleFactory<C,R> ejbRole()
	{
		return new EjbSecurityRoleFactory<C,R>(cRole);
	}
	
	public EjbSecurityViewFactory<C,V> ejbView()
	{
		return new EjbSecurityViewFactory<C,V>(cView);
	}
	
	public EjbSecurityUsecaseFactory<C,U> ejbUsecase() {return new EjbSecurityUsecaseFactory<C,U>(cUsecase);}
	public EjbSecurityActionFactory<V,A> ejbAction() {return new EjbSecurityActionFactory<V,A>(cAction);}
	
	public EjbSecurityActionTemplateFactory<C,AT> ejbTemplate()
	{
		return new EjbSecurityActionTemplateFactory<C,AT>(cTemplate);
	}
	
	public EjbSecurityMenuFactory<V,M> ejbMenu(Class<M> cM){return new EjbSecurityMenuFactory<V,M>(cM);}
	public EjbSecurityAreaFactory<V,AR> ejbArea() {return new EjbSecurityAreaFactory<V,AR>(cArea);}
	
	public EjbSecurityUserFactory<USER> ejbUser()
	{
		return new EjbSecurityUserFactory<USER>(cUser);
	}
	
	public <STAFF extends JeeslStaff<R,USER,D1,D2>, D1 extends EjbWithId, D2 extends EjbWithId>
				EjbStaffFactory<R,USER,STAFF,D1,D2> ejbStaff(final Class<STAFF> cStaff)
	{
		return new EjbStaffFactory<R,USER,STAFF,D1,D2>(cStaff);
	}
	
	public <STAFF extends JeeslStaff<R,USER,D1,D2>, D1 extends EjbWithId, D2 extends EjbWithId>
		TxtStaffFactory<L,D,R,USER,STAFF,D1,D2> txtStaff(String localeCode)
	{
		return new TxtStaffFactory<L,D,R,USER,STAFF,D1,D2>(localeCode);
	}
	
	public JsonPageFactory<L,D,C,V,M> jsonPage()
	{
		return new JsonPageFactory<L,D,C,V,M>();
	}
	
	public JsonPagesFactory<L,D,C,R,V,U,A,AT,M,AR,USER> jsonPages()
	{
		return new JsonPagesFactory<L,D,C,R,V,U,A,AT,M,AR,USER>(this);
	}
}