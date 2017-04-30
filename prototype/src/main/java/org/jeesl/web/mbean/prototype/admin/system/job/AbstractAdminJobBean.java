package org.jeesl.web.mbean.prototype.admin.system.job;

import java.io.Serializable;

import org.jeesl.api.facade.system.JeeslJobFacade;
import org.jeesl.controller.handler.sb.SbMultiHandler;
import org.jeesl.factory.factory.JobFactoryFactory;
import org.jeesl.interfaces.bean.sb.SbToggleBean;
import org.jeesl.interfaces.model.system.job.JeeslJob;
import org.jeesl.interfaces.model.system.job.JeeslJobCache;
import org.jeesl.interfaces.model.system.job.JeeslJobRobot;
import org.jeesl.interfaces.model.system.job.JeeslJobTemplate;
import org.jeesl.web.mbean.prototype.admin.AbstractAdminBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.bean.FacesMessageBean;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.EjbWithEmail;

public abstract class AbstractAdminJobBean <L extends UtilsLang,D extends UtilsDescription,
									TEMPLATE extends JeeslJobTemplate<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FT,STATUS,ROBOT,CACHE,USER>,
									CATEGORY extends UtilsStatus<CATEGORY,L,D>,
									TYPE extends UtilsStatus<TYPE,L,D>,
									JOB extends JeeslJob<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FT,STATUS,ROBOT,CACHE,USER>,
									FT extends UtilsStatus<FT,L,D>,
									STATUS extends UtilsStatus<STATUS,L,D>,
									ROBOT extends JeeslJobRobot<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FT,STATUS,ROBOT,CACHE,USER>,
									CACHE extends JeeslJobCache<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FT,STATUS,ROBOT,CACHE,USER>,
									USER extends EjbWithEmail
									>
					extends AbstractAdminBean<L,D>
					implements Serializable,SbToggleBean
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminJobBean.class);
	
	protected JeeslJobFacade<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FT,STATUS,ROBOT,CACHE,USER> fJob;
	
	protected Class<TEMPLATE> cTemplate;
	protected Class<CATEGORY> cCategory;
	protected Class<TYPE> cType;
	protected Class<JOB> cJob;
	protected Class<STATUS> cStatus;
	protected Class<ROBOT> cRobot;

	protected JobFactoryFactory<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FT,STATUS,ROBOT,CACHE,USER> ffJob;
	
	protected SbMultiHandler<CATEGORY> sbhCategory; public SbMultiHandler<CATEGORY> getSbhCategory() {return sbhCategory;}
	protected SbMultiHandler<TYPE> sbhType; public SbMultiHandler<TYPE> getSbhType() {return sbhType;}
	 
	protected void initSuper(String[] langs, FacesMessageBean bMessage, JeeslJobFacade<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FT,STATUS,ROBOT,CACHE,USER> fJob, final Class<L> cLang, final Class<D> cDescription, Class<TEMPLATE> cTemplate, Class<CATEGORY> cCategory, Class<TYPE> cType, Class<JOB> cJob, Class<STATUS> cStatus, Class<ROBOT> cConsumer)
	{
		super.initAdmin(langs,cLang,cDescription,bMessage);
		this.fJob=fJob;
		
		this.cTemplate=cTemplate;
		this.cCategory=cCategory;
		this.cType=cType;
		this.cJob=cJob;
		this.cStatus=cStatus;
		this.cRobot=cConsumer;
		
		ffJob = JobFactoryFactory.factory(cTemplate,cConsumer);
		
		sbhCategory = new SbMultiHandler<CATEGORY>(cCategory,fJob.allOrderedPositionVisible(cCategory),this);
		sbhCategory.selectAll();
		
		sbhType = new SbMultiHandler<TYPE>(cType,fJob.allOrderedPositionVisible(cType),this);
		sbhType.selectAll();
	}
	
	@Override public void toggled(Class<?> c)
	{
		
	}
}