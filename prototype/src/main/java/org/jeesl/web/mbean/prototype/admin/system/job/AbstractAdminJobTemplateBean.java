package org.jeesl.web.mbean.prototype.admin.system.job;

import java.io.Serializable;
import java.util.List;

import org.jeesl.api.facade.system.JeeslJobFacade;
import org.jeesl.factory.ejb.system.job.EjbJobTemplateFactory;
import org.jeesl.interfaces.model.system.job.JeeslJob;
import org.jeesl.interfaces.model.system.job.JeeslJobConsumer;
import org.jeesl.interfaces.model.system.job.JeeslJobTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.interfaces.bean.FacesMessageBean;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractAdminJobTemplateBean <L extends UtilsLang,D extends UtilsDescription,
									TEMPLATE extends JeeslJobTemplate<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,STATUS,CONSUMER>,
									CATEGORY extends UtilsStatus<CATEGORY,L,D>,
									TYPE extends UtilsStatus<TYPE,L,D>,
									JOB extends JeeslJob<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,STATUS,CONSUMER>,
									FEEDBACK extends UtilsStatus<FEEDBACK,L,D>,
									STATUS extends UtilsStatus<STATUS,L,D>,
									CONSUMER extends JeeslJobConsumer<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,STATUS,CONSUMER>
									>
					extends AbstractAdminJobBean<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,STATUS,CONSUMER>
					implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminJobTemplateBean.class);
	
	private List<TEMPLATE> templates; public List<TEMPLATE> getTemplates() {return templates;}
	
	private TEMPLATE template; public TEMPLATE getTemplate() {return template;} public void setTemplate(TEMPLATE template) {this.template = template;}

	private EjbJobTemplateFactory<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,STATUS,CONSUMER> efTemplate;
	
	protected void initSuper(String[] langs, FacesMessageBean bMessage, JeeslJobFacade<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,STATUS,CONSUMER> fJob, final Class<L> cLang, final Class<D> cDescription, Class<TEMPLATE> cTemplate, Class<CATEGORY> cCategory, Class<TYPE> cType, Class<JOB> cJob, Class<STATUS> cStatus, Class<CONSUMER> cConsumer)
	{
		super.initSuper(langs,bMessage,fJob,cLang,cDescription,cTemplate,cCategory,cType,cJob,cStatus,cConsumer);
		
		efTemplate = ffJob.template();
		
		if(debugOnInfo)
		{
			logger.info(AbstractLogMessage.multiStatus(cCategory,sbhCategory.getSelected(),sbhCategory.getList()));
			logger.info(AbstractLogMessage.multiStatus(cType,sbhType.getSelected(),sbhType.getList()));
	}
	}
	
	@Override public void toggled(Class<?> c)
	{
		logger.info(AbstractLogMessage.toggled(c));
		super.toggled(c);
		reloadTemplates();
		reset(true);
	}
	
	public void cancelTemplate(){reset(true);}
	private void reset(boolean rTemplate)
	{
		if(rTemplate){template=null;}
	}
	
	protected void reloadTemplates()
	{
//		jobs = fJob.fJobs(sbhCategory.getSelected(),sbhType.getSelected(),sbhStatus.getSelected());
		templates = fJob.all(cTemplate);
		if(debugOnInfo){logger.info(AbstractLogMessage.reloaded(cTemplate,templates));}
//		Collections.sort(templates, comparatorTemplate);
	}
	
	public void addTemplate()
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.addEntity(cTemplate));}
		template = efTemplate.build(null,null);
		template.setName(efLang.createEmpty(langs));
		template.setDescription(efDescription.createEmpty(langs));
	}
	
	public void selectTemplate()
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.selectEntity(template));}
	}
	
	public void saveTemplate() throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.saveEntity(template));}
		template.setCategory(fJob.find(cCategory,template.getCategory()));
		template.setType(fJob.find(cType,template.getType()));
		template = fJob.save(template);
		reloadTemplates();
	}
	
	public void deleteTemplate() throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.rmEntity(template));}
		fJob.rm(template);
		reloadTemplates();
		reset(true);
	}
}