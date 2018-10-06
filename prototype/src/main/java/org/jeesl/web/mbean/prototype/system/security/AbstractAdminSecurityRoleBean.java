package org.jeesl.web.mbean.prototype.system.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatusFixedCode;
import net.sf.ahtutils.jsf.util.PositionListReorderer;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;
import net.sf.exlp.util.io.StringUtil;

public class AbstractAdminSecurityRoleBean <L extends UtilsLang, D extends UtilsDescription,
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
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminSecurityRoleBean.class);
			
	private List<R> roles; public List<R> getRoles(){return roles;}
	private List<V> views; public List<V> getViews(){return views;}
	private List<A> actions; public List<A> getActions(){return actions;}
	private List<U> usecases; public List<U> getUsecases(){return usecases;}
	
	private R role; public R getRole(){return role;} public void setRole(R role) {this.role = role;}
	
	private boolean denyRemove; public boolean isDenyRemove(){return denyRemove;}
	
	public AbstractAdminSecurityRoleBean(SecurityFactoryBuilder<L,D,C,R,V,U,A,AT,M,USER> fbSecurity)
	{
		super(fbSecurity);
	}
	
	public void initSuper(String[] langs, JeeslSecurityFacade<L,D,C,R,V,U,A,AT,M,USER> fSecurity, JeeslFacesMessageBean bMessage)
	{
		categoryType = JeeslSecurityCategory.Type.role;
		super.initSecuritySuper(langs,fSecurity,bMessage);
		
		opViews = fSecurity.all(fbSecurity.getClassView());
		Collections.sort(opViews, comparatorView);
		
		opActions = new ArrayList<A>();
		opUsecases = fSecurity.all(fbSecurity.getClassUsecase());
		Collections.sort(opUsecases,comparatorUsecase);
		
		roles = new ArrayList<R>();
	}
	
	public void addCategory() 
	{
		category = efCategory.create("", JeeslSecurityCategory.Type.role.toString());
		category.setName(efLang.createEmpty(langs));
		category.setDescription(efDescription.createEmpty(langs));
	}
	
	@Override public void categorySelected() throws UtilsNotFoundException
	{
		reloadRoles();
		role=null;
	}
	@Override protected void categorySaved() throws UtilsNotFoundException
	{
		reloadRoles();
	}
	
	private void reloadRoles() throws UtilsNotFoundException
	{
		roles.clear();
		logger.trace(StringUtil.stars());
		for(R r : fSecurity.allForCategory(fbSecurity.getClassRole(),fbSecurity.getClassCategory(),category.getCode()))
		{
			logger.trace("Role "+r.toString());
			if(r.isVisible() | uiShowInvisible){roles.add(r);}
		}
		Collections.sort(roles, comparatorRole);
		
		logger.info("Reloaded "+roles.size()+" (uiShowInvisible:"+uiShowInvisible+")");
	}
	
	

	private void reloadActions()
	{
		for(V v : role.getViews())
		{
			v = fSecurity.load(fbSecurity.getClassView(),v);
			opActions.addAll(v.getActions());
		}
	}

	//Role
	public void addRole() throws UtilsConstraintViolationException
	{
		logger.info(AbstractLogMessage.addEntity(fbSecurity.getClassRole()));
		role = efRole.build(category,"");
		role.setName(efLang.createEmpty(langs));
		role.setDescription(efDescription.createEmpty(langs));
	}
	public void rmRole() throws UtilsConstraintViolationException, UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.rmEntity(role));
		fSecurity.rm(role);
		role=null;
		reloadRoles();
		roleUpdatePerformed();
	}
	public void saveRole() throws UtilsLockingException, UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.saveEntity(role));
		
		try
		{
			role.setCategory(fSecurity.find(fbSecurity.getClassCategory(), role.getCategory()));
			role = fSecurity.save(role);
			selectRole();
			reloadRoles();
			roleUpdatePerformed();
		}
		catch (UtilsConstraintViolationException e) {bMessage.errorConstraintViolationDuplicateObject();}
	}
	
	public void selectRole()
	{
		logger.trace(AbstractLogMessage.selectEntity(role));
		role = fSecurity.load(fbSecurity.getClassRole(),role);
		role = efLang.persistMissingLangs(fSecurity,langs,role);
		role = efDescription.persistMissingLangs(fSecurity,langs,role);		
		role = fSecurity.load(fbSecurity.getClassRole(),role);
		reloadActions();
		
		views = role.getViews();
		actions = role.getActions();
		usecases = role.getUsecases();
		
		Collections.sort(views,comparatorView);
		Collections.sort(actions,comparatorAction);
		Collections.sort(usecases,comparatorUsecase);
		
		tblView=null;
		tblAction=null;
		tblUsecase=null;
		
		denyRemove = false;
		if(role instanceof UtilsStatusFixedCode)
		{
			for(String fixed : ((UtilsStatusFixedCode)role).getFixedCodes())
			{
				if(fixed.equals(role.getCode())){denyRemove=true;}
			}
		}
	}
	
	protected void roleUpdatePerformed(){}
	
	//OverlayPanel
	public void opAddView() throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(!role.getViews().contains(opView))
		{
			role.getViews().add(opView);
			role = fSecurity.save(role);
			opView = null;
			selectRole();
		}
	}
	public void opAddAction() throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(!role.getActions().contains(opAction))
		{
			role.getActions().add(opAction);
			role = fSecurity.save(role);
			opAction = null;
			selectRole();
		}
	}
	public void opAddUsecase() throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(!role.getUsecases().contains(opUsecase))
		{
			role.getUsecases().add(opUsecase);
			role = fSecurity.save(role);
			opUsecase = null;
			selectRole();
		}
	}
	
	//Overlay-Rm
	public void opRmView() throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(tblView!=null && role.getViews().contains(tblView))
		{
			role.getViews().remove(tblView);
			role = fSecurity.save(role);
			tblView = null;
			selectRole();
		}
	}
	public void opRmAction() throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(tblAction!=null && role.getActions().contains(tblAction))
		{
			role.getActions().remove(tblAction);
			role = fSecurity.save(role);
			tblAction = null;
			selectRole();
		}
	}
	public void opRmUsecase() throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(tblUsecase!=null && role.getUsecases().contains(tblUsecase))
		{
			role.getUsecases().remove(tblUsecase);
			role = fSecurity.save(role);
			tblUsecase = null;
			selectRole();
		}
	}
	
	//Order
	public void reorderRoles() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fSecurity, roles);}
}