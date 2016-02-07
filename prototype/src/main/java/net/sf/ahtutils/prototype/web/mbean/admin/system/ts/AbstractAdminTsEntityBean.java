package net.sf.ahtutils.prototype.web.mbean.admin.system.ts;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.factory.ejb.system.ts.EjbTsClassFactory;
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
import net.sf.ahtutils.jsf.util.PositionListReorderer;
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
					extends AbstractAdminTsBean<L,D,CAT,SCOPE,UNIT,TS,ENTITY,EC,INT,DATA,WS,QAF>
					implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminTsEntityBean.class);
			
	private EjbTsClassFactory<L,D,CAT,SCOPE,UNIT,TS,ENTITY,EC,INT,DATA,WS,QAF> efClass;
	
	protected List<EC> classes; public List<EC> getClasses() {return classes;}
	
	protected EC entity; public void setEntity(EC entityClass) {this.entity = entityClass;} public EC getEntity() {return entity;}

	protected void initSuper(String[] langs, UtilsTsFacade<L,D,CAT,SCOPE,UNIT,TS,ENTITY,EC,INT,DATA,WS,QAF> fTs, FacesMessageBean bMessage, final Class<L> cLang, final Class<D> cDescription, Class<CAT> cCategory, Class<SCOPE> cScope, Class<UNIT> cUnit, Class<EC> cEc, Class<INT> cInt)
	{
		super.initTsSuper(langs,fTs,bMessage,cLang,cDescription,cCategory,cScope,cUnit,cEc,cInt);
		
		efClass = EjbTsClassFactory.factory(cEc);
		showInvisibleEntities=true;
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
		classes = fTs.findClasses(cEc, cCategory, sbhCategory.getSelected(), showInvisibleEntities);
		logger.info(AbstractLogMessage.reloaded(cEc, classes));
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
	
	protected void reorderEntities() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fTs, cEc, classes);}
	protected void updatePerformed(){}
		
	private boolean showInvisibleEntities; public boolean isShowInvisibleEntities() {return showInvisibleEntities;}
	
	protected void updateSecurity(UtilsJsfSecurityHandler jsfSecurityHandler, String action)
	{
		allowSave = jsfSecurityHandler.allow(action);

		if(logger.isTraceEnabled())
		{
			logger.info(allowSave+" allowSave "+action);
		}
	}
}