package org.jeesl.web.servlet;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.commons.io.IOUtils;
import org.jeesl.factory.svg.SvgSymbolFactory;
import org.jeesl.interfaces.model.system.symbol.JeeslGraphic;
import org.jeesl.interfaces.model.system.symbol.JeeslGraphicType;
import org.openfuxml.content.media.Image;
import org.openfuxml.factory.xml.media.XmlImageFactory;
import org.openfuxml.media.transcode.Svg2SvgTranscoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.processing.UtilsProcessingException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class AbstractGraphicSymbolizerServlet<L extends UtilsLang,D extends UtilsDescription, G extends JeeslGraphic<L,D,G,GT,GS>, GT extends UtilsStatus<GT,L,D>,GS extends UtilsStatus<GS,L,D>>
	extends HttpServlet
	implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractGraphicSymbolizerServlet.class);
	
	private SvgSymbolFactory<L,D,G,GT,GS> svgF;
	
	public AbstractGraphicSymbolizerServlet()
	{
		svgF = SvgSymbolFactory.factory();
	}
	
	protected Image getPathInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		if (request.getPathInfo() == null)
		{
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}

        String path = URLDecoder.decode(request.getPathInfo(), "UTF-8");
        if(path.length()<1)
        {
        	response.sendError(HttpServletResponse.SC_NOT_FOUND);
        	return null;
        }
        
        String[] pathElements = path.split("/");
        Integer size = new Integer(pathElements[1]);
        Long id = new Long(pathElements[2]);
        
        if(logger.isTraceEnabled())
        {
        	logger.trace("Requested size " +size+" id:"+id);
        }
        
        return XmlImageFactory.idHeight(id,size);
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
	    	SVGGraphics2D g = svgF.build(size,graphic);
	    	bytes = Svg2SvgTranscoder.transcode(g);
	    	respond(request,response,bytes,"svg");
		}
    	else if(graphic.getType().getCode().equals(JeeslGraphicType.Code.svg.toString()))
    	{
//    		bytes = Svg2PngTranscoder.transcode(size,graphic.getData());
    		respond(request,response,graphic.getData(),"svg");
    	}
	}
	
	protected void respond(HttpServletRequest request, HttpServletResponse response,byte[] bytes, String suffix) throws ServletException, IOException
    {
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		
		response.reset();
		response.setContentType(getServletContext().getMimeType("x."+suffix));
		response.setHeader("Content-Length", String.valueOf(bytes.length));
		
	  	IOUtils.copy(bais,response.getOutputStream());
	}
}