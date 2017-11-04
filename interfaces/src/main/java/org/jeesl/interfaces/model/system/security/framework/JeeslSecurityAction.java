package org.jeesl.interfaces.model.system.security.framework;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

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

public interface JeeslSecurityAction<L extends UtilsLang, D extends UtilsDescription,
								   R extends JeeslSecurityRole<L,D,?,R,V,U,?,AT,?>,
								   V extends JeeslSecurityView<L,D,?,R,U,?>,
								   U extends JeeslSecurityUsecase<L,D,?,R,V,?>,
								   AT extends JeeslSecurityTemplate<L,D,?,R,V,U,?,AT,?>>
			extends Serializable,EjbWithCode,EjbPersistable,EjbSaveable,EjbRemoveable,
					EjbWithPositionVisible,EjbWithParentAttributeResolver,
					EjbWithLang<L>,EjbWithDescription<D>
{	
	public String toCode();
	public Map<String,L> toName();
	
	public V getView();
	public void setView(V view);
	
	public List<R> getRoles();
	public void setRoles(List<R> roles);
	
	public List<U> getUsecases();
	public void setUsecases(List<U> usecases);
	
	public Boolean getDocumentation();
	public void setDocumentation(Boolean documentation);
	
	public AT getTemplate();
	public void setTemplate(AT template);
}