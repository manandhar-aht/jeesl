package org.jeesl.web.mbean.prototype.admin.system.revision;

import java.io.Serializable;
import java.util.List;

import org.jeesl.interfaces.model.system.revision.UtilsRevisionAttribute;
import org.jeesl.interfaces.model.system.revision.UtilsRevisionEntity;
import org.jeesl.interfaces.model.system.revision.UtilsRevisionEntityMapping;
import org.jeesl.interfaces.model.system.revision.UtilsRevisionScope;
import org.jeesl.interfaces.model.system.revision.UtilsRevisionView;
import org.jeesl.interfaces.model.system.revision.UtilsRevisionViewMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.bean.FacesMessageBean;
import net.sf.ahtutils.interfaces.facade.UtilsRevisionFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.web.UtilsJsfSecurityHandler;
import net.sf.ahtutils.jsf.util.PositionListReorderer;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractAdminRevisionViewBean <L extends UtilsLang,D extends UtilsDescription,
											RC extends UtilsStatus<RC,L,D>,
											RV extends UtilsRevisionView<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
											RVM extends UtilsRevisionViewMapping<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
											RS extends UtilsRevisionScope<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
											RST extends UtilsStatus<RST,L,D>,
											RE extends UtilsRevisionEntity<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
											REM extends UtilsRevisionEntityMapping<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
											RA extends UtilsRevisionAttribute<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
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
	
	protected void initSuper(String[] langs, FacesMessageBean bMessage, UtilsRevisionFacade<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT> fRevision, final Class<L> cLang, final Class<D> cDescription, Class<RC> cCategory, Class<RV> cView,Class<RVM> cMapping, Class<RS> cScope, Class<RST> cScopeType, Class<RE> cEntity, Class<REM> cEntityMapping,Class<RA> cAttribute, Class<RAT> cRat)
	{
		super.initRevisionSuper(langs,bMessage,fRevision,cLang,cDescription,cCategory,cView,cMapping,cScope,cScopeType,cEntity,cEntityMapping,cAttribute,cRat);		
		entities = fRevision.all(cEntity);
		reloadViews();
	}

	public void reloadViews()
	{
		views = fRevision.allOrderedPosition(cView);
		logger.info(AbstractLogMessage.reloaded(cView,views));
//		if(showInvisibleCategories){categories = fUtils.allOrderedPosition(cCategory);}
//		else{categories = fUtils.allOrderedPositionVisible(cCategory);}
	}
	
	public void add() throws UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.addEntity(cView));
		rv = efView.build();
		rv.setName(efLang.createEmpty(langs));
		rv.setDescription(efDescription.createEmpty(langs));
	}
	
	public void select() throws UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.selectEntity(rv));
		rv = fRevision.find(cView, rv);
		rv = efLang.persistMissingLangs(fRevision,langs,rv);
		rv = efDescription.persistMissingLangs(fRevision,langs,rv);
		mapping=null;
		reloadView();
	}
	
	private void reloadView()
	{
		rv = fRevision.load(cView, rv);
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
			mapping.setEntity(fRevision.find(cEntity,mapping.getEntity()));
			logger.info(AbstractLogMessage.selectOneMenuChange(mapping.getEntity()));
			reloadEntityMappings();
			if(entityMappings.isEmpty()){mapping.setEntityMapping(null);}
			else{mapping.setEntityMapping(entityMappings.get(0));}
		}
	}
	
	private void reloadEntityMappings()
	{
		RE e = fRevision.load(cEntity, mapping.getEntity());
		entityMappings = e.getMaps();
	}
	
	public void addMapping() throws UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.addEntity(cMappingView)+" entites:"+entities.size()+" empty:"+entities.isEmpty());
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
		mapping = fRevision.find(cMappingView, mapping);
		reloadEntityMappings();
	}
	
	public void saveMapping() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.saveEntity(mapping));
		mapping.setEntityMapping(fRevision.find(cMappingEntity,mapping.getEntityMapping()));
		mapping.setEntity(fRevision.find(cEntity,mapping.getEntity()));
		mapping = fRevision.save(mapping);
		reloadView();
		bMessage.growlSuccessSaved();
		updatePerformed();
	}
	
	public void rmMapping() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.rmEntity(mapping));
		fRevision.rm(cMappingView,mapping);
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
	
	@Override protected void updateSecurity2(UtilsJsfSecurityHandler jsfSecurityHandler, String actionDeveloper)
	{
		uiAllowSave = jsfSecurityHandler.allow(actionDeveloper);

		if(logger.isTraceEnabled())
		{
			logger.info(uiAllowSave+" allowSave ("+actionDeveloper+")");
		}
	}
}