package org.jeesl.web.mbean.prototype.system.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.api.facade.core.JeeslUserFacade;
import org.jeesl.api.facade.system.JeeslSecurityFacade;
import org.jeesl.factory.builder.system.SecurityFactoryBuilder;
import org.jeesl.factory.ejb.system.security.EjbSecurityUserFactory;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityView;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityTemplate;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityAction;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityUsecase;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityCategory;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityMenu;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.jeesl.web.mbean.prototype.admin.AbstractAdminBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.controller.audit.UtilsRevisionPageFlow;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.with.EjbWithPwd;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public abstract class AbstractAdminSecurityUserBean <L extends UtilsLang,
											D extends UtilsDescription,
											C extends JeeslSecurityCategory<L,D>,
											R extends JeeslSecurityRole<L,D,C,V,U,A,USER>,
											V extends JeeslSecurityView<L,D,C,R,U,A>,
											U extends JeeslSecurityUsecase<L,D,C,R,V,A>,
											A extends JeeslSecurityAction<L,D,R,V,U,AT>,
											AT extends JeeslSecurityTemplate<L,D,C>,
											M extends JeeslSecurityMenu<V,M>,
											USER extends JeeslUser<R>>
		extends AbstractAdminBean<L,D>
		implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminSecurityUserBean.class);

	protected JeeslUserFacade<USER> fUtilsUser;
	protected JeeslSecurityFacade<L,D,C,R,V,U,A,AT,M,USER> fUtilsSecurity;
	private final SecurityFactoryBuilder<L,D,C,R,V,U,A,AT,M,USER> fbSecurity;
	
	protected List<USER> users;public List<USER> getUsers() {return users;}
	protected List<R> roles; public List<R> getRoles() {return roles;}
	
	protected USER user; public USER getUser(){return user;} public void setUser(USER user){this.user = user;}
	
	protected Map<Long,Boolean> mapRoles; public Map<Long, Boolean> getMapRoles() {return mapRoles;}
	protected final EjbSecurityUserFactory<USER> efUser;
	
	protected boolean performPasswordCheck;
	protected String pwd1; public String getPwd1() {return pwd1;} public void setPwd1(String pwd1) {this.pwd1 = pwd1;}
	protected String pwd2;public String getPwd2() {return pwd2;}public void setPwd2(String pwd2){this.pwd2 = pwd2;}
	
	protected UtilsRevisionPageFlow<USER,USER> revision; public UtilsRevisionPageFlow<USER, USER> getRevision() {return revision;}
	
	public AbstractAdminSecurityUserBean(SecurityFactoryBuilder<L,D,C,R,V,U,A,AT,M,USER> fbSecurity)
	{
		super(fbSecurity.getClassL(),fbSecurity.getClassD());
		this.fbSecurity=fbSecurity;
		efUser = fbSecurity.ejbUser();
	}
	
	public void initSuper(JeeslUserFacade<USER> fUtilsUser, JeeslSecurityFacade<L,D,C,R,V,U,A,AT,M,USER> fUtilsSecurity, JeeslFacesMessageBean bMessage)
	{
		super.initAdmin(langs, cL, cD, bMessage);
		this.fUtilsUser=fUtilsUser;
		this.fUtilsSecurity=fUtilsSecurity;
		
		mapRoles = new Hashtable<Long,Boolean>();
		roles = new ArrayList<R>();
		
		performPasswordCheck = true;
	}
	
	public void cancelUser() {reset(true);}
	protected void reset(boolean rUser)
	{
		if(rUser) {user=null;}
	}
	
	protected void reloadUsers()
	{
		users = fUtilsUser.all(fbSecurity.getClassUser());
	}

	public void addUser() throws UtilsNotFoundException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.addEntity(fbSecurity.getClassUser()));}
		user = efUser.build();
		if(revision!=null){revision.pageFlowPrimaryAdd();}
		postAdd();
	}
	protected abstract void postAdd() throws UtilsNotFoundException;
	
	public void selectUser()
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.selectEntity(user));}
		if(revision!=null){revision.pageFlowPrimarySelect(user);}
		reloadUser();
	}
	
	protected void reloadUser()
	{		
		user = fUtilsUser.load(user);
		mapRoles.clear();
		if(debugOnInfo){logger.info("Settings roles: "+user.getRoles().size());}
		for(R r : user.getRoles()){mapRoles.put(r.getId(), true);}
	}
	
	public void saveUser() throws UtilsLockingException
	{
		try
		{
			if(debugOnInfo){logger.info(AbstractLogMessage.saveEntity(user));}
			checkPwd();
			preSave();
			user = fUtilsUser.saveTransaction(user);
			reloadUser();
			bMessage.growlSuccessSaved();
			if(revision!=null){revision.pageFlowPrimarySave(user);}
			userChangePerformed();
		}
		catch (UtilsConstraintViolationException e) {constraintViolationOnSave();}
	}
	protected void preSave() {}
	
	public void rm(USER myUser){this.user=myUser;deleteUser();}
	public void deleteUser()
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.rmEntity(user));}
		try
		{
			fUtilsUser.rm(user);
			user = null;
			bMessage.growlSuccessRemoved();
			if(revision!=null){revision.pageFlowPrimaryCancel();}
			userChangePerformed();
		}
		catch (UtilsConstraintViolationException e){constraintViolationOnRemove();}
	}
	
	protected void userChangePerformed() {}
	
	protected void checkPwd()
	{
		if(performPasswordCheck && EjbWithPwd.class.isAssignableFrom(fbSecurity.getClassUser()))
		{
			if(debugOnInfo) {logger.info("Checking PWD");}
			if(pwd1.length()!=pwd2.length())
			{
				bMessage.growlError("fmPwdDidNotMatch");
				return;
			}
	
			if(pwd1.length()>0 && pwd2.length()>0)
			{
				if(pwd1.equals(pwd2))
				{
					bMessage.growlSuccess("fmPwdChanged");
					
					EjbWithPwd ejb = (EjbWithPwd)user;
					ejb.setPwd(pwd1);
				}
				else
				{
					bMessage.growlError("fmPwdDidNotMatch");
					return;
				}
			}
		}
		else {logger.warn("Password Checking and updating deactivated");}
	}
	
	
	protected void constraintViolationOnSave() {logger.warn("constraintViolationOnSave, this should be @Overriden");}
	protected void constraintViolationOnRemove() {}
	protected void passwordsDoNotMatch() {}

	/*
	public void addRole(R role) throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(debugOnInfo){logger.info("Add Role");}
		fUtilsSecurity.grantRole(cUser,cRole,user,role,true);
		reloadUser();
	}

	public void rmRole(R role) throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(debugOnInfo){logger.info("Remove Role");}
		fUtilsSecurity.grantRole(cUser,cRole,user,role,false);
		reloadUser();
	}
	*/
	
	public void grantRole(R role, boolean grant) throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(debugOnInfo){logger.info("Grant ("+grant+") "+role.toString());}
		fUtilsSecurity.grantRole(fbSecurity.getClassUser(),fbSecurity.getClassRole(),user,role,grant);
		reloadUser();
	}
}