package org.jeesl.web.mbean.prototype.system.io.revision;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

import org.jeesl.api.facade.io.JeeslIoRevisionFacade;
import org.jeesl.controller.handler.sb.SbMultiHandler;
import org.jeesl.factory.builder.io.IoRevisionFactoryBuilder;
import org.jeesl.factory.ejb.system.revision.EjbRevisionAttributeFactory;
import org.jeesl.factory.ejb.system.revision.EjbRevisionEntityFactory;
import org.jeesl.factory.ejb.system.revision.EjbRevisionMappingEntityFactory;
import org.jeesl.factory.ejb.system.revision.EjbRevisionMappingViewFactory;
import org.jeesl.factory.ejb.system.revision.EjbRevisionScopeFactory;
import org.jeesl.factory.ejb.system.revision.EjbRevisionViewFactory;
import org.jeesl.interfaces.bean.sb.SbToggleBean;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionAttribute;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntity;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntityMapping;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionScope;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionView;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionViewMapping;
import org.jeesl.util.comparator.ejb.system.io.revision.RevisionEntityComparator;
import org.jeesl.util.comparator.ejb.system.io.revision.RevisionScopeComparator;
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
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public abstract class AbstractAdminRevisionBean <L extends UtilsLang, D extends UtilsDescription,
											RC extends UtilsStatus<RC,L,D>,
											RV extends JeeslRevisionView<L,D,RVM>,
											RVM extends JeeslRevisionViewMapping<RV,RE,REM>,
											RS extends JeeslRevisionScope<L,D,RC,RA>,
											RST extends UtilsStatus<RST,L,D>,
											RE extends JeeslRevisionEntity<L,D,RC,REM,RA>,
											REM extends JeeslRevisionEntityMapping<RS,RST,RE>,
											RA extends JeeslRevisionAttribute<L,D,RE,RER,RAT>,
											RER extends UtilsStatus<RER,L,D>,
											
											RAT extends UtilsStatus<RAT,L,D>>
					extends AbstractAdminBean<L,D>
					implements Serializable,SbToggleBean
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminRevisionBean.class);
	
	protected JeeslIoRevisionFacade<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RER,RAT> fRevision;
	protected final IoRevisionFactoryBuilder<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RER,RAT> fbRevision;
	
	protected final EjbRevisionViewFactory<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RER,RAT> efView;
	protected final EjbRevisionMappingViewFactory<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RER,RAT> efMappingView;
	protected final EjbRevisionScopeFactory<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RER,RAT> efScope;
	protected final EjbRevisionEntityFactory<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RER,RAT> efEntity;
	protected final EjbRevisionMappingEntityFactory<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RER,RAT> efMappingEntity;
	protected final EjbRevisionAttributeFactory<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RER,RAT> efAttribute;
	
	protected List<RA> attributes; public List<RA> getAttributes() {return attributes;}
	protected List<RC> categories; public List<RC> getCategories() {return categories;}
	protected List<RS> scopes; public List<RS> getScopes() {return scopes;}
	protected List<RST> scopeTypes; public List<RST> getScopeTypes() {return scopeTypes;}
	protected List<RE> entities; public List<RE> getEntities() {return entities;}
	protected List<REM> entityMappings; public List<REM> getEntityMappings() {return entityMappings;}
	protected List<RAT> types; public List<RAT> getTypes() {return types;}
	protected List<RER> relations; public List<RER> getrelations() {return relations;}
	
	protected RA attribute; public RA getAttribute() {return attribute;}public void setAttribute(RA attribute) {this.attribute = attribute;}

	protected final Comparator<RS> comparatorScope;
	protected final Comparator<RE> comparatorEntity;
	
	protected SbMultiHandler<RC> sbhCategory; public SbMultiHandler<RC> getSbhCategory() {return sbhCategory;}
	
	public AbstractAdminRevisionBean(final IoRevisionFactoryBuilder<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RER,RAT> fbRevision)
	{
		super(fbRevision.getClassL(),fbRevision.getClassD());
		this.fbRevision=fbRevision;
		
		efView = fbRevision.ejbView();
		efMappingView = fbRevision.ejbMappingView();
		efScope = fbRevision.ejbScope();
		efEntity = fbRevision.ejbEntity();
		efMappingEntity = fbRevision.ejbMappingEntity();
		efAttribute = fbRevision.ejbAttribute();
		
		comparatorEntity = (new RevisionEntityComparator<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RER,RAT>()).factory(RevisionEntityComparator.Type.position);
		comparatorScope = (new RevisionScopeComparator<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RER,RAT>()).factory(RevisionScopeComparator.Type.position);
	}
	
	protected void initRevisionSuper(String[] langs, FacesMessageBean bMessage, JeeslIoRevisionFacade<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RER,RAT> fRevision)
	{
		super.initAdmin(langs,cL,cD,bMessage);
		this.fRevision=fRevision; 
		 
	
		if(fRevision==null) {logger.warn(JeeslIoRevisionFacade.class.getSimpleName()+" is NULL");}
		if(fbRevision==null) {logger.warn(IoRevisionFactoryBuilder.class.getSimpleName()+" is NULL");}
		if(fbRevision.getClassCategory()==null) {logger.warn(IoRevisionFactoryBuilder.class.getSimpleName()+".getClassCategory() is NULL");}
		
		categories = fRevision.allOrderedPositionVisible(fbRevision.getClassCategory());
		sbhCategory = new SbMultiHandler<RC>(fbRevision.getClassCategory(),categories,this);
	}
	
	@Override public void toggled(Class<?> c) throws UtilsLockingException, UtilsConstraintViolationException
	{

	}
	
	public void addAttribute() throws UtilsNotFoundException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.addEntity(fbRevision.getClassAttribute()));}
		attribute = efAttribute.build(null);
		attribute.setName(efLang.createEmpty(langs));
		attribute.setDescription(efDescription.createEmpty(langs));
	}
	
	public void selectAttribute() throws UtilsNotFoundException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.selectEntity(attribute));}
		attribute = fRevision.find(fbRevision.getClassAttribute(), attribute);
		attribute = efLang.persistMissingLangs(fRevision,langs,attribute);
		attribute = efDescription.persistMissingLangs(fRevision,langs,attribute);
	}
	
	public void cancelAttribute()
	{
		attribute=null;
	}
}