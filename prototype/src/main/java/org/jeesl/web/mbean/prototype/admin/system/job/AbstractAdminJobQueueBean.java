package org.jeesl.web.mbean.prototype.admin.system.job;

import java.io.Serializable;
import java.util.List;

import org.jeesl.api.facade.system.JeeslJobFacade;
import org.jeesl.interfaces.model.system.job.JeeslJob;
import org.jeesl.interfaces.model.system.job.JeeslJobTemplate;
import org.jeesl.web.mbean.prototype.admin.AbstractAdminBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.bean.FacesMessageBean;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.prototype.controller.handler.ui.SbMultiStatusHandler;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractAdminJobQueueBean <L extends UtilsLang,D extends UtilsDescription,
									TEMPLATE extends JeeslJobTemplate<L,D,TEMPLATE,CATEGORY,TYPE,JOB,STATUS>,
									CATEGORY extends UtilsStatus<CATEGORY,L,D>,
									TYPE extends UtilsStatus<TYPE,L,D>,
									JOB extends JeeslJob<L,D,TEMPLATE,CATEGORY,TYPE,JOB,STATUS>,
									STATUS extends UtilsStatus<STATUS,L,D>
									>
					extends AbstractAdminBean<L,D>
					implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminJobQueueBean.class);
	
	protected JeeslJobFacade<L,D,TEMPLATE,CATEGORY,TYPE,JOB,STATUS> fJob;
	
	private Class<CATEGORY> cCategory;
	private Class<TYPE> cType;
	private Class<JOB> cJob;
	private Class<STATUS> cStatus;

	private List<CATEGORY> categories; public List<CATEGORY> getCategories() {return categories;}
	private List<JOB> jobs; public List<JOB> getJobs() {return jobs;}
	
	private JOB job; public JOB getJob() {return job;} public void setJob(JOB job) {this.job = job;}
	
	protected SbMultiStatusHandler<L,D,CATEGORY> sbhCategory; public SbMultiStatusHandler<L,D,CATEGORY> getSbhCategory() {return sbhCategory;}
	protected SbMultiStatusHandler<L,D,TYPE> sbhType; public SbMultiStatusHandler<L,D,TYPE> getSbhType() {return sbhType;}
	protected SbMultiStatusHandler<L,D,STATUS> sbhStatus; public SbMultiStatusHandler<L,D,STATUS> getSbhStatus() {return sbhStatus;}
	 
	protected void initSuper(String[] langs, FacesMessageBean bMessage, JeeslJobFacade<L,D,TEMPLATE,CATEGORY,TYPE,JOB,STATUS> fJob, final Class<L> cLang, final Class<D> cDescription, Class<CATEGORY> cCategory, Class<TYPE> cType, Class<JOB> cJob, Class<STATUS> cStatus)
	{
		super.initAdmin(langs,cLang,cDescription,bMessage);
		this.fJob=fJob;
		
		this.cCategory=cCategory;
		this.cType=cType;
		this.cJob=cJob;
		this.cStatus=cStatus;
		
		sbhCategory = new SbMultiStatusHandler<L,D,CATEGORY>(cCategory,fJob.allOrderedPositionVisible(cCategory));
		sbhCategory.selectAll();
		
		sbhType = new SbMultiStatusHandler<L,D,TYPE>(cType,fJob.allOrderedPositionVisible(cType));
		sbhType.selectAll();
		
		try
		{
			sbhStatus = new SbMultiStatusHandler<L,D,STATUS>(cStatus,fJob.allOrderedPositionVisible(cStatus));
			sbhStatus.select(fJob.fByCode(cStatus,JeeslJob.Status.queue));
			sbhStatus.select(fJob.fByCode(cStatus,JeeslJob.Status.working));
		}
		catch (UtilsNotFoundException e) {logger.error(e.getMessage());}
		
		if(debugOnInfo)
		{
			logger.info(AbstractLogMessage.multiStatus(cCategory,sbhCategory.getSelected(),sbhCategory.getList()));
			logger.info(AbstractLogMessage.multiStatus(cType,sbhType.getSelected(),sbhType.getList()));
			logger.info(AbstractLogMessage.multiStatus(cStatus,sbhStatus.getSelected(),sbhStatus.getList()));
		}
	}
	
	public void multiToggle(UtilsStatus<?,L,D> o)
	{
		logger.info(AbstractLogMessage.toggle(o)+" Class: "+o.getClass().getSimpleName()+" ");
		if(cCategory.isAssignableFrom(o.getClass())){sbhCategory.multiToggle(o);}
		if(cType.isAssignableFrom(o.getClass())){sbhType.multiToggle(o);}
		if(cStatus.isAssignableFrom(o.getClass())){sbhStatus.multiToggle(o);}
		else {logger.warn("No Handling for toggle class "+o.getClass().getSimpleName()+": "+o.toString());}
		reloadJobs();
		clear(true);
	}
	
	private void clear(boolean clearJob)
	{
		if(clearJob){job=null;}
	}
	
	//*************************************************************************************
	protected void reloadJobs()
	{
		jobs = fJob.fJobs(sbhCategory.getSelected(),sbhType.getSelected(),sbhStatus.getSelected());
		if(debugOnInfo){logger.info(AbstractLogMessage.reloaded(cJob,jobs));}
//		Collections.sort(templates, comparatorTemplate);
	}
		
	public void selectJob()
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.selectEntity(job));}
		
	}
}