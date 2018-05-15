package org.jeesl.interfaces.model.system.io.domain;

import java.io.Serializable;

import org.jeesl.interfaces.model.system.with.code.EjbWithCode;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPosition;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionParent;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslDomainSet <L extends UtilsLang, D extends UtilsDescription,
									ITEM extends JeeslDomainItem<?>>
		extends Serializable,EjbSaveable,EjbRemoveable,
				EjbWithPosition,EjbWithPositionParent,EjbWithCode,
				EjbWithLang<L>,EjbWithDescription<D>
{
	public enum Attributes{category,position}


}