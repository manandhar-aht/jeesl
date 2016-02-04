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
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionAttribute;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionEntity;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionMapping;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionScope;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionView;
import net.sf.ahtutils.interfaces.web.UtilsJsfSecurityHandler;
import net.sf.ahtutils.jsf.util.PositionListReorderer;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractAdminRevisionEntityBean <L extends UtilsLang,D extends UtilsDescription,
											RV extends UtilsRevisionView<L,D,RV,RM,RS,RE,RA>,
											RM extends UtilsRevisionMapping<L,D,RV,RM,RS,RE,RA>,
											RS extends UtilsRevisionScope<L,D,RV,RM,RS,RE,RA>,
											RE extends UtilsRevisionEntity<L,D,RV,RM,RS,RE,RA>,
											RA extends UtilsRevisionAttribute<L,D,RV,RM,RS,RE,RA>>
					extends AbstractAdminRevisionBean<L,D,RV,RM,RS,RE,RA>
					implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminRevisionEntityBean.class);
	
	private List<RE> entities; public List<RE> getEntities() {return entities;}
	private List<RA> attributes; public List<RA> getAttributes() {return attributes;}
	
	private RE entity; public RE getEntity() {return entity;} public void setEntity(RE entity) {this.entity = entity;}
	private RA attribute; public RA getAttribute() {return attribute;}public void setAttribute(RA attribute) {this.attribute = attribute;}
	
	protected void initSuper(String[] langs, FacesMessageBean bMessage, UtilsRevisionFacade<L,D,RV,RM,RS,RE,RA> fRevision, final Class<L> cLang, final Class<D> cDescription, Class<RV> cView,Class<RM> cMapping, Class<RS> cScope, Class<RE> cEntity, Class<RA> cAttribute)
	{
		super.initRevisionSuper(langs,bMessage,fRevision,cLang,cDescription,cView,cMapping,cScope,cEntity,cAttribute);
		scopes = fRevision.all(cScope);
		reloadEntities();
	}

	private void reloadEntities()
	{
		entities = fRevision.all(cEntity);
		logger.info(AbstractLogMessage.reloaded(cEntity,entities));
//		if(showInvisibleCategories){categories = fUtils.allOrderedPosition(cCategory);}
//		else{categories = fUtils.allOrderedPositionVisible(cCategory);}
	}
	
	public void addEntity() throws UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.addEntity(cEntity));
		entity = efEntity.build(null);
		entity.setName(efLang.createEmpty(langs));
		entity.setDescription(efDescription.createEmpty(langs));
		attribute=null;
	}
	
	private void reloadEntity()
	{
		entity = fRevision.load(cEntity, entity);
		attributes = entity.getAttributes();
	}
	
	public void selectEntity() throws UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.selectEntity(entity));
		entity = fRevision.find(cEntity, entity);
		entity = efLang.persistMissingLangs(fRevision,langs,entity);
		entity = efDescription.persistMissingLangs(fRevision,langs,entity);
		reloadEntity();
		attribute=null;
	}
	
	public void saveEntity() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.saveEntity(entity));
		entity.setScope(fRevision.find(cScope, entity.getScope()));
		entity = fRevision.save(entity);
		reloadEntities();
		reloadEntity();
		bMessage.growlSuccessSaved();
		updatePerformed();
	}
	
	public void rmEntity() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.rmEntity(entity));
		fRevision.rm(entity);
		entity=null;
		bMessage.growlSuccessRemoved();
		reloadEntities();
		updatePerformed();
	}
	
	public void cancelEntity()
	{
		entity = null;
		attribute=null;
	}
	
	public void addAttribute() throws UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.addEntity(cAttribute));
		attribute = efAttribute.build(entity);
		attribute.setName(efLang.createEmpty(langs));
		attribute.setDescription(efDescription.createEmpty(langs));
	}
	
	public void selectAttribute() throws UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.selectEntity(attribute));
		attribute = fRevision.find(cAttribute, attribute);
		attribute = efLang.persistMissingLangs(fRevision,langs,attribute);
		attribute = efDescription.persistMissingLangs(fRevision,langs,attribute);
	}
	
	public void saveAttribute() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.saveEntity(attribute));
		attribute = fRevision.save(attribute);
		reloadEntity();
		bMessage.growlSuccessSaved();
		updatePerformed();
	}
	
	public void rmAttribute() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.rmEntity(attribute));
		fRevision.rm(attribute);
		attribute=null;
		bMessage.growlSuccessRemoved();
		reloadEntity();
		updatePerformed();
	}
	
	public void cancelAttribute()
	{
		attribute=null;
	}
	
	protected void reorderEntites() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fRevision, entities);}
	protected void reorderAttributes() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fRevision, attributes);}
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