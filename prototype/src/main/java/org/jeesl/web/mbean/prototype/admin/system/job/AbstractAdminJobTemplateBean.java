package org.jeesl.web.mbean.prototype.admin.system.job;

import java.io.Serializable;
import java.util.List;

import org.jeesl.api.facade.system.JeeslJobFacade;
import org.jeesl.factory.ejb.system.job.EjbJobTemplateFactory;
import org.jeesl.interfaces.model.system.job.JeeslJob;
import org.jeesl.interfaces.model.system.job.JeeslJobCache;
import org.jeesl.interfaces.model.system.job.JeeslJobFeedback;
import org.jeesl.interfaces.model.system.job.JeeslJobRobot;
import org.jeesl.interfaces.model.system.job.JeeslJobTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.interfaces.bean.FacesMessageBean;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.EjbWithEmail;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractAdminJobTemplateBean <L extends UtilsLang,D extends UtilsDescription,
									TEMPLATE extends JeeslJobTemplate<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,FT,STATUS,ROBOT,CACHE,USER>,
									CATEGORY extends UtilsStatus<CATEGORY,L,D>,
									TYPE extends UtilsStatus<TYPE,L,D>,
									JOB extends JeeslJob<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,FT,STATUS,ROBOT,CACHE,USER>,
									FEEDBACK extends JeeslJobFeedback<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,FT,STATUS,ROBOT,CACHE,USER>,
									FT extends UtilsStatus<FT,L,D>,
									STATUS extends UtilsStatus<STATUS,L,D>,
									ROBOT extends JeeslJobRobot<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,FT,STATUS,ROBOT,CACHE,USER>,
									CACHE extends JeeslJobCache<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,FT,STATUS,ROBOT,CACHE,USER>,
									USER extends EjbWithEmail
									>
					extends AbstractAdminJobBean<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,FT,STATUS,ROBOT,CACHE,USER>
					implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminJobTemplateBean.class);
	
	private List<TEMPLATE> templates; public List<TEMPLATE> getTemplates() {return templates;}
	
	private TEMPLATE template; public TEMPLATE getTemplate() {return template;} public void setTemplate(TEMPLATE template) {this.template = template;}

	private EjbJobTemplateFactory<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,FT,STATUS,ROBOT,CACHE,USER> efTemplate;
	
	protected void initSuper(String[] langs, FacesMessageBean bMessage, JeeslJobFacade<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,FT,STATUS,ROBOT,CACHE,USER> fJob, final Class<L> cLang, final Class<D> cDescription, Class<TEMPLATE> cTemplate, Class<CATEGORY> cCategory, Class<TYPE> cType, Class<JOB> cJob, Class<STATUS> cStatus, Class<ROBOT> cRobot, Class<CACHE> cCache)
	{
		super.initSuper(langs,bMessage,fJob,cLang,cDescription,cTemplate,cCategory,cType,cJob,cStatus,cRobot,cCache);
		
		efTemplate = ffJob.template();
		
		if(debugOnInfo)
		{
			logger.info(AbstractLogMessage.multiStatus(cCategory,sbhCategory.getSelected(),sbhCategory.getList()));
			logger.info(AbstractLogMessage.multiStatus(cType,sbhType.getSelected(),sbhType.getList()));
		}
		reloadTemplates();
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
	
	private void reloadTemplates()
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