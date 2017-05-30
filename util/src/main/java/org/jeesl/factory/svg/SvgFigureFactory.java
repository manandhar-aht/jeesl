package org.jeesl.factory.svg;

import java.util.List;

import org.apache.batik.dom.svg.SVGDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.jeesl.interfaces.model.system.symbol.JeeslGraphic;
import org.jeesl.interfaces.model.system.symbol.JeeslGraphicFigure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.DOMImplementation;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class SvgFigureFactory<L extends UtilsLang, D extends UtilsDescription,
									G extends JeeslGraphic<L,D,G,GT,F,FS>,
									GT extends UtilsStatus<GT,L,D>,
									F extends JeeslGraphicFigure<L,D,G,GT,F,FS>,
									FS extends UtilsStatus<FS,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(SvgFigureFactory.class);
		
	private DOMImplementation impl;
	
	public SvgFigureFactory()
	{
		impl = SVGDOMImplementation.getDOMImplementation();
	}
   
	public SVGGraphics2D build(List<F> list)
	{
	
	    
	    return null;
	}
}