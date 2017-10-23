package org.jeesl.web.rest.system;

import java.util.Collections;
import java.util.Comparator;

import org.jeesl.api.facade.system.JeeslSecurityFacade;
import org.jeesl.api.rest.system.security.JeeslSecurityRestExport;
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
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityView;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityTemplate;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.jeesl.interfaces.model.system.security.util.JeeslStaff;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityAction;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityUsecase;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityCategory;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.jeesl.util.comparator.ejb.system.security.SecurityActionComparator;
import org.jeesl.util.comparator.ejb.system.security.SecurityRoleComparator;
import org.jeesl.util.comparator.ejb.system.security.SecurityUsecaseComparator;
import org.jeesl.util.comparator.ejb.system.security.SecurityViewComparator;
import org.jeesl.util.query.xml.SecurityQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.controller.factory.xml.acl.XmlViewsFactory;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.rest.security.UtilsSecurityViewImport;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.web.rest.security.AbstractSecurityInit;
import net.sf.ahtutils.web.rest.security.SecurityInitRoles;
import net.sf.ahtutils.web.rest.security.SecurityInitTemplates;
import net.sf.ahtutils.web.rest.security.SecurityInitUsecases;
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
import net.sf.ahtutils.xml.security.Tmp;
import net.sf.ahtutils.xml.sync.DataUpdate;

public class SecurityRestService <L extends UtilsLang,D extends UtilsDescription,
								C extends JeeslSecurityCategory<L,D>,
								R extends JeeslSecurityRole<L,D,C,R,V,U,A,AT,USER>,
								V extends JeeslSecurityView<L,D,C,R,V,U,A,AT,USER>,
								U extends JeeslSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
								A extends JeeslSecurityAction<L,D,C,R,V,U,A,AT,USER>,
								AT extends JeeslSecurityTemplate<L,D,C,R,V,U,A,AT,USER>,
								USER extends JeeslUser<L,D,C,R,V,U,A,AT,USER>>
				implements JeeslSecurityRestExport,UtilsSecurityViewImport
{
	final static Logger logger = LoggerFactory.getLogger(SecurityRestService.class);
	
	private JeeslSecurityFacade<L,D,C,R,V,U,A,AT,USER> fSecurity;
	
	private final Class<C> cCategory;
	private final Class<R> cRole;
	private final Class<V> cView;
	private final Class<U> cUsecase;
//	private final Class<A> cAction;
	private final Class<AT> cTemplate;
	
	private XmlCategoryFactory<L,D,C,R,V,U,A,AT,USER> fCategory;
	private org.jeesl.factory.xml.system.security.XmlViewFactory<L,D,C,R,V,U,A,AT,USER> xfView,xfViewOld;
	private XmlRoleFactory<L,D,C,R,V,U,A,AT,USER> xfRole,fRoleDescription;
	private XmlActionFactory<L,D,C,R,V,U,A,AT,USER> xfAction,xfActionOld,xfActionDoc;
	private XmlTemplateFactory<L,D,C,R,V,U,A,AT,USER> fTemplate;
	private XmlUsecaseFactory<L,D,C,R,V,U,A,AT,USER> fUsecase,fUsecaseDoc;
	
	protected Comparator<R> comparatorRole;
	private Comparator<V> comparatorView;
	private Comparator<U> comparatorUsecase;
	private Comparator<A> comparatorAction;
	
	private SecurityInitViews<L,D,C,R,V,U,A,AT,USER> initViews;
	private SecurityInitTemplates<L,D,C,R,V,U,A,AT,USER> initTemplates;
	private SecurityInitRoles<L,D,C,R,V,U,A,AT,USER> initRoles;
	private SecurityInitUsecases<L,D,C,R,V,U,A,AT,USER> initUsecases;
	
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
		xfView = new org.jeesl.factory.xml.system.security.XmlViewFactory<L,D,C,R,V,U,A,AT,USER>(SecurityQuery.exView());
		xfViewOld = new org.jeesl.factory.xml.system.security.XmlViewFactory(SecurityQuery.exViewOld());
		xfRole = new XmlRoleFactory<L,D,C,R,V,U,A,AT,USER>(SecurityQuery.exRole(),null);
		fRoleDescription = new XmlRoleFactory<L,D,C,R,V,U,A,AT,USER>(SecurityQuery.role(),null);
		xfAction = new XmlActionFactory<L,D,C,R,V,U,A,AT,USER>(SecurityQuery.exAction());
		xfActionOld = new XmlActionFactory<L,D,C,R,V,U,A,AT,USER>(SecurityQuery.exActionAcl());
		xfActionDoc = new XmlActionFactory<L,D,C,R,V,U,A,AT,USER>(SecurityQuery.docActionAcl());
		fTemplate = new XmlTemplateFactory<L,D,C,R,V,U,A,AT,USER>(SecurityQuery.exTemplate());
		fUsecase = new XmlUsecaseFactory<L,D,C,R,V,U,A,AT,USER>(SecurityQuery.exUsecase());
		fUsecaseDoc = new XmlUsecaseFactory<L,D,C,R,V,U,A,AT,USER>(SecurityQuery.docUsecase());
		
		comparatorRole = (new SecurityRoleComparator<L,D,C,R,V,U,A,AT,USER>()).factory(SecurityRoleComparator.Type.position);
		comparatorView = (new SecurityViewComparator<L,D,C,R,V,U,A,AT,USER>()).factory(SecurityViewComparator.Type.position);
		comparatorUsecase = (new SecurityUsecaseComparator<L,D,C,R,V,U,A,AT,USER>()).factory(SecurityUsecaseComparator.Type.position);
		comparatorAction = (new SecurityActionComparator<L,D,C,R,V,U,A,AT,USER>()).factory(SecurityActionComparator.Type.position);
		
		initViews = AbstractSecurityInit.factoryViews(cL,cD,cCategory,cRole,cView,cUsecase,cAction,cTemplate,cUser,fSecurity);
		initTemplates = AbstractSecurityInit.factoryTemplates(cL,cD,cCategory,cRole,cView,cUsecase,cAction,cTemplate,cUser,fSecurity);
		initRoles = AbstractSecurityInit.factoryRoles(cL,cD,cCategory,cRole,cView,cUsecase,cAction,cTemplate,cUser,fSecurity);
		initUsecases = AbstractSecurityInit.factoryUsecases(cL,cD,cCategory,cRole,cView,cUsecase,cAction,cTemplate,cUser,fSecurity);
	}
	
	public static <L extends UtilsLang,D extends UtilsDescription,C extends JeeslSecurityCategory<L,D>,R extends JeeslSecurityRole<L,D,C,R,V,U,A,AT,USER>,V extends JeeslSecurityView<L,D,C,R,V,U,A,AT,USER>,U extends JeeslSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,A extends JeeslSecurityAction<L,D,C,R,V,U,A,AT,USER>,AT extends JeeslSecurityTemplate<L,D,C,R,V,U,A,AT,USER>,USER extends JeeslUser<L,D,C,R,V,U,A,AT,USER>>
		SecurityRestService<L,D,C,R,V,U,A,AT,USER>
		factory(JeeslSecurityFacade<L,D,C,R,V,U,A,AT,USER> fSecurity, final Class<L> cL,final Class<D> cD,final Class<C> cCategory, final Class<V> cView, final Class<R> cRole, final Class<U> cUsecase,final Class<A> cAction,final Class<AT> cTemplate,final Class<USER> cUser)
	{
		return new SecurityRestService<L,D,C,R,V,U,A,AT,USER>(fSecurity,cL,cD,cCategory,cView,cRole,cUsecase,cAction,cTemplate,cUser);
	}
	
	public DataUpdate iuSecurityTemplates(Security templates){return initTemplates.iuSecurityTemplates(templates);}
	public DataUpdate iuSecurityViews(Access views){return initViews.iuViews(views);}
	public DataUpdate iuSecurityRoles(Security roles){return initRoles.iuSecurityRoles(roles);}
	public DataUpdate iuSecurityUsecases(Security usecases){return initUsecases.iuSecurityUsecases(usecases);}

	public <STAFF extends JeeslStaff<L,D,C,R,V,U,A,AT,USER,D1,D2>, D1 extends EjbWithId, D2 extends EjbWithId> Staffs exportStaffs(Class<STAFF> cStaff)
	{
		XmlStaffFactory<L,D,C,R,V,U,A,AT,USER,STAFF,D1,D2> f = new XmlStaffFactory<L,D,C,R,V,U,A,AT,USER,STAFF,D1,D2>(SecurityQuery.exStaff());
		
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
			if(category.getType().equals(JeeslSecurityCategory.Type.view.toString()))
			{
				try
				{
					net.sf.ahtutils.xml.security.Category xCategory = fCategory.build(category);
					xCategory.setViews(XmlViewsFactory.build());
					xCategory.setTmp(new Tmp());
					for(V eView : fSecurity.allForCategory(cView, cCategory, category.getCode()))
					{
						eView = fSecurity.load(cView,eView);
						net.sf.ahtutils.xml.security.View xView = xfView.build(eView);
						xView.setActions(XmlActionsFactory.build());
						for(A action : eView.getActions())
						{
							net.sf.ahtutils.xml.security.Action xAction = xfAction.build(action);							
							xView.getActions().getAction().add(xAction);
						}					
						xCategory.getTmp().getView().add(xView);
					}
					
					xml.getCategory().add(xCategory);
				}
				catch (UtilsNotFoundException e) {e.printStackTrace();}
			}
		}		
		return xml;
	}
	
	@Override public Security exportSecurityViewsOld()
	{
		Security xml = XmlSecurityFactory.build();		
		for(C category : fSecurity.allOrderedPosition(cCategory))
		{
			if(category.getType().equals(JeeslSecurityCategory.Type.view.toString()))
			{
				try
				{
					net.sf.ahtutils.xml.security.Category xCategory = fCategory.build(category);
					xCategory.setViews(XmlViewsFactory.build());
					for(V eView : fSecurity.allForCategory(cView, cCategory, category.getCode()))
					{
						eView = fSecurity.load(cView,eView);
						View xView = xfViewOld.create(eView);
						xView.setActions(XmlActionsFactory.create());
						for(A action : eView.getActions())
						{
							net.sf.ahtutils.xml.access.Action xAction = xfActionOld.create(action);							
							xView.getActions().getAction().add(xAction);
						}						
						xCategory.getViews().getView().add(xView);
					}
					
					xml.getCategory().add(xCategory);
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
			if(category.getType().equals(JeeslSecurityCategory.Type.role.toString()))
			{
				try
				{
					net.sf.ahtutils.xml.security.Category xCat = fCategory.build(category);
					xCat.setRoles(XmlRolesFactory.build());
					for(R role : fSecurity.allForCategory(cRole, cCategory, category.getCode()))
					{
						role = fSecurity.load(cRole,role);
						Collections.sort(role.getUsecases(),comparatorUsecase);
						Role xRole = xfRole.build(role);
						xCat.getRoles().getRole().add(xRole);
					}
					xml.getCategory().add(xCat);
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
			if(category.getType().equals(JeeslSecurityCategory.Type.action.toString()))
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

	@Override public Security exportSecurityUsecases()
	{
		Security xml = XmlSecurityFactory.build();

		for(C category : fSecurity.allOrderedPosition(cCategory))
		{
			if(category.getType().equals(JeeslSecurityCategory.Type.usecase.toString()))
			{
				try
				{
					net.sf.ahtutils.xml.security.Category xmlCat = fCategory.build(category);
					xmlCat.setUsecases(XmlUsecasesFactory.build());
					for(U usecase : fSecurity.allForCategory(cUsecase, cCategory, category.getCode()))
					{
						usecase = fSecurity.load(cUsecase, usecase);
						Collections.sort(usecase.getActions(),comparatorAction);
						Collections.sort(usecase.getViews(),comparatorView);
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
			if(category.getType().equals(JeeslSecurityCategory.Type.view.toString()))
			{
				try
				{
					net.sf.ahtutils.xml.security.Category xmlCat = fCategory.build(category);
					xmlCat.setViews(XmlViewsFactory.build());
					for(V view : fSecurity.allForCategory(cView, cCategory, category.getCode()))
					{
						view = fSecurity.load(cView,view);
						View xView = xfViewOld.create(view);
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
			if(category.getType().equals(JeeslSecurityCategory.Type.view.toString()))
			{
				try
				{
					Views views = XmlViewsFactory.build();
					
					for(V view : fSecurity.allForCategory(cView, cCategory, category.getCode()))
					{
						view = fSecurity.load(cView,view);
						View xView = xfViewOld.create(view);
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
			if(category.getType().equals(JeeslSecurityCategory.Type.usecase.toString()))
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