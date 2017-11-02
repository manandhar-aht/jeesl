package org.jeesl.interfaces.model.system.io.report;

import org.jeesl.interfaces.model.system.util.JeeslTrafficLight;
import org.jeesl.interfaces.model.system.with.code.EjbWithCode;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionVisible;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslReportStyle<L extends UtilsLang,D extends UtilsDescription,
								CATEGORY extends UtilsStatus<CATEGORY,L,D>,
								REPORT extends JeeslIoReport<L,D,CATEGORY,WORKBOOK>,
								IMPLEMENTATION extends UtilsStatus<IMPLEMENTATION,L,D>,
								WORKBOOK extends JeeslReportWorkbook<REPORT,SHEET>,
								SHEET extends JeeslReportSheet<L,D,IMPLEMENTATION,WORKBOOK,GROUP,ROW>,
								GROUP extends JeeslReportColumnGroup<L,D,SHEET,COLUMN,STYLE>,
								COLUMN extends JeeslReportColumn<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS>,
								ROW extends JeeslReportRow<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS>,
								TEMPLATE extends JeeslReportTemplate<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS>,
								CELL extends JeeslReportCell<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS>,
								STYLE extends JeeslReportStyle<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS>,
								CDT extends UtilsStatus<CDT,L,D>,
								CW extends UtilsStatus<CW,L,D>,
								RT extends UtilsStatus<RT,L,D>,
								ENTITY extends EjbWithId,
								ATTRIBUTE extends EjbWithId,
								TL extends JeeslTrafficLight<L,D,TLS>,
								TLS extends UtilsStatus<TLS,L,D>>
		extends EjbWithId,EjbSaveable,EjbRemoveable,
				EjbWithCode,EjbWithPositionVisible,
				EjbWithLang<L>,EjbWithDescription<D>
{		
	String getFont();
	void setFont(String font);
	
	boolean isFontBold();
	void setFontBold(boolean fontBold);
	
	boolean isFontItalic();
	void setFontItalic(boolean fontItalic);
	
	int getSizeFont();
	void setSizeFont(int sizeFont);
		
		
	boolean isBorderTop();
	void setBorderTop(boolean borderTop);
	
	boolean isBorderLeft();
	void setBorderLeft(boolean borderLeft);
	
	boolean isBorderRight();
	void setBorderRight(boolean borderRight);
	
	boolean isBorderBottom();
	void setBorderBottom(boolean borderBottom);
	
	
	String getColorBackground();
	void setColorBackground(String colorBackground);
	
	String getColorFont();
	void setColorFont(String colorFont);
	
	String getColorBorderTop();
	void setColorBorderTop(String colorBorderTop);
	
	String getColorBorderLeft();
	void setColorBorderLeft(String colorBorderLeft);
	
	String getColorBorderRight();
	void setColorBorderRight(String colorBorderRight);
	
	String getColorBorderBottom();
	void setColorBorderBottom(String colorBorderBottom);
	
	
	int getSizeBorderTop();
	void setSizeBorderTop(int sizeBorderTop);
	
	int getSizeBorderLeft();
	void setSizeBorderLeft(int sizeBorderLeft);
	
	int getSizeBorderRight();
	void setSizeBorderRight(int sizeBorderRight);
	
	int getSizeBorderBottom();
	void setSizeBorderBottom(int sizeBorderBottom);
	
}