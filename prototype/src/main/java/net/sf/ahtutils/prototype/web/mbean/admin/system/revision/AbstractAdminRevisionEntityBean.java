package net.sf.ahtutils.prototype.web.mbean.admin.system.revision;

import java.io.Serializable;
import java.util.Collections;
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
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionViewMapping;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionScope;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionView;
import net.sf.ahtutils.interfaces.web.UtilsJsfSecurityHandler;
import net.sf.ahtutils.jsf.util.PositionListReorderer;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractAdminRevisionEntityBean <L extends UtilsLang,D extends UtilsDescription,
											RC extends UtilsStatus<RC,L,D>,
											RV extends UtilsRevisionView<L,D,RC,RV,RVM,RS,RE,REM,RA,RAT>,
											RVM extends UtilsRevisionViewMapping<L,D,RC,RV,RVM,RS,RE,REM,RA,RAT>,
											RS extends UtilsRevisionScope<L,D,RC,RV,RVM,RS,RE,REM,RA,RAT>,
											RE extends UtilsRevisionEntity<L,D,RC,RV,RVM,RS,RE,REM,RA,RAT>,
											REM extends UtilsRevisionEntityMapping<L,D,RC,RV,RVM,RS,RE,REM,RA,RAT>,
											RA extends UtilsRevisionAttribute<L,D,RC,RV,RVM,RS,RE,REM,RA,RAT>,
											RAT extends UtilsStatus<RAT,L,D>>
					extends AbstractAdminRevisionBean<L,D,RC,RV,RVM,RS,RE,REM,RA,RAT>
					implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminRevisionEntityBean.class);
	
	private List<REM> mappings; public List<REM> getMappings() {return mappings;}
	
	private RE entity; public RE getEntity() {return entity;} public void setEntity(RE entity) {this.entity = entity;}
	private REM mapping; public REM getMapping() {return mapping;}public void setMapping(REM mapping) {this.mapping = mapping;}
	
	protected void initSuper(String[] langs, FacesMessageBean bMessage, UtilsRevisionFacade<L,D,RC,RV,RVM,RS,RE,REM,RA,RAT> fRevision, final Class<L> cLang, final Class<D> cDescription, Class<RC> cCategory,Class<RV> cView,Class<RVM> cMapping, Class<RS> cScope, Class<RE> cEntity, Class<REM> cEntityMapping, Class<RA> cAttribute, Class<RAT> cRat)
	{
		super.initRevisionSuper(langs,bMessage,fRevision,cLang,cDescription,cCategory,cView,cMapping,cScope,cEntity,cEntityMapping,cAttribute,cRat);
		scopes = fRevision.all(cScope);
		categories = fRevision.allOrderedPositionVisible(cCategory);
		types = fRevision.allOrderedPositionVisible(cRat);
		reloadEntities();
	}

	private void reloadEntities()
	{
		entities = fRevision.all(cEntity);
		if(debugOnInfo){logger.info(AbstractLogMessage.reloaded(cEntity,entities));}
//		if(showInvisibleCategories){categories = fUtils.allOrderedPosition(cCategory);}
//		else{categories = fUtils.allOrderedPositionVisible(cCategory);}
		Collections.sort(entities, comparatorEntity);
	}
	
	public void addEntity() throws UtilsNotFoundException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.addEntity(cEntity));}
		entity = efEntity.build();
		entity.setName(efLang.createEmpty(langs));
		entity.setDescription(efDescription.createEmpty(langs));
		attribute=null;
	}
	
	private void reloadEntity()
	{
		entity = fRevision.load(cEntity, entity);
		attributes = entity.getAttributes();
		mappings = entity.getMaps();
	}
	
	public void selectEntity() throws UtilsNotFoundException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.selectEntity(entity));}
		entity = fRevision.find(cEntity, entity);
		entity = efLang.persistMissingLangs(fRevision,langs,entity);
		entity = efDescription.persistMissingLangs(fRevision,langs,entity);
		reloadEntity();
		attribute=null;
		mapping=null;
	}
	
	public void saveEntity() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.saveEntity(entity));}
		if(entity.getCategory()!=null){entity.setCategory(fRevision.find(cCategory, entity.getCategory()));}
		entity = fRevision.save(entity);
		reloadEntities();
		reloadEntity();
		bMessage.growlSuccessSaved();
		updatePerformed();
	}
	
	public void rmEntity() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.rmEntity(entity));}
		fRevision.rm(entity);
		entity=null;
		mapping=null;
		attribute=null;
		bMessage.growlSuccessRemoved();
		reloadEntities();
		updatePerformed();
	}
	
	public void cancelEntity()
	{
		entity = null;
		attribute=null;
		mapping=null;
	}
	
	//*************************************************************************************
	
	public void saveAttribute() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.saveEntity(attribute));}
		if(attribute.getType()!=null){attribute.setType(fRevision.find(cRat, attribute.getType()));}
		attribute = fRevision.save(cEntity,entity,attribute);
		reloadEntity();
		bMessage.growlSuccessSaved();
		updatePerformed();
	}
	
	public void rmAttribute() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.rmEntity(attribute));}
		fRevision.rm(cEntity,entity,attribute);
		attribute=null;
		bMessage.growlSuccessRemoved();
		reloadEntity();
		updatePerformed();
	}
	
	//*************************************************************************************
	
	public void addMapping() throws UtilsNotFoundException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.addEntity(cMappingEntity));}
		mapping = efMappingEntity.build(entity);
//		attribute.setName(efLang.createEmpty(langs));
//		attribute.setDescription(efDescription.createEmpty(langs));
	}
	
	public void selectMapping() throws UtilsNotFoundException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.selectEntity(mapping));}
		mapping = fRevision.find(cMappingEntity, mapping);
	}
	
	public void saveMapping() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.saveEntity(mapping));}
		mapping.setScope(fRevision.find(cScope,mapping.getScope()));
		mapping = fRevision.save(mapping);
		reloadEntity();
		bMessage.growlSuccessSaved();
		updatePerformed();
	}
	
	public void rmMapping() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.rmEntity(mapping));}
		fRevision.rm(mapping);
		mapping=null;
		bMessage.growlSuccessRemoved();
		reloadEntity();
		updatePerformed();
	}
	
	public void cancelMapping()
	{
		mapping=null;
	}
	
	protected void reorderEntites() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fRevision, cEntity, entities);Collections.sort(entities, comparatorEntity);}
	protected void reorderAttributes() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fRevision, attributes);}
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