package org.jeesl.interfaces.factory.txt;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslReportAggregationNameFactory
{
	String buildTreeLevelName(String localeCode, EjbWithId ejb);
}