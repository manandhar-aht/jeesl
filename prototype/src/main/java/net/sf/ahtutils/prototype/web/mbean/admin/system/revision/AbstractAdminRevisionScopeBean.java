package net.sf.ahtutils.prototype.web.mbean.admin.system.revision;

import java.io.Serializable;
import java.util.Collections;

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
import net.sf.ahtutils.jsf.util.PositionListReorderer;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractAdminRevisionScopeBean <L extends UtilsLang,D extends UtilsDescription,
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
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminRevisionScopeBean.class);
	
	private RS scope; public RS getScope() {return scope;} public void setScope(RS scope) {this.scope = scope;}

	protected void initSuper(String[] langs, FacesMessageBean bMessage, UtilsRevisionFacade<L,D,RC,RV,RVM,RS,RE,REM,RA,RAT> fRevision, final Class<L> cLang, final Class<D> cDescription, Class<RC> cCategory,Class<RV> cView,Class<RVM> cMapping, Class<RS> cScope, Class<RE> cEntity, Class<REM> cEntityMapping,Class<RA> cAttribute,Class<RAT> cRat)
	{
		super.initRevisionSuper(langs,bMessage,fRevision,cLang,cDescription,cCategory,cView,cMapping,cScope,cEntity,cEntityMapping,cAttribute,cRat);
		types = fRevision.allOrderedPositionVisible(cRat);
		reloadScopes();
	}
	
	public void multiToggle(UtilsStatus<?,L,D> o)
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.toggle(o)+" Class: "+o.getClass().getSimpleName());}
		sbhCategory.multiToggle(o);
		reloadScopes();
		cancel();
	}

	public void reloadScopes()
	{
		scopes = fRevision.findScopes(cScope, cCategory, sbhCategory.getSelected(), true);
		logger.info(AbstractLogMessage.reloaded(cScope,scopes));
		Collections.sort(scopes, comparatorScope);
	}
	
	private void reloadScope()
	{
		scope = fRevision.load(cScope, scope);
		attributes = scope.getAttributes();
	}
	
	public void add() throws UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.addEntity(cScope));
		scope = efScope.build();
		scope.setName(efLang.createEmpty(langs));
		scope.setDescription(efDescription.createEmpty(langs));
	}
	
	public void select() throws UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.selectEntity(scope));
		scope = fRevision.find(cScope,scope);
		scope = efLang.persistMissingLangs(fRevision,langs,scope);
		scope = efDescription.persistMissingLangs(fRevision,langs,scope);
		reloadScope();
		attribute=null;
	}
	
	public void save() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.saveEntity(scope));
		scope = fRevision.save(scope);
		bMessage.growlSuccessSaved();
		reloadScopes();
		reloadScope();
		updatePerformed();
	}
	
	public void rm() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.rmEntity(scope));
		fRevision.rm(scope);
		bMessage.growlSuccessRemoved();
		scope=null;
		reloadScopes();
		updatePerformed();
	}
	
	public void cancel()
	{
		scope=null;
		attribute=null;
	}
	
	public void saveAttribute() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.saveEntity(attribute));}
		if(attribute.getType()!=null){attribute.setType(fRevision.find(cRat, attribute.getType()));}
		attribute = fRevision.save(cScope,scope,attribute);
		reloadScope();
		bMessage.growlSuccessSaved();
		updatePerformed();
	}
	
	public void rmAttribute() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.rmEntity(attribute));}
		fRevision.rm(cScope,scope,attribute);
		attribute=null;
		bMessage.growlSuccessRemoved();
		reloadScope();
		updatePerformed();
	}
	
	protected void reorderScopes() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fRevision, cScope, scopes);Collections.sort(scopes, comparatorScope);}
	protected void reorderAttributes() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fRevision, attributes);}
	
	protected void updatePerformed(){}	
}