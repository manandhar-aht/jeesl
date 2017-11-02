package org.jeesl.interfaces.model.system.io.report;

import java.io.Serializable;

import org.jeesl.interfaces.model.system.with.code.EjbWithCode;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionVisible;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslReportStyle<L extends UtilsLang,D extends UtilsDescription>
		extends Serializable,EjbPersistable,EjbSaveable,EjbRemoveable,
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