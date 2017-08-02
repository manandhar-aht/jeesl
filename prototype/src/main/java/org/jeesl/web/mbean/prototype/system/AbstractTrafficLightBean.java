package org.jeesl.web.mbean.prototype.system;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.jeesl.api.bean.JeeslTrafficLightBean;
import org.jeesl.api.facade.system.graphic.JeeslTrafficLightFacade;
import org.jeesl.interfaces.model.system.util.JeeslTrafficLight;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class AbstractTrafficLightBean <L extends UtilsLang,D extends UtilsDescription,
										LIGHT extends JeeslTrafficLight<L,D,SCOPE>,
										SCOPE extends UtilsStatus<SCOPE,L,D>>
					implements Serializable,JeeslTrafficLightBean<L,D,LIGHT,SCOPE>
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractTrafficLightBean.class);
	
	private JeeslTrafficLightFacade<L,D,LIGHT,SCOPE> fLight;
		
	
	private Hashtable<String,List<LIGHT>> cache;
	
	protected void init(JeeslTrafficLightFacade<L,D,LIGHT,SCOPE> fLight)
	{
		this.fLight=fLight;
	}
	
	protected List<LIGHT> trafficLights;
	public List<LIGHT> getTrafficLights(){return trafficLights;}
	
	public void refreshTrafficLights()
	{
	    cache = new Hashtable<String,List<LIGHT>>();
	    
	    trafficLights = fLight.allOrderedTrafficLights();
	    for (LIGHT light : trafficLights)
	    {
	    		SCOPE scope = light.getScope();
			if (cache.containsKey(scope.getCode()))
			{
			    cache.get(scope.getCode()).add(light);
			}
			else
			{
			    ArrayList<LIGHT> scopeLights = new ArrayList<LIGHT>();
			    scopeLights.add(light);
			    cache.put(scope.getCode(), scopeLights);
			}
	    }
	}
	
	@Override public List<LIGHT> getTrafficLights(String scope)
	{
	    if (cache == null) {refreshTrafficLights();}
	    if(scope==null || scope.length()==0 || !cache.containsKey(scope)) {return null;}
	    if(logger.isTraceEnabled()){logger.info("Found " +cache.get(scope).size() +" lights definitions in scope " +scope);}
	    return cache.get(scope);
	}
}