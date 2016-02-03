package net.sf.ahtutils.prototype.web.mbean.admin.security;

import java.io.Serializable;
import java.util.ArrayList;
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
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityCategory;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityRole;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityUsecase;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityView;
import net.sf.ahtutils.interfaces.model.system.security.UtilsUser;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractAdminSecurityUsecasesBean <L extends UtilsLang,
											D extends UtilsDescription,
											C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,
											R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,
											V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,
											U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,
											A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,
											USER extends UtilsUser<L,D,C,R,V,U,A,USER>>
		extends AbstractAdminSecurityBean<L,D,C,R,V,U,A,USER>
		implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminSecurityUsecasesBean.class);
	
	private List<U> usecases; public List<U> getUsecases() {return usecases;}
	
	private U usecase; public U getUsecase(){return usecase;} public void setUsecase(U usecase){this.usecase = usecase;}
	
	public void initSuper(FacesMessageBean bMessage, String[] langs,final Class<L> cLang,final Class<D> cDescription, final Class<C> cCategory,final Class<R> cRole,final Class<V> cView,final Class<U> cUsecase, final Class<A> cAction,final Class<USER> cUser)
	{
		categoryType = UtilsSecurityCategory.Type.usecase;
		initSecuritySuper(bMessage,cLang,cDescription,cCategory,cRole,cView,cUsecase,cAction,cUser,langs);
		
		opViews = fSecurity.all(cView);
		Collections.sort(opViews, comparatorView);
		
		opActions = new ArrayList<A>();
	}
	
	//SELECT
	public void selectCategory() throws UtilsNotFoundException
	{
		super.selectCategory();
		reloadUsecases();
		usecase=null;
	}
	public void selectUsecase()
	{
		logger.info(AbstractLogMessage.selectEntity(usecase));
		usecase = efLang.persistMissingLangs(fSecurity,langs,usecase);
		usecase = efDescription.persistMissingLangs(fSecurity,langs,usecase);
		usecase = fSecurity.find(cUsecase,usecase);
		Collections.sort(usecase.getViews(), comparatorView);
		Collections.sort(usecase.getActions(), comparatorAction);
		reloadActions();
	}
	
	//Reload
	private void reloadUsecases() throws UtilsNotFoundException
	{
		logger.info("reloadUsecases");
		usecases = fSecurity.allForCategory(cUsecase,cCategory,category.getCode());
	}
	private void reloadActions()
	{
		opActions.clear();
		for(V v : usecase.getViews())
		{
			v = fSecurity.load(cView,v);
			opActions.addAll(v.getActions());
		}
		Collections.sort(opActions, comparatorAction);
	}
	
	//Add
	public void addCategory() throws UtilsConstraintViolationException
	{
		logger.info(AbstractLogMessage.addEntity(cCategory));
		category = efCategory.create(null,UtilsSecurityCategory.Type.usecase.toString());
		category.setName(efLang.createEmpty(langs));
		category.setDescription(efDescription.createEmpty(langs));
	}
	public void addUsecase() throws UtilsConstraintViolationException
	{
		logger.info(AbstractLogMessage.addEntity(cUsecase));
		usecase = efUsecase.create(category,"");
		usecase.setName(efLang.createEmpty(langs));
		usecase.setDescription(efDescription.createEmpty(langs));
	}

	//Save
	public void saveCategory() throws UtilsNotFoundException, UtilsConstraintViolationException, UtilsLockingException
	{
		logger.info(AbstractLogMessage.saveEntity(category));
		category = fSecurity.save(category);
		reloadCategories();
		reloadUsecases();
		categorySaved();
	}
	public void saveUsecase() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.saveEntity(usecase));
		usecase.setCategory(fSecurity.find(cCategory, usecase.getCategory()));
		usecase = fSecurity.save(usecase);
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
	
	//Order
	protected void reorderUsecases() throws UtilsConstraintViolationException, UtilsLockingException
	{
		logger.info("updateOrder "+usecases.size());
		int i=1;
		for(U u : usecases)
		{
			u.setPosition(i);
			fSecurity.update(u);
			i++;
		}
	}
}