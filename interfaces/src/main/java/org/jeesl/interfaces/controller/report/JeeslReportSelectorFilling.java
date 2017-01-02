package org.jeesl.interfaces.controller.report;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public interface JeeslReportSelectorFilling <FILLING extends UtilsStatus<FILLING,L,D>, L extends UtilsLang,D extends UtilsDescription> extends JeeslReport
{		
	void setReportSettingFilling(FILLING filling);
}