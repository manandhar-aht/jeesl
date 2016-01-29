package net.sf.ahtutils.interfaces.model.graphic;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface UtilsGraphic<L extends UtilsLang,
								D extends UtilsDescription,
								GT extends UtilsStatus<GT,L,D>,
								GS extends UtilsStatus<GS,L,D>>
		extends EjbWithId
{					
	GT getType();
	void setType(GT type);
	
	GS getStyle();
	void setStyle(GS style);
	
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
}