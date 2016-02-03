package net.sf.ahtutils.prototype.web.mbean.admin.system.ts;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.factory.ejb.ts.EjbTimeSeriesClassFactory;
import net.sf.ahtutils.interfaces.bean.FacesMessageBean;
import net.sf.ahtutils.interfaces.facade.UtilsTsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.system.ts.UtilsTimeSeries;
import net.sf.ahtutils.interfaces.model.system.ts.UtilsTsData;
import net.sf.ahtutils.interfaces.model.system.ts.UtilsTsEntity;
import net.sf.ahtutils.interfaces.model.system.ts.UtilsTsEntityClass;
import net.sf.ahtutils.interfaces.model.system.ts.UtilsTsScope;
import net.sf.ahtutils.interfaces.web.UtilsJsfSecurityHandler;
import net.sf.ahtutils.prototype.web.mbean.admin.AbstractAdminBean;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractAdminTsEntityBean <L extends UtilsLang,
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
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminTsEntityBean.class);
	
	protected UtilsTsFacade<L,D,CAT,SCOPE,UNIT,TS,ENTITY,EC,INT,DATA,WS,QAF> fTs;
		
	private EjbTimeSeriesClassFactory<L,D,CAT,SCOPE,UNIT,TS,ENTITY,EC,INT,DATA,WS,QAF> efClass;
	
	protected Class<EC> cEc;
	
	protected List<EC> classes; public List<EC> getClasses() {return classes;}
	
	protected EC entity; public void setEntity(EC entityClass) {this.entity = entityClass;} public EC getEntity() {return entity;}

	protected void initSuper(String[] langs, FacesMessageBean bMessage, final Class<L> cLang, final Class<D> cDescription, Class<EC> cEc)
	{
		super.initAdmin(langs,cLang,cDescription,bMessage);
		allowSave=true;
		this.cEc=cEc;
		
		efClass = EjbTimeSeriesClassFactory.factory(cEc);
		
		
		reloadClasses();
	}
	
	public void reloadClasses()
	{
		logger.info("reloadCategories");
		classes = fTs.all(cEc);
//		if(showInvisibleCategories){categories = fUtils.allOrderedPosition(cCategory);}
//		else{categories = fUtils.allOrderedPositionVisible(cCategory);}
	}
	
	public void add() throws UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.addEntity(cEc));
		entity = efClass.build();
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
	
//	protected void reorder() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fTs, scopes);}
	protected void updatePerformed(){}
	
	//Security Handling
	private boolean allowSave; public boolean getAllowSave() {return allowSave;}
	
	protected void updateSecurity(UtilsJsfSecurityHandler jsfSecurityHandler, String action)
	{
		allowSave = jsfSecurityHandler.allow(action);

		if(logger.isTraceEnabled())
		{
			logger.info(allowSave+" allowSave "+action);
		}
	}
}