package org.jeesl.web.mbean.prototype.admin.system.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jeesl.api.facade.system.JeeslSecurityFacade;
import org.jeesl.factory.builder.SecurityFactoryBuilder;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityView;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityTemplate;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityAction;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityUsecase;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityCategory;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.bean.FacesMessageBean;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.jsf.util.PositionListReorderer;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractAdminSecurityUsecasesBean <L extends UtilsLang, D extends UtilsDescription,
											C extends JeeslSecurityCategory<L,D>,
											R extends JeeslSecurityRole<L,D,C,R,V,U,A,AT,USER>,
											V extends JeeslSecurityView<L,D,C,R,V,U,A,AT,USER>,
											U extends JeeslSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
											A extends JeeslSecurityAction<L,D,C,R,V,U,A,AT,USER>,
											AT extends JeeslSecurityTemplate<L,D,C,R,V,U,A,AT,USER>,
											USER extends JeeslUser<L,D,C,R,V,U,A,AT,USER>>
		extends AbstractAdminSecurityBean<L,D,C,R,V,U,A,AT,USER>
		implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminSecurityUsecasesBean.class);
	
	private List<U> usecases; public List<U> getUsecases() {return usecases;}
	private List<R> roles; public List<R> getRoles(){return roles;}
	private List<V> views; public List<V> getViews(){return views;}
	private List<A> actions; public List<A> getActions(){return actions;}
	
	private U usecase; public U getUsecase(){return usecase;} public void setUsecase(U usecase){this.usecase = usecase;}
	
	public AbstractAdminSecurityUsecasesBean(SecurityFactoryBuilder<L,D,C,R,V,U,A,AT,USER> fbSecurity)
	{
		super(fbSecurity);
	}
	
	public void initSuper(String[] langs, JeeslSecurityFacade<L,D,C,R,V,U,A,AT,USER> fSecurity, FacesMessageBean bMessage)
	{
		categoryType = JeeslSecurityCategory.Type.usecase;
		initSecuritySuper(langs,fSecurity,bMessage);
		
		opViews = fSecurity.all(fbSecurity.getClassView());
		Collections.sort(opViews,comparatorView);
		
		opActions = new ArrayList<A>();
	}
	
	public void categorySelected() throws UtilsNotFoundException
	{
		reloadUsecases();
		usecase=null;
	}
	public void saveCategory() throws UtilsNotFoundException, UtilsConstraintViolationException, UtilsLockingException
	{
		logger.info(AbstractLogMessage.saveEntity(category));
		category = fSecurity.save(category);
		reloadCategories();
		reloadUsecases();
		categorySaved();
	}
	
	public void selectUsecase()
	{
		logger.info(AbstractLogMessage.selectEntity(usecase));
		reloadUsecase();
		usecase = efLang.persistMissingLangs(fSecurity,langs,usecase);
		usecase = efDescription.persistMissingLangs(fSecurity,langs,usecase);
		
		reloadActions();
	}
	
	//Reload
	private void reloadUsecase()
	{
		usecase = fSecurity.load(fbSecurity.getClassUsecase(),usecase);
		roles = usecase.getRoles();
		views = usecase.getViews();
		actions = usecase.getActions();
		
		Collections.sort(views,comparatorView);
		Collections.sort(actions,comparatorAction);
		Collections.sort(roles,comparatorRole);
	}
	
	private void reloadUsecases() throws UtilsNotFoundException
	{
		logger.info("reloadUsecases");
		usecases = fSecurity.allForCategory(fbSecurity.getClassUsecase(),fbSecurity.getClassCategory(),category.getCode());
	}
	private void reloadActions()
	{
		opActions.clear();
		for(V v : usecase.getViews())
		{
			v = fSecurity.load(fbSecurity.getClassView(),v);
			opActions.addAll(v.getActions());
		}
		Collections.sort(opActions, comparatorAction);
	}
	
	//Add
	public void addCategory() throws UtilsConstraintViolationException
	{
		logger.info(AbstractLogMessage.addEntity(fbSecurity.getClassCategory()));
		category = efCategory.create(null,JeeslSecurityCategory.Type.usecase.toString());
		category.setName(efLang.createEmpty(langs));
		category.setDescription(efDescription.createEmpty(langs));
	}
	public void addUsecase() throws UtilsConstraintViolationException
	{
		logger.info(AbstractLogMessage.addEntity(fbSecurity.getClassUsecase()));
		usecase = efUsecase.create(category,"");
		usecase.setName(efLang.createEmpty(langs));
		usecase.setDescription(efDescription.createEmpty(langs));
	}

	//Save

	public void saveUsecase() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.saveEntity(usecase));
		usecase.setCategory(fSecurity.find(fbSecurity.getClassCategory(), usecase.getCategory()));
		usecase = fSecurity.save(usecase);
		reloadUsecase();
		reloadUsecases();
	}
	
	//OverlayPanel
	public void opAddView() throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(!usecase.getViews().contains(opView))
		{
			usecase.getViews().add(opView);
			usecase = fSecurity.save(usecase);
			opView = null;
			selectUsecase();
		}
	}
	public void opAddAction() throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(!usecase.getActions().contains(opAction))
		{
			usecase.getActions().add(opAction);
			usecase = fSecurity.save(usecase);
			opAction = null;
			selectUsecase();
		}
	}
	
	//Overlay-Rm
	public void opRmView() throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(tblView!=null && usecase.getViews().contains(tblView))
		{
			usecase.getViews().remove(tblView);
			usecase = fSecurity.save(usecase);
			tblView = null;
			selectUsecase();
		}
	}
	public void opRmAction() throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(tblAction!=null && usecase.getActions().contains(tblAction))
		{
			usecase.getActions().remove(tblAction);
			usecase = fSecurity.save(usecase);
			tblAction = null;
			selectUsecase();
		}
	}
	
	protected void reorderUsecases() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fSecurity, usecases);}
}