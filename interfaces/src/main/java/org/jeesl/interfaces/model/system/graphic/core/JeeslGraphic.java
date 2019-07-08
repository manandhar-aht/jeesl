package org.jeesl.interfaces.model.system.graphic.core;

import java.io.Serializable;
import java.util.List;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslGraphic<L extends UtilsLang, D extends UtilsDescription,
								GT extends UtilsStatus<GT,L,D>,
								F extends JeeslGraphicFigure<L,D,?,GT,F,FS>, FS extends UtilsStatus<FS,L,D>>
		extends Serializable,EjbWithId,EjbSaveable
{		
	Long getVersionLock();
	
	GT getType();
	void setType(GT type);
	
	FS getStyle();
	void setStyle(FS style);
	
	byte[] getData();
	void setData(byte[] data);
	
	Integer getSize();
	void setSize(Integer size);
	
	Integer getSizeBorder();
	void setSizeBorder(Integer size);
	
	String getColor();
	void setColor(String color);
	
	String getColorBorder();
	void setColorBorder(String color);
	
	List<F> getFigures();
	void setFigures(List<F> figures);
}