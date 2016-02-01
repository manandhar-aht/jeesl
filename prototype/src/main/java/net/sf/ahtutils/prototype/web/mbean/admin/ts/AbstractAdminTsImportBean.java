package net.sf.ahtutils.prototype.web.mbean.admin.ts;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.facade.UtilsTsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.ts.UtilsTimeSeries;
import net.sf.ahtutils.interfaces.model.ts.UtilsTsData;
import net.sf.ahtutils.interfaces.model.ts.UtilsTsEntity;
import net.sf.ahtutils.interfaces.model.ts.UtilsTsScope;
import net.sf.ahtutils.prototype.web.mbean.admin.AbstractAdminBean;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractAdminTsImportBean <L extends UtilsLang,
											D extends UtilsDescription,
											CAT extends UtilsStatus<CAT,L,D>,
											SCOPE extends UtilsTsScope<L,D,CAT,SCOPE,UNIT,TS,ENTITY,EC,INT,DATA,WS,QAF>,
											UNIT extends UtilsStatus<UNIT,L,D>,
											TS extends UtilsTimeSeries<L,D,CAT,SCOPE,UNIT,TS,ENTITY,EC,INT,DATA,WS,QAF>,
											ENTITY extends UtilsTsEntity<L,D,CAT,SCOPE,UNIT,TS,ENTITY,EC,INT,DATA,WS,QAF>,
											EC extends UtilsStatus<EC,L,D>,
											INT extends UtilsStatus<INT,L,D>,
											DATA extends UtilsTsData<L,D,CAT,SCOPE,UNIT,TS,ENTITY,EC,INT,DATA,WS,QAF>,
											WS extends UtilsStatus<WS,L,D>,
											QAF extends UtilsStatus<QAF,L,D>>
					extends AbstractAdminBean<L,D>
					implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminTsImportBean.class);
	
	protected UtilsTsFacade<L,D,CAT,SCOPE,UNIT,TS,ENTITY,EC,INT,DATA,WS,QAF> fTs;
	
	protected Class<CAT> cCategory;
	protected Class<SCOPE> cScope;
	protected Class<UNIT> cUnit;
	protected Class<INT> cInt;
	protected Class<EC> cEc;
	
	private List<CAT> categories; public List<CAT> getCategories() {return categories;}
	private List<SCOPE> scopes; public List<SCOPE> getScopes() {return scopes;}
	private List<EC> classes; public List<EC> getClasses() {return classes;}
	private List<INT> intervals; public List<INT> getIntervals() {return intervals;}
	
	private CAT category;public CAT getCategory() {return category;}public void setCategory(CAT category) {this.category = category;}
	private SCOPE scope; public SCOPE getScope() {return scope;} public void setScope(SCOPE scope) {this.scope = scope;}
	private EC clas; public EC getClas() {return clas;} public void setClas(EC clas) {this.clas = clas;}
	private INT interval; public INT getInterval() {return interval;} public void setInterval(INT interval) {this.interval = interval;}
	
	protected void initSuper(String[] langs, final Class<L> cLang, final Class<D> cDescription, Class<CAT> cCategory, Class<SCOPE> cScope, Class<UNIT> cUnit, Class<EC> cEc, Class<INT> cInt)
	{
		super.initAdmin(langs, cLang, cDescription);
		this.cCategory=cCategory;
		this.cScope=cScope;
		this.cUnit=cUnit;
		this.cEc=cEc;
		this.cInt=cInt;
	}
	
	protected void initLists()
	{
		categories = fTs.all(cCategory);
		
		category = null; if(categories.size()>0){category = categories.get(0);}
		changeCategory();
	}
	
	public void changeCategory()
	{
		scope=null;
		clas=null;
		interval=null;
		if(category!=null)
		{
			category = fTs.find(cCategory, category);
			if(debugOnInfo){logger.info(AbstractLogMessage.selectOneMenuChange(category));}
			scopes = fTs.allOrderedPositionVisibleParent(cScope, category);
			if(scopes.size()>0){scope=scopes.get(0);}
			changeScope();
		}
	}
	
	public void changeScope()
	{
		clas=null;
		interval=null;
		if(scope!=null)
		{
			scope = fTs.find(cScope, scope);
			if(debugOnInfo){logger.info(AbstractLogMessage.selectOneMenuChange(scope));}
			
			classes = scope.getClasses();
			if(classes.size()>0){clas=classes.get(0);}
			
			intervals = scope.getIntervals();
			if(intervals.size()>0){interval=intervals.get(0);}
		}
	}
	
	public void changeClass()
	{
		if(clas!=null)
		{
			clas = fTs.find(cEc, clas);
			if(debugOnInfo){logger.info(AbstractLogMessage.selectOneMenuChange(clas));}
		}
	}
	
	public void changeInterval()
	{
		if(interval!=null)
		{
			interval = fTs.find(cInt, interval);
			if(debugOnInfo){logger.info(AbstractLogMessage.selectOneMenuChange(interval));}
		}
	}
}