package net.sf.ahtutils.prototype.web.mbean.admin.system.revision;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.factory.ejb.system.revision.EjbRevisionViewFactory;
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
import net.sf.ahtutils.prototype.web.mbean.admin.AbstractAdminBean;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractAdminRevisionViewBean <L extends UtilsLang,D extends UtilsDescription,
											RV extends UtilsRevisionView<L,D,RV,RM,RS,RE,RA>,
											RM extends UtilsRevisionMapping<L,D,RV,RM,RS,RE,RA>,
											RS extends UtilsRevisionScope<L,D,RV,RM,RS,RE,RA>,
											RE extends UtilsRevisionEntity<L,D,RV,RM,RS,RE,RA>,
											RA extends UtilsRevisionAttribute<L,D,RV,RM,RS,RE,RA>>
																extends AbstractAdminBean<L,D>
					implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminRevisionViewBean.class);
	
	private UtilsRevisionFacade<L,D,RV,RM,RS,RE,RA> fRevision;
	
	protected Class<RV> cView;
	
	private List<RV> views; public List<RV> getViews() {return views;}
	private RV rv; public RV getRv() {return rv;} public void setRv(RV rv) {this.rv = rv;}

	private EjbRevisionViewFactory<L,D,RV,RM,RS,RE,RA> efView;
	
	protected void initSuper(String[] langs, UtilsRevisionFacade<L,D,RV,RM,RS,RE,RA> fRevision, final Class<L> cLang, final Class<D> cDescription, Class<RV> cView)
	{
		super.initAdmin(langs, cLang, cDescription);
		this.fRevision=fRevision;
		this.cView=cView;
				
		efView = EjbRevisionViewFactory.factory(cView);
		
		reloadViews();
		
		allowSave = true;
	}

	public void reloadViews()
	{
		logger.info("reloadCategories");
		views = fRevision.all(cView);
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
		reloadViews();
		updatePerformed();
	}
	
	public void rm() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.rmEntity(rv));
		fRevision.rm(rv);
		rv=null;
		reloadViews();
		updatePerformed();
	}
	
	public void cancel()
	{
		rv = null;
	}
	
	protected void reorder() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fRevision, views);}
	protected void updatePerformed(){}	
	
	//Security Handling for Invisible entries
	private boolean allowSave; public boolean getAllowSave() {return allowSave;}
	
	protected void updateSecurity(UtilsJsfSecurityHandler jsfSecurityHandler, String actionDeveloper)
	{
		allowSave = jsfSecurityHandler.allow(actionDeveloper);

		if(logger.isTraceEnabled())
		{
			logger.info(allowSave+" allowSave ("+actionDeveloper+")");
		}
	}
}