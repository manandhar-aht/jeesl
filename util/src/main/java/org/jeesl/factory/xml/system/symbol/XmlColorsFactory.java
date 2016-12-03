package org.jeesl.factory.xml.system.symbol;

import org.jeesl.interfaces.model.system.symbol.JeeslGraphic;
import org.jeesl.interfaces.model.system.symbol.JeeslGraphicStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.xml.symbol.Color;
import net.sf.ahtutils.xml.symbol.Colors;

public class XmlColorsFactory <L extends UtilsLang,D extends UtilsDescription,G extends JeeslGraphic<L,D,G,GT,GS>,GT extends UtilsStatus<GT,L,D>,GS extends UtilsStatus<GS,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(XmlColorsFactory.class);
		
	private Colors q;
	
	public XmlColorsFactory(Colors q)
	{
		this.q=q;
		
	}
	
	public Colors build(G graphic)
	{
		Colors xml = build();
		
		if(q.isSetColor())
		{
			xml.getColor().add(XmlColorFactory.build(JeeslGraphicStyle.Color.outer, graphic.getColor()));
		}
		
		return xml;
	}
	
	public static Colors build()
	{
		Colors xml = new Colors();
		return xml;
	}
	
	public static Colors build(Color color)
	{
		Colors xml = build();
		xml.getColor().add(color);
		return xml;
	}
}