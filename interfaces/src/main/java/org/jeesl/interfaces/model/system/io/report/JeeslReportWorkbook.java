package org.jeesl.interfaces.model.system.io.report;

import java.io.Serializable;
import java.util.List;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslReportWorkbook<REPORT extends JeeslIoReport<?,?,?,?>,
									SHEET extends JeeslReportSheet<?,?,?,?,?,?>>
		extends Serializable,EjbWithId,EjbPersistable,EjbSaveable,EjbRemoveable
{					
	REPORT getReport();
	void setReport(REPORT report);
	
	List<SHEET> getSheets();
	void setSheets(List<SHEET> sheets);
}