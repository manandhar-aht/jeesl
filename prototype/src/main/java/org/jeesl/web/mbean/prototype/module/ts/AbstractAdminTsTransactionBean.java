package org.jeesl.web.mbean.prototype.module.ts;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.api.facade.module.JeeslTsFacade;
import org.jeesl.api.handler.sb.SbDateIntervalSelection;
import org.jeesl.controller.handler.sb.SbDateHandler;
import org.jeesl.controller.handler.ui.helper.CodeConfirmationHandler;
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
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.EjbWithLangDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractAdminTsTransactionBean <L extends UtilsLang, D extends UtilsDescription,
											CAT extends UtilsStatus<CAT,L,D>,
											SCOPE extends JeeslTsScope<L,D,CAT,ST,UNIT,EC,INT>,
											ST extends UtilsStatus<ST,L,D>,
											UNIT extends UtilsStatus<UNIT,L,D>,
											MP extends JeeslTsMultiPoint<L,D,SCOPE,UNIT>,
											TS extends JeeslTimeSeries<SCOPE,BRIDGE,INT>,
											TRANSACTION extends JeeslTsTransaction<SOURCE,DATA,USER>,
											SOURCE extends EjbWithLangDescription<L,D>, 
											BRIDGE extends JeeslTsBridge<EC>,
											EC extends JeeslTsEntityClass<L,D,CAT>,
											INT extends UtilsStatus<INT,L,D>,
											DATA extends JeeslTsData<TS,TRANSACTION,SAMPLE,WS>,
											POINT extends JeeslTsDataPoint<DATA,MP>,
											SAMPLE extends JeeslTsSample, 
											USER extends EjbWithId, 
											WS extends UtilsStatus<WS,L,D>,
											QAF extends UtilsStatus<QAF,L,D>>
					extends AbstractAdminTsBean<L,D,CAT,SCOPE,ST,UNIT,MP,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,DATA,POINT,SAMPLE,USER,WS,QAF>
					implements Serializable,SbDateIntervalSelection
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminTsTransactionBean.class);
	
	private SbDateHandler sbDateHandler; public SbDateHandler getSbDateHandler() {return sbDateHandler;}
	
	private Map<EC,Map<Long,EjbWithId>> map; public Map<EC, Map<Long, EjbWithId>> getMap() {return map;}
	private List<TRANSACTION> transactions; public List<TRANSACTION> getTransactions() {return transactions;}
	private List<DATA> datas; public List<DATA> getDatas() {return datas;}
	
	
	private TRANSACTION transaction; public TRANSACTION getTransaction() {return transaction;} public void setTransaction(TRANSACTION transaction) {this.transaction = transaction;}

	public CodeConfirmationHandler getCch() { return cch; }

	private CodeConfirmationHandler cch;

	public AbstractAdminTsTransactionBean(final TsFactoryBuilder<L,D,CAT,SCOPE,ST,UNIT,MP,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,DATA,POINT,SAMPLE,USER,WS,QAF> fbTs)
	{
		super(fbTs);
		sbDateHandler = new SbDateHandler(this);
		sbDateHandler.setEnforceStartOfDay(true);
		sbDateHandler.initMonthsToNow(2);
	}
	
	protected void initSuper(String[] langs, JeeslTsFacade<L,D,CAT,SCOPE,ST,UNIT,MP,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,DATA,POINT,SAMPLE,USER,WS,QAF> fTs, JeeslFacesMessageBean bMessage)
	{
		super.initTsSuper(langs,fTs,bMessage);
		cch = new CodeConfirmationHandler();
		reloadTransactions();
	}
	
	@Override public void callbackDateChanged()
	{
		reloadTransactions();
	}
	
	private void reloadTransactions()
	{
		transactions = fTs.fTransactions(null,sbDateHandler.getDate1(),sbDateHandler.toDate2Plus1());
	}
	
	public void selectTransaction()
	{
		logger.info(AbstractLogMessage.selectEntity(transaction));
		datas = fTs.fData(transaction);
		
		map = new HashMap<EC,Map<Long,EjbWithId>>();
		Map<EC,List<Long>> x = efBridge.dataToBridgeIds(datas);
		for(EC ec : x.keySet())
		{
			try
			{
				Class<EjbWithId> c = (Class<EjbWithId>)Class.forName(ec.getCode()).asSubclass(EjbWithId.class);
				Map<Long,EjbWithId> m = new HashMap<Long,EjbWithId>();
				List<EjbWithId> list = fTs.find(c, x.get(ec));
				for(EjbWithId e : list)
				{
					m.put(e.getId(),e);
				}
				map.put(ec,m);
			}
			catch (ClassNotFoundException e) {e.printStackTrace();}
		}
	}
	
	public void deleteTransaction() throws UtilsConstraintViolationException
	{
		if(cch.isCodeConfirmed())
		{
			logger.info(AbstractLogMessage.rmEntity(transaction));
			fTs.deleteTransaction(transaction);
			transaction = null;
			reloadTransactions();
		}
	}
}