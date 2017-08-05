package org.jeesl.interfaces.model.system.lang;

import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslLocale<L extends UtilsLang>
						extends EjbWithId, EjbWithLang<L>
{
	String getLocaleCode();
	void setLocaleCode(String localeCode);
}