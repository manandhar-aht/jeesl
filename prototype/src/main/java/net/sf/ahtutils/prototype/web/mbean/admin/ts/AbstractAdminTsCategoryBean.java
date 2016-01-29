package net.sf.ahtutils.prototype.web.mbean.admin.ts;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.ts.UtilsTimeSeries;
import net.sf.ahtutils.interfaces.model.ts.UtilsTsCategory;
import net.sf.ahtutils.interfaces.model.ts.UtilsTsData;
import net.sf.ahtutils.interfaces.web.UtilsJsfSecurityHandler;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractAdminTsCategoryBean <L extends UtilsLang,
											D extends UtilsDescription,
											CAT extends UtilsTsCategory<L,D,CAT,UNIT,TS,ENTITY,INT,DATA>,
											UNIT extends UtilsStatus<UNIT,L,D>,
											TS extends UtilsTimeSeries<L,D,CAT,UNIT,TS,ENTITY,INT,DATA>,
											ENTITY extends EjbWithId,
											INT extends UtilsStatus<INT,L,D>,
											DATA extends UtilsTsData<L,D,CAT,UNIT,TS,ENTITY,INT,DATA>>
					implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminTsCategoryBean.class);
	
	protected String[] langs;
	protected UtilsFacade fTs;
	
	protected Class<CAT> cCategory;

	protected List<CAT> categories; public List<CAT> getCategories() {return categories;}
	protected CAT category; public void setCategory(CAT category) {this.category = category;} public CAT getCategory() {return category;}
		
	
	public void initSuper(String[] langs, Class<CAT> cCategory)
	{
		this.langs=langs;
		this.cCategory=cCategory;
		
//		efLang = new EjbLangFactory<L>(cLang);
//		efDescription = new EjbDescriptionFactory<D>(cDescription);
		
		reloadCategories();
	}
	
	protected void reloadCategories()
	{
		logger.info("reloadCategories");
		categories = fTs.all(cCategory);
//		if(showInvisibleCategories){categories = fUtils.allOrderedPosition(cCategory);}
//		else{categories = fUtils.allOrderedPositionVisible(cCategory);}
	}
	
	protected void addCategory() throws UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.addEntity(cCategory));
//		category = efLang.persistMissingLangs(fSecurity,langs,category);
//		category = efDescription.persistMissingLangs(fSecurity,langs,category);
	}
	
	protected void selectCategory() throws UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.selectEntity(category));
//		category = efLang.persistMissingLangs(fSecurity,langs,category);
//		category = efDescription.persistMissingLangs(fSecurity,langs,category);
	}
	
	protected void reorderCategories() throws UtilsConstraintViolationException, UtilsLockingException
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
	
	//Handling for Invisible entries
	private boolean showInvisibleCategories; public boolean isShowInvisibleCategories() {return showInvisibleCategories;}
	
	protected void updateSecurity(UtilsJsfSecurityHandler jsfSecurityHandler, String actionDeveloper)
	{
		showInvisibleCategories = jsfSecurityHandler.allow(actionDeveloper);

		if(logger.isTraceEnabled())
		{
			logger.info(showInvisibleCategories+" showInvisibleCategories "+actionDeveloper);
		}
	}
}