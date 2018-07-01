package org.jeesl.web.mbean.prototype.module.ts;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import org.jeesl.api.bean.JeeslTranslationBean;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.api.facade.module.JeeslTsFacade;
import org.jeesl.factory.builder.module.TsFactoryBuilder;
import org.jeesl.interfaces.model.module.ts.JeeslTimeSeries;
import org.jeesl.interfaces.model.module.ts.JeeslTsBridge;
import org.jeesl.interfaces.model.module.ts.JeeslTsData;
import org.jeesl.interfaces.model.module.ts.JeeslTsDataPoint;
import org.jeesl.interfaces.model.module.ts.JeeslTsEntityClass;
import org.jeesl.interfaces.model.module.ts.JeeslTsMultiPoint;
import org.jeesl.interfaces.model.module.ts.JeeslTsSample;
import org.jeesl.interfaces.model.module.ts.JeeslTsScope;
import org.jeesl.interfaces.model.module.ts.JeeslTsTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.EjbWithLangDescription;
import net.sf.ahtutils.interfaces.web.UtilsJsfSecurityHandler;
import net.sf.ahtutils.jsf.util.PositionListReorderer;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractAdminTsScopeBean <L extends UtilsLang,
											D extends UtilsDescription,
											CAT extends UtilsStatus<CAT,L,D>,
											SCOPE extends JeeslTsScope<L,D,CAT,ST,UNIT,EC,INT>,
											ST extends UtilsStatus<ST,L,D>,
											UNIT extends UtilsStatus<UNIT,L,D>,
											MP extends JeeslTsMultiPoint<L,D,UNIT>,
											TS extends JeeslTimeSeries<SCOPE,BRIDGE,INT>,
											TRANSACTION extends JeeslTsTransaction<SOURCE,DATA,USER>,
											SOURCE extends EjbWithLangDescription<L,D>, 
											BRIDGE extends JeeslTsBridge<EC>,
											EC extends JeeslTsEntityClass<L,D,CAT>,
											INT extends UtilsStatus<INT,L,D>,
											DATA extends JeeslTsData<TS,TRANSACTION,SAMPLE,WS>,
											POINT extends JeeslTsDataPoint<DATA>,
											SAMPLE extends JeeslTsSample, 
											USER extends EjbWithId, 
											WS extends UtilsStatus<WS,L,D>,
											QAF extends UtilsStatus<QAF,L,D>>
					extends AbstractAdminTsBean<L,D,CAT,SCOPE,ST,UNIT,MP,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,DATA,POINT,SAMPLE,USER,WS,QAF>
					implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminTsScopeBean.class);

	protected List<SCOPE> scopes; public List<SCOPE> getScopes() {return scopes;}
	protected List<UNIT> units; public List<UNIT> getUnits() {return units;}
	protected List<ST> scopeTypes; public List<ST> getScopeTypes() {return scopeTypes;}
	protected List<INT> opIntervals; public List<INT> getOpIntervals() {return opIntervals;}
	protected List<EC> opClasses; public List<EC> getOpClasses() {return opClasses;}
	
	protected SCOPE scope; public void setScope(SCOPE scope) {this.scope = scope;} public SCOPE getScope() {return scope;}
	protected INT opInterval;public INT getOpInterval(){return opInterval;}public void setOpInterval(INT opInterval){this.opInterval = opInterval;}
	protected INT tbInterval;public INT getTbInterval(){return tbInterval;}public void setTbInterval(INT tbInterval){this.tbInterval = tbInterval;}	
	
	protected EC opClass;public EC getOpClass() {return opClass;}public void setOpClass(EC opClass) {this.opClass = opClass;}
	protected EC tbClass;public EC getTbClass() {return tbClass;}public void setTbClass(EC tbClass) {this.tbClass = tbClass;}
	
	public AbstractAdminTsScopeBean(final TsFactoryBuilder<L,D,CAT,SCOPE,ST,UNIT,MP,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,DATA,POINT,SAMPLE,USER,WS,QAF> fbTs)
	{
		super(fbTs);
	}
	
	protected void postConstructScope(JeeslTranslationBean bTranslation, JeeslTsFacade<L,D,CAT,SCOPE,ST,UNIT,MP,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,DATA,POINT,SAMPLE,USER,WS,QAF> fTs, JeeslFacesMessageBean bMessage)
	{
		super.initTsSuper(bTranslation.getLangKeys().toArray(new String[bTranslation.getLangKeys().size()]),fTs,bMessage);
		initLists();
		reloadScopes();
	}
	
	private void initLists()
	{
		units = fTs.all(fbTs.getClassUnit());
		scopeTypes = fTs.all(fbTs.getClassScopeType());
		opIntervals = fTs.all(fbTs.getClassInterval());
		opClasses = fTs.all(fbTs.getClassEntity());
	}
	
	@Override public void toggled(Class<?> c) throws UtilsLockingException, UtilsConstraintViolationException
	{
		super.toggled(c);
		if(fbTs.getClassCategory().isAssignableFrom(c)){reloadScopes();cancel();}
	}
	
	public void reloadScopes()
	{
		if(debugOnInfo){logger.info("reloadScopes");}
		scopes = fTs.findScopes(fbTs.getClassScope(),fbTs.getClassCategory(), sbhCategory.getSelected(), uiShowInvisible);
		Collections.sort(scopes, comparatorScope);
	}
	
	public void add() throws UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.addEntity(fbTs.getClassScope()));
		scope = efScope.build(null);
		scope.setName(efLang.createEmpty(localeCodes));
		scope.setDescription(efDescription.createEmpty(localeCodes));
	}
	
	public void select() throws UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.selectEntity(scope));
		scope = fTs.find(fbTs.getClassScope(), scope);
		scope = efLang.persistMissingLangs(fTs,localeCodes,scope);
		scope = efDescription.persistMissingLangs(fTs,localeCodes,scope);
	}
	
	public void save() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.saveEntity(scope));
		scope.setUnit(fTs.find(fbTs.getClassUnit(), scope.getUnit()));
		scope.setCategory(fTs.find(fbTs.getClassCategory(), scope.getCategory()));
		if(scope.getType()!=null) {scope.setType(fTs.find(fbTs.getClassScopeType(), scope.getType()));}
		scope = fTs.save(scope);
		reloadScopes();
		updatePerformed();
	}
	
	public void rm() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.rmEntity(scope));
		fTs.rm(scope);
		scope=null;
		reloadScopes();
	}
	
	public void cancel()
	{
		scope = null;
	}
	
	protected void reorderScopes() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fTs, fbTs.getClassScope(), scopes);Collections.sort(scopes, comparatorScope);}
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
	
	
	@Override protected void updateSecurity2(UtilsJsfSecurityHandler jsfSecurityHandler, String viewCode)
	{
		uiShowInvisible= jsfSecurityHandler.allow(viewCode);

		if(logger.isTraceEnabled())
		{
			logger.info(uiShowInvisible+" showInvisible a:"+viewCode);
		}
	}
}