package net.sf.ahtutils.prototype.web.mbean.admin.ts;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.factory.ejb.ts.EjbTimeSeriesCategoryFactory;
import net.sf.ahtutils.interfaces.facade.UtilsTsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.ts.UtilsTimeSeries;
import net.sf.ahtutils.interfaces.model.ts.UtilsTsData;
import net.sf.ahtutils.interfaces.model.ts.UtilsTsScope;
import net.sf.ahtutils.interfaces.web.UtilsJsfSecurityHandler;
import net.sf.ahtutils.jsf.util.PositionListReorderer;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.prototype.web.mbean.admin.AbstractAdminBean;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractAdminTsScopeBean <L extends UtilsLang,
											D extends UtilsDescription,
											CAT extends UtilsStatus<CAT,L,D>,
											SCOPE extends UtilsTsScope<L,D,CAT,SCOPE,UNIT,TS,ENTITY,INT,DATA,WS,QAF>,
											UNIT extends UtilsStatus<UNIT,L,D>,
											TS extends UtilsTimeSeries<L,D,CAT,SCOPE,UNIT,TS,ENTITY,INT,DATA,WS,QAF>,
											ENTITY extends EjbWithId,
											INT extends UtilsStatus<INT,L,D>,
											DATA extends UtilsTsData<L,D,CAT,SCOPE,UNIT,TS,ENTITY,INT,DATA,WS,QAF>,
											WS extends UtilsStatus<WS,L,D>,
											QAF extends UtilsStatus<QAF,L,D>>
					extends AbstractAdminBean<L,D>
					implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminTsScopeBean.class);
	
	protected UtilsTsFacade<L,D,CAT,SCOPE,UNIT,TS,ENTITY,INT,DATA,WS,QAF> fTs;
		
	private EjbTimeSeriesCategoryFactory<L,D,CAT,SCOPE,UNIT,TS,ENTITY,INT,DATA,WS,QAF> efCategory;
	
	protected Class<SCOPE> cScope;
	protected Class<UNIT> cUnit;
	protected Class<CAT> cCategory;

	protected List<SCOPE> scopes; public List<SCOPE> getScopes() {return scopes;}
	protected List<UNIT> units; public List<UNIT> getUnits() {return units;}
	protected List<CAT> categories; public List<CAT> getCategories() {return categories;}
	
	protected SCOPE scope; public void setScope(SCOPE scope) {this.scope = scope;} public SCOPE getScope() {return scope;}
		
	protected void initSuper(String[] langs, final Class<L> cLang, final Class<D> cDescription, Class<SCOPE> cScope, Class<UNIT> cUnit, Class<CAT> cCategory)
	{
		super.initAdmin(langs, cLang, cDescription);
		this.cScope=cScope;
		this.cUnit=cUnit;
		this.cCategory=cCategory;
		
		efCategory = EjbTimeSeriesCategoryFactory.factory(cScope);
		
		allowSave = true;
		showInvisibleScopes = true;
		
		reloadCategories();
	}
	
	protected void initLists()
	{
		units = fTs.all(cUnit);
		categories = fTs.all(cCategory);
	}
	
	public void reloadCategories()
	{
		logger.info("reloadCategories");
		scopes = fTs.all(cScope);
//		if(showInvisibleCategories){categories = fUtils.allOrderedPosition(cCategory);}
//		else{categories = fUtils.allOrderedPositionVisible(cCategory);}
	}
	
	public void add() throws UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.addEntity(cScope));
		scope = efCategory.build(null);
		scope.setName(efLang.createEmpty(langs));
		scope.setDescription(efDescription.createEmpty(langs));
	}
	
	public void select() throws UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.selectEntity(scope));
		scope = efLang.persistMissingLangs(fTs,langs,scope);
		scope = efDescription.persistMissingLangs(fTs,langs,scope);
	}
	
	public void save() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.saveEntity(scope));
		scope.setUnit(fTs.find(cUnit, scope.getUnit()));
		scope = fTs.save(scope);
		reloadCategories();
		updatePerformed();
	}
	
	public void rm() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.rmEntity(scope));
		fTs.rm(scope);
		scope=null;
		reloadCategories();
	}
	
	public void cancel()
	{
		scope = null;
	}
	
	protected void reorder() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fTs, scopes);}
	protected void updatePerformed(){}
	
	//Security Handling for Invisible entries
	private boolean showInvisibleScopes; public boolean isShowInvisibleScopes() {return showInvisibleScopes;}
	private boolean allowSave; public boolean getAllowSave() {return allowSave;}
	
	protected void updateSecurity(UtilsJsfSecurityHandler jsfSecurityHandler, String actionDeveloper)
	{
		showInvisibleScopes = jsfSecurityHandler.allow(actionDeveloper);

		if(logger.isTraceEnabled())
		{
			logger.info(showInvisibleScopes+" showInvisibleScopes "+actionDeveloper);
		}
	}
}