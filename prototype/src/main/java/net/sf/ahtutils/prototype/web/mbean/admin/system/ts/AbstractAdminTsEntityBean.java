package net.sf.ahtutils.prototype.web.mbean.admin.system.ts;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import org.jeesl.api.facade.module.JeeslTsFacade;
import org.jeesl.interfaces.model.module.ts.JeeslTimeSeries;
import org.jeesl.interfaces.model.module.ts.JeeslTsBridge;
import org.jeesl.interfaces.model.module.ts.JeeslTsData;
import org.jeesl.interfaces.model.module.ts.JeeslTsEntityClass;
import org.jeesl.interfaces.model.module.ts.JeeslTsScope;
import org.jeesl.interfaces.model.module.ts.JeeslTsTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.bean.FacesMessageBean;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.web.UtilsJsfSecurityHandler;
import net.sf.ahtutils.jsf.util.PositionListReorderer;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractAdminTsEntityBean <L extends UtilsLang,
											D extends UtilsDescription,
											CAT extends UtilsStatus<CAT,L,D>,
											SCOPE extends JeeslTsScope<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,BRIDGE,EC,INT,DATA,USER,WS,QAF>,
											UNIT extends UtilsStatus<UNIT,L,D>,
											TS extends JeeslTimeSeries<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,BRIDGE,EC,INT,DATA,USER,WS,QAF>,
											TRANSACTION extends JeeslTsTransaction<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,BRIDGE,EC,INT,DATA,USER,WS,QAF>, 
											BRIDGE extends JeeslTsBridge<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,BRIDGE,EC,INT,DATA,USER,WS,QAF>,
											EC extends JeeslTsEntityClass<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,BRIDGE,EC,INT,DATA,USER,WS,QAF>,
											INT extends UtilsStatus<INT,L,D>,
											DATA extends JeeslTsData<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,BRIDGE,EC,INT,DATA,USER,WS,QAF>,
											USER extends EjbWithId, 
											WS extends UtilsStatus<WS,L,D>,
											QAF extends UtilsStatus<QAF,L,D>>
					extends AbstractAdminTsBean<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,BRIDGE,EC,INT,DATA,USER,WS,QAF>
					implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminTsEntityBean.class);
			
	protected List<EC> classes; public List<EC> getClasses() {return classes;}
	
	protected EC entity; public void setEntity(EC entityClass) {this.entity = entityClass;} public EC getEntity() {return entity;}

	public AbstractAdminTsEntityBean(final Class<L> cL, final Class<D> cD, final Class<TRANSACTION> cTransaction)
	{
		super(cL,cD,cTransaction);
	}
	
	protected void initSuper(String[] langs, JeeslTsFacade<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,BRIDGE,EC,INT,DATA,USER,WS,QAF> fTs, FacesMessageBean bMessage, final Class<L> cLang, final Class<D> cDescription, Class<CAT> cCategory, Class<SCOPE> cScope, Class<UNIT> cUnit, Class<TS> cTs, Class<BRIDGE> cBridge,Class<EC> cEc, Class<INT> cInt, Class<DATA> cData, Class<WS> cWs)
	{
		super.initTsSuper(langs,fTs,bMessage,cLang,cDescription,cCategory,cScope,cUnit,cTs,cBridge,cEc,cInt,cData,cWs);
		reloadClasses();
	}
	
	public void multiToggle(UtilsStatus<?,L,D> o)
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.toggle(o)+" Class: "+o.getClass().getSimpleName());}
		sbhCategory.multiToggle(o);
		reloadClasses();
		cancel();
	}
	
	public void reloadClasses()
	{
		classes = fTs.findClasses(cEc, cCategory, sbhCategory.getSelected(), uiShowInvisible);
		logger.info(AbstractLogMessage.reloaded(cEc, classes));
		Collections.sort(classes, comparatorClass);
	}
	
	public void add() throws UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.addEntity(cEc));
		entity = efClass.build(null);
		entity.setName(efLang.createEmpty(langs));
		entity.setDescription(efDescription.createEmpty(langs));
	}
	
	public void select() throws UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.selectEntity(entity));
		entity = fTs.find(cEc, entity);
		entity = efLang.persistMissingLangs(fTs,langs,entity);
		entity = efDescription.persistMissingLangs(fTs,langs,entity);
	}
	
	public void save() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.saveEntity(entity));
		entity.setCategory(fTs.find(cCategory, entity.getCategory()));
		entity = fTs.save(entity);
		reloadClasses();
		updatePerformed();
	}
	
	public void rm() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.rmEntity(entity));
		fTs.rm(entity);
		entity=null;
		reloadClasses();
	}
	
	public void cancel()
	{
		entity = null;
	}
	
	protected void reorderEntities() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fTs, cEc, classes);Collections.sort(classes, comparatorClass);}
	protected void updatePerformed(){}
	
	@Override protected void updateSecurity2(UtilsJsfSecurityHandler jsfSecurityHandler, String action)
	{
		uiAllowSave = jsfSecurityHandler.allow(action);

		if(logger.isTraceEnabled())
		{
			logger.info(uiAllowSave+" uiAllowSave "+action);
		}
	}
}