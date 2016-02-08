package net.sf.ahtutils.prototype.web.mbean.admin.security;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.bean.FacesMessageBean;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityAction;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityActionTemplate;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityCategory;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityRole;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityUsecase;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityView;
import net.sf.ahtutils.interfaces.model.system.security.UtilsUser;
import net.sf.ahtutils.jsf.util.PositionListReorderer;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractAdminSecurityViewBean <L extends UtilsLang,
											D extends UtilsDescription,
											C extends UtilsSecurityCategory<L,D,C,R,V,U,A,AT,USER>,
											R extends UtilsSecurityRole<L,D,C,R,V,U,A,AT,USER>,
											V extends UtilsSecurityView<L,D,C,R,V,U,A,AT,USER>,
											U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
											A extends UtilsSecurityAction<L,D,C,R,V,U,A,AT,USER>,
											AT extends UtilsSecurityActionTemplate<L,D,C,R,V,U,A,AT,USER>,
											USER extends UtilsUser<L,D,C,R,V,U,A,AT,USER>>
		extends AbstractAdminSecurityBean<L,D,C,R,V,U,A,AT,USER>
		implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminSecurityViewBean.class);

	private List<V> views;public List<V> getViews(){return views;}
	private List<A> actions;public List<A> getActions(){return actions;}
	
	private V view;public V getView(){return view;}public void setView(V view) {this.view = view;}
	private A action;public A getAction(){return action;}public void setAction(A action) {this.action = action;}
	
	
	public void initSuper(FacesMessageBean bMessage, final Class<L> cLang, final Class<D> cDescription, final Class<C> cCategory, final Class<R> cRole, final Class<V> cView, final Class<U> cUsecase, final Class<A> cAction, final Class<USER> cUser, String[] langs)
	{
		categoryType = UtilsSecurityCategory.Type.view;
		initSecuritySuper(bMessage,cLang,cDescription,cCategory,cRole,cView,cUsecase,cAction,cUser,langs);		
	}
	
	// SELECT
	public void selectCategory() throws UtilsNotFoundException
	{
		super.selectCategory();
		reloadViews();
		view=null;
		action=null;
	}
	public void selectView()
	{
		logger.info(AbstractLogMessage.selectEntity(view));
		view = efLang.persistMissingLangs(fSecurity,langs,view);
		view = efDescription.persistMissingLangs(fSecurity,langs,view);
		reloadActions();
		action=null;
	}
	public void selectAction()
	{
		logger.info(AbstractLogMessage.selectEntity(action));
		action = efLang.persistMissingLangs(fSecurity,langs,action);
		action = efDescription.persistMissingLangs(fSecurity,langs,action);
	}
	
	//RELOAD
	private void reloadViews() throws UtilsNotFoundException
	{
		views = fSecurity.allForCategory(cView,cCategory,category.getCode());
		
		logger.info("Reloaded "+views.size());
	}
	
	private void reloadActions()
	{
		view = fSecurity.load(cView,view);
		actions = view.getActions();
		Collections.sort(actions, comparatorAction);
	}
	
	//SAVE
	public void saveCategory() throws UtilsConstraintViolationException, UtilsLockingException
	{
		logger.info(AbstractLogMessage.saveEntity(category));
		category = fSecurity.save(category);
		reloadCategories();
		categorySaved();
	}
	
	public void saveView() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.saveEntity(view));
		view.setCategory(fSecurity.find(cCategory, view.getCategory()));
		view = fSecurity.save(view);
		reloadViews();
	}
	
	public void saveAction() throws UtilsConstraintViolationException, UtilsLockingException
	{
		logger.info(AbstractLogMessage.saveEntity(action));
		action = fSecurity.save(action);
		reloadActions();
	}
	
	//ACTION
	public void addAction() throws UtilsConstraintViolationException
	{
		logger.info(AbstractLogMessage.addEntity(cAction));
		action = efAction.create(view,"");
		action.setName(efLang.createEmpty(langs));
		action.setDescription(efDescription.createEmpty(langs));
	}
	
	public void rmAction() throws UtilsConstraintViolationException
	{
		logger.info(AbstractLogMessage.rmEntity(action));
		fSecurity.rm(action);
		action=null;
	}
	
	protected void reorderViews() throws UtilsConstraintViolationException, UtilsLockingException
	{
		logger.info("updateOrder "+views.size());
		PositionListReorderer.reorder(fSecurity, views);
	}
	
	protected void reorderActions() throws UtilsConstraintViolationException, UtilsLockingException
	{
		logger.info("updateOrder "+actions.size());
		PositionListReorderer.reorder(fSecurity, actions);
	}
}