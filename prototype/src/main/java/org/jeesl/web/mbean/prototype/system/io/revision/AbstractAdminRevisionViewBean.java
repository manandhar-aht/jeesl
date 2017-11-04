package org.jeesl.web.mbean.prototype.system.io.revision;

import java.io.Serializable;
import java.util.List;

import org.jeesl.api.facade.io.JeeslIoRevisionFacade;
import org.jeesl.factory.builder.system.RevisionFactoryBuilder;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionAttribute;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntity;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntityMapping;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionScope;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionView;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionViewMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.bean.FacesMessageBean;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.web.UtilsJsfSecurityHandler;
import net.sf.ahtutils.jsf.util.PositionListReorderer;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractAdminRevisionViewBean <L extends UtilsLang,D extends UtilsDescription,
											RC extends UtilsStatus<RC,L,D>,
											RV extends JeeslRevisionView<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
											RVM extends JeeslRevisionViewMapping<RV,RE,REM>,
											RS extends JeeslRevisionScope<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
											RST extends UtilsStatus<RST,L,D>,
											RE extends JeeslRevisionEntity<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
											REM extends JeeslRevisionEntityMapping<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
											RA extends JeeslRevisionAttribute<L,D,RE,RAT>,
											RAT extends UtilsStatus<RAT,L,D>>
					extends AbstractAdminRevisionBean<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>
					implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminRevisionViewBean.class);
	
	private List<RV> views; public List<RV> getViews() {return views;}
	private List<RVM> viewMappings; public List<RVM> getViewMappings() {return viewMappings;}
	
	private RV rv; public RV getRv() {return rv;} public void setRv(RV rv) {this.rv = rv;}
	private RVM mapping; public RVM getMapping() {return mapping;}public void setMapping(RVM mapping) {this.mapping = mapping;}
	
	public AbstractAdminRevisionViewBean(final RevisionFactoryBuilder<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT> fbRevision){super(fbRevision);}

	protected void initSuper(String[] langs, FacesMessageBean bMessage, JeeslIoRevisionFacade<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT> fRevision)
	{
		super.initRevisionSuper(langs,bMessage,fRevision);		
		entities = fRevision.all(fbRevision.getClassEntity());
		reloadViews();
	}

	public void reloadViews()
	{
		views = fRevision.allOrderedPosition(fbRevision.getClassView());
		logger.info(AbstractLogMessage.reloaded(fbRevision.getClassView(),views));
//		if(showInvisibleCategories){categories = fUtils.allOrderedPosition(cCategory);}
//		else{categories = fUtils.allOrderedPositionVisible(cCategory);}
	}
	
	public void add() throws UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.addEntity(fbRevision.getClassView()));
		rv = efView.build();
		rv.setName(efLang.createEmpty(langs));
		rv.setDescription(efDescription.createEmpty(langs));
	}
	
	public void select() throws UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.selectEntity(rv));
		rv = fRevision.find(fbRevision.getClassView(), rv);
		rv = efLang.persistMissingLangs(fRevision,langs,rv);
		rv = efDescription.persistMissingLangs(fRevision,langs,rv);
		mapping=null;
		reloadView();
	}
	
	private void reloadView()
	{
		rv = fRevision.load(fbRevision.getClassView(), rv);
		viewMappings = rv.getMaps();
	}
	
	public void save() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.saveEntity(rv));
		rv = fRevision.save(rv);
		bMessage.growlSuccessSaved();
		reloadViews();
		reloadView();
		updatePerformed();
	}
	
	public void rm() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.rmEntity(rv));
		fRevision.rm(rv);
		bMessage.growlSuccessRemoved();
		rv=null;
		reloadViews();
		updatePerformed();
	}
	
	public void cancel()
	{
		rv = null;
		mapping=null;
	}
	
	//*************************************************************************************
	
	public void changeEntity()
	{
		if(mapping.getEntity()!=null)
		{
			mapping.setEntity(fRevision.find(fbRevision.getClassEntity(),mapping.getEntity()));
			logger.info(AbstractLogMessage.selectOneMenuChange(mapping.getEntity()));
			reloadEntityMappings();
			if(entityMappings.isEmpty()){mapping.setEntityMapping(null);}
			else{mapping.setEntityMapping(entityMappings.get(0));}
		}
	}
	
	private void reloadEntityMappings()
	{
		RE e = fRevision.load(fbRevision.getClassEntity(), mapping.getEntity());
		entityMappings = e.getMaps();
	}
	
	public void addMapping() throws UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.addEntity(fbRevision.getClassViewMapping())+" entites:"+entities.size()+" empty:"+entities.isEmpty());
		RE re = null;
		if(!entities.isEmpty())
		{
			re = entities.get(0);
			mapping = efMappingView.build(rv,re,null);
			reloadEntityMappings();
			if(!entityMappings.isEmpty()){mapping.setEntityMapping(entityMappings.get(0));}
		}
		else
		{
			mapping = efMappingView.build(rv,re,null);
			entityMappings.clear();
		}	
	}
	
	public void selectMapping() throws UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.selectEntity(mapping));
		mapping = fRevision.find(fbRevision.getClassViewMapping(), mapping);
		reloadEntityMappings();
	}
	
	public void saveMapping() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.saveEntity(mapping));
		mapping.setEntityMapping(fRevision.find(fbRevision.getClassEntityMapping(),mapping.getEntityMapping()));
		mapping.setEntity(fRevision.find(fbRevision.getClassEntity(),mapping.getEntity()));
		mapping = fRevision.save(mapping);
		reloadView();
		bMessage.growlSuccessSaved();
		updatePerformed();
	}
	
	public void rmMapping() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.rmEntity(mapping));
		fRevision.rm(fbRevision.getClassViewMapping(),mapping);
		mapping=null;
		bMessage.growlSuccessRemoved();
		reloadView();
		updatePerformed();
	}
	
	public void cancelMapping()
	{
		mapping=null;
	}
	
	protected void reorderViews() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fRevision, views);}
	protected void reorderMappings() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fRevision, viewMappings);}
	protected void updatePerformed(){}	
	
	@SuppressWarnings("rawtypes")
	@Override protected void updateSecurity2(UtilsJsfSecurityHandler jsfSecurityHandler, String actionDeveloper)
	{
		uiAllowSave = jsfSecurityHandler.allow(actionDeveloper);

		if(logger.isTraceEnabled())
		{
			logger.info(uiAllowSave+" allowSave ("+actionDeveloper+")");
		}
	}
}