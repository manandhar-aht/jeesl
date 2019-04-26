package org.jeesl.interfaces.model.system.security.framework;

import java.io.Serializable;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.with.code.EjbWithNonUniqueCode;
import net.sf.ahtutils.interfaces.model.with.parent.EjbWithParentAttributeResolver;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPosition;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslSecurityArea<L extends UtilsLang, D extends UtilsDescription,	   
								   V extends JeeslSecurityView<L,D,?,?,?,?>>
			extends Serializable,EjbWithNonUniqueCode,EjbPersistable,EjbSaveable,EjbRemoveable,
					EjbWithPosition,EjbWithParentAttributeResolver,
					EjbWithLang<L>,EjbWithDescription<D>
{	
	public enum Attributes{view}
	
	public V getView();
	public void setView(V view);
	
//	public boolean isRestricted();
//	void setRestricted(boolean restricted);
}