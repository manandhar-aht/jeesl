package org.jeesl.web.mbean.admin.system.io;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.jeesl.factory.ejb.system.io.EjbIoTemplateFactory;
import org.jeesl.interfaces.facade.JeeslIoTemplateFacade;
import org.jeesl.interfaces.model.system.io.templates.JeeslIoTemplate;
import org.jeesl.interfaces.model.system.io.templates.JeeslIoTemplateDefinition;
import org.jeesl.interfaces.model.system.io.templates.JeeslIoTemplateToken;
import org.jeesl.interfaces.model.system.revision.UtilsRevisionAttribute;
import org.jeesl.interfaces.model.system.revision.UtilsRevisionEntity;
import org.jeesl.interfaces.model.system.revision.UtilsRevisionEntityMapping;
import org.jeesl.interfaces.model.system.revision.UtilsRevisionScope;
import org.jeesl.interfaces.model.system.revision.UtilsRevisionView;
import org.jeesl.interfaces.model.system.revision.UtilsRevisionViewMapping;
import org.jeesl.util.comparator.ejb.system.io.IoTemplateComparator;
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
import net.sf.ahtutils.prototype.controller.handler.ui.SbMultiStatusHandler;
import net.sf.ahtutils.prototype.web.mbean.admin.system.revision.AbstractAdminRevisionBean;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractAdminIoTemplateBean <L extends UtilsLang,D extends UtilsDescription,
											RC extends UtilsStatus<RC,L,D>,
											RV extends UtilsRevisionView<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
											RVM extends UtilsRevisionViewMapping<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
											RS extends UtilsRevisionScope<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
											RST extends UtilsStatus<RST,L,D>,
											RE extends UtilsRevisionEntity<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
											REM extends UtilsRevisionEntityMapping<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
											RA extends UtilsRevisionAttribute<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
											RAT extends UtilsStatus<RAT,L,D>,

											CATEGORY extends UtilsStatus<CATEGORY,L,D>,
											TYPE extends UtilsStatus<TYPE,L,D>,
											TEMPLATE extends JeeslIoTemplate<L,D,CATEGORY,TYPE,TEMPLATE,DEFINITION,TOKEN>,
											DEFINITION extends JeeslIoTemplateDefinition<L,D,CATEGORY,TYPE,TEMPLATE,DEFINITION,TOKEN>,
											TOKEN extends JeeslIoTemplateToken<L,D,CATEGORY,TYPE,TEMPLATE,DEFINITION,TOKEN>>
					extends AbstractAdminRevisionBean<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>
					implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminIoTemplateBean.class);
	
	protected JeeslIoTemplateFacade<L,D,CATEGORY,TYPE,TEMPLATE,DEFINITION,TOKEN> fTemplate;
	
	private Class<CATEGORY> cCategory2;
	private Class<TEMPLATE> cTemplate;
	
	private List<CATEGORY> categories2; public List<CATEGORY> getCategories2() {return categories2;}
	private List<TEMPLATE> templates; public List<TEMPLATE> getTemplates() {return templates;}
	
	private TEMPLATE template; public TEMPLATE getTemplate() {return template;} public void setTemplate(TEMPLATE template) {this.template = template;}
	
	private RE entity; public RE getEntity() {return entity;} public void setEntity(RE entity) {this.entity = entity;}
	private REM mapping; public REM getMapping() {return mapping;}public void setMapping(REM mapping) {this.mapping = mapping;}
	
	protected EjbIoTemplateFactory<L,D,CATEGORY,TYPE,TEMPLATE,DEFINITION,TOKEN> efTemplate;
	
	private SbMultiStatusHandler<L,D,CATEGORY> sbhCategory2; public SbMultiStatusHandler<L,D,CATEGORY> getSbhCategory2() {return sbhCategory2;}
	protected Comparator<TEMPLATE> comparatorTemplate;
	
	protected void initSuper(String[] langs, FacesMessageBean bMessage, JeeslIoTemplateFacade<L,D,CATEGORY,TYPE,TEMPLATE,DEFINITION,TOKEN> fTemplate, UtilsRevisionFacade<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT> fRevision, final Class<L> cLang, final Class<D> cDescription, Class<RC> cCategory,Class<RV> cView,Class<RVM> cMapping, Class<RS> cScope, Class<RST> cScopeType, Class<RE> cEntity, Class<REM> cEntityMapping, Class<RA> cAttribute, Class<RAT> cRat, Class<CATEGORY> cCategory2, Class<TEMPLATE> cTemplate)
	{
		super.initRevisionSuper(langs,bMessage,fRevision,cLang,cDescription,cCategory,cView,cMapping,cScope,cScopeType,cEntity,cEntityMapping,cAttribute,cRat);
		this.fTemplate=fTemplate;
		this.cCategory2=cCategory2;
		this.cTemplate=cTemplate;
		
		scopes = fRevision.all(cScope);
		types = fRevision.allOrderedPositionVisible(cRat);
		scopeTypes = fRevision.allOrderedPositionVisible(cScopeType);
		
		efTemplate = EjbIoTemplateFactory.factory(cLang,cDescription,cTemplate);
		
		comparatorTemplate = new IoTemplateComparator<L,D,CATEGORY,TYPE,TEMPLATE,DEFINITION,TOKEN>().factory(IoTemplateComparator.Type.position);
		
		categories2 = fRevision.allOrderedPositionVisible(cCategory2);
		sbhCategory2 = new SbMultiStatusHandler<L,D,CATEGORY>(cCategory2,categories2);
		sbhCategory2.selectAll();
		reloadEntities();
	}
	
	public void multiToggle(UtilsStatus<?,L,D> o)
	{
		logger.info(AbstractLogMessage.toggle(o)+" Class: "+o.getClass().getSimpleName());
		sbhCategory2.multiToggle(o);
		reloadEntities();
		cancelEntity();
	}

	private void reloadEntities()
	{
		entities = fRevision.findEntities(cEntity, cCategory, sbhCategory.getSelected(), true);
		templates = fTemplate.fTemplates(cTemplate, cCategory2, sbhCategory2.getSelected(), true);
		if(debugOnInfo){logger.info(AbstractLogMessage.reloaded(cEntity,entities));}
		Collections.sort(entities, comparatorEntity);
	}
	
	public void addEntity() throws UtilsNotFoundException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.addEntity(cEntity));}
		entity = efEntity.build(null);
		entity.setName(efLang.createEmpty(langs));
		entity.setDescription(efDescription.createEmpty(langs));
		attribute=null;
		mapping=null;
	}
	
	public void addTemplate() throws UtilsNotFoundException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.addEntity(cTemplate));}
		template = efTemplate.build(null);
		template.setName(efLang.createEmpty(langs));
		template.setDescription(efDescription.createEmpty(langs));

	}
	
	private void reloadEntity()
	{
		entity = fRevision.load(cEntity, entity);
		attributes = entity.getAttributes();
		entityMappings = entity.getMaps();
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
	
	public void selectTemplate() throws UtilsNotFoundException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.selectEntity(template));}
		template = fTemplate.find(cTemplate, template);
		template = efLang.persistMissingLangs(fTemplate,langs,template);
		template = efDescription.persistMissingLangs(fTemplate,langs,template);
		reloadEntity();
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
		RST rst = null; if(!scopeTypes.isEmpty()){rst=scopeTypes.get(0);}
		mapping = efMappingEntity.build(entity,null,rst);
		updateUi();
	}
	
	public void selectMapping() throws UtilsNotFoundException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.selectEntity(mapping));}
		mapping = fRevision.find(cMappingEntity, mapping);
		updateUi();
	}
	
	public void saveMapping() throws UtilsLockingException, UtilsNotFoundException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.saveEntity(mapping));}
		mapping.setScope(fRevision.find(cScope,mapping.getScope()));
		mapping.setType(fRevision.find(cScopeType, mapping.getType()));
		
		try
		{
			mapping = fRevision.save(mapping);
			updateUi();
			reloadEntity();
			bMessage.growlSuccessSaved();
			updatePerformed();
		}
		catch (UtilsConstraintViolationException e) {bMessage.errorConstraintViolationDuplicateObject();}
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
	
	public void changeScopeType()
	{
		mapping.setType(fRevision.find(cScopeType, mapping.getType()));
		logger.info(AbstractLogMessage.selectEntity(mapping, mapping.getType()));
		updateUi();
	}
	
	//UI
	private boolean uiWithJpqlTree;
	
	public boolean isUiWithJpqlTree() {
		return uiWithJpqlTree;
	}
	private void updateUi()
	{
		uiWithJpqlTree = false;
		if(mapping!=null)
		{
			if(mapping.getType().getCode().equals(UtilsRevisionEntityMapping.Type.jpqlTree.toString())){uiWithJpqlTree=true;}
		}
	}
	
	protected void reorderTemplates() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fTemplate, cTemplate, templates);Collections.sort(templates, comparatorTemplate);}
	protected void reorderEntites() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fRevision, cEntity, entities);Collections.sort(entities, comparatorEntity);}
	protected void reorderAttributes() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fRevision, attributes);}
	protected void reorderMappings() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fRevision, entityMappings);}
	
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