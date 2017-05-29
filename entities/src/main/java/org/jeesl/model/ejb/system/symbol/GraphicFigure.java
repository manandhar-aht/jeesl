package org.jeesl.model.ejb.system.symbol;

import java.io.Serializable;

import javax.persistence.ManyToOne;

import org.jeesl.interfaces.model.system.symbol.JeeslGraphicFigure;
import org.jeesl.model.ejb.system.status.Description;
import org.jeesl.model.ejb.system.status.Lang;

import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.model.qualifier.EjbErNode;

@EjbErNode(name="Figure",category="symbol",subset="symbol")
public class GraphicFigure implements EjbRemoveable,Serializable,EjbPersistable,
								JeeslGraphicFigure<Lang,Description,Graphic,GraphicType,GraphicFigure,GraphicStyle>
{
	public static final long serialVersionUID=1;


	private long id;
	@Override public long getId() {return id;}
	@Override public void setId(long id) {this.id = id;}
	
	@ManyToOne
	private GraphicStyle style;
	public GraphicStyle getStyle() {return style;}
	public void setStyle(GraphicStyle style) {this.style = style;}
	
	double size;
	
	public double getSize() {
		return size;
	}
	public void setSize(double size) {
		this.size = size;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public double getOffsetX() {
		return offsetX;
	}
	public void setOffsetX(double offsetX) {
		this.offsetX = offsetX;
	}
	public double getOffsetY() {
		return offsetY;
	}
	public void setOffsetY(double offsetY) {
		this.offsetY = offsetY;
	}
	public double getRotation() {
		return rotation;
	}
	public void setRotation(double rotation) {
		this.rotation = rotation;
	}

	String color;

	double offsetX;
	
	double offsetY;
	
	double rotation;
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
			sb.append(id);
		return sb.toString();
	}
	@Override
	public int getPosition() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void setPosition(int position) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean isVisible() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void setVisible(boolean visible) {
		// TODO Auto-generated method stub
		
	}
}