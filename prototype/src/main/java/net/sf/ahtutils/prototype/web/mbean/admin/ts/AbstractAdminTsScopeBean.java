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
import net.sf.ahtutils.interfaces.model.ts.UtilsTsEntity;
import net.sf.ahtutils.interfaces.model.ts.UtilsTsEntityClass;
import net.sf.ahtutils.interfaces.model.ts.UtilsTsScope;
import net.sf.ahtutils.interfaces.web.UtilsJsfSecurityHandler;
import net.sf.ahtutils.jsf.util.PositionListReorderer;
import net.sf.ahtutils.prototype.web.mbean.admin.AbstractAdminBean;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractAdminTsScopeBean <L extends UtilsLang,
											D extends UtilsDescription,
											CAT extends UtilsStatus<CAT,L,D>,
											SCOPE extends UtilsTsScope<L,D,CAT,SCOPE,UNIT,TS,ENTITY,EC,INT,DATA,WS,QAF>,
											UNIT extends UtilsStatus<UNIT,L,D>,
											TS extends UtilsTimeSeries<L,D,CAT,SCOPE,UNIT,TS,ENTITY,EC,INT,DATA,WS,QAF>,
											ENTITY extends UtilsTsEntity<L,D,CAT,SCOPE,UNIT,TS,ENTITY,EC,INT,DATA,WS,QAF>,
											EC extends UtilsTsEntityClass<L,D,CAT,SCOPE,UNIT,TS,ENTITY,EC,INT,DATA,WS,QAF>,
											INT extends UtilsStatus<INT,L,D>,
											DATA extends UtilsTsData<L,D,CAT,SCOPE,UNIT,TS,ENTITY,EC,INT,DATA,WS,QAF>,
											WS extends UtilsStatus<WS,L,D>,
											QAF extends UtilsStatus<QAF,L,D>>
					extends AbstractAdminBean<L,D>
					implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminTsScopeBean.class);
	
	protected UtilsTsFacade<L,D,CAT,SCOPE,UNIT,TS,ENTITY,EC,INT,DATA,WS,QAF> fTs;
		
	private EjbTimeSeriesCategoryFactory<L,D,CAT,SCOPE,UNIT,TS,ENTITY,EC,INT,DATA,WS,QAF> efCategory;
	
	protected Class<CAT> cCategory;
	protected Class<SCOPE> cScope;
	protected Class<UNIT> cUnit;
	protected Class<INT> cInt;
	protected Class<EC> cEc;
	
	protected List<CAT> categories; public List<CAT> getCategories() {return categories;}
	protected List<SCOPE> scopes; public List<SCOPE> getScopes() {return scopes;}
	protected List<UNIT> units; public List<UNIT> getUnits() {return units;}
	protected List<INT> opIntervals; public List<INT> getOpIntervals() {return opIntervals;}
	protected List<EC> opClasses; public List<EC> getOpClasses() {return opClasses;}
	
	protected SCOPE scope; public void setScope(SCOPE scope) {this.scope = scope;} public SCOPE getScope() {return scope;}
	protected INT opInterval;public INT getOpInterval(){return opInterval;}public void setOpInterval(INT opInterval){this.opInterval = opInterval;}
	protected INT tbInterval;public INT getTbInterval(){return tbInterval;}public void setTbInterval(INT tbInterval){this.tbInterval = tbInterval;}	
	
	protected EC opClass;public EC getOpClass() {return opClass;}public void setOpClass(EC opClass) {this.opClass = opClass;}
	protected EC tbClass;public EC getTbClass() {return tbClass;}public void setTbClass(EC tbClass) {this.tbClass = tbClass;}
	
	protected void initSuper(String[] langs, final Class<L> cLang, final Class<D> cDescription, Class<CAT> cCategory, Class<SCOPE> cScope, Class<UNIT> cUnit, Class<EC> cEc, Class<INT> cInt)
	{
		super.initAdmin(langs, cLang, cDescription);
		this.cScope=cScope;
		this.cUnit=cUnit;
		this.cEc=cEc;
		this.cInt=cInt;
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
		opIntervals = fTs.all(cInt);
		opClasses = fTs.all(cEc);
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
		scope = fTs.find(cScope, scope);
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
	
	//OverlayPanel Interval
	public void opAddInterval() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.selectOverlayPanel(opInterval));}
		
		if(!scope.getIntervals().contains(opInterval))
		{
			scope.getIntervals().add(opInterval);
			scope = fTs.save(scope);
			opInterval = null;
			select();
		}
	}
	public void opRmInterval() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		if(tbInterval!=null && scope.getIntervals().contains(tbInterval))
		{
			scope.getIntervals().remove(tbInterval);
			scope = fTs.save(scope);
			tbInterval = null;
			select();
		}
	}
	public void selectTbInterval()
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.selectEntity(tbInterval));}
	}
	
	//OverlayPanel Class
	public void opAddClass() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.selectOverlayPanel(opClass));}
		
		if(!scope.getClasses().contains(opClass))
		{
			scope.getClasses().add(opClass);
			scope = fTs.save(scope);
			opInterval = null;
			select();
		}
	}
	public void opRmClass() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		if(tbClass!=null && scope.getClasses().contains(tbClass))
		{
			scope.getClasses().remove(tbClass);
			scope = fTs.save(scope);
			tbClass = null;
			select();
		}
	}
	public void selectTbClass()
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.selectEntity(tbClass));}
	}
	
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