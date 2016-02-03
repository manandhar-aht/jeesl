package net.sf.ahtutils.prototype.web.mbean.admin.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.factory.ejb.security.EjbSecurityUserFactory;
import net.sf.ahtutils.interfaces.bean.FacesMessageBean;
import net.sf.ahtutils.interfaces.controller.audit.UtilsRevisionPageFlow;
import net.sf.ahtutils.interfaces.facade.UtilsSecurityFacade;
import net.sf.ahtutils.interfaces.facade.UtilsUserFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityAction;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityCategory;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityRole;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityUsecase;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityView;
import net.sf.ahtutils.interfaces.model.system.security.UtilsUser;
import net.sf.ahtutils.interfaces.model.with.EjbWithPwd;
import net.sf.ahtutils.prototype.web.mbean.admin.AbstractAdminBean;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractAdminSecurityUserBean <L extends UtilsLang,
											D extends UtilsDescription,
											C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,
											R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,
											V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,
											U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,
											A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,
											USER extends UtilsUser<L,D,C,R,V,U,A,USER>>
		extends AbstractAdminBean<L,D>
		implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminSecurityUserBean.class);

	protected UtilsUserFacade<L,D,C,R,V,U,A,USER> fUtilsUser;
	protected UtilsSecurityFacade<L,D,C,R,V,U,A,USER> fUtilsSecurity;
	
	protected FacesMessageBean bUtilsMessage;
	
	private Class<R> cRole;
	private Class<USER> cUser;
	
	protected List<USER> users;public List<USER> getUsers() {return users;}
	private List<USER> fvUsers; public List<USER> getFvUsers() {return fvUsers;} public void setFvUsers(List<USER> fvUsers) {this.fvUsers = fvUsers;}
	protected List<R> roles; public List<R> getRoles() {return roles;}
	
	protected USER user; public USER getUser(){return user;} public void setUser(USER user){this.user = user;}
	
	protected Map<Long,Boolean> mapRoles; public Map<Long, Boolean> getMapRoles() {return mapRoles;}
	protected EjbSecurityUserFactory<L,D,C,R,V,U,A,USER> efUser;
	
	protected String pwd1; public String getPwd1() {return pwd1;} public void setPwd1(String pwd1) {this.pwd1 = pwd1;}
	protected String pwd2;public String getPwd2() {return pwd2;}public void setPwd2(String pwd2){this.pwd2 = pwd2;}
	
	protected UtilsRevisionPageFlow<USER,USER> revision; public UtilsRevisionPageFlow<USER, USER> getRevision() {return revision;}
	
	public void initSuper(UtilsUserFacade<L,D,C,R,V,U,A,USER> fUtilsUser, UtilsSecurityFacade<L,D,C,R,V,U,A,USER> fUtilsSecurity, FacesMessageBean bUtilsMessage, final Class<R> cRole, final Class<USER> cUser)
	{
		this.fUtilsUser=fUtilsUser;
		this.fUtilsSecurity=fUtilsSecurity;
		this.bUtilsMessage=bUtilsMessage;
		
		this.cRole=cRole;
		this.cUser=cUser;
		
		efUser = EjbSecurityUserFactory.factory(cUser);
		mapRoles = new Hashtable<Long,Boolean>();
		roles = new ArrayList<R>();
	}
	
	protected void reloadUsers()
	{
		users = fUtilsUser.all(cUser);
	}

	public void addUser()
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.addEntity(cUser));}
		user = efUser.build();
		if(revision!=null){revision.pageFlowPrimaryAdd();}
	}
	
	public void selectUser()
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.selectEntity(user));}
		if(revision!=null){revision.pageFlowPrimarySelect(user);}
		reloadUser();
	}
	
	protected void reloadUser()
	{		
		user = fUtilsUser.load(cUser,user);
		mapRoles.clear();
		if(debugOnInfo){logger.info("Settings roles: "+user.getRoles().size());}
		for(R r : user.getRoles()){mapRoles.put(r.getId(), true);}
	}
	
	public void cancelUser()
	{
		user = null;
	}
	
	public void saveUser() throws UtilsLockingException
	{
		try
		{
			if(debugOnInfo){logger.info(AbstractLogMessage.saveEntity(user));}
			checkPwd();
			user = fUtilsUser.saveTransaction(user);
			reloadUser();
			bUtilsMessage.growlSuccessSaved();
			if(revision!=null){revision.pageFlowPrimarySave(user);}
			userChangePerformed();
		}
		catch (UtilsConstraintViolationException e) {constraintViolationOnSave();}
	}
	
	public void rm(USER myUser)
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.rmEntity(myUser));}
		try
		{
			fUtilsUser.rm(myUser);
			user = null;
			bUtilsMessage.growlSuccessRemoved();
			if(revision!=null){revision.pageFlowPrimaryCancel();}
			userChangePerformed();
		}
		catch (UtilsConstraintViolationException e){constraintViolationOnRemove();}
	}
	
	protected void checkPwd()
	{
		if(EjbWithPwd.class.isAssignableFrom(cUser))
		{
			logger.info("Checking PWD");
			if(pwd1.length()!=pwd2.length())
			{
				bUtilsMessage.growlError("fmPwdDidNotMatch");
				return;
			}
	
			if(pwd1.length()>0 && pwd2.length()>0)
			{
				if(pwd1.equals(pwd2))
				{
					bUtilsMessage.growlSuccess("fmPwdChanged");
					
					EjbWithPwd ejb = (EjbWithPwd)user;
					ejb.setPwd(pwd1);
				}
				else
				{
					bUtilsMessage.growlError("fmPwdDidNotMatch");
					return;
				}
			}
		}
	}
	
	protected void userChangePerformed() {}
	protected void constraintViolationOnSave() {}
	protected void constraintViolationOnRemove() {}

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
		fUtilsSecurity.grantRole(cUser,cRole,user,role,grant);
		reloadUser();
	}
}