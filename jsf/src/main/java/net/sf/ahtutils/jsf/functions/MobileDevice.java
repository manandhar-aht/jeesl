package net.sf.ahtutils.jsf.functions;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class MobileDevice
{
	final static Logger logger = LoggerFactory.getLogger(MobileDevice.class);
	
    private MobileDevice()
    {
    	
    }
    
    public static boolean mobileDevice()
    {
	    	String userAgent = ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getHeader("user-agent");
	    	return detect(userAgent);
    }
    
    private static boolean detect(String userAgent)
    {
    		if(userAgent.contains("Android")){return true;}
	    	else if(userAgent.contains("webOS")){return true;}
	    	else if(userAgent.contains("iPhone")){return true;}
	    	else if(userAgent.contains("iPad")){return true;}
	    	else if(userAgent.contains("iPod")){return true;}
	    	else if(userAgent.contains("BlackBerry")){return true;}
	    	else {return false;}
    }
}