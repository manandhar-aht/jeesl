package org.jeesl.web.mbean.prototype.admin.system.job;

import java.io.Serializable;
import java.util.List;

import org.jeesl.api.facade.system.JeeslJobFacade;
import org.jeesl.factory.ejb.system.job.EjbJobRobotFactory;
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

public class AbstractAdminJobRobotBean <L extends UtilsLang,D extends UtilsDescription,
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
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminJobRobotBean.class);
	
	private List<CONSUMER> consumers; public List<CONSUMER> getConsumers() {return consumers;}
	
	private CONSUMER consumer; public CONSUMER getConsumer() {return consumer;} public void setConsumer(CONSUMER consumer) {this.consumer = consumer;}
	
	private EjbJobRobotFactory<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,STATUS,CONSUMER> efConsumer;

	
	protected void initSuper(String[] langs, FacesMessageBean bMessage, JeeslJobFacade<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,STATUS,CONSUMER> fJob, final Class<L> cLang, final Class<D> cDescription, Class<TEMPLATE> cTemplate, Class<CATEGORY> cCategory, Class<TYPE> cType, Class<JOB> cJob, Class<STATUS> cStatus)
	{
		super.initSuper(langs,bMessage,fJob,cLang,cDescription,cTemplate,cCategory,cType,cJob,cStatus,cConsumer);
		
		if(debugOnInfo)
		{
			logger.info(AbstractLogMessage.multiStatus(cCategory,sbhCategory.getSelected(),sbhCategory.getList()));
			logger.info(AbstractLogMessage.multiStatus(cType,sbhType.getSelected(),sbhType.getList()));
		}
	}
	
	public void cancelConsumer(){reset(true);}
	private void reset(boolean clearConsumer)
	{
		if(clearConsumer){consumers=null;}
	}
	
	protected void reloadConsumers()
	{
		consumers = fJob.all(cConsumer);
		if(debugOnInfo){logger.info(AbstractLogMessage.reloaded(cConsumer,consumers));}
//		Collections.sort(templates, comparatorTemplate);
	}
		
	public void selectConsumer()
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.selectEntity(consumer));}
	}
	
	public void addConsumer()
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.addEntity(cConsumer));}
		consumer = efConsumer.build();
		consumer.setName(efLang.createEmpty(langs));
		consumer.setDescription(efDescription.createEmpty(langs));
	}
	
	public void saveConsumer() throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.saveEntity(consumer));}
		consumer = fJob.save(consumer);
		reloadConsumers();
	}
}