package org.jeesl.interfaces.controller.report;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public interface JeeslReportSelectorTransformation <L extends UtilsLang, D extends UtilsDescription,
														TRANSFORMATION extends UtilsStatus<TRANSFORMATION,L,D>
														>
				extends JeeslReport
{	
	TRANSFORMATION getReportSettingTransformation();
	void setReportSettingTransformation(TRANSFORMATION transformation);
}