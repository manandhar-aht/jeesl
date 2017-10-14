package org.jeesl.web.mbean.prototype.admin.system.revision;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

import org.jeesl.api.facade.io.JeeslIoRevisionFacade;
import org.jeesl.controller.handler.sb.SbMultiHandler;
import org.jeesl.factory.ejb.system.revision.EjbRevisionAttributeFactory;
import org.jeesl.factory.ejb.system.revision.EjbRevisionEntityFactory;
import org.jeesl.factory.ejb.system.revision.EjbRevisionMappingEntityFactory;
import org.jeesl.factory.ejb.system.revision.EjbRevisionMappingViewFactory;
import org.jeesl.factory.ejb.system.revision.EjbRevisionScopeFactory;
import org.jeesl.factory.ejb.system.revision.EjbRevisionViewFactory;
import org.jeesl.interfaces.bean.sb.SbToggleBean;
import org.jeesl.interfaces.model.system.revision.JeeslRevisionAttribute;
import org.jeesl.interfaces.model.system.revision.JeeslRevisionEntity;
import org.jeesl.interfaces.model.system.revision.JeeslRevisionEntityMapping;
import org.jeesl.interfaces.model.system.revision.JeeslRevisionScope;
import org.jeesl.interfaces.model.system.revision.JeeslRevisionView;
import org.jeesl.interfaces.model.system.revision.JeeslRevisionViewMapping;
import org.jeesl.web.mbean.prototype.admin.AbstractAdminBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.bean.FacesMessageBean;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.util.comparator.ejb.revision.RevisionEntityComparator;
import net.sf.ahtutils.util.comparator.ejb.revision.RevisionScopeComparator;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public abstract class AbstractAdminRevisionBean <L extends UtilsLang,D extends UtilsDescription,
											RC extends UtilsStatus<RC,L,D>,
											RV extends JeeslRevisionView<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
											RVM extends JeeslRevisionViewMapping<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
											RS extends JeeslRevisionScope<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
											RST extends UtilsStatus<RST,L,D>,
											RE extends JeeslRevisionEntity<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
											REM extends JeeslRevisionEntityMapping<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
											RA extends JeeslRevisionAttribute<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
											RAT extends UtilsStatus<RAT,L,D>>
					extends AbstractAdminBean<L,D>
					implements Serializable,SbToggleBean
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminRevisionBean.class);
	
	protected JeeslIoRevisionFacade<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT> fRevision;
	
	protected final Class<RC> cCategory;
	protected final Class<RV> cView;
	protected final Class<RVM> cViewMapping;
	protected final Class<RS> cScope;
	protected final Class<RST> cScopeType;
	protected final Class<RE> cEntity;
	protected final Class<REM> cMappingEntity;
	protected final Class<RA> cAttribute;
	protected final Class<RAT> cRat;
	
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
	
	protected SbMultiHandler<RC> sbhCategory; public SbMultiHandler<RC> getSbhCategory() {return sbhCategory;}
	
	public AbstractAdminRevisionBean(final Class<L> cL, final Class<D> cD, Class<RC> cCategory,Class<RV> cView,Class<RVM> cViewMapping, Class<RS> cScope, Class<RST> cScopeType, Class<RE> cEntity, Class<REM> cEntityMapping, Class<RA> cAttribute, Class<RAT> cRat)
	{
		super(cL,cD);
		this.cCategory=cCategory;
		this.cView=cView;
		this.cViewMapping=cViewMapping;
		this.cScope=cScope;
		this.cScopeType=cScopeType;
		this.cEntity=cEntity;
		this.cMappingEntity=cEntityMapping;
		this.cAttribute=cAttribute;
		this.cRat=cRat;
	}
	
	protected void initRevisionSuper(String[] langs, FacesMessageBean bMessage, JeeslIoRevisionFacade<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT> fRevision)
	{
		super.initAdmin(langs,cL,cD,bMessage);
		this.fRevision=fRevision;

		efView = EjbRevisionViewFactory.factory(cView);
		efMappingView = EjbRevisionMappingViewFactory.factory(cViewMapping);
		efScope = EjbRevisionScopeFactory.factory(cScope);
		efEntity = EjbRevisionEntityFactory.factory(cL,cD,cEntity);
		efMappingEntity = EjbRevisionMappingEntityFactory.factory(cMappingEntity);
		efAttribute = EjbRevisionAttributeFactory.factory(cAttribute);
		
		comparatorScope = (new RevisionScopeComparator<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>()).factory(RevisionScopeComparator.Type.position);
		comparatorEntity = (new RevisionEntityComparator<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>()).factory(RevisionEntityComparator.Type.position);
		
		categories = fRevision.allOrderedPositionVisible(cCategory);
		sbhCategory = new SbMultiHandler<RC>(cCategory,categories,this);
	}
	
	@Override public void toggled(Class<?> c) throws UtilsLockingException, UtilsConstraintViolationException
	{

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