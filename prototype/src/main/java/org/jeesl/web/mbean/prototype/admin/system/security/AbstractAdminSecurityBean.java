package org.jeesl.web.mbean.prototype.admin.system.security;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

import org.jeesl.api.facade.system.JeeslSecurityFacade;
import org.jeesl.factory.ejb.system.security.EjbSecurityActionFactory;
import org.jeesl.factory.ejb.system.security.EjbSecurityActionTemplateFactory;
import org.jeesl.factory.ejb.system.security.EjbSecurityCategoryFactory;
import org.jeesl.factory.ejb.system.security.EjbSecurityRoleFactory;
import org.jeesl.factory.ejb.system.security.EjbSecurityUsecaseFactory;
import org.jeesl.factory.ejb.system.security.EjbSecurityViewFactory;
import org.jeesl.factory.factory.SecurityFactoryFactory;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityView;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityTemplate;
import org.jeesl.interfaces.model.system.security.user.UtilsUser;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityAction;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityUsecase;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityCategory;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.jeesl.web.mbean.prototype.admin.AbstractAdminBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.bean.FacesMessageBean;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.util.comparator.ejb.security.SecurityActionComparator;
import net.sf.ahtutils.util.comparator.ejb.security.SecurityRoleComparator;
import net.sf.ahtutils.util.comparator.ejb.security.SecurityUsecaseComparator;
import net.sf.ahtutils.util.comparator.ejb.security.SecurityViewComparator;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractAdminSecurityBean <L extends UtilsLang,D extends UtilsDescription,
											C extends JeeslSecurityCategory<L,D,C,R,V,U,A,AT,USER>,
											R extends JeeslSecurityRole<L,D,C,R,V,U,A,AT,USER>,
											V extends JeeslSecurityView<L,D,C,R,V,U,A,AT,USER>,
											U extends JeeslSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
											A extends JeeslSecurityAction<L,D,C,R,V,U,A,AT,USER>,
											AT extends JeeslSecurityTemplate<L,D,C,R,V,U,A,AT,USER>,
											USER extends UtilsUser<L,D,C,R,V,U,A,AT,USER>>
					extends AbstractAdminBean<L,D>
					implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminSecurityBean.class);
	
	protected JeeslSecurityFacade<L,D,C,R,V,U,A,AT,USER> fSecurity;
	protected JeeslSecurityCategory.Type categoryType;
	
	protected EjbSecurityCategoryFactory<L,D,C,R,V,U,A,AT,USER> efCategory;
	protected EjbSecurityViewFactory<L,D,C,R,V,U,A,AT,USER> efView;
	protected EjbSecurityRoleFactory<L,D,C,R,V,U,A,AT,USER> efRole;
	protected EjbSecurityUsecaseFactory<L,D,C,R,V,U,A,AT,USER> efUsecase;
	protected EjbSecurityActionFactory<L,D,C,R,V,U,A,AT,USER> efAction;
	protected EjbSecurityActionTemplateFactory<L,D,C,R,V,U,A,AT,USER> efTemplate;
	
	protected Class<C> cCategory;
	protected Class<R> cRole;
	protected Class<V> cView;
	protected Class<U> cUsecase;
	protected Class<A> cAction;
	protected Class<AT> cTemplate;
	protected final Class<USER> cUser;
	
	protected Comparator<R> comparatorRole;
	protected Comparator<V> comparatorView;
	protected Comparator<U> comparatorUsecase;
	protected Comparator<A> comparatorAction;
		
	//Category
	protected List<C> categories; public List<C> getCategories() {return categories;}
	protected List<V> opViews; public List<V> getOpViews(){return opViews;}
	protected List<A> opActions; public List<A> getOpActions(){return opActions;}
	protected List<U> opUsecases; public List<U> getOpUsecases(){return opUsecases;}
	protected List<AT> templates; public List<AT> getTemplates(){return templates;}
	
	private List<V> opFvActions; public List<V> getOpFvActions(){return opFvActions;} public void setOpFvActions(List<V> opFvActions){this.opFvActions = opFvActions;}
	private List<V> opFvViews;public List<V> getOpFvViews(){return opFvViews;}public void setOpFvViews(List<V> opFvViews){this.opFvViews = opFvViews;}
	private List<U> opFvUsecases;public List<U> getOpFvUsecases(){return opFvUsecases;}public void setOpFvUsecases(List<U> opFvUsecases){this.opFvUsecases = opFvUsecases;}
	
	protected C category;public void setCategory(C category) {this.category = category;}public C getCategory() {return category;}
	
	protected V opView;public V getOpView(){return opView;}public void setOpView(V opView){this.opView = opView;}
	protected V tblView;public V getTblView(){return tblView;}public void setTblView(V tblView){this.tblView = tblView;}
	protected A opAction;public A getOpAction(){return opAction;}public void setOpAction(A opAction){this.opAction = opAction;}
	protected A tblAction;public A getTblAction(){return tblAction;}public void setTblAction(A tblAction){this.tblAction = tblAction;}
	protected U opUsecase;public U getOpUsecase(){return opUsecase;}public void setOpUsecase(U opUsecase){this.opUsecase = opUsecase;}
	protected U tblUsecase;public U getTblUsecase(){return tblUsecase;}public void setTblUsecase(U tblUsecase){this.tblUsecase = tblUsecase;}
	
	public AbstractAdminSecurityBean(final Class<L> cL, final Class<D> cD, final Class<C> cCategory, final Class<R> cRole, final Class<V> cView, final Class<U> cUsecase, final Class<A> cAction, final Class<AT> cTemplate, final Class<USER> cUser)
	{
		super(cL,cD);
		this.cCategory=cCategory;
		this.cRole=cRole;
		this.cUsecase=cUsecase;
		this.cView=cView;
		this.cAction=cAction;
		this.cTemplate=cTemplate;
		this.cUser=cUser;
	}
	
	public void initSecuritySuper(String[] langs, JeeslSecurityFacade<L,D,C,R,V,U,A,AT,USER> fSecurity, FacesMessageBean bMessage)
	{
		super.initAdmin(langs,cL,cD,bMessage);
		this.fSecurity=fSecurity;
		
		SecurityFactoryFactory<L,D,C,R,V,U,A,AT,USER> ffSecurity = SecurityFactoryFactory.factory(cL,cD,cAction);
		
		efCategory = EjbSecurityCategoryFactory.factory(cL,cD,cCategory,cRole,cView,cUsecase,cAction,cUser);
		efView = EjbSecurityViewFactory.factory(cL,cD,cCategory,cRole,cView,cUsecase,cAction,cUser);
		efRole = EjbSecurityRoleFactory.factory(cL,cD,cCategory,cRole,cView,cUsecase,cAction,cUser);
		efUsecase = EjbSecurityUsecaseFactory.factory(cL,cD,cCategory,cRole,cView,cUsecase,cAction,cUser);
		efAction = ffSecurity.ejbAction();
		efTemplate = EjbSecurityActionTemplateFactory.factory(cL,cD,cTemplate);
		
		comparatorRole = (new SecurityRoleComparator<L,D,C,R,V,U,A,AT,USER>()).factory(SecurityRoleComparator.Type.position);
		comparatorView = (new SecurityViewComparator<L,D,C,R,V,U,A,AT,USER>()).factory(SecurityViewComparator.Type.position);
		comparatorUsecase = (new SecurityUsecaseComparator<L,D,C,R,V,U,A,AT,USER>()).factory(SecurityUsecaseComparator.Type.position);
		comparatorAction = (new SecurityActionComparator<L,D,C,R,V,U,A,AT,USER>()).factory(SecurityActionComparator.Type.position);
		
		reloadCategories();
	}
	
	public void selectTblAction() {logger.info(AbstractLogMessage.selectEntity(tblAction));}
	public void selectTblView() {logger.info(AbstractLogMessage.selectEntity(tblView));}
	public void selectTblUsecase() {logger.info(AbstractLogMessage.selectEntity(tblUsecase));}
	
	protected void selectCategory() throws UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.selectEntity(category));
		category = efLang.persistMissingLangs(fSecurity,langs,category);
		category = efDescription.persistMissingLangs(fSecurity,langs,category);
		categorySelected();
	}
	protected void categorySelected() throws UtilsNotFoundException {}
	
	public void saveCategory() throws UtilsNotFoundException, UtilsConstraintViolationException, UtilsLockingException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.saveEntity(category));}
		category = fSecurity.save(category);
		reloadCategories();
		categorySaved();
		bMessage.growlSuccessSaved();
	}
	protected void categorySaved()  throws UtilsNotFoundException {}
	
	public void rmCategory() throws UtilsNotFoundException, UtilsConstraintViolationException, UtilsLockingException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.rmEntity(category));}
		if(categoryRemoveable())
		{
			fSecurity.rm(category);
			category = null;
			reloadCategories();
			bMessage.growlSuccessRemoved();
		}
		else
		{
			logger.warn(cCategory.getSimpleName()+" not removeable ... in use!");
			bMessage.errorConstraintViolationInUse("category");
		}
	}
	protected boolean categoryRemoveable() throws UtilsNotFoundException {return false;}
	
	protected void reloadCategories()
	{
		logger.info("reloadCategories");
		
		if(uiShowInvisible){categories = fSecurity.allOrderedPosition(cCategory,categoryType);}
		else{categories = fSecurity.allOrderedPositionVisible(cCategory,categoryType);}
	}
	
	protected void reorderCategories() throws UtilsConstraintViolationException, UtilsLockingException
	{
		logger.info("updateOrder "+categories.size());
		int i=1;
		for(C c : categories)
		{
			c.setPosition(i);
			fSecurity.update(c);
			i++;
		}
	}
}