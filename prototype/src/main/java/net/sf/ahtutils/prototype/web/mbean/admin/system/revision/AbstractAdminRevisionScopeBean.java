package net.sf.ahtutils.prototype.web.mbean.admin.system.revision;

import java.io.Serializable;

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
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionEntityMapping;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionScope;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionView;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionViewMapping;
import net.sf.ahtutils.jsf.util.PositionListReorderer;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractAdminRevisionScopeBean <L extends UtilsLang,D extends UtilsDescription,
											RV extends UtilsRevisionView<L,D,RV,RVM,RS,RE,REM,RA>,
											RVM extends UtilsRevisionViewMapping<L,D,RV,RVM,RS,RE,REM,RA>,
											RS extends UtilsRevisionScope<L,D,RV,RVM,RS,RE,REM,RA>,
											RE extends UtilsRevisionEntity<L,D,RV,RVM,RS,RE,REM,RA>,
											REM extends UtilsRevisionEntityMapping<L,D,RV,RVM,RS,RE,REM,RA>,
											RA extends UtilsRevisionAttribute<L,D,RV,RVM,RS,RE,REM,RA>>
					extends AbstractAdminRevisionBean<L,D,RV,RVM,RS,RE,REM,RA>
					implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminRevisionScopeBean.class);
	
	private RS scope; public RS getScope() {return scope;} public void setScope(RS scope) {this.scope = scope;}

	protected void initSuper(String[] langs, FacesMessageBean bMessage, UtilsRevisionFacade<L,D,RV,RVM,RS,RE,REM,RA> fRevision, final Class<L> cLang, final Class<D> cDescription, Class<RV> cView,Class<RVM> cMapping, Class<RS> cScope, Class<RE> cEntity, Class<REM> cEntityMapping,Class<RA> cAttribute)
	{
		super.initRevisionSuper(langs,bMessage,fRevision,cLang,cDescription,cView,cMapping,cScope,cEntity,cEntityMapping,cAttribute);	
		reloadViews();
	}

	public void reloadViews()
	{
		scopes = fRevision.all(cScope);
		logger.info(AbstractLogMessage.reloaded(cScope,scopes));
//		if(showInvisibleCategories){categories = fUtils.allOrderedPosition(cCategory);}
//		else{categories = fUtils.allOrderedPositionVisible(cCategory);}
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
	}
	
	public void save() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.saveEntity(scope));
		scope = fRevision.save(scope);
		bMessage.growlSuccessSaved();
		reloadViews();
		updatePerformed();
	}
	
	public void rm() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.rmEntity(scope));
		fRevision.rm(scope);
		bMessage.growlSuccessRemoved();
		scope=null;
		reloadViews();
		updatePerformed();
	}
	
	public void cancel()
	{
		scope = null;
	}
	
	protected void reorder() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fRevision, scopes);}
	protected void updatePerformed(){}	
}