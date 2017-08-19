package net.sf.ahtutils.controller.facade;

import javax.persistence.EntityManager;

import org.jeesl.api.facade.module.JeeslMonitoringFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UtilsMonitoringFacadeBean extends UtilsFacadeBean implements JeeslMonitoringFacade
{
	final static Logger logger = LoggerFactory.getLogger(UtilsMonitoringFacadeBean.class);
	
	public UtilsMonitoringFacadeBean(EntityManager em)
	{
		super(em);
	}

}