package org.jeesl.interfaces.model.system.symbol;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.parent.EjbWithParentAttributeResolver;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionVisible;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslGraphicFigure<L extends UtilsLang, D extends UtilsDescription,
								G extends JeeslGraphic<L,D,G,GT,F,FS>, GT extends UtilsStatus<GT,L,D>,
								F extends JeeslGraphicFigure<L,D,G,GT,F,FS>, FS extends UtilsStatus<FS,L,D>>
		extends EjbWithId,EjbSaveable,EjbRemoveable,EjbWithPositionVisible,EjbWithParentAttributeResolver
{
	public enum Attributes{graphic}
	public static enum Style{circle,square}
	
	G getGraphic();
	void setGraphic(G graphic);
	
	FS getStyle();
	void setStyle(FS style);
	
	boolean isCss();
	void setCss(boolean css);
	
	double getSize();
	void setSize(double size);
	
	String getColor();
	void setColor(String color);

	double getOffsetX();
	void setOffsetX(double offsetX);
	
	double getOffsetY();
	void setOffsetY(double offsetY);
	
	double getRotation();
	void setRotation(double rotation);
}