package org.jeesl.interfaces.model.system.security.framework;

import java.io.Serializable;
import java.util.List;

import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.jeesl.interfaces.model.system.security.with.JeeslSecurityWithActions;
import org.jeesl.interfaces.model.system.security.with.JeeslSecurityWithCategory;
import org.jeesl.interfaces.model.system.security.with.JeeslSecurityWithViews;
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

public interface JeeslSecurityRole<L extends UtilsLang, D extends UtilsDescription, 
						 		   C extends JeeslSecurityCategory<L,D>,
						 		   V extends JeeslSecurityView<L,D,C,?,U,A>,
						 		   U extends JeeslSecurityUsecase<L,D,C,?,V,A>,
						 		   A extends JeeslSecurityAction<L,D,?,V,U,?>,
						 		   USER extends JeeslUser<?>>
			extends Serializable,EjbPersistable,EjbSaveable,EjbRemoveable,
					EjbWithPositionVisible,EjbWithParentAttributeResolver,
					EjbWithCode,EjbWithLang<L>,EjbWithDescription<D>,
					JeeslSecurityWithCategory<C>,
					JeeslSecurityWithViews<V>,
					JeeslSecurityWithActions<A>
					
{
	public static final String extractId = "securityRoles";
	
	public List<U> getUsecases();
	public void setUsecases(List<U> usecases);
	
	List<USER> getUsers();
	void setUsers(List<USER> users);
	
	public Boolean getDocumentation();
	public void setDocumentation(Boolean documentation);
}