package org.jeesl.interfaces.model.module.attribute;

import java.io.Serializable;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.with.code.EjbWithNonUniqueCode;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPosition;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionParent;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslAttributeOption<L extends UtilsLang, D extends UtilsDescription,CRITERIA extends JeeslAttributeCriteria<L,D,?,?>>
			extends Serializable,EjbWithId,EjbSaveable,EjbRemoveable,
					EjbWithNonUniqueCode,EjbWithPosition,EjbWithPositionParent,
					EjbWithLang<L>,EjbWithDescription<D>
{
	public enum Attributes{criteria}
	
	CRITERIA getCriteria();
	void setCriteria(CRITERIA criteria);
}