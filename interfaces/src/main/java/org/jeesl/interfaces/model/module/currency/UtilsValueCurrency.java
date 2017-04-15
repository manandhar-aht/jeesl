package org.jeesl.interfaces.model.module.currency;

import net.sf.ahtutils.interfaces.model.finance.UtilsFinance;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public interface UtilsValueCurrency<C extends UtilsCurrency<L>, L extends UtilsLang> extends UtilsFinance
{
	C getCurrency();
	void setCurrency(C currency);
}