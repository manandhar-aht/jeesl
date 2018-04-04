package org.jeesl.interfaces.controller.report;

import org.jeesl.interfaces.model.system.io.report.JeeslIoReport;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public interface JeeslReportSelectorFilling <L extends UtilsLang,D extends UtilsDescription,
												REPORT extends JeeslIoReport<L,D,?,?>,
												FILLING extends UtilsStatus<FILLING,L,D>
												>
			extends JeeslReport<REPORT>
{		
	void setReportSettingFilling(FILLING filling);
}