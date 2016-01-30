package net.sf.ahtutils.prototype.web.mbean.admin.ts;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.factory.ejb.ts.EjbTimeSeriesCategoryFactory;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.facade.UtilsTsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.ts.UtilsTimeSeries;
import net.sf.ahtutils.interfaces.model.ts.UtilsTsScope;
import net.sf.ahtutils.interfaces.model.ts.UtilsTsData;
import net.sf.ahtutils.interfaces.web.UtilsJsfSecurityHandler;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.prototype.web.mbean.admin.AbstractAdminBean;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractAdminTsCategoryBean <L extends UtilsLang,
											D extends UtilsDescription,
											CAT extends UtilsStatus<CAT,L,D>,
											SCOPE extends UtilsTsScope<L,D,CAT,SCOPE,UNIT,TS,ENTITY,INT,DATA>,
											UNIT extends UtilsStatus<UNIT,L,D>,
											TS extends UtilsTimeSeries<L,D,CAT,SCOPE,UNIT,TS,ENTITY,INT,DATA>,
											ENTITY extends EjbWithId,
											INT extends UtilsStatus<INT,L,D>,
											DATA extends UtilsTsData<L,D,CAT,SCOPE,UNIT,TS,ENTITY,INT,DATA>>
					extends AbstractAdminBean<L,D>
					implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminTsCategoryBean.class);
	
	protected UtilsTsFacade<L,D,CAT,SCOPE,UNIT,TS,ENTITY,INT,DATA> fTs;
		
	private EjbTimeSeriesCategoryFactory<L,D,CAT,SCOPE,UNIT,TS,ENTITY,INT,DATA> efCategory;
	
	protected Class<SCOPE> cCategory;
	protected Class<UNIT> cUnit;

	protected List<UNIT> units; public List<UNIT> getUnits() {return units;}
	protected List<SCOPE> categories; public List<SCOPE> getCategories() {return categories;}
	
	protected SCOPE category; public void setCategory(SCOPE category) {this.category = category;} public SCOPE getCategory() {return category;}
		
	protected void initSuper(String[] langs, final Class<L> cLang, final Class<D> cDescription, Class<SCOPE> cCategory, Class<UNIT> cUnit)
	{
		super.initAdmin(langs, cLang, cDescription);
		this.cCategory=cCategory;
		this.cUnit=cUnit;
		
		efCategory = EjbTimeSeriesCategoryFactory.factory(cCategory);
		
		allowSave = true;
		showInvisibleCategories = true;
		
		units = fTs.all(cUnit);
		reloadCategories();
	}
	
	public void reloadCategories()
	{
		logger.info("reloadCategories");
		categories = fTs.all(cCategory);
//		if(showInvisibleCategories){categories = fUtils.allOrderedPosition(cCategory);}
//		else{categories = fUtils.allOrderedPositionVisible(cCategory);}
	}
	
	public void add() throws UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.addEntity(cCategory));
		category = efCategory.build(null);
		category.setName(efLang.createEmpty(langs));
		category.setDescription(efDescription.createEmpty(langs));
	}
	
	public void select() throws UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.selectEntity(category));
		category = efLang.persistMissingLangs(fTs,langs,category);
		category = efDescription.persistMissingLangs(fTs,langs,category);
	}
	
	public void save() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.saveEntity(category));
		category.setUnit(fTs.find(cUnit, category.getUnit()));
		category = fTs.save(category);
		reloadCategories();
		updatePerformed();
	}
	
	public void rm() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.rmEntity(category));
		fTs.rm(category);
		category=null;
		reloadCategories();
	}
	
	public void cancel()
	{
		category = null;
	}
	
	protected void reorder() throws UtilsConstraintViolationException, UtilsLockingException
	{
		logger.info("updateOrder "+categories.size());
/*		int i=1;
		for(CAT c : categories)
		{
			c.setPosition(i);
			fUtils.update(c);
			i++;
		}
*/	}
	
	protected void updatePerformed(){}
	
	//Security Handling for Invisible entries
	private boolean showInvisibleCategories; public boolean isShowInvisibleCategories() {return showInvisibleCategories;}
	private boolean allowSave; public boolean getAllowSave() {return allowSave;}
	
	protected void updateSecurity(UtilsJsfSecurityHandler jsfSecurityHandler, String actionDeveloper)
	{
		showInvisibleCategories = jsfSecurityHandler.allow(actionDeveloper);

		if(logger.isTraceEnabled())
		{
			logger.info(showInvisibleCategories+" showInvisibleCategories "+actionDeveloper);
		}
	}
}