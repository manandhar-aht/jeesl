package org.jeesl.web.mbean.prototype.system.security;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

import org.jeesl.api.facade.system.JeeslSecurityFacade;
import org.jeesl.factory.builder.system.SecurityFactoryBuilder;
import org.jeesl.factory.ejb.system.security.EjbSecurityActionFactory;
import org.jeesl.factory.ejb.system.security.EjbSecurityActionTemplateFactory;
import org.jeesl.factory.ejb.system.security.EjbSecurityCategoryFactory;
import org.jeesl.factory.ejb.system.security.EjbSecurityRoleFactory;
import org.jeesl.factory.ejb.system.security.EjbSecurityUsecaseFactory;
import org.jeesl.factory.ejb.system.security.EjbSecurityViewFactory;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityView;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityTemplate;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.jeesl.util.comparator.ejb.system.security.SecurityActionComparator;
import org.jeesl.util.comparator.ejb.system.security.SecurityRoleComparator;
import org.jeesl.util.comparator.ejb.system.security.SecurityUsecaseComparator;
import org.jeesl.util.comparator.ejb.system.security.SecurityViewComparator;
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
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractAdminSecurityBean <L extends UtilsLang,D extends UtilsDescription,
											C extends JeeslSecurityCategory<L,D>,
											R extends JeeslSecurityRole<L,D,C,V,U,A,USER>,
											V extends JeeslSecurityView<L,D,C,R,U,A>,
											U extends JeeslSecurityUsecase<L,D,C,R,V,A>,
											A extends JeeslSecurityAction<L,D,R,V,U,AT>,
											AT extends JeeslSecurityTemplate<L,D,C,R,V,U,A,AT,USER>,
											USER extends JeeslUser<L,D,C,R,V,U,A,AT,USER>>
					extends AbstractAdminBean<L,D>
					implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminSecurityBean.class);
	
	protected JeeslSecurityFacade<L,D,C,R,V,U,A,AT,USER> fSecurity;
	protected JeeslSecurityCategory.Type categoryType;
	
	protected final SecurityFactoryBuilder<L,D,C,R,V,U,A,AT,USER> fbSecurity;
	
	protected final EjbSecurityCategoryFactory<L,D,C,R,V,U,A,AT,USER> efCategory;
	protected final EjbSecurityRoleFactory<L,D,C,R,V,U,A,AT,USER> efRole;
	protected final EjbSecurityViewFactory<L,D,C,R,V,U,A,AT,USER> efView;
	protected final EjbSecurityUsecaseFactory<L,D,C,R,V,U,A,AT,USER> efUsecase;
	protected final EjbSecurityActionFactory<L,D,C,R,V,U,A,AT,USER> efAction;
	protected final EjbSecurityActionTemplateFactory<L,D,C,R,V,U,A,AT,USER> efTemplate;
	

	protected final Comparator<R> comparatorRole;
	protected final Comparator<V> comparatorView;
	protected final Comparator<U> comparatorUsecase;
	protected final Comparator<A> comparatorAction;
		
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
	
	public AbstractAdminSecurityBean(SecurityFactoryBuilder<L,D,C,R,V,U,A,AT,USER> fbSecurity)
	{
		super(fbSecurity.getClassL(),fbSecurity.getClassD());
		this.fbSecurity=fbSecurity;

		efCategory = fbSecurity.ejbCategory();
		efRole = fbSecurity.ejbRole();
		efView = fbSecurity.ejbView();
		efUsecase = fbSecurity.ejbUsecase();
		efAction = fbSecurity.ejbAction();
		efTemplate = fbSecurity.ejbTemplate();
		
		comparatorRole = (new SecurityRoleComparator<L,D,C,R,V,U,A,AT,USER>()).factory(SecurityRoleComparator.Type.position);
		comparatorView = (new SecurityViewComparator<L,D,C,R,V,U,A,AT,USER>()).factory(SecurityViewComparator.Type.position);
		comparatorUsecase = (new SecurityUsecaseComparator<L,D,C,R,V,U,A,AT,USER>()).factory(SecurityUsecaseComparator.Type.position);
		comparatorAction = (new SecurityActionComparator<L,D,C,R,V,U,A,AT,USER>()).factory(SecurityActionComparator.Type.position);
	}
	
	public void initSecuritySuper(String[] langs, JeeslSecurityFacade<L,D,C,R,V,U,A,AT,USER> fSecurity, FacesMessageBean bMessage)
	{
		super.initAdmin(langs,cL,cD,bMessage);
		this.fSecurity=fSecurity;
		

		
		reloadCategories();
	}
	
	public void selectTblAction() {logger.info(AbstractLogMessage.selectEntity(tblAction));}
	public void selectTblView() {logger.info(AbstractLogMessage.selectEntity(tblView));}
	public void selectTblUsecase() {logger.info(AbstractLogMessage.selectEntity(tblUsecase));}
	
	public void selectCategory() throws UtilsNotFoundException
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
			logger.warn(fbSecurity.getClassCategory().getSimpleName()+" not removeable ... in use!");
			bMessage.errorConstraintViolationInUse("category");
		}
	}
	protected boolean categoryRemoveable() throws UtilsNotFoundException {return false;}
	
	protected void reloadCategories()
	{
		
		if(categoryType!=null)
		{
			if(uiShowInvisible){categories = fSecurity.allOrderedPosition(fbSecurity.getClassCategory(),categoryType);}
			else{categories = fSecurity.allOrderedPositionVisible(fbSecurity.getClassCategory(),categoryType);}
			if(debugOnInfo){logger.info(AbstractLogMessage.reloaded(fbSecurity.getClassCategory(),categories));}
		}
	}
	
	public void reorderCategories() throws UtilsConstraintViolationException, UtilsLockingException
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