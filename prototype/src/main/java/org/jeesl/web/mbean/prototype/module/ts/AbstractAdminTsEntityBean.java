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

public class AbstractAdminTsEntityBean <L extends UtilsLang, D extends UtilsDescription,LOC extends UtilsStatus<LOC,L,D>,
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
					implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminTsEntityBean.class);
			
	protected List<EC> classes; public List<EC> getClasses() {return classes;}
	
	protected EC entity; public void setEntity(EC entityClass) {this.entity = entityClass;} public EC getEntity() {return entity;}

	public AbstractAdminTsEntityBean(final TsFactoryBuilder<L,D,CAT,SCOPE,ST,UNIT,MP,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,DATA,POINT,SAMPLE,USER,WS,QAF> fbTs)
	{
		super(fbTs);
	}
	
	protected void postConstructEntity(JeeslTranslationBean bTranslation, JeeslTsFacade<L,D,CAT,SCOPE,ST,UNIT,MP,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,DATA,POINT,SAMPLE,USER,WS,QAF> fTs, JeeslFacesMessageBean bMessage)
	{
		super.initTsSuper(bTranslation.getLangKeys().toArray(new String[bTranslation.getLangKeys().size()]),fTs,bMessage);
		reloadClasses();
	}
	
	@Override public void toggled(Class<?> c) throws UtilsLockingException, UtilsConstraintViolationException
	{
		super.toggled(c);
		if(fbTs.getClassCategory().isAssignableFrom(c)){reloadClasses();cancel();}
	}
	
	public void reloadClasses()
	{
		classes = fTs.findClasses(fbTs.getClassEntity(), fbTs.getClassCategory(), sbhCategory.getSelected(), uiShowInvisible);
		logger.info(AbstractLogMessage.reloaded(fbTs.getClassEntity(), classes));
		Collections.sort(classes, comparatorClass);
	}
	
	public void add() throws UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.addEntity(fbTs.getClassEntity()));
		entity = efClass.build(null);
		entity.setName(efLang.createEmpty(localeCodes));
		entity.setDescription(efDescription.createEmpty(localeCodes));
	}
	
	public void select() throws UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.selectEntity(entity));
		entity = fTs.find(fbTs.getClassEntity(), entity);
		entity = efLang.persistMissingLangs(fTs,localeCodes,entity);
		entity = efDescription.persistMissingLangs(fTs,localeCodes,entity);
	}
	
	public void save() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.saveEntity(entity));
		entity.setCategory(fTs.find(fbTs.getClassCategory(), entity.getCategory()));
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
	
	protected void reorderEntities() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fTs, fbTs.getClassEntity(), classes);Collections.sort(classes, comparatorClass);}
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