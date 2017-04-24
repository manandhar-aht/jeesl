package org.jeesl.factory.factory;

import org.jeesl.factory.ejb.system.job.EjbJobRobotFactory;
import org.jeesl.factory.ejb.system.job.EjbJobTemplateFactory;
import org.jeesl.interfaces.model.system.job.JeeslJob;
import org.jeesl.interfaces.model.system.job.JeeslJobRobot;
import org.jeesl.interfaces.model.system.job.JeeslJobTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class JobFactoryFactory<L extends UtilsLang,D extends UtilsDescription,
								TEMPLATE extends JeeslJobTemplate<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,STATUS,ROBOT>,
								CATEGORY extends UtilsStatus<CATEGORY,L,D>,
								TYPE extends UtilsStatus<TYPE,L,D>,
								JOB extends JeeslJob<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,STATUS,ROBOT>,
								FEEDBACK extends UtilsStatus<FEEDBACK,L,D>,
								STATUS extends UtilsStatus<STATUS,L,D>,
								ROBOT extends JeeslJobRobot<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,STATUS,ROBOT>
								>
{
	final static Logger logger = LoggerFactory.getLogger(JobFactoryFactory.class);
	
//	final Class<L> cL;
//	final Class<D> cD;
//	final Class<CATEGORY> cCategory;
	private final Class<TEMPLATE> cTemplate;
	
	private final Class<ROBOT> cConsumer;
	
	private JobFactoryFactory(final Class<TEMPLATE> cTemplate, final Class<ROBOT> cConsumer)
	{       
		this.cTemplate = cTemplate;
		this.cConsumer = cConsumer;
	}
	
	public static <L extends UtilsLang,D extends UtilsDescription,
					TEMPLATE extends JeeslJobTemplate<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,STATUS,ROBOT>,
					CATEGORY extends UtilsStatus<CATEGORY,L,D>,
					TYPE extends UtilsStatus<TYPE,L,D>,
					JOB extends JeeslJob<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,STATUS,ROBOT>,
					FEEDBACK extends UtilsStatus<FEEDBACK,L,D>,
					STATUS extends UtilsStatus<STATUS,L,D>,
					ROBOT extends JeeslJobRobot<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,STATUS,ROBOT>
					>
		JobFactoryFactory<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,STATUS,ROBOT> factory(final Class<TEMPLATE> cTemplate, final Class<ROBOT> cConsumer)
	{
		return new JobFactoryFactory<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,STATUS,ROBOT>(cTemplate,cConsumer);
	}
	
	public EjbJobTemplateFactory<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,STATUS,ROBOT> template()
	{
		return new EjbJobTemplateFactory<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,STATUS,ROBOT>(cTemplate);
	}
	
	public EjbJobRobotFactory<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,STATUS,ROBOT> consumer()
	{
		return new EjbJobRobotFactory<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,STATUS,ROBOT>(cConsumer);
	}
}