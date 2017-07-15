package org.jeesl.factory.css;

import java.util.List;

import org.jeesl.interfaces.model.system.symbol.JeeslGraphic;
import org.jeesl.interfaces.model.system.symbol.JeeslGraphicFigure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class CssColorFactory<L extends UtilsLang, D extends UtilsDescription,
								G extends JeeslGraphic<L,D,G,GT,F,FS>, GT extends UtilsStatus<GT,L,D>,
								F extends JeeslGraphicFigure<L,D,G,GT,F,FS>, FS extends UtilsStatus<FS,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(CssColorFactory.class);
    
	public static String colorGrey = "#F8F8FF";
	
	public CssColorFactory()
	{  

	}
	    
	public String build(F figure)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("#").append(figure.getColor());
		return sb.toString();
	}
	
	public String firstCss(G graphic)
	{
		return css(0,graphic.getFigures(),"");
	}
	
	public String css(int index, G graphic, String fallback)
	{
		return css(index,graphic.getFigures(),fallback);
	}
	
	private String css(int index, List<F> figures, String fallback)
	{
		if(figures.size()>index)
		{
			return build(figures.get(index));
		}
		return fallback;
	}
}