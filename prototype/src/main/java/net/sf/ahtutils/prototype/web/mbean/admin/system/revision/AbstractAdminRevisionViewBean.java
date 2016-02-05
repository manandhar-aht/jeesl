package net.sf.ahtutils.prototype.web.mbean.admin.system.revision;

import java.io.Serializable;
import java.util.List;

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
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionAttribute;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionEntity;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionEntityMapping;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionScope;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionView;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionViewMapping;
import net.sf.ahtutils.interfaces.web.UtilsJsfSecurityHandler;
import net.sf.ahtutils.jsf.util.PositionListReorderer;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractAdminRevisionViewBean <L extends UtilsLang,D extends UtilsDescription,
											RC extends UtilsStatus<RC,L,D>,
											RV extends UtilsRevisionView<L,D,RC,RV,RVM,RS,RE,REM,RA>,
											RVM extends UtilsRevisionViewMapping<L,D,RC,RV,RVM,RS,RE,REM,RA>,
											RS extends UtilsRevisionScope<L,D,RC,RV,RVM,RS,RE,REM,RA>,
											RE extends UtilsRevisionEntity<L,D,RC,RV,RVM,RS,RE,REM,RA>,
											REM extends UtilsRevisionEntityMapping<L,D,RC,RV,RVM,RS,RE,REM,RA>,
											RA extends UtilsRevisionAttribute<L,D,RC,RV,RVM,RS,RE,REM,RA>>
					extends AbstractAdminRevisionBean<L,D,RC,RV,RVM,RS,RE,REM,RA>
					implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminRevisionViewBean.class);
	
	private List<RV> views; public List<RV> getViews() {return views;}
	private List<RVM> mappings; public List<RVM> getMappings() {return mappings;}
	
	private RV rv; public RV getRv() {return rv;} public void setRv(RV rv) {this.rv = rv;}
	private RVM mapping; public RVM getMapping() {return mapping;}public void setMapping(RVM mapping) {this.mapping = mapping;}
	
	protected void initSuper(String[] langs, FacesMessageBean bMessage, UtilsRevisionFacade<L,D,RC,RV,RVM,RS,RE,REM,RA> fRevision, final Class<L> cLang, final Class<D> cDescription, Class<RC> cCategory, Class<RV> cView,Class<RVM> cMapping, Class<RS> cScope, Class<RE> cEntity, Class<REM> cEntityMapping,Class<RA> cAttribute)
	{
		super.initRevisionSuper(langs,bMessage,fRevision,cLang,cDescription,cCategory,cView,cMapping,cScope,cEntity,cEntityMapping,cAttribute);		
		entities = fRevision.all(cEntity);
		scopes = fRevision.all(cScope);
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
		reloadView();
	}
	
	private void reloadView()
	{
		rv = fRevision.load(cView, rv);
		mappings = rv.getMaps();
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
		scopes.clear();
		if(mapping.getEntity()!=null)
		{
			mapping.setEntity(fRevision.find(cEntity,mapping.getEntity()));
			logger.info(AbstractLogMessage.selectOneMenuChange(mapping.getEntity()));
			RE e = fRevision.load(cEntity, mapping.getEntity());
			for(REM rem : e.getMaps())
			{
				scopes.add(rem.getScope());
			}
		}
	}
	
	public void addMapping() throws UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.addEntity(cMappingView));
		RE re = null;
		if(entities.size()>0){re = entities.get(0);}
		mapping = efMappingView.build(rv,re,null);
		changeEntity();
		
	}
	
	public void selectMapping() throws UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.selectEntity(mapping));
		mapping = fRevision.find(cMappingView, mapping);
		changeEntity();
	}
	
	public void saveMapping() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.saveEntity(mapping));
		mapping.setScope(fRevision.find(cScope,mapping.getScope()));
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
	protected void reorderMappings() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fRevision, mappings);}
	protected void updatePerformed(){}	
	
	
	protected void updateSecurity(UtilsJsfSecurityHandler jsfSecurityHandler, String actionDeveloper)
	{
		allowSave = jsfSecurityHandler.allow(actionDeveloper);

		if(logger.isTraceEnabled())
		{
			logger.info(allowSave+" allowSave ("+actionDeveloper+")");
		}
	}
}