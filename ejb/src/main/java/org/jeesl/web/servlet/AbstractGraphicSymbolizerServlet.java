package org.jeesl.web.servlet;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.transcoder.TranscoderException;
import org.jeesl.api.facade.system.graphic.JeeslGraphicFacade;
import org.jeesl.factory.svg.SvgFigureFactory;
import org.jeesl.factory.svg.SvgSymbolFactory;
import org.jeesl.interfaces.model.system.symbol.JeeslGraphic;
import org.jeesl.interfaces.model.system.symbol.JeeslGraphicFigure;
import org.jeesl.interfaces.model.system.symbol.JeeslGraphicType;
import org.openfuxml.content.media.Image;
import org.openfuxml.media.transcode.Svg2SvgTranscoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.processing.UtilsProcessingException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class AbstractGraphicSymbolizerServlet<L extends UtilsLang, D extends UtilsDescription,
												S extends EjbWithId,
												G extends JeeslGraphic<L,D,G,GT,F,FS>, GT extends UtilsStatus<GT,L,D>,
												F extends JeeslGraphicFigure<L,D,G,GT,F,FS>, FS extends UtilsStatus<FS,L,D>>
	extends AbstractSymbolizerServlet<L,D,G,GT,F,FS>
	implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractGraphicSymbolizerServlet.class);
	
	private final Class<F> cF;
	
	private final SvgSymbolFactory<L,D,G,GT,F,FS> fSvgGraphic;
	private final SvgFigureFactory<L,D,G,GT,F,FS> fSvgFigure;
	
	public AbstractGraphicSymbolizerServlet(final Class<F> cF)
	{
		this.cF=cF;
		fSvgGraphic = SvgSymbolFactory.factory();
		fSvgFigure = SvgFigureFactory.factory();
	}
	
	protected void process(HttpServletRequest request, HttpServletResponse response, G graphic, Image image) throws ServletException, IOException, TranscoderException, UtilsProcessingException
    {
		byte[] bytes = null;
    	
		String id = image.getId();
		int size = (int) image.getHeight().getValue();
		
		if(graphic==null){throw new UtilsProcessingException("graphic is null");}
		if(graphic.getType()==null){throw new UtilsProcessingException("graphic.type is null");}
    	if(graphic.getType().getCode().equals(JeeslGraphicType.Code.symbol.toString()))
		{
			logger.info("Build SVG: size " + size + " id:" + id);
	    	SVGGraphics2D g = fSvgGraphic.build(size,graphic);
	    	bytes = Svg2SvgTranscoder.transcode(g);
	    	respond(request,response,bytes,"svg");
		}
    	else if(graphic.getType().getCode().equals(JeeslGraphicType.Code.svg.toString()))
    	{
    		respond(request,response,graphic.getData(),"svg");
    	}
	}
	
	protected void process(HttpServletRequest request, HttpServletResponse response, JeeslGraphicFacade<L,D,S,G,GT,F,FS> fGraphic, G graphic, Image image) throws ServletException, IOException, TranscoderException, UtilsProcessingException
    {
		byte[] bytes = null;
    	
		String id = image.getId();
		int size = (int) image.getHeight().getValue();
		
		if(graphic==null){throw new UtilsProcessingException("graphic is null");}
		if(graphic.getType()==null){throw new UtilsProcessingException("graphic.type is null");}
    	if(graphic.getType().getCode().equals(JeeslGraphicType.Code.symbol.toString()))
		{
    		logger.info("Build SVG: size " + size + " id:" + id);
    		List<F> figures = fGraphic.allForParent(cF,graphic);
			if(figures.isEmpty())
			{
		    	SVGGraphics2D g = fSvgGraphic.build(size,graphic);
		    	bytes = Svg2SvgTranscoder.transcode(g);
		    	respond(request,response,bytes,"svg");
			}
			else
			{
				SVGGraphics2D g = fSvgFigure.build(figures,size);
		    	bytes = Svg2SvgTranscoder.transcode(g);
		    	respond(request,response,bytes,"svg");
			}
		}
    	else if(graphic.getType().getCode().equals(JeeslGraphicType.Code.svg.toString()))
    	{
    		respond(request,response,graphic.getData(),"svg");
    	}
	}
}