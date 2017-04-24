package org.jeesl.factory.factory;

import org.jeesl.factory.ejb.system.job.EjbJobRobotFactory;
import org.jeesl.factory.ejb.system.job.EjbJobTemplateFactory;
import org.jeesl.interfaces.model.system.job.JeeslJob;
import org.jeesl.interfaces.model.system.job.JeeslJobConsumer;
import org.jeesl.interfaces.model.system.job.JeeslJobTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class JobFactoryFactory<L extends UtilsLang,D extends UtilsDescription,
								TEMPLATE extends JeeslJobTemplate<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,STATUS,CONSUMER>,
								CATEGORY extends UtilsStatus<CATEGORY,L,D>,
								TYPE extends UtilsStatus<TYPE,L,D>,
								JOB extends JeeslJob<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,STATUS,CONSUMER>,
								FEEDBACK extends UtilsStatus<FEEDBACK,L,D>,
								STATUS extends UtilsStatus<STATUS,L,D>,
								CONSUMER extends JeeslJobConsumer<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,STATUS,CONSUMER>
								>
{
	final static Logger logger = LoggerFactory.getLogger(JobFactoryFactory.class);
	
//	final Class<L> cL;
//	final Class<D> cD;
//	final Class<CATEGORY> cCategory;
	private final Class<TEMPLATE> cTemplate;
	
	private final Class<CONSUMER> cConsumer;
	
	private JobFactoryFactory(final Class<TEMPLATE> cTemplate, final Class<CONSUMER> cConsumer)
	{       
		this.cTemplate = cTemplate;
		this.cConsumer = cConsumer;
	}
	
	public static <L extends UtilsLang,D extends UtilsDescription,
					TEMPLATE extends JeeslJobTemplate<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,STATUS,CONSUMER>,
					CATEGORY extends UtilsStatus<CATEGORY,L,D>,
					TYPE extends UtilsStatus<TYPE,L,D>,
					JOB extends JeeslJob<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,STATUS,CONSUMER>,
					FEEDBACK extends UtilsStatus<FEEDBACK,L,D>,
					STATUS extends UtilsStatus<STATUS,L,D>,
					CONSUMER extends JeeslJobConsumer<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,STATUS,CONSUMER>
					>
		JobFactoryFactory<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,STATUS,CONSUMER> factory(final Class<TEMPLATE> cTemplate, final Class<CONSUMER> cConsumer)
	{
		return new JobFactoryFactory<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,STATUS,CONSUMER>(cTemplate,cConsumer);
	}
	
	public EjbJobTemplateFactory<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,STATUS,CONSUMER> template()
	{
		return new EjbJobTemplateFactory<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,STATUS,CONSUMER>(cTemplate);
	}
	
	public EjbJobRobotFactory<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,STATUS,CONSUMER> consumer()
	{
		return new EjbJobRobotFactory<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,STATUS,CONSUMER>(cConsumer);
	}
}