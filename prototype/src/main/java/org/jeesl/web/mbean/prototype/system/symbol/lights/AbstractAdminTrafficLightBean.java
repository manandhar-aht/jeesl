package org.jeesl.web.mbean.prototype.system.symbol.lights;

import java.io.Serializable;
import java.util.List;

import org.jeesl.api.bean.JeeslTrafficLightBean;
import org.jeesl.api.facade.system.graphic.JeeslTrafficLightFacade;
import org.jeesl.factory.builder.system.LightFactoryBuilder;
import org.jeesl.factory.ejb.system.util.EjbTrafficLightFactory;
import org.jeesl.interfaces.model.system.util.JeeslTrafficLight;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractAdminTrafficLightBean <L extends UtilsLang, D extends UtilsDescription,
											LIGHT extends JeeslTrafficLight<L,D,SCOPE>,
											SCOPE extends UtilsStatus<SCOPE,L,D>>
	implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminTrafficLightBean.class);
	
	private JeeslTrafficLightFacade<L,D,LIGHT,SCOPE> fTl;
	private final LightFactoryBuilder<L,D,LIGHT,SCOPE> fbLight;
	private JeeslTrafficLightBean<L,D,LIGHT,SCOPE> bLight;
	
	private String[] defaultLangs;
	
	private EjbTrafficLightFactory<L,D,SCOPE,LIGHT> efLight;
	
	private List<SCOPE> trafficLightScopes; public List<SCOPE> getTrafficLightScopes(){return trafficLightScopes;}
	protected List<LIGHT> trafficLights; public List<LIGHT> getTrafficLights(){return trafficLights;}
	
	protected SCOPE scope; public SCOPE getScope(){return scope;} public void setScope(SCOPE scope){this.scope = scope;}
	
	public AbstractAdminTrafficLightBean(LightFactoryBuilder<L,D,LIGHT,SCOPE> fbLight)
	{
		this.fbLight=fbLight;
		efLight = EjbTrafficLightFactory.factory(fbLight.getClassL(),fbLight.getClassD(),fbLight.getClassLight());
	}
	
	public void initSuper(String[] defaultLangs, JeeslTrafficLightFacade<L,D,LIGHT,SCOPE> fTl, JeeslTrafficLightBean<L,D,LIGHT,SCOPE> bLight)
	{
		this.defaultLangs=defaultLangs;
		this.fTl=fTl;
		
		 reloadTrafficLightScopes();
	}
	
	private void reloadTrafficLightScopes()
	{
		trafficLightScopes = fTl.all(fbLight.getClassScope());
		logger.trace("Results: " + trafficLightScopes.size() +" scopes loaded.");
	}
		
	
	public void selectScope()
	{
		logger.info(AbstractLogMessage.selectEntity(scope));
		reloadTrafficLights();
	}

	private void reloadTrafficLights()
	{
		trafficLights = fTl.allOrderedTrafficLights(scope);
		logger.debug("Results: " + trafficLights.size());
	}
	
	//Light
	protected LIGHT trafficLight;
	public LIGHT getTrafficLight(){return trafficLight;}
	public void setTrafficLight(LIGHT trafficLight){this.trafficLight = trafficLight;}
	
	public void addTrafficLight() throws UtilsConstraintViolationException
	{
		logger.debug(AbstractLogMessage.addEntity(fbLight.getClassLight()));
		trafficLight = efLight.build(defaultLangs,scope);
	}
	
	public void selectTrafficLight()
	{
		logger.debug(AbstractLogMessage.selectEntity(trafficLight));
	}
	
	public void save() throws UtilsLockingException, UtilsConstraintViolationException
	{
		logger.debug(AbstractLogMessage.saveEntity(trafficLight));
		trafficLight = fTl.save(trafficLight);
		reloadTrafficLights();
		bLight.refreshTrafficLights();
	}
	
	public void rm() throws UtilsConstraintViolationException
	{
		logger.debug(AbstractLogMessage.rmEntity(trafficLight));
		fTl.rm(trafficLight);
		trafficLight=null;
		reloadTrafficLights();
		bLight.refreshTrafficLights();
	}
}