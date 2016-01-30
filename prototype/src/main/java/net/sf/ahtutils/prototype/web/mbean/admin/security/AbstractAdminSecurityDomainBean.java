package net.sf.ahtutils.prototype.web.mbean.admin.security;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.factory.ejb.security.EjbStaffFactory;
import net.sf.ahtutils.interfaces.facade.UtilsSecurityFacade;
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
	
	protected Class<STAFF> cStaff;
	
	protected EjbStaffFactory<L,D,C,R,V,U,A,USER,STAFF,DOMAIN> efStaff;
	
	protected DOMAIN domain; public DOMAIN getDomain(){return domain;} public void setDomain(DOMAIN domain){this.domain = domain;}
	protected STAFF staff; public STAFF getStaff(){return staff;} public void setStaff(STAFF staff) {this.staff = staff;}
	
	protected List<STAFF> staffs; public List<STAFF> getStaffs(){return staffs;}
	
	protected void initSuper(UtilsSecurityFacade<L,D,C,R,V,U,A,USER> fSecurity, Class<STAFF> cStaff)
	{
		this.fSecurity=fSecurity;
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
}