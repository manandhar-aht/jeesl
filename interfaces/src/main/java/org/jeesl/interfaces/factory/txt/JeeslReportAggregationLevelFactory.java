package org.jeesl.interfaces.factory.txt;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslReportAggregationLevelFactory
{
	String buildTreeLevelName(String localeCode, EjbWithId ejb);
	String build(EjbWithId ejb);
}