package net.sf.ahtutils.prototype.web.mbean.admin.system.revision;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

import org.jeesl.factory.ejb.system.revision.EjbRevisionAttributeFactory;
import org.jeesl.factory.ejb.system.revision.EjbRevisionEntityFactory;
import org.jeesl.factory.ejb.system.revision.EjbRevisionMappingEntityFactory;
import org.jeesl.factory.ejb.system.revision.EjbRevisionMappingViewFactory;
import org.jeesl.factory.ejb.system.revision.EjbRevisionScopeFactory;
import org.jeesl.factory.ejb.system.revision.EjbRevisionViewFactory;
import org.jeesl.interfaces.model.system.revision.UtilsRevisionAttribute;
import org.jeesl.interfaces.model.system.revision.UtilsRevisionEntity;
import org.jeesl.interfaces.model.system.revision.UtilsRevisionEntityMapping;
import org.jeesl.interfaces.model.system.revision.UtilsRevisionScope;
import org.jeesl.interfaces.model.system.revision.UtilsRevisionView;
import org.jeesl.interfaces.model.system.revision.UtilsRevisionViewMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.bean.FacesMessageBean;
import net.sf.ahtutils.interfaces.facade.UtilsRevisionFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.prototype.controller.handler.ui.SbMultiStatusHandler;
import net.sf.ahtutils.prototype.web.mbean.admin.AbstractAdminBean;
import net.sf.ahtutils.util.comparator.ejb.revision.RevisionEntityComparator;
import net.sf.ahtutils.util.comparator.ejb.revision.RevisionScopeComparator;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public abstract class AbstractAdminRevisionBean <L extends UtilsLang,D extends UtilsDescription,
											RC extends UtilsStatus<RC,L,D>,
											RV extends UtilsRevisionView<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
											RVM extends UtilsRevisionViewMapping<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
											RS extends UtilsRevisionScope<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
											RST extends UtilsStatus<RST,L,D>,
											RE extends UtilsRevisionEntity<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
											REM extends UtilsRevisionEntityMapping<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
											RA extends UtilsRevisionAttribute<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
											RAT extends UtilsStatus<RAT,L,D>>
					extends AbstractAdminBean<L,D>
					implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminRevisionBean.class);
	
	protected UtilsRevisionFacade<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT> fRevision;
	
	protected Class<RC> cCategory;
	protected Class<RV> cView;
	protected Class<RVM> cMappingView;
	protected Class<RS> cScope;
	protected Class<RST> cScopeType;
	protected Class<RE> cEntity;
	protected Class<REM> cMappingEntity;
	protected Class<RA> cAttribute;
	protected Class<RAT> cRat;
	
	protected List<RA> attributes; public List<RA> getAttributes() {return attributes;}
	protected List<RC> categories; public List<RC> getCategories() {return categories;}
	protected List<RS> scopes; public List<RS> getScopes() {return scopes;}
	protected List<RST> scopeTypes; public List<RST> getScopeTypes() {return scopeTypes;}
	protected List<RE> entities; public List<RE> getEntities() {return entities;}
	protected List<REM> entityMappings; public List<REM> getEntityMappings() {return entityMappings;}
	protected List<RAT> types; public List<RAT> getTypes() {return types;}
	
	protected RA attribute; public RA getAttribute() {return attribute;}public void setAttribute(RA attribute) {this.attribute = attribute;}

	protected EjbRevisionViewFactory<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT> efView;
	protected EjbRevisionMappingViewFactory<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT> efMappingView;
	protected EjbRevisionScopeFactory<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT> efScope;
	protected EjbRevisionEntityFactory<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT> efEntity;
	protected EjbRevisionMappingEntityFactory<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT> efMappingEntity;
	protected EjbRevisionAttributeFactory<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT> efAttribute;
	
	protected Comparator<RS> comparatorScope;
	protected Comparator<RE> comparatorEntity;
	
	protected SbMultiStatusHandler<L,D,RC> sbhCategory; public SbMultiStatusHandler<L,D,RC> getSbhCategory() {return sbhCategory;}
	
	protected void initRevisionSuper(String[] langs, FacesMessageBean bMessage, UtilsRevisionFacade<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT> fRevision, final Class<L> cLang, final Class<D> cDescription, Class<RC> cCategory,Class<RV> cView, Class<RVM> cMappingView, Class<RS> cScope, Class<RST> cScopeType, Class<RE> cEntity, Class<REM> cEntityMapping, Class<RA> cAttribute, Class<RAT> cRat)
	{
		super.initAdmin(langs,cLang,cDescription,bMessage);
		this.fRevision=fRevision;
		this.cCategory=cCategory;
		this.cView=cView;
		this.cMappingView=cMappingView;
		this.cScope=cScope;
		this.cScopeType=cScopeType;
		this.cEntity=cEntity;
		this.cMappingEntity=cEntityMapping;
		this.cAttribute=cAttribute;
		this.cRat=cRat;
		
		efView = EjbRevisionViewFactory.factory(cView);
		efMappingView = EjbRevisionMappingViewFactory.factory(cMappingView);
		efScope = EjbRevisionScopeFactory.factory(cScope);
		efEntity = EjbRevisionEntityFactory.factory(cLang,cDescription,cEntity);
		efMappingEntity = EjbRevisionMappingEntityFactory.factory(cEntityMapping);
		efAttribute = EjbRevisionAttributeFactory.factory(cAttribute);
		
		comparatorScope = (new RevisionScopeComparator<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>()).factory(RevisionScopeComparator.Type.position);
		comparatorEntity = (new RevisionEntityComparator<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>()).factory(RevisionEntityComparator.Type.position);
		
		categories = fRevision.allOrderedPositionVisible(cCategory);
		sbhCategory = new SbMultiStatusHandler<L,D,RC>(cCategory,categories); sbhCategory.selectAll();
	}
	
	public void addAttribute() throws UtilsNotFoundException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.addEntity(cAttribute));}
		attribute = efAttribute.build(null);
		attribute.setName(efLang.createEmpty(langs));
		attribute.setDescription(efDescription.createEmpty(langs));
	}
	
	public void selectAttribute() throws UtilsNotFoundException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.selectEntity(attribute));}
		attribute = fRevision.find(cAttribute, attribute);
		attribute = efLang.persistMissingLangs(fRevision,langs,attribute);
		attribute = efDescription.persistMissingLangs(fRevision,langs,attribute);
	}
	
	public void cancelAttribute()
	{
		attribute=null;
	}
}