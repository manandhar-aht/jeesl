package org.jeesl.factory.xml.system.symbol;

import org.jeesl.factory.xml.system.status.XmlStyleFactory;
import org.jeesl.factory.xml.system.status.XmlStylesFactory;
import org.jeesl.interfaces.model.system.symbol.JeeslGraphic;
import org.jeesl.interfaces.model.system.symbol.JeeslGraphicStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.xml.status.Styles;
import net.sf.ahtutils.xml.symbol.Symbol;

public class XmlSymbolFactory <L extends UtilsLang,D extends UtilsDescription,G extends JeeslGraphic<L,D,G,GT,GS>,GT extends UtilsStatus<GT,L,D>,GS extends UtilsStatus<GS,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(XmlSymbolFactory.class);
		
	private Symbol q;
	
	private XmlStyleFactory<GS,L,D> xfStyle;
	private XmlColorsFactory<L,D,G,GT,GS> xfColors;
	private XmlSizesFactory<L,D,G,GT,GS> xfSizes;
	
	public XmlSymbolFactory(String localeCode, Symbol q)
	{
		this.q=q;
		if(q.isSetStyles() && q.getStyles().isSetStyle()){xfStyle = new XmlStyleFactory<GS,L,D>(localeCode,q.getStyles().getStyle().get(0));}
		if(q.isSetColors()){xfColors = new XmlColorsFactory<L,D,G,GT,GS>(q.getColors());}
		if(q.isSetSizes()){xfSizes = new XmlSizesFactory<L,D,G,GT,GS>(q.getSizes());}
	}
	
	public Symbol build(G graphic)
	{
		Symbol xml = build();
		
		if(q.isSetStyles())
		{
			Styles styles = XmlStylesFactory.build();
			
			if(graphic.getStyle()!=null){styles.getStyle().add(xfStyle.build(JeeslGraphicStyle.Group.outer,graphic.getStyle()));}
			
			xml.setStyles(styles);
		}
		
		if(q.isSetColors()){xml.setColors(xfColors.build(graphic));}
		if(q.isSetStyles()){xml.setSizes(xfSizes.build(graphic));}
		
		return xml;
	}
	
	public static Symbol build()
	{
		Symbol xml = new Symbol();
		return xml;
	}
}