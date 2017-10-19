package org.jeesl.interfaces.model.system.security.with;

import java.util.List;

import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityView;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityTemplate;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityAction;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityUsecase;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityCategory;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslSecurityWithViews<L extends UtilsLang,
										D extends UtilsDescription, 
										C extends JeeslSecurityCategory<L,D,C,R,V,U,A,AT,USER>,
										R extends JeeslSecurityRole<L,D,C,R,V,U,A,AT,USER>,
										V extends JeeslSecurityView<L,D,C,R,V,U,A,AT,USER>,
										U extends JeeslSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
										A extends JeeslSecurityAction<L,D,C,R,V,U,A,AT,USER>,
										AT extends JeeslSecurityTemplate<L,D,C,R,V,U,A,AT,USER>,
										USER extends JeeslUser<L,D,C,R,V,U,A,AT,USER>>
										extends EjbWithId
{
	public List<V> getViews();
	public void setViews(List<V> views);
}