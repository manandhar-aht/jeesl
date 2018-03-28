package org.jeesl.interfaces.controller.report;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public interface JeeslReportSelectorFilling <L extends UtilsLang,D extends UtilsDescription,
												FILLING extends UtilsStatus<FILLING,L,D>
												>
			extends JeeslReport
{		
	void setReportSettingFilling(FILLING filling);
}