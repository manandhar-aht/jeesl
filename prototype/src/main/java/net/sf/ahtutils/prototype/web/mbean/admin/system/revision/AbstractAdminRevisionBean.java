package net.sf.ahtutils.prototype.web.mbean.admin.system.revision;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.factory.ejb.system.revision.EjbRevisionAttributeFactory;
import net.sf.ahtutils.factory.ejb.system.revision.EjbRevisionEntityFactory;
import net.sf.ahtutils.factory.ejb.system.revision.EjbRevisionMappingEntityFactory;
import net.sf.ahtutils.factory.ejb.system.revision.EjbRevisionMappingViewFactory;
import net.sf.ahtutils.factory.ejb.system.revision.EjbRevisionScopeFactory;
import net.sf.ahtutils.factory.ejb.system.revision.EjbRevisionViewFactory;
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
import net.sf.ahtutils.prototype.web.mbean.admin.AbstractAdminBean;
import net.sf.ahtutils.util.comparator.ejb.revision.RevisionEntityComparator;
import net.sf.ahtutils.util.comparator.ejb.security.SecurityRoleComparator;

public abstract class AbstractAdminRevisionBean <L extends UtilsLang,D extends UtilsDescription,
											RC extends UtilsStatus<RC,L,D>,
											RV extends UtilsRevisionView<L,D,RC,RV,RVM,RS,RE,REM,RA>,
											RVM extends UtilsRevisionViewMapping<L,D,RC,RV,RVM,RS,RE,REM,RA>,
											RS extends UtilsRevisionScope<L,D,RC,RV,RVM,RS,RE,REM,RA>,
											RE extends UtilsRevisionEntity<L,D,RC,RV,RVM,RS,RE,REM,RA>,
											REM extends UtilsRevisionEntityMapping<L,D,RC,RV,RVM,RS,RE,REM,RA>,
											RA extends UtilsRevisionAttribute<L,D,RC,RV,RVM,RS,RE,REM,RA>>
					extends AbstractAdminBean<L,D>
					implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminRevisionBean.class);
	
	protected UtilsRevisionFacade<L,D,RC,RV,RVM,RS,RE,REM,RA> fRevision;
	
	protected Class<RC> cCategory;
	protected Class<RV> cView;
	protected Class<RVM> cMappingView;
	protected Class<RS> cScope;
	protected Class<RE> cEntity;
	protected Class<REM> cMappingEntity;
	protected Class<RA> cAttribute;
	
	protected EjbRevisionViewFactory<L,D,RC,RV,RVM,RS,RE,REM,RA> efView;
	protected EjbRevisionMappingViewFactory<L,D,RC,RV,RVM,RS,RE,REM,RA> efMappingView;
	protected EjbRevisionScopeFactory<L,D,RC,RV,RVM,RS,RE,REM,RA> efScope;
	protected EjbRevisionEntityFactory<L,D,RC,RV,RVM,RS,RE,REM,RA> efEntity;
	protected EjbRevisionMappingEntityFactory<L,D,RC,RV,RVM,RS,RE,REM,RA> efMappingEntity;
	protected EjbRevisionAttributeFactory<L,D,RC,RV,RVM,RS,RE,REM,RA> efAttribute;
	
	protected List<RC> categories; public List<RC> getCategories() {return categories;}
	protected List<RS> scopes; public List<RS> getScopes() {return scopes;}
	protected List<RE> entities; public List<RE> getEntities() {return entities;}
	
	protected Comparator<RE> comparatorEntity;
	
	protected void initRevisionSuper(String[] langs, FacesMessageBean bMessage, UtilsRevisionFacade<L,D,RC,RV,RVM,RS,RE,REM,RA> fRevision, final Class<L> cLang, final Class<D> cDescription, Class<RC> cCategory,Class<RV> cView, Class<RVM> cMappingView, Class<RS> cScope, Class<RE> cEntity, Class<REM> cEntityMapping, Class<RA> cAttribute)
	{
		super.initAdmin(langs,cLang,cDescription,bMessage);
		this.fRevision=fRevision;
		this.cCategory=cCategory;
		this.cView=cView;
		this.cMappingView=cMappingView;
		this.cScope=cScope;
		this.cEntity=cEntity;
		this.cMappingEntity=cEntityMapping;
		this.cAttribute=cAttribute;
		
		efView = EjbRevisionViewFactory.factory(cView);
		efMappingView = EjbRevisionMappingViewFactory.factory(cMappingView);
		efScope = EjbRevisionScopeFactory.factory(cScope);
		efEntity = EjbRevisionEntityFactory.factory(cEntity);
		efMappingEntity = EjbRevisionMappingEntityFactory.factory(cEntityMapping);
		efAttribute = EjbRevisionAttributeFactory.factory(cAttribute);
		
		comparatorEntity = (new RevisionEntityComparator<L,D,RC,RV,RVM,RS,RE,REM,RA>()).factory(RevisionEntityComparator.Type.position);
		
		allowSave = true;
	}
	
	//Security Handling for Invisible entries
	protected boolean allowSave; public boolean getAllowSave() {return allowSave;}
}