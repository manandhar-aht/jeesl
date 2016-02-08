package net.sf.ahtutils.web.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.controller.factory.xml.acl.XmlViewFactory;
import net.sf.ahtutils.controller.factory.xml.acl.XmlViewsFactory;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.factory.xml.security.XmlActionFactory;
import net.sf.ahtutils.factory.xml.security.XmlActionsFactory;
import net.sf.ahtutils.factory.xml.security.XmlCategoryFactory;
import net.sf.ahtutils.factory.xml.security.XmlRoleFactory;
import net.sf.ahtutils.factory.xml.security.XmlRolesFactory;
import net.sf.ahtutils.factory.xml.security.XmlSecurityFactory;
import net.sf.ahtutils.factory.xml.security.XmlStaffFactory;
import net.sf.ahtutils.factory.xml.security.XmlUsecaseFactory;
import net.sf.ahtutils.factory.xml.security.XmlUsecasesFactory;
import net.sf.ahtutils.interfaces.facade.UtilsSecurityFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityAction;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityActionTemplate;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityCategory;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityRole;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityUsecase;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityView;
import net.sf.ahtutils.interfaces.model.system.security.UtilsStaff;
import net.sf.ahtutils.interfaces.model.system.security.UtilsUser;
import net.sf.ahtutils.interfaces.rest.security.UtilsSecurityRestExport;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.util.query.SecurityQuery;
import net.sf.ahtutils.web.rest.security.AbstractSecurityInit;
import net.sf.ahtutils.web.rest.security.SecurityInitViews;
import net.sf.ahtutils.xml.access.Access;
import net.sf.ahtutils.xml.access.View;
import net.sf.ahtutils.xml.access.Views;
import net.sf.ahtutils.xml.security.Role;
import net.sf.ahtutils.xml.security.Roles;
import net.sf.ahtutils.xml.security.Security;
import net.sf.ahtutils.xml.security.Staffs;
import net.sf.ahtutils.xml.sync.DataUpdate;

public class SecurityRestService <L extends UtilsLang,D extends UtilsDescription,
								C extends UtilsSecurityCategory<L,D,C,R,V,U,A,AT,USER>,
								R extends UtilsSecurityRole<L,D,C,R,V,U,A,AT,USER>,
								V extends UtilsSecurityView<L,D,C,R,V,U,A,AT,USER>,
								U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
								A extends UtilsSecurityAction<L,D,C,R,V,U,A,AT,USER>,
								AT extends UtilsSecurityActionTemplate<L,D,C,R,V,U,A,AT,USER>,
								USER extends UtilsUser<L,D,C,R,V,U,A,AT,USER>>
				implements UtilsSecurityRestExport
{
	final static Logger logger = LoggerFactory.getLogger(SecurityRestService.class);
	
	private UtilsSecurityFacade<L,D,C,R,V,U,A,AT,USER> fSecurity;
	
	private final Class<C> cCategory;
	private final Class<R> cRole;
	private final Class<V> cView;
	private final Class<U> cUsecase;
	private final Class<A> cAction;
	
	private SecurityInitViews<L,D,C,R,V,U,A,AT,USER> initViews;
	
	private XmlCategoryFactory<L,D,C,R,V,U,A,AT,USER> fCategory;
	private XmlViewFactory xfView;
	private XmlRoleFactory<L,D,C,R,V,U,A,AT,USER> fRole,fRoleDescription,fRoleCode;
	private XmlActionFactory<L,D,C,R,V,U,A,AT,USER> fAction;
	private XmlUsecaseFactory<L,D,C,R,V,U,A,AT,USER> fUsecase,fUsecaseDoc;
	
	private SecurityRestService(UtilsSecurityFacade<L,D,C,R,V,U,A,AT,USER> fSecurity,final Class<L> cL,final Class<D> cD,final Class<C> cCategory,final Class<V> cView,final Class<R> cRole,final Class<U> cUsecase,final Class<A> cAction,final Class<USER> cUser)
	{
		this.fSecurity=fSecurity;
		this.cCategory=cCategory;
		this.cRole=cRole;
		this.cView=cView;
		this.cUsecase=cUsecase;
		this.cAction=cAction;
		
		fCategory = new XmlCategoryFactory<L,D,C,R,V,U,A,AT,USER>(null,SecurityQuery.exCategory());
		xfView = new XmlViewFactory(SecurityQuery.exViewOld(),null);
		fRole = new XmlRoleFactory<L,D,C,R,V,U,A,AT,USER>(SecurityQuery.exRole(),null);
		fRoleDescription = new XmlRoleFactory<L,D,C,R,V,U,A,AT,USER>(SecurityQuery.role(),null);
		fRoleCode = new XmlRoleFactory<L,D,C,R,V,U,A,AT,USER>(XmlRoleFactory.build(""));
		fAction = new XmlActionFactory<L,D,C,R,V,U,A,AT,USER>(SecurityQuery.exActionAcl());
		fUsecase = new XmlUsecaseFactory<L,D,C,R,V,U,A,AT,USER>(SecurityQuery.exUsecase());
		fUsecaseDoc = new XmlUsecaseFactory<L,D,C,R,V,U,A,AT,USER>(SecurityQuery.docUsecase());
		
		initViews = AbstractSecurityInit.factoryViews(cL,cD,cCategory,cRole,cView,cUsecase,cAction,cUser,fSecurity);
	}
	
	public static <L extends UtilsLang,D extends UtilsDescription,C extends UtilsSecurityCategory<L,D,C,R,V,U,A,AT,USER>,R extends UtilsSecurityRole<L,D,C,R,V,U,A,AT,USER>,V extends UtilsSecurityView<L,D,C,R,V,U,A,AT,USER>,U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,A extends UtilsSecurityAction<L,D,C,R,V,U,A,AT,USER>,AT extends UtilsSecurityActionTemplate<L,D,C,R,V,U,A,AT,USER>,USER extends UtilsUser<L,D,C,R,V,U,A,AT,USER>>
	SecurityRestService<L,D,C,R,V,U,A,AT,USER>
		factory(UtilsSecurityFacade<L,D,C,R,V,U,A,AT,USER> fSecurity, final Class<L> cL,final Class<D> cD,final Class<C> cCategory, final Class<V> cView, final Class<R> cRole, final Class<U> cUsecase,final Class<A> cAction,final Class<USER> cUser)
	{
		return new SecurityRestService<L,D,C,R,V,U,A,AT,USER>(fSecurity,cL,cD,cCategory,cView,cRole,cUsecase,cAction,cUser);
	}
	
	public DataUpdate iuSecurityViews(Access views){return initViews.iuViews(views);}

	public <STAFF extends UtilsStaff<L,D,C,R,V,U,A,AT,USER,DOMAIN>,DOMAIN extends EjbWithId> Staffs exportStaffs(Class<STAFF> cStaff)
	{
		XmlStaffFactory<L,D,C,R,V,U,A,AT,USER,STAFF,DOMAIN> f = new XmlStaffFactory<L,D,C,R,V,U,A,AT,USER,STAFF,DOMAIN>(SecurityQuery.exStaff());
		
		Staffs staffs = new Staffs();
		
		for(STAFF ejb : fSecurity.all(cStaff))
		{
			staffs.getStaff().add(f.build(ejb));
		}
		
		return staffs;
	}

	@Override public Security exportSecurityViews()
	{
		Security xml = XmlSecurityFactory.build();		
		for(C category : fSecurity.allOrderedPosition(cCategory))
		{
			if(category.getType().equals(UtilsSecurityCategory.Type.view.toString()))
			{
				try
				{
					net.sf.ahtutils.xml.security.Category xmlCat = fCategory.build(category);
					xmlCat.setViews(XmlViewsFactory.build());
					for(V view : fSecurity.allForCategory(cView, cCategory, category.getCode()))
					{
						view = fSecurity.load(cView,view);
						View xView = xfView.build(view);
						xView.setActions(XmlActionsFactory.create());
						for(A action : view.getActions())
						{
							net.sf.ahtutils.xml.access.Action xAction = fAction.create(action);
							
							List<R> roles = fSecurity.rolesForAction(cAction, action);
							if(roles.size()>0)
							{
								Roles xRoles = XmlRolesFactory.build();
								for(R r : roles)
								{
									xRoles.getRole().add(fRoleCode.build(r));
								}
								xAction.setRoles(xRoles);
							}
							
							xView.getActions().getAction().add(xAction);
						}
						
						Roles xRoles = XmlRolesFactory.build();
						for(R role : fSecurity.rolesForView(cView, view))
						{
							xRoles.getRole().add(fRoleDescription.build(role));
						}
						xView.setRoles(xRoles);
						
						xmlCat.getViews().getView().add(xView);
					}
					
					xml.getCategory().add(xmlCat);
				}
				catch (UtilsNotFoundException e) {e.printStackTrace();}
			}
		}		
		return xml;
	}

	@Override public Security exportSecurityRoles()
	{
		Security xml = XmlSecurityFactory.build();
		for(C category : fSecurity.allOrderedPosition(cCategory))
		{
			if(category.getType().equals(UtilsSecurityCategory.Type.role.toString()))
			{
				try
				{
					net.sf.ahtutils.xml.security.Category xmlCat = fCategory.build(category);
					xmlCat.setRoles(XmlRolesFactory.build());
					for(R role : fSecurity.allForCategory(cRole, cCategory, category.getCode()))
					{
						role = fSecurity.load(cRole,role);
						Role xRole = fRole.build(role);
						
						xmlCat.getRoles().getRole().add(xRole);
					}
					xml.getCategory().add(xmlCat);
				}
				catch (UtilsNotFoundException e) {e.printStackTrace();}
			}
		}		
		return xml;
	}

	@Override
	public Security exportSecurityUsecases()
	{
		Security xml = XmlSecurityFactory.build();

		for(C category : fSecurity.allOrderedPosition(cCategory))
		{
			if(category.getType().equals(UtilsSecurityCategory.Type.usecase.toString()))
			{
				try
				{
					net.sf.ahtutils.xml.security.Category xmlCat = fCategory.build(category);
					xmlCat.setUsecases(XmlUsecasesFactory.build());
					for(U usecase : fSecurity.allForCategory(cUsecase, cCategory, category.getCode()))
					{
						xmlCat.getUsecases().getUsecase().add(fUsecase.build(usecase));
					}
					xml.getCategory().add(xmlCat);
				}
				catch (UtilsNotFoundException e) {e.printStackTrace();}
			}
		}		
		return xml;
	}

	@Override public Security documentationSecurityPageActions()
	{
		Security xml = XmlSecurityFactory.build();
		for(C category : fSecurity.allOrderedPosition(cCategory))
		{
			if(category.getType().equals(UtilsSecurityCategory.Type.view.toString()))
			{
				try
				{
					
//					net.sf.ahtutils.xml.security.Views views = net.sf.ahtutils.factory.xml.security.XmlViewsFactory.build();
					Views views = XmlViewsFactory.build();
					
					for(V view : fSecurity.allForCategory(cView, cCategory, category.getCode()))
					{
						view = fSecurity.load(cView,view);
						View xView = xfView.build(view);
						xView.setActions(XmlActionsFactory.create());
						for(A action : view.getActions())
						{
							xView.getActions().getAction().add(fAction.create(action));
						}
						
						Roles xRoles = XmlRolesFactory.build();
						for(R role : fSecurity.rolesForView(cView, view))
						{
							xRoles.getRole().add(fRoleDescription.build(role));
						}
						xView.setRoles(xRoles);
						
						views.getView().add(xView);
					}
					
					net.sf.ahtutils.xml.security.Category xCategory = fCategory.build(category);
					xCategory.setViews(views);
					xml.getCategory().add(xCategory);
				}
				catch (UtilsNotFoundException e) {e.printStackTrace();}
			}
		}		
		return xml;
	}
	
	@Override public Security documentationSecurityUsecases()
	{
		Security xml = XmlSecurityFactory.build();

		for(C category : fSecurity.allOrderedPosition(cCategory))
		{
			if(category.getType().equals(UtilsSecurityCategory.Type.usecase.toString()))
			{
				try
				{
					net.sf.ahtutils.xml.security.Category xmlCat = fCategory.build(category);
					xmlCat.setUsecases(XmlUsecasesFactory.build());
					for(U usecase : fSecurity.allForCategory(cUsecase, cCategory, category.getCode()))
					{
						xmlCat.getUsecases().getUsecase().add(fUsecaseDoc.build(usecase));
					}
					xml.getCategory().add(xmlCat);
				}
				catch (UtilsNotFoundException e) {e.printStackTrace();}
			}
		}		
		return xml;
	}
}