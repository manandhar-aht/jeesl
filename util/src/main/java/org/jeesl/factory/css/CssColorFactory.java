package org.jeesl.factory.css;

import java.util.List;

import org.jeesl.interfaces.model.system.symbol.JeeslGraphic;
import org.jeesl.interfaces.model.system.symbol.JeeslGraphicFigure;
import org.jeesl.interfaces.model.system.util.JeeslTrafficLight;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class CssColorFactory
{
	final static Logger logger = LoggerFactory.getLogger(CssColorFactory.class);
    
	public static String colorGrey = "#F8F8FF";
	    
	public static <L extends UtilsLang, D extends UtilsDescription, G extends JeeslGraphic<L,D,G,GT,F,FS>, GT extends UtilsStatus<GT,L,D>, F extends JeeslGraphicFigure<L,D,G,GT,F,FS>, FS extends UtilsStatus<FS,L,D>>
		String build(F figure)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("#").append(figure.getColor());
		return sb.toString();
	}
	
	public static <L extends UtilsLang, D extends UtilsDescription, G extends JeeslGraphic<L,D,G,GT,F,FS>, GT extends UtilsStatus<GT,L,D>, F extends JeeslGraphicFigure<L,D,G,GT,F,FS>, FS extends UtilsStatus<FS,L,D>>
		String firstCss(G graphic)
	{
		return css(0,graphic.getFigures(),"");
	}
	
	public static <L extends UtilsLang, D extends UtilsDescription, G extends JeeslGraphic<L,D,G,GT,F,FS>, GT extends UtilsStatus<GT,L,D>, F extends JeeslGraphicFigure<L,D,G,GT,F,FS>, FS extends UtilsStatus<FS,L,D>>
		String css(int index, G graphic, String fallback)
	{
		return css(index,graphic.getFigures(),fallback);
	}
	
	private static <L extends UtilsLang, D extends UtilsDescription, G extends JeeslGraphic<L,D,G,GT,F,FS>, GT extends UtilsStatus<GT,L,D>, F extends JeeslGraphicFigure<L,D,G,GT,F,FS>, FS extends UtilsStatus<FS,L,D>>
		String css(int index, List<F> figures, String fallback)
	{
		if(figures.size()>index)
		{
			return build(figures.get(index));
		}
		return fallback;
	}
	
	public static <L extends UtilsLang,D extends UtilsDescription, LIGHT extends JeeslTrafficLight<L,D,SCOPE>,SCOPE extends UtilsStatus<SCOPE,L,D>>
		void appendColor(StringBuilder sb, LIGHT light)
	{
		if(sb!=null && light!=null)
		{
			sb.append(" background: #").append(light.getColorBackground()).append(";");
		}
	}
}