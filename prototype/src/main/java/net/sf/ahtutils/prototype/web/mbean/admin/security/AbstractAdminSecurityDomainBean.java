package net.sf.ahtutils.prototype.web.mbean.admin.security;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.factory.ejb.security.EjbStaffFactory;
import net.sf.ahtutils.interfaces.facade.UtilsSecurityFacade;
import net.sf.ahtutils.interfaces.facade.UtilsUserFacade;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityAction;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityCategory;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityRole;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityUsecase;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityView;
import net.sf.ahtutils.interfaces.model.security.UtilsStaff;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.idm.UtilsUser;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractAdminSecurityDomainBean <L extends UtilsLang,
												D extends UtilsDescription,
												C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,
												R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,
												V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,
												U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,
												A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,
												USER extends UtilsUser<L,D,C,R,V,U,A,USER>,
												STAFF extends UtilsStaff<L,D,C,R,V,U,A,USER,DOMAIN>,
												DOMAIN extends EjbWithId>
		implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminSecurityDomainBean.class);

	protected UtilsSecurityFacade<L,D,C,R,V,U,A,USER> fSecurity;
	protected UtilsUserFacade<L,D,C,R,V,U,A,USER> fUser;
	
	protected Class<R> cRole;
	protected Class<USER> cUser;
	protected Class<STAFF> cStaff;
	
	protected EjbStaffFactory<L,D,C,R,V,U,A,USER,STAFF,DOMAIN> efStaff;
	
	protected DOMAIN domain; public DOMAIN getDomain(){return domain;} public void setDomain(DOMAIN domain){this.domain = domain;}
	protected STAFF staff; public STAFF getStaff(){return staff;} public void setStaff(STAFF staff) {this.staff = staff;}
	
	protected List<STAFF> staffs; public List<STAFF> getStaffs(){return staffs;}
	
	protected void initSuper(UtilsSecurityFacade<L,D,C,R,V,U,A,USER> fSecurity, UtilsUserFacade<L,D,C,R,V,U,A,USER> fUser, Class<R> cRole, Class<USER> cUser, Class<STAFF> cStaff)
	{
		this.fSecurity=fSecurity;
		this.fUser=fUser;
		this.cRole=cRole;
		this.cUser=cUser;
		this.cStaff=cStaff;
		
		efStaff = EjbStaffFactory.factory(cStaff);
	}
	
	public void selectDomain()
	{
		logger.info(AbstractLogMessage.selectEntity(domain));
		reloadStaffs();
		
		staff = null;
	}

	protected void reloadStaffs()
	{
		staffs = fSecurity.fStaffD(cStaff, domain);
	}
	
	public void cancel()
	{
		staff = null;
	}
	
	public void addStaff()
	{
		logger.info(AbstractLogMessage.addEntity(cStaff));
		staff = efStaff.build(null,null,domain);
	}
	
	public void selectStaff()
	{
		logger.info(AbstractLogMessage.selectEntity(staff));
	}
	
	public void save() throws UtilsLockingException
	{
		try
		{
			staff.setUser(fSecurity.find(cUser,staff.getUser()));
			staff.setRole(fSecurity.find(cRole,staff.getRole()));
			logger.info(AbstractLogMessage.saveEntity(staff));
			staff = fSecurity.save(staff);
			reloadStaffs();
		}
		catch (UtilsConstraintViolationException e) {saveThrowsConstraintViolation();}
	}
	
	public void rmStaff() throws UtilsConstraintViolationException, UtilsLockingException
	{
		logger.info(AbstractLogMessage.rmEntity(staff));
		fSecurity.rm(staff);
		staff=null;
		reloadStaffs();
	}
	
	protected void saveThrowsConstraintViolation()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("A "+UtilsConstraintViolationException.class.getSimpleName()+" was detected");
		sb.append(" Most probably by a duplicate object.");
		sb.append(" This should be handled in the implementation class");
		logger.warn(sb.toString());
	}
	
	//AutoComplete User
	public List<USER> autoComplete(String query)
	{
		List<USER> users = fUser.likeNameFirstLast(cUser,query);
		logger.info(AbstractLogMessage.autoComplete(cUser,query,users.size()));
		return users;
	}

	public void autoCompleteSelect()
	{
		staff.setUser(fUser.find(cUser,staff.getUser()));
		logger.info(AbstractLogMessage.autoCompleteSelect(staff.getUser()));
	}
}