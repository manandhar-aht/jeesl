package org.jeesl.interfaces.model.system.io.cms;

import java.io.Serializable;
import java.util.List;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.with.code.EjbWithNonUniqueCode;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionVisibleParent;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslIoCmsSection<L extends UtilsLang, S extends JeeslIoCmsSection<L,S>>
		extends Serializable,EjbWithId,
					EjbRemoveable,EjbPersistable,EjbSaveable,
					EjbWithNonUniqueCode,
					EjbWithPositionVisibleParent,EjbWithLang<L>
{	
	public enum Attributes{section}
	
	S getSection();
	void setSection(S section);
	
	List<S> getSections();
	void setSections(List<S> columns);
	
	
}