package org.jeesl.web.mbean.prototype.user;

import java.io.Serializable;

import org.jeesl.api.facade.core.JeeslUserFacade;
import org.jeesl.api.facade.system.JeeslSecurityFacade;
import org.jeesl.factory.builder.system.StatusFactoryBuilder;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityAction;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityUsecase;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityView;
import org.jeesl.interfaces.model.system.security.user.JeeslIdentity;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.jeesl.web.mbean.prototype.admin.AbstractAdminBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public abstract class AbstractUserBean <L extends UtilsLang, D extends UtilsDescription, 
											R extends JeeslSecurityRole<L,D,?,V,U,A,USER>,
											V extends JeeslSecurityView<L,D,?,R,U,A>,
											U extends JeeslSecurityUsecase<L,D,?,R,V,A>,
											A extends JeeslSecurityAction<L,D,R,V,U,?>,
											USER extends JeeslUser<R>>
				extends AbstractAdminBean<L,D>
				implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractUserBean.class);
	
	private JeeslUserFacade<USER> fUser;
	protected JeeslSecurityFacade<L,D,?,R,V,U,A,?,USER> fSecurity;
	
	protected USER user;
	private JeeslIdentity<R,V,U,A,USER> identity2;
	
	protected String ipAddress;
	protected String localeCode;
	
	protected AbstractUserBean(StatusFactoryBuilder<L,D,?> fbStatus)
	{
		super(fbStatus.getClassL(),fbStatus.getClassD());
	}
	
	protected void postConstruct(JeeslUserFacade<USER> fUser, JeeslSecurityFacade<L,D,?,R,V,U,A,?,USER> fSecurity)
	{
		this.fUser=fUser;
		this.fSecurity=fSecurity;
	}
}