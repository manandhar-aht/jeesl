package net.sf.ahtutils.interfaces.model.finance;

import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public interface UtilsValueCurrency<C extends UtilsCurrency<L>, L extends UtilsLang> extends UtilsFinance
{
	C getCurrency();
	void setCurrency(C currency);
}