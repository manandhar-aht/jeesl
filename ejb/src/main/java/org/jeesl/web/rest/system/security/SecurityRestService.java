package org.jeesl.web.rest.system.security;

import org.jeesl.factory.xml.system.security.XmlActionFactory;
import org.jeesl.factory.xml.system.security.XmlActionsFactory;
import org.jeesl.factory.xml.system.security.XmlCategoryFactory;
import org.jeesl.factory.xml.system.security.XmlRoleFactory;
import org.jeesl.factory.xml.system.security.XmlRolesFactory;
import org.jeesl.factory.xml.system.security.XmlSecurityFactory;
import org.jeesl.factory.xml.system.security.XmlStaffFactory;
import org.jeesl.factory.xml.system.security.XmlTemplateFactory;
import org.jeesl.factory.xml.system.security.XmlTemplatesFactory;
import org.jeesl.factory.xml.system.security.XmlUsecaseFactory;
import org.jeesl.factory.xml.system.security.XmlUsecasesFactory;
import org.jeesl.interfaces.facade.JeeslSecurityFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.controller.factory.xml.acl.XmlViewFactory;
import net.sf.ahtutils.controller.factory.xml.acl.XmlViewsFactory;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
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
import net.sf.ahtutils.xml.access.Action;
import net.sf.ahtutils.xml.access.View;
import net.sf.ahtutils.xml.access.Views;
import net.sf.ahtutils.xml.security.Role;
import net.sf.ahtutils.xml.security.Roles;
import net.sf.ahtutils.xml.security.Security;
import net.sf.ahtutils.xml.security.Staffs;
import net.sf.ahtutils.xml.security.Template;
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
	
	private JeeslSecurityFacade<L,D,C,R,V,U,A,AT,USER> fSecurity;
	
	private final Class<C> cCategory;
	private final Class<R> cRole;
	private final Class<V> cView;
	private final Class<U> cUsecase;
//	private final Class<A> cAction;
	private final Class<AT> cTemplate;
	
	private SecurityInitViews<L,D,C,R,V,U,A,AT,USER> initViews;
	
	private XmlCategoryFactory<L,D,C,R,V,U,A,AT,USER> fCategory;
	private XmlViewFactory xfView;
	private XmlRoleFactory<L,D,C,R,V,U,A,AT,USER> fRole,fRoleDescription;
	private XmlActionFactory<L,D,C,R,V,U,A,AT,USER> xfAction,xfActionDoc;
	private XmlTemplateFactory<L,D,C,R,V,U,A,AT,USER> fTemplate;
	private XmlUsecaseFactory<L,D,C,R,V,U,A,AT,USER> fUsecase,fUsecaseDoc;
	
	private SecurityRestService(JeeslSecurityFacade<L,D,C,R,V,U,A,AT,USER> fSecurity,final Class<L> cL,final Class<D> cD,final Class<C> cCategory,final Class<V> cView,final Class<R> cRole,final Class<U> cUsecase,final Class<A> cAction,final Class<AT> cTemplate,final Class<USER> cUser)
	{
		this.fSecurity=fSecurity;
		this.cCategory=cCategory;
		this.cRole=cRole;
		this.cView=cView;
		this.cUsecase=cUsecase;
//		this.cAction=cAction;
		this.cTemplate=cTemplate;
		
		fCategory = new XmlCategoryFactory<L,D,C,R,V,U,A,AT,USER>(null,SecurityQuery.exCategory());
		xfView = new XmlViewFactory(SecurityQuery.exViewOld(),null);
		fRole = new XmlRoleFactory<L,D,C,R,V,U,A,AT,USER>(SecurityQuery.exRole(),null);
		fRoleDescription = new XmlRoleFactory<L,D,C,R,V,U,A,AT,USER>(SecurityQuery.role(),null);
		xfAction = new XmlActionFactory<L,D,C,R,V,U,A,AT,USER>(SecurityQuery.exActionAcl());
		xfActionDoc = new XmlActionFactory<L,D,C,R,V,U,A,AT,USER>(SecurityQuery.docActionAcl());
		fTemplate = new XmlTemplateFactory<L,D,C,R,V,U,A,AT,USER>(SecurityQuery.exTemplate());
		fUsecase = new XmlUsecaseFactory<L,D,C,R,V,U,A,AT,USER>(SecurityQuery.exUsecase());
		fUsecaseDoc = new XmlUsecaseFactory<L,D,C,R,V,U,A,AT,USER>(SecurityQuery.docUsecase());
		
		initViews = AbstractSecurityInit.factoryViews(cL,cD,cCategory,cRole,cView,cUsecase,cAction,cTemplate,cUser,fSecurity);
	}
	
	public static <L extends UtilsLang,D extends UtilsDescription,C extends UtilsSecurityCategory<L,D,C,R,V,U,A,AT,USER>,R extends UtilsSecurityRole<L,D,C,R,V,U,A,AT,USER>,V extends UtilsSecurityView<L,D,C,R,V,U,A,AT,USER>,U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,A extends UtilsSecurityAction<L,D,C,R,V,U,A,AT,USER>,AT extends UtilsSecurityActionTemplate<L,D,C,R,V,U,A,AT,USER>,USER extends UtilsUser<L,D,C,R,V,U,A,AT,USER>>
	SecurityRestService<L,D,C,R,V,U,A,AT,USER>
		factory(JeeslSecurityFacade<L,D,C,R,V,U,A,AT,USER> fSecurity, final Class<L> cL,final Class<D> cD,final Class<C> cCategory, final Class<V> cView, final Class<R> cRole, final Class<U> cUsecase,final Class<A> cAction,final Class<AT> cTemplate,final Class<USER> cUser)
	{
		return new SecurityRestService<L,D,C,R,V,U,A,AT,USER>(fSecurity,cL,cD,cCategory,cView,cRole,cUsecase,cAction,cTemplate,cUser);
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
							net.sf.ahtutils.xml.access.Action xAction = xfAction.create(action);
/*	2016-05-30 deactivated
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
*/							
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
	
	@Override public Security exportSecurityActions()
	{
		Security xml = XmlSecurityFactory.build();
		for(C category : fSecurity.allOrderedPosition(cCategory))
		{
			if(category.getType().equals(UtilsSecurityCategory.Type.action.toString()))
			{
				try
				{
					net.sf.ahtutils.xml.security.Category xmlCat = fCategory.build(category);
					xmlCat.setTemplates(XmlTemplatesFactory.build());
					for(AT template : fSecurity.allForCategory(cTemplate, cCategory, category.getCode()))
					{
						Template xTemplate = fTemplate.build(template);
						xmlCat.getTemplates().getTemplate().add(xTemplate);
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
						usecase = fSecurity.load(cUsecase, usecase);
						xmlCat.getUsecases().getUsecase().add(fUsecase.build(usecase));
					}
					xml.getCategory().add(xmlCat);
				}
				catch (UtilsNotFoundException e) {e.printStackTrace();}
			}
		}		
		return xml;
	}
	
	@Override public Security documentationSecurityViews()
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
							net.sf.ahtutils.xml.access.Action xAction = xfActionDoc.create(action);
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

	@Override public Security documentationSecurityPageActions()
	{
		Security xml = XmlSecurityFactory.build();
		for(C category : fSecurity.allOrderedPosition(cCategory))
		{
			if(category.getType().equals(UtilsSecurityCategory.Type.view.toString()))
			{
				try
				{
					Views views = XmlViewsFactory.build();
					
					for(V view : fSecurity.allForCategory(cView, cCategory, category.getCode()))
					{
						view = fSecurity.load(cView,view);
						View xView = xfView.build(view);
						xView.setActions(XmlActionsFactory.create());
						for(A action : view.getActions())
						{
							Action xAction = xfActionDoc.create(action);
							xView.getActions().getAction().add(xAction);
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