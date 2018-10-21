package org.jeesl.web.mbean.prototype.system.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.jeesl.api.bean.JeeslSecurityBean;
import org.jeesl.api.bean.JeeslTranslationBean;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.api.facade.system.JeeslSecurityFacade;
import org.jeesl.factory.builder.system.SecurityFactoryBuilder;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityAction;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityCategory;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityMenu;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityTemplate;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityUsecase;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityView;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.jeesl.jsf.util.TriStateBinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.jsf.util.PositionListReorderer;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public abstract class AbstractAdminSecurityViewBean <L extends UtilsLang, D extends UtilsDescription,
											C extends JeeslSecurityCategory<L,D>,
											R extends JeeslSecurityRole<L,D,C,V,U,A,USER>,
											V extends JeeslSecurityView<L,D,C,R,U,A>,
											U extends JeeslSecurityUsecase<L,D,C,R,V,A>,
											A extends JeeslSecurityAction<L,D,R,V,U,AT>,
											AT extends JeeslSecurityTemplate<L,D,C>,
											M extends JeeslSecurityMenu<V,M>,
											USER extends JeeslUser<R>>
		extends AbstractAdminSecurityBean<L,D,C,R,V,U,A,AT,M,USER>
		implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminSecurityViewBean.class);

	private List<V> views; public List<V> getViews(){return views;}
	private List<A> actions; public List<A> getActions(){return actions;}
	private List<R> roles; public List<R> getRoles(){return roles;}
	private List<U> usecases; public List<U> getUsecases(){return usecases;}
	
	private V view;public V getView(){return view;}public void setView(V view) {this.view = view;}
	private A action;public A getAction(){return action;}public void setAction(A action) {this.action = action;}
	
	private JeeslSecurityBean<L,D,C,R,V,U,A,AT,M,USER> bSecurity;
	private final TriStateBinder tsb; public TriStateBinder getTsb() {return tsb;}
	
	public AbstractAdminSecurityViewBean(SecurityFactoryBuilder<L,D,C,R,V,U,A,AT,M,USER> fbSecurity)
	{
		super(fbSecurity);
		categoryType = JeeslSecurityCategory.Type.view;
		tsb = new TriStateBinder();
	}
	
	public void initSuper(JeeslSecurityFacade<L,D,C,R,V,U,A,AT,M,USER> fSecurity, JeeslTranslationBean bTranslation, JeeslFacesMessageBean bMessage, JeeslSecurityBean<L,D,C,R,V,U,A,AT,M,USER> bSecurity)
	{
		super.postConstructSecurity(fSecurity,bTranslation,bMessage);
		this.bSecurity=bSecurity;
		templates = fSecurity.allOrderedPositionVisible(fbSecurity.getClassTemplate());
	}
	
	@Override public void categorySelected() throws UtilsNotFoundException
	{
		reloadViews();
		view=null;
		action=null;
	}
	@Override protected void categorySaved() throws UtilsNotFoundException
	{
		reloadViews();
	}
	
	protected boolean categoryRemoveable() throws UtilsNotFoundException
	{
		views = fSecurity.allForCategory(fbSecurity.getClassView(),fbSecurity.getClassCategory(),category.getCode());
		return views.isEmpty();
	}
	
	private void reloadViews() throws UtilsNotFoundException
	{
		views = fSecurity.allForCategory(fbSecurity.getClassView(),fbSecurity.getClassCategory(),category.getCode());
		logger.info(AbstractLogMessage.reloaded(fbSecurity.getClassView(), views));
	}
	
	private void reloadView()
	{
		view = fSecurity.load(fbSecurity.getClassView(),view);
		tsb.booleanToA(view.getRedirect());
		roles = view.getRoles();
		Collections.sort(roles,comparatorRole);
		
		usecases = view.getUsecases();
		Collections.sort(usecases,comparatorUsecase);
	}
	
	private void reloadActions()
	{
		actions = view.getActions();
		Collections.sort(actions, comparatorAction);
	}
	
	private void reset(boolean rView, boolean rAction)
	{
		if(rView) {view=null;}
		if(rAction) {action=null;}
	}
	
	//Add
	public void addCategory() throws UtilsConstraintViolationException
	{
		logger.info(AbstractLogMessage.addEntity(fbSecurity.getClassCategory()));
		category = efCategory.create(null,JeeslSecurityCategory.Type.view.toString());
		category.setName(efLang.createEmpty(localeCodes));
		category.setDescription(efDescription.createEmpty(localeCodes));
	}
	
	//VIEW
	public void addView() throws UtilsConstraintViolationException
	{
		logger.info(AbstractLogMessage.addEntity(fbSecurity.getClassView()));
		view = efView.build(category,"",views);
		view.setName(efLang.createEmpty(localeCodes));
		view.setDescription(efDescription.createEmpty(localeCodes));
		tsb.booleanToA(view.getRedirect());
	}
	
	public void selectView()
	{
		logger.info(AbstractLogMessage.selectEntity(view));
		view = fSecurity.load(fbSecurity.getClassView(), view);
		view = efLang.persistMissingLangs(fSecurity,localeCodes,view);
		view = efDescription.persistMissingLangs(fSecurity,localeCodes,view);
		reloadView();
		reloadActions();
		action=null;
	}
	
	public void selectAction()
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.selectEntity(action));}
		action = efLang.persistMissingLangs(fSecurity,localeCodes,action);
		action = efDescription.persistMissingLangs(fSecurity,localeCodes,action);
	}
	
	public void saveView() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.saveEntity(view));
		view.setCategory(fSecurity.find(fbSecurity.getClassCategory(), view.getCategory()));
		view.setRedirect(tsb.aToBoolean());
		view = fSecurity.save(view);
		reloadView();
		reloadViews();
		bMessage.growlSuccessSaved();
		propagateChanges();
		bSecurity.update(view);
	}
	
	public void cloneView() throws UtilsConstraintViolationException
	{
		logger.info(AbstractLogMessage.addEntity(fbSecurity.getClassView()));
		V clone = efView.clone(view);
		clone.setName(efLang.clone(view.getName()));
		clone.setDescription(efDescription.clone(view.getDescription()));
		reset(true,true);
		view = clone;
	}
	
	protected abstract void propagateChanges();
	
	public void deleteView() throws UtilsConstraintViolationException, UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.rmEntity(view));
		fSecurity.rm(view);
		reset(true,true);
		reloadViews();
		propagateChanges();
		bMessage.growlSuccessRemoved();
	}
	
	public void saveAction() throws UtilsConstraintViolationException, UtilsLockingException
	{
		logger.info(AbstractLogMessage.saveEntity(action));
		List<L> langs = new ArrayList<L>();
		List<D> descriptions = new ArrayList<D>();
		if(action.getTemplate()!=null)
		{
			action.setTemplate(fSecurity.find(fbSecurity.getClassTemplate(), action.getTemplate()));
			logger.info("Testing ... "+action.toString());
			
			if(action.getName()!=null){langs.addAll(action.getName().values());}
			if(action.getDescription()!=null){descriptions.addAll(action.getDescription().values());}
			
			action.setName(null);
			action.setDescription(null);
		}
		action = fSecurity.save(action);
		if(!langs.isEmpty()){fSecurity.rm(langs);}
		if(!descriptions.isEmpty()){fSecurity.rm(descriptions);}
		reloadView();
		reloadActions();
		propagateChanges();
		bMessage.growlSuccessSaved();
	}
	
	//ACTION
	public void addAction() throws UtilsConstraintViolationException
	{
		logger.info(AbstractLogMessage.addEntity(fbSecurity.getClassAction()));
		action = efAction.build(view,"",actions);
		action.setName(efLang.createEmpty(localeCodes));
		action.setDescription(efDescription.createEmpty(localeCodes));
	}
	
	public void rmAction() throws UtilsConstraintViolationException
	{
		logger.info(AbstractLogMessage.rmEntity(action));
		fSecurity.rm(action);
		action=null;
		reloadView();
		reloadActions();
		propagateChanges();
		bMessage.growlSuccessRemoved();
	}
	
	public void changeTemplate()
	{
		logger.info(AbstractLogMessage.selectOneMenuChange(action.getTemplate()));
		if(action.getTemplate()!=null)
		{
			action.setTemplate(fSecurity.find(fbSecurity.getClassTemplate(), action.getTemplate()));
			action.setCode(UUID.randomUUID().toString());
		}
		else
		{
			action = efLang.persistMissingLangs(fSecurity,localeCodes,action);
			action = efDescription.persistMissingLangs(fSecurity,localeCodes,action);
		}
	}
	
	public void reorderViews() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fSecurity, views);}
	public void reorderActions() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fSecurity, actions);}
}