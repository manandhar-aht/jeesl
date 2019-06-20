package org.jeesl.interfaces.model.util.finance;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface UtilsFinance extends EjbWithId
{
	double getValue();
	void setValue(double value);
}