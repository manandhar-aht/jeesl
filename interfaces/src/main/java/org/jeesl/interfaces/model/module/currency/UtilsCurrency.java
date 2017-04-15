package org.jeesl.interfaces.model.module.currency;

import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.with.code.EjbWithCode;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface UtilsCurrency<L extends UtilsLang>
			extends EjbWithId,EjbWithCode,EjbWithLang<L>
{
	public String getSymbol();
	public void setSymbol(String code);
}