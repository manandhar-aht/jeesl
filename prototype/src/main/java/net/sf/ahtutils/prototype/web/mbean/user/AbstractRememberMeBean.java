package net.sf.ahtutils.prototype.web.mbean.user;

import java.io.Serializable;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;

import org.jeesl.web.mbean.prototype.user.admin.AbstractAdminBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.facade.UtilsUserFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.system.security.UtilsRememberMe;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityAction;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityActionTemplate;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityCategory;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityRole;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityUsecase;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityView;
import net.sf.ahtutils.interfaces.model.system.security.UtilsUser;
import net.sf.ahtutils.interfaces.model.with.EjbWithEmail;

public class AbstractRememberMeBean <L extends UtilsLang,D extends UtilsDescription,
										C extends UtilsSecurityCategory<L,D,C,R,V,U,A,AT,USER>,
										R extends UtilsSecurityRole<L,D,C,R,V,U,A,AT,USER>,
										V extends UtilsSecurityView<L,D,C,R,V,U,A,AT,USER>,
										U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
										A extends UtilsSecurityAction<L,D,C,R,V,U,A,AT,USER>,
										AT extends UtilsSecurityActionTemplate<L,D,C,R,V,U,A,AT,USER>,
										USER extends UtilsUser<L,D,C,R,V,U,A,AT,USER>,
										REM extends UtilsRememberMe<L,D,C,R,V,U,A,AT,USER>>
		extends AbstractAdminBean<L,D>
		implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(AbstractRememberMeBean.class);
	private static final long serialVersionUID = 1L;
	
	protected boolean logOnInfo = false;
	private boolean dummy; public boolean isDummy() {return dummy;} public void setDummy(boolean dummy) {this.dummy = dummy;}
	
	protected void readCookie(Class<REM> cRem, UtilsUserFacade<L,D,C,R,V,U,A,AT,USER> fUser, String cookieName)
	{
		Map<String,Object> cookies = FacesContext.getCurrentInstance().getExternalContext().getRequestCookieMap();
		if(cookies.containsKey(cookieName))
		{
			try
			{
				if(logOnInfo){logger.info("Cookie found");}
				Cookie cookie = (Cookie) cookies.get(cookieName);
				REM rem = fUser.fByCode(cRem, cookie.getValue());
				if(logOnInfo){logger.info("REM found");}
				if(rem.getUser() instanceof EjbWithEmail)
				{
					if(logOnInfo){logger.info("ASSIGN");}
					EjbWithEmail email = (EjbWithEmail)rem.getUser();
					preSelect(email.getEmail());
				}
				else{if(logOnInfo){logger.info("NOT");}}
			}
			catch (UtilsNotFoundException e){if(logOnInfo){logger.info(e.getMessage());}}
		}
		else{if(logOnInfo){logger.info("Cookie NOT found");}}
	}
	
	protected void preSelect(String userName){}
}