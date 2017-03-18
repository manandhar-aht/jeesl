package org.jeesl.web.mbean.prototype.admin.system.job;

import java.io.Serializable;
import java.util.List;

import org.jeesl.api.facade.system.JeeslJobFacade;
import org.jeesl.factory.ejb.system.job.EjbJobTemplateFactory;
import org.jeesl.interfaces.model.system.job.JeeslJob;
import org.jeesl.interfaces.model.system.job.JeeslJobTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.bean.FacesMessageBean;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractAdminJobTemplateBean <L extends UtilsLang,D extends UtilsDescription,
									TEMPLATE extends JeeslJobTemplate<L,D,TEMPLATE,CATEGORY,TYPE,JOB,STATUS>,
									CATEGORY extends UtilsStatus<CATEGORY,L,D>,
									TYPE extends UtilsStatus<TYPE,L,D>,
									JOB extends JeeslJob<L,D,TEMPLATE,CATEGORY,TYPE,JOB,STATUS>,
									STATUS extends UtilsStatus<STATUS,L,D>
									>
					extends AbstractAdminJobBean<L,D,TEMPLATE,CATEGORY,TYPE,JOB,STATUS>
					implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminJobTemplateBean.class);
	
	private List<TEMPLATE> templates; public List<TEMPLATE> getTemplates() {return templates;}
	
	private TEMPLATE template; public TEMPLATE getTemplate() {return template;} public void setTemplate(TEMPLATE template) {this.template = template;}

	private EjbJobTemplateFactory<L,D,TEMPLATE,CATEGORY,TYPE,JOB,STATUS> efTemplate;
	
	protected void initSuper(String[] langs, FacesMessageBean bMessage, JeeslJobFacade<L,D,TEMPLATE,CATEGORY,TYPE,JOB,STATUS> fJob, final Class<L> cLang, final Class<D> cDescription, Class<TEMPLATE> cTemplate, Class<CATEGORY> cCategory, Class<TYPE> cType, Class<JOB> cJob, Class<STATUS> cStatus)
	{
		super.initSuper(langs,bMessage,fJob,cLang,cDescription,cTemplate,cCategory,cType,cJob,cStatus);
		
		efTemplate = ffTemplate.mail();
		
		if(debugOnInfo)
		{
			logger.info(AbstractLogMessage.multiStatus(cCategory,sbhCategory.getSelected(),sbhCategory.getList()));
			logger.info(AbstractLogMessage.multiStatus(cType,sbhType.getSelected(),sbhType.getList()));
	}
	}
	
	public void multiToggle(UtilsStatus<?,L,D> o)
	{
		logger.info(AbstractLogMessage.toggle(o)+" Class: "+o.getClass().getSimpleName()+" ");
		if(cCategory.isAssignableFrom(o.getClass())){sbhCategory.multiToggle(o);}
		if(cType.isAssignableFrom(o.getClass())){sbhType.multiToggle(o);}
		else {logger.warn("No Handling for toggle class "+o.getClass().getSimpleName()+": "+o.toString());}
		reloadTemplates();
		clear(true);
	}
	
	private void clear(boolean clearTemplate)
	{
		if(clearTemplate){template=null;}
	}
	
	//*************************************************************************************
	protected void reloadTemplates()
	{
//		jobs = fJob.fJobs(sbhCategory.getSelected(),sbhType.getSelected(),sbhStatus.getSelected());
		templates = fJob.all(cTemplate);
		if(debugOnInfo){logger.info(AbstractLogMessage.reloaded(cTemplate,templates));}
//		Collections.sort(templates, comparatorTemplate);
	}
	
	public void addTemplate()
	{
		template = efTemplate.build(null,null);
	}
	
	public void selectTemplate()
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.selectEntity(template));}
	}
}