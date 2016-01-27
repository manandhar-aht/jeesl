package net.sf.ahtutils.model.ejb.symbol;

import java.io.Serializable;

import javax.persistence.ManyToOne;

import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.symbol.UtilsGraphic;
import net.sf.ahtutils.model.ejb.status.AhtUtilsDescription;
import net.sf.ahtutils.model.ejb.status.AhtUtilsLang;
import net.sf.ahtutils.model.qualifier.EjbErNode;

@EjbErNode(name="Graphic",category="symbol",subset="symbol")
public class Graphic implements EjbRemoveable,Serializable,EjbPersistable,
								UtilsGraphic<AhtUtilsLang,AhtUtilsDescription,GraphicType,GraphicStyle>
{
	public static final long serialVersionUID=1;

	public static String[] defaultLangs = {"fr","en","de"};
	
	private long id;
	@Override public long getId() {return id;}
	@Override  public void setId(long id) {this.id = id;}
	
	@ManyToOne
	private GraphicType type;
	public GraphicType getType() {return type;}
	public void setType(GraphicType type) {this.type = type;}

	@ManyToOne
	private GraphicStyle style;
	public GraphicStyle getStyle() {return style;}
	public void setStyle(GraphicStyle style) {this.style = style;}
	
	private byte[] data;
    @Override public byte[] getData() {return data;}
    @Override public void setData(byte[] data) {this.data = data;}
    
    private Integer size;
	public Integer getSize() {return size;}
	public void setSize(Integer size) {this.size = size;}
	
    private Integer sizeBorder;
	public Integer getSizeBorder() {return sizeBorder;}
	public void setSizeBorder(Integer sizeBorder) {this.sizeBorder = sizeBorder;}
	
	private String color;
	public String getColor() {return color;}
	public void setColor(String color) {this.color = color;}
	
	private String colorBorder;
	public String getColorBorder() {return colorBorder;}
	public void setColorBorder(String colorBorder) {this.colorBorder = colorBorder;}
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
			sb.append(id);
		return sb.toString();
	}
}