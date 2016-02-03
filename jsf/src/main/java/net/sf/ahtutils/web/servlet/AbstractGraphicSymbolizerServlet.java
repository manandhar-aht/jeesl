package net.sf.ahtutils.web.servlet;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.factory.xml.sync.XmlMapperFactory;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.xml.sync.Mapper;

public class AbstractGraphicSymbolizerServlet<L extends UtilsLang,D extends UtilsDescription,GT extends UtilsStatus<GT,L,D>,GS extends UtilsStatus<GS,L,D>>
	extends HttpServlet
	implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractGraphicSymbolizerServlet.class);
	
	
	public void initSuper()
	{
		
	}
	
	protected Mapper getPathInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
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
        
        logger.info("Requested size " +size+" id:"+id);
        
        return XmlMapperFactory.build(size, id);
	}
	
	protected void respond(HttpServletRequest request, HttpServletResponse response,byte[] bytes) throws ServletException, IOException
    {
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		
		response.reset();
		response.setContentType(getServletContext().getMimeType("x.png"));
		response.setHeader("Content-Length", String.valueOf(bytes.length));
		
	  	IOUtils.copy(bais, response.getOutputStream());
	}
}