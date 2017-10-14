package org.jeesl.web.mbean.prototype.admin.system.revision;

import java.io.Serializable;
import java.util.Collections;

import org.jeesl.api.facade.io.JeeslIoRevisionFacade;
import org.jeesl.interfaces.model.system.revision.JeeslRevisionAttribute;
import org.jeesl.interfaces.model.system.revision.JeeslRevisionEntity;
import org.jeesl.interfaces.model.system.revision.JeeslRevisionEntityMapping;
import org.jeesl.interfaces.model.system.revision.JeeslRevisionScope;
import org.jeesl.interfaces.model.system.revision.JeeslRevisionView;
import org.jeesl.interfaces.model.system.revision.JeeslRevisionViewMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.bean.FacesMessageBean;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.jsf.util.PositionListReorderer;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractAdminRevisionScopeBean <L extends UtilsLang,D extends UtilsDescription,
											RC extends UtilsStatus<RC,L,D>,
											RV extends JeeslRevisionView<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
											RVM extends JeeslRevisionViewMapping<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
											RS extends JeeslRevisionScope<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
											RST extends UtilsStatus<RST,L,D>,
											RE extends JeeslRevisionEntity<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
											REM extends JeeslRevisionEntityMapping<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
											RA extends JeeslRevisionAttribute<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
											RAT extends UtilsStatus<RAT,L,D>>
					extends AbstractAdminRevisionBean<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>
					implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminRevisionScopeBean.class);
	
	private RS scope; public RS getScope() {return scope;} public void setScope(RS scope) {this.scope = scope;}

	public AbstractAdminRevisionScopeBean(final Class<L> cL, final Class<D> cD, Class<RC> cCategory,Class<RV> cView,Class<RVM> cMapping, Class<RS> cScope, Class<RST> cScopeType, Class<RE> cEntity, Class<REM> cEntityMapping, Class<RA> cAttribute, Class<RAT> cRat){super(cL,cD,cCategory,cView,cMapping,cScope,cScopeType,cEntity,cEntityMapping,cAttribute,cRat);}
	
	protected void initSuper(String[] langs, FacesMessageBean bMessage, JeeslIoRevisionFacade<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT> fRevision)
	{
		super.initRevisionSuper(langs,bMessage,fRevision);
		types = fRevision.allOrderedPositionVisible(cRat);
		reloadScopes();
	}
	
	@Override public void toggled(Class<?> c) throws UtilsLockingException, UtilsConstraintViolationException
	{
		super.toggled(c);
		logger.info(AbstractLogMessage.toggled(c));
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