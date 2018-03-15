package org.jeesl.interfaces.model.system.io.report;

import java.util.List;

import org.jeesl.interfaces.model.system.with.code.EjbWithCode;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionParent;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionVisible;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslReportSheet<L extends UtilsLang,D extends UtilsDescription,
									IMPLEMENTATION extends UtilsStatus<IMPLEMENTATION,L,D>,
									WORKBOOK extends JeeslReportWorkbook<?,?>,
									GROUP extends JeeslReportColumnGroup<L,D,?,?,?>,
									ROW extends JeeslReportRow<L,D,?,?,IMPLEMENTATION,WORKBOOK,?,GROUP,?,ROW,?,?,?,?,?,?,?,?,?,?>>
		extends EjbWithId,EjbSaveable,
				EjbWithCode,EjbWithPositionVisible,EjbWithPositionParent,
				EjbWithLang<L>,EjbWithDescription<D>
{	
	public enum Attributes{workbook,code}
	public enum Offset{rows,columns} 
	
	WORKBOOK getWorkbook();
	void setWorkbook(WORKBOOK workbook);
	
	IMPLEMENTATION getImplementation();
	void setImplementation(IMPLEMENTATION implementation);
	
	String getQueryTable();
	void setQueryTable(String queryTable);
	
	List<ROW> getRows();
	void setRows(List<ROW> rows);
	
	List<GROUP> getGroups();
	void setGroups(List<GROUP> groups);
}