package org.jeesl.interfaces.model.system.security.framework;

import java.io.Serializable;
import java.util.List;

import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.jeesl.interfaces.model.system.security.with.JeeslSecurityWithActions;
import org.jeesl.interfaces.model.system.security.with.JeeslSecurityWithCategory;
import org.jeesl.interfaces.model.system.with.code.EjbWithCode;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.with.parent.EjbWithParentAttributeResolver;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionVisible;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslSecurityView<L extends UtilsLang,
								   D extends UtilsDescription,
								   C extends JeeslSecurityCategory<L,D>,
								   R extends JeeslSecurityRole<L,D,C,R,?,U,A,?,?>,
								   U extends JeeslSecurityUsecase<L,D,C,R,?,U,A,?,?>,
								   A extends JeeslSecurityAction<L,D,C,R,?,U,A,?,?>>
			extends Serializable,EjbPersistable,EjbSaveable,EjbRemoveable,
					EjbWithCode,EjbWithPositionVisible,EjbWithParentAttributeResolver,
					EjbWithLang<L>,EjbWithDescription<D>,
					JeeslSecurityWithCategory<C>,
					JeeslSecurityWithActions<A>
{
	public static final String extractId = "securityViews";
	
	public Boolean getAccessPublic();
	public void setAccessPublic(Boolean accessPublic);
	
	public Boolean getAccessLogin();
	public void setAccessLogin(Boolean accessLogin);
	
	public String getPackageName();
	public void setPackageName(String packageName);
	
	public String getViewPattern();
	public void setViewPattern(String viewPattern);
	
	public String getUrlMapping();
	public void setUrlMapping(String urlMapping);
	
	public String getUrlBase();
	public void setUrlBase(String urlBase);
	
	public List<R> getRoles();
	public void setRoles(List<R> roles);
	
	public List<U> getUsecases();
	public void setUsecases(List<U> usecases);
	
	public Boolean getDocumentation();
	public void setDocumentation(Boolean documentation);
}