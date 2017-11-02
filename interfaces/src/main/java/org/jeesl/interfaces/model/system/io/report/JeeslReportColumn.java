package org.jeesl.interfaces.model.system.io.report;

import java.io.Serializable;

import org.jeesl.interfaces.model.system.with.code.EjbWithCode;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionParent;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionVisible;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslReportColumn<L extends UtilsLang,D extends UtilsDescription,
									GROUP extends JeeslReportColumnGroup<L,D,?,?,STYLE>,
									STYLE extends JeeslReportStyle<L,D>,
									CDT extends UtilsStatus<CDT,L,D>,
									CW extends UtilsStatus<CW,L,D>,
									TLS extends UtilsStatus<TLS,L,D>>

		extends Serializable,EjbRemoveable,EjbPersistable,EjbSaveable,
				EjbWithCode,EjbWithPositionVisible,EjbWithPositionParent,
				EjbWithLang<L>,EjbWithDescription<D>
{					
	GROUP getGroup();
	void setGroup(GROUP group);
	
	Boolean getShowLabel();
	void setShowLabel(Boolean showLabel);
	
	Boolean getShowWeb();
	void setShowWeb(Boolean showWeb);
	
	Boolean getShowHint();
	void setShowHint(Boolean showHint);
	
	CDT getDataType();
	void setDataType(CDT dataType);
	
	String getQueryHeader();
	void setQueryHeader(String queryHeader);
	
	String getQueryCell();
	void setQueryCell(String queryCell);
	
	String getQueryFooter();
	void setQueryFooter(String queryFooter);

	String getQueryTrafficLight();
	void setQueryTrafficLight(String queryTrafficLight);
	
	CW getColumWidth();
	void setColumWidth(CW columWidth);
	
	Integer getColumSize();
	void setColumSize(Integer columSize);
	
	STYLE getStyleHeader();
	void setStyleHeader(STYLE styleHeader);
	
	STYLE getStyleCell();
	void setStyleCell(STYLE styleCell);
	
	STYLE getStyleFooter();
	void setStyleFooter(STYLE styleFooter);
	
	TLS getTrafficLightScope();
	void setTrafficLightScope(TLS trafficLightScope);
}