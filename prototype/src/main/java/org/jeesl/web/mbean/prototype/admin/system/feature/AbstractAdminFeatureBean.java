package org.jeesl.web.mbean.prototype.admin.system.feature;

import java.io.Serializable;

import org.jeesl.api.bean.JeeslFeatureManagerBean;
import org.jeesl.factory.ejb.system.EjbSystemFeatureFactory;
import org.jeesl.interfaces.model.system.JeeslFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.bean.FacesMessageBean;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;

public class AbstractAdminFeatureBean <F extends JeeslFeature>
		implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminFeatureBean.class);
	
	private UtilsFacade fFeature;
	
	private JeeslFeatureManagerBean<F> bFeature;
	private FacesMessageBean bMessage;
	
	private Class<F> cFeature;
		
	private F feature; public F getFeature() {return feature;} public void setFeature(F feature) {this.feature = feature;}

	private EjbSystemFeatureFactory<F> efFeature;
	
	public void initSuper(UtilsFacade fFeature, JeeslFeatureManagerBean<F> bFeature, FacesMessageBean bMessage, final Class<F> cFeature)
	{
		this.fFeature=fFeature;
		this.bFeature=bFeature;
		this.bMessage=bMessage;
		this.cFeature=cFeature;
		
		efFeature = EjbSystemFeatureFactory.factory(cFeature);
	}

	public void selectFeature() throws UtilsNotFoundException
	{
		feature = fFeature.find(cFeature, feature);
	}
	
	public void addFeature()
	{
		feature = efFeature.build();
	}
	
	public void saveFeature() throws UtilsNotFoundException, UtilsConstraintViolationException, UtilsLockingException
	{
		feature = fFeature.saveTransaction(feature);
		bFeature.realodFeatures();
		bMessage.growlSuccessSaved();
	}
}