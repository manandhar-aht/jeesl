package net.sf.ahtutils.interfaces.model.with;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface EjbWithLangDescription<L extends UtilsLang, D extends UtilsDescription>
					extends EjbWithId, EjbWithLang<L>, EjbWithDescription<D>
{

}