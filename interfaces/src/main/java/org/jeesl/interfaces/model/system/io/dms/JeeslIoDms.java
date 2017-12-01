package org.jeesl.interfaces.model.system.io.dms;

import java.io.Serializable;

import org.jeesl.interfaces.model.module.attribute.JeeslAttributeSet;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslIoDms<L extends UtilsLang,D extends UtilsDescription,
							OS extends JeeslAttributeSet<L,D,?,?>
							//,S extends JeeslIoDmsSection<L,S
							>
								
		extends Serializable,EjbWithId,
				EjbSaveable,EjbRemoveable
{	
	public enum Attributes{xx}
	
	OS getSet();
	void setSet(OS set);
}