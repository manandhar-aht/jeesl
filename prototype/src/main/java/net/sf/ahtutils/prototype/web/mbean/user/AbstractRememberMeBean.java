package net.sf.ahtutils.prototype.web.mbean.user;

import java.io.Serializable;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;

import org.jeesl.api.facade.core.JeeslUserFacade;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityView;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityTemplate;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.jeesl.interfaces.model.system.security.util.JeeslRememberMe;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityAction;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityUsecase;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityCategory;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.jeesl.web.mbean.prototype.admin.AbstractAdminBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.with.EjbWithEmail;

public class AbstractRememberMeBean <L extends UtilsLang,D extends UtilsDescription,
										C extends JeeslSecurityCategory<L,D>,
										R extends JeeslSecurityRole<L,D,C,R,V,U,A,AT,USER>,
										V extends JeeslSecurityView<L,D,C,R,U,A>,
										U extends JeeslSecurityUsecase<L,D,C,R,V,A>,
										A extends JeeslSecurityAction<L,D,C,R,V,U,A,AT,USER>,
										AT extends JeeslSecurityTemplate<L,D,C,R,V,U,A,AT,USER>,
										USER extends JeeslUser<L,D,C,R,V,U,A,AT,USER>,
										REM extends JeeslRememberMe<L,D,C,R,V,U,A,AT,USER>>
		extends AbstractAdminBean<L,D>
		implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(AbstractRememberMeBean.class);
	private static final long serialVersionUID = 1L;
	
	protected boolean logOnInfo = false;
	private boolean dummy; public boolean isDummy() {return dummy;} public void setDummy(boolean dummy) {this.dummy = dummy;}
	
	public AbstractRememberMeBean(final Class<L> cL, final Class<D> cD)
	{
		super(cL,cD);
	}
	
	protected void readCookie(Class<REM> cRem, JeeslUserFacade<L,D,C,R,V,U,A,AT,USER> fUser, String cookieName)
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