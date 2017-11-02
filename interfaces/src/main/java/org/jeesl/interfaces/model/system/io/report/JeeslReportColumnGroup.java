package org.jeesl.interfaces.model.system.io.report;

import java.io.Serializable;
import java.util.List;

import org.jeesl.interfaces.model.system.with.code.EjbWithCode;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionParent;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionVisible;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslReportColumnGroup<L extends UtilsLang,D extends UtilsDescription,
									SHEET extends JeeslReportSheet<L,D,?,?,?,?>,
									COLUMN extends JeeslReportColumn<L,D,?,?,?,?,SHEET,?,COLUMN,?,?,?,STYLE,?,?,?,?,?,?,?>,
									STYLE extends JeeslReportStyle<L,D>
									>
		extends Serializable,EjbRemoveable,EjbPersistable,EjbSaveable,
				EjbWithCode,EjbWithPositionVisible,EjbWithPositionParent,
				EjbWithLang<L>,EjbWithDescription<D>
{					
	SHEET getSheet();
	void setSheet(SHEET sheet);
	
	List<COLUMN> getColumns();
	void setColumns(List<COLUMN> columns);
	
	Boolean getShowLabel();
	void setShowLabel(Boolean showLabel);
	
	Boolean getShowWeb();
	void setShowWeb(Boolean showWeb);
	
	STYLE getStyleHeader();
	void setStyleHeader(STYLE styleHeader);
	
	String getQueryColumns();
	void setQueryColumns(String queryColumns);
}