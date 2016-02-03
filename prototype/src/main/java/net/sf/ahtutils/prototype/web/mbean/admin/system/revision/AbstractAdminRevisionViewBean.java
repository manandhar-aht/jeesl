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

public class AbstractAdminRevisionViewBean <L extends UtilsLang,D extends UtilsDescription,
											RV extends UtilsRevisionView<L,D,RV,RM,RS,RE,RA>,
											RM extends UtilsRevisionMapping<L,D,RV,RM,RS,RE,RA>,
											RS extends UtilsRevisionScope<L,D,RV,RM,RS,RE,RA>,
											RE extends UtilsRevisionEntity<L,D,RV,RM,RS,RE,RA>,
											RA extends UtilsRevisionAttribute<L,D,RV,RM,RS,RE,RA>>
					extends AbstractAdminRevisionBean<L,D,RV,RM,RS,RE,RA>
					implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminRevisionViewBean.class);
	
	private List<RV> views; public List<RV> getViews() {return views;}
	private RV rv; public RV getRv() {return rv;} public void setRv(RV rv) {this.rv = rv;}
	
	protected void initSuper(String[] langs, FacesMessageBean bMessage, UtilsRevisionFacade<L,D,RV,RM,RS,RE,RA> fRevision, final Class<L> cLang, final Class<D> cDescription, Class<RV> cView,Class<RM> cMapping, Class<RS> cScope, Class<RE> cEntity, Class<RA> cAttribute)
	{
		super.initRevisionSuper(langs,bMessage,fRevision,cLang,cDescription,cView,cMapping,cScope,cEntity,cAttribute);		
		reload();
	}

	public void reload()
	{
		views = fRevision.all(cView);
		logger.info(AbstractLogMessage.reloaded(cView,views));
//		if(showInvisibleCategories){categories = fUtils.allOrderedPosition(cCategory);}
//		else{categories = fUtils.allOrderedPositionVisible(cCategory);}
	}
	
	public void add() throws UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.addEntity(cView));
		rv = efView.build();
		rv.setName(efLang.createEmpty(langs));
		rv.setDescription(efDescription.createEmpty(langs));
	}
	
	public void select() throws UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.selectEntity(rv));
		rv = fRevision.find(cView, rv);
		rv = efLang.persistMissingLangs(fRevision,langs,rv);
		rv = efDescription.persistMissingLangs(fRevision,langs,rv);
	}
	
	public void save() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.saveEntity(rv));
		rv = fRevision.save(rv);
		bMessage.growlSuccessSaved();
		reload();
		updatePerformed();
	}
	
	public void rm() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.rmEntity(rv));
		fRevision.rm(rv);
		bMessage.growlSuccessRemoved();
		rv=null;
		reload();
		updatePerformed();
	}
	
	public void cancel()
	{
		rv = null;
	}
	
	protected void reorder() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fRevision, views);}
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