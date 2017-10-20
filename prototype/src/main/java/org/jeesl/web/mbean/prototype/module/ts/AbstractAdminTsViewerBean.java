package org.jeesl.web.mbean.prototype.module.ts;

import java.io.Serializable;
import java.util.Collections;

import org.jeesl.api.facade.module.JeeslTsFacade;
import org.jeesl.controller.handler.op.OpEntitySelectionHandler;
import org.jeesl.controller.handler.sb.SbSingleHandler;
import org.jeesl.interfaces.bean.sb.SbSingleBean;
import org.jeesl.interfaces.model.module.ts.JeeslTimeSeries;
import org.jeesl.interfaces.model.module.ts.JeeslTsBridge;
import org.jeesl.interfaces.model.module.ts.JeeslTsData;
import org.jeesl.interfaces.model.module.ts.JeeslTsEntityClass;
import org.jeesl.interfaces.model.module.ts.JeeslTsSample;
import org.jeesl.interfaces.model.module.ts.JeeslTsScope;
import org.jeesl.interfaces.model.module.ts.JeeslTsTransaction;
import org.jeesl.web.mbean.prototype.admin.module.ts.AbstractAdminTsBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.interfaces.bean.FacesMessageBean;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.EjbWithLangDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractAdminTsViewerBean <L extends UtilsLang, D extends UtilsDescription,
											CAT extends UtilsStatus<CAT,L,D>,
											SCOPE extends JeeslTsScope<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF>,
											UNIT extends UtilsStatus<UNIT,L,D>,
											TS extends JeeslTimeSeries<L,D,SCOPE,BRIDGE,INT>,
											TRANSACTION extends JeeslTsTransaction<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF>,
											SOURCE extends EjbWithLangDescription<L,D>, 
											BRIDGE extends JeeslTsBridge<EC>,
											EC extends JeeslTsEntityClass<L,D,CAT>,
											INT extends UtilsStatus<INT,L,D>,
											DATA extends JeeslTsData<L,D,TS,TRANSACTION,SAMPLE,WS>,
											SAMPLE extends JeeslTsSample, 
											USER extends EjbWithId, 
											WS extends UtilsStatus<WS,L,D>,
											QAF extends UtilsStatus<QAF,L,D>>
					extends AbstractAdminTsBean<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF>
					implements Serializable,SbSingleBean
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminTsViewerBean.class);

	private final OpEntitySelectionHandler<TS> tsh; public OpEntitySelectionHandler<TS> getTsh() {return tsh;}
	
	protected final SbSingleHandler<SCOPE> sbhScope; public SbSingleHandler<SCOPE> getSbhScope() {return sbhScope;}
	protected final SbSingleHandler<EC> sbhClass; public SbSingleHandler<EC> getSbhClass() {return sbhClass;}
	protected final SbSingleHandler<INT> sbhInterval; public SbSingleHandler<INT> getSbhInterval() {return sbhInterval;}
	
	public AbstractAdminTsViewerBean(final Class<L> cL, final Class<D> cD, final Class<CAT> cCategory, final Class<SCOPE> cScope, final Class<UNIT> cUnit, final Class<TS> cTs, final Class<TRANSACTION> cTransaction, final Class<SOURCE> cSource, final Class<BRIDGE> cBridge, final Class<EC> cEc, final Class<INT> cInt, final Class<DATA> cData, final Class<WS> cWs)
	{
		super(cL,cD,cCategory,cScope,cUnit,cTs,cTransaction,cSource,cBridge,cEc,cInt,cData,cWs);
		sbhScope = new SbSingleHandler<SCOPE>(cScope,this);
		sbhClass = new SbSingleHandler<EC>(cEc,this);
		sbhInterval = new SbSingleHandler<INT>(cInt,this);
		
		tsh = new OpEntitySelectionHandler<TS>(null);
	}
	
	protected void initSuper(String[] langs, JeeslTsFacade<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF> fTs, FacesMessageBean bMessage)
	{
		super.initTsSuper(langs,fTs,bMessage);
		sbhCategory.toggleAll();
	}
	
	@Override public void toggled(Class<?> c) throws UtilsLockingException, UtilsConstraintViolationException
	{
		super.toggled(c);
		if(cCategory.isAssignableFrom(c))
		{
			sbhScope.setList(fTs.findScopes(cScope, cCategory, sbhCategory.getSelected(), uiShowInvisible));
			Collections.sort(sbhScope.getList(), comparatorScope);
			if(debugOnInfo){logger.info(AbstractLogMessage.reloaded(cScope,sbhScope.getList()));};
			sbhScope.silentCallback();
		}
	}
	
	@Override
	public void selectSbSingle(EjbWithId item) throws UtilsLockingException, UtilsConstraintViolationException
	{
		if(cScope.isAssignableFrom(item.getClass()))
		{
			sbhClass.clear();sbhInterval.clear();
			
			sbhClass.setList(sbhScope.getSelection().getClasses());
			if(debugOnInfo){logger.info(AbstractLogMessage.reloaded(cEc,sbhClass.getList()));}
			sbhClass.selectDefault();sbhClass.silentCallback();
			
			sbhInterval.setList(sbhScope.getSelection().getIntervals());
			if(debugOnInfo){logger.info(AbstractLogMessage.reloaded(cInt,sbhInterval.getList()));}
			sbhInterval.selectDefault();sbhInterval.silentCallback();
		}
		else if(cEc.isAssignableFrom(item.getClass())) {if(sbhClass.isSelected() && sbhInterval.isSelected()) {reloadBridges();}}
		else if(cInt.isAssignableFrom(item.getClass())) {if(sbhClass.isSelected() && sbhInterval.isSelected()) {reloadBridges();}}
	}
	
	private void reloadBridges()
	{
		tsh.setTbList(fTs.fTimeSeries(sbhScope.getSelection(), sbhInterval.getSelection(), sbhClass.getSelection()));
		if(debugOnInfo){logger.info(AbstractLogMessage.reloaded(cTs,tsh.getTbList()));}
	}
	
	public void selectTimeseries()
	{
		
	}
	
}