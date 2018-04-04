package org.jeesl.interfaces.controller.report;

import org.jeesl.interfaces.model.system.io.report.JeeslIoReport;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public interface JeeslReportSelectorTransformation <L extends UtilsLang, D extends UtilsDescription,
														REPORT extends JeeslIoReport<L,D,?,?>,
														TRANSFORMATION extends UtilsStatus<TRANSFORMATION,L,D>
														>
				extends JeeslReport<REPORT>
{	
	TRANSFORMATION getReportSettingTransformation();
	void setReportSettingTransformation(TRANSFORMATION transformation);
}