package net.sf.ahtutils.interfaces.model.system.security;

import java.util.List;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.with.EjbWithParent;
import net.sf.ahtutils.interfaces.model.with.code.EjbWithCode;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionVisible;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface UtilsSecurityView<L extends UtilsLang,
								   D extends UtilsDescription,
								   C extends UtilsSecurityCategory<L,D,C,R,V,U,A,AT,USER>,
								   R extends UtilsSecurityRole<L,D,C,R,V,U,A,AT,USER>,
								   V extends UtilsSecurityView<L,D,C,R,V,U,A,AT,USER>,
								   U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
								   A extends UtilsSecurityAction<L,D,C,R,V,U,A,AT,USER>,
								   AT extends UtilsSecurityActionTemplate<L,D,C,R,V,U,A,AT,USER>,
								   USER extends UtilsUser<L,D,C,R,V,U,A,AT,USER>>
			extends EjbWithCode,EjbSaveable,EjbWithPositionVisible,EjbWithParent,
					EjbWithLang<L>,EjbWithDescription<D>,
					UtilsSecurityWithCategory<L,D,C,R,V,U,A,AT,USER>,
					UtilsSecurityWithActions<L,D,C,R,V,U,A,AT,USER>,
					UtilsSecurityWithActionTemplates<L,D,C,R,V,U,A,AT,USER>
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