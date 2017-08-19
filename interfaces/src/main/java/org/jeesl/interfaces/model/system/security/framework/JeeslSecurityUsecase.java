package org.jeesl.interfaces.model.system.security.framework;

import java.util.List;

import org.jeesl.interfaces.model.system.security.user.UtilsUser;
import org.jeesl.interfaces.model.system.security.with.JeeslSecurityWithActions;
import org.jeesl.interfaces.model.system.security.with.JeeslSecurityWithCategory;
import org.jeesl.interfaces.model.system.security.with.JeeslSecurityWithViews;
import org.jeesl.interfaces.model.system.with.code.EjbWithCode;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.with.parent.EjbWithParentAttributeResolver;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionVisible;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslSecurityUsecase<L extends UtilsLang,
 									  D extends UtilsDescription, 
 									  C extends JeeslSecurityCategory<L,D,C,R,V,U,A,AT,USER>,
 									  R extends JeeslSecurityRole<L,D,C,R,V,U,A,AT,USER>,
 									  V extends JeeslSecurityView<L,D,C,R,V,U,A,AT,USER>,
 									  U extends JeeslSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
 									  A extends JeeslSecurityAction<L,D,C,R,V,U,A,AT,USER>,
 									 AT extends JeeslSecurityTemplate<L,D,C,R,V,U,A,AT,USER>,
 									  USER extends UtilsUser<L,D,C,R,V,U,A,AT,USER>>
			extends EjbWithCode,EjbSaveable,EjbWithPositionVisible,EjbWithParentAttributeResolver,
					EjbWithLang<L>,EjbWithDescription<D>,
					JeeslSecurityWithCategory<L,D,C,R,V,U,A,AT,USER>,
					JeeslSecurityWithViews<L,D,C,R,V,U,A,AT,USER>,
					JeeslSecurityWithActions<L,D,C,R,V,U,A,AT,USER>
//					UtilsSecurityWithActionTemplates<L,D,C,R,V,U,A,AT,USER>
{
	public static final String extractId = "securityUsecases";
	
	public List<R> getRoles();
	public void setRoles(List<R> roles);
	
	public Boolean getDocumentation();
	public void setDocumentation(Boolean documentation);
}