package net.sf.ahtutils.prototype.web.mbean.admin.system.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.jeesl.api.facade.system.JeeslSecurityFacade;
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

public class AbstractAdminSecurityViewBean <L extends UtilsLang,D extends UtilsDescription,
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

	private List<V> views; public List<V> getViews(){return views;}
	private List<A> actions; public List<A> getActions(){return actions;}
	private List<R> roles; public List<R> getRoles(){return roles;}
	private List<U> usecases; public List<U> getUsecases(){return usecases;}
	
	private V view;public V getView(){return view;}public void setView(V view) {this.view = view;}
	private A action;public A getAction(){return action;}public void setAction(A action) {this.action = action;}
	
	public AbstractAdminSecurityViewBean(final Class<L> cL, final Class<D> cD)
	{
		super(cL,cD);
	}
	
	public void initSuper(String[] langs, JeeslSecurityFacade<L,D,C,R,V,U,A,AT,USER> fSecurity, FacesMessageBean bMessage, final Class<L> cLang, final Class<D> cDescription, final Class<C> cCategory, final Class<R> cRole, final Class<V> cView, final Class<U> cUsecase, final Class<A> cAction, final Class<AT> cTemplate,final Class<USER> cUser)
	{
		categoryType = UtilsSecurityCategory.Type.view;
		initSecuritySuper(langs,fSecurity,bMessage,cLang,cDescription,cCategory,cRole,cView,cUsecase,cAction,cTemplate,cUser);
		
		templates = fSecurity.allOrderedPositionVisible(cTemplate);
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
		views = fSecurity.allForCategory(cView,cCategory,category.getCode());
		return views.isEmpty();
	}
	
	
	private void reloadViews() throws UtilsNotFoundException
	{
		views = fSecurity.allForCategory(cView,cCategory,category.getCode());
		logger.info(AbstractLogMessage.reloaded(cView, views));
	}
	
	private void reloadView()
	{
		view = fSecurity.load(cView,view);
		
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
	
	//VIEW
	public void addView() throws UtilsConstraintViolationException
	{
		logger.info(AbstractLogMessage.addEntity(cView));
		view = efView.create(category,"");
		view.setName(efLang.createEmpty(langs));
		view.setDescription(efDescription.createEmpty(langs));
	}
	
	public void selectView()
	{
		logger.info(AbstractLogMessage.selectEntity(view));
		view = fSecurity.load(cView, view);
		view = efLang.persistMissingLangs(fSecurity,langs,view);
		view = efDescription.persistMissingLangs(fSecurity,langs,view);
		reloadView();
		reloadActions();
		action=null;
	}
	
	public void selectAction()
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.selectEntity(action));}
		action = efLang.persistMissingLangs(fSecurity,langs,action);
		action = efDescription.persistMissingLangs(fSecurity,langs,action);
	}
	
	public void saveView() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.saveEntity(view));
		view.setCategory(fSecurity.find(cCategory, view.getCategory()));
		view = fSecurity.save(view);
		reloadView();
		reloadViews();
		bMessage.growlSuccessSaved();
	}
	
	public void rmView() throws UtilsConstraintViolationException, UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.rmEntity(view));
		fSecurity.rm(view);
		view=null;
		action=null;
		reloadViews();
		bMessage.growlSuccessRemoved();
	}
	
	public void saveAction() throws UtilsConstraintViolationException, UtilsLockingException
	{
		logger.info(AbstractLogMessage.saveEntity(action));
		List<L> langs = new ArrayList<L>();
		List<D> descriptions = new ArrayList<D>();
		if(action.getTemplate()!=null)
		{
			action.setTemplate(fSecurity.find(cTemplate, action.getTemplate()));
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
		bMessage.growlSuccessSaved();
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
		reloadView();
		reloadActions();
		bMessage.growlSuccessRemoved();
	}
	
	public void changeTemplate()
	{
		logger.info(AbstractLogMessage.selectOneMenuChange(action.getTemplate()));
		if(action.getTemplate()!=null)
		{
			action.setTemplate(fSecurity.find(cTemplate, action.getTemplate()));
			action.setCode(UUID.randomUUID().toString());
		}
		else
		{
			action = efLang.persistMissingLangs(fSecurity,langs,action);
			action = efDescription.persistMissingLangs(fSecurity,langs,action);
		}
	}
	
	protected void reorderViews() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fSecurity, views);}
	protected void reorderActions() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fSecurity, actions);}
}