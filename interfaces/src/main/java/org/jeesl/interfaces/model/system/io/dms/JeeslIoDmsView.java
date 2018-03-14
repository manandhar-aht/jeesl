package org.jeesl.interfaces.model.system.io.dms;

import java.io.Serializable;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionVisibleParent;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslIoDmsView<L extends UtilsLang,
								DMS extends JeeslIoDms<L,?,?,?,?>>
					extends Serializable,EjbWithId,EjbRemoveable,EjbPersistable,EjbSaveable,
							EjbWithPositionVisibleParent,EjbWithLang<L>
							
{	
	public enum Attributes{dms}
	
	DMS getDms();
	void setDms(DMS dms);
}