package org.jeesl.factory.factory;

import org.jeesl.factory.ejb.system.job.EjbJobCacheFactory;
import org.jeesl.factory.ejb.system.job.EjbJobFactory;
import org.jeesl.factory.ejb.system.job.EjbJobRobotFactory;
import org.jeesl.factory.ejb.system.job.EjbJobTemplateFactory;
import org.jeesl.interfaces.model.system.job.JeeslJob;
import org.jeesl.interfaces.model.system.job.JeeslJobCache;
import org.jeesl.interfaces.model.system.job.JeeslJobFeedback;
import org.jeesl.interfaces.model.system.job.JeeslJobRobot;
import org.jeesl.interfaces.model.system.job.JeeslJobTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.EjbWithEmail;

public class JobFactoryFactory<L extends UtilsLang,D extends UtilsDescription,
								TEMPLATE extends JeeslJobTemplate<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,FT,STATUS,ROBOT,CACHE,USER>,
								CATEGORY extends UtilsStatus<CATEGORY,L,D>,
								TYPE extends UtilsStatus<TYPE,L,D>,
								JOB extends JeeslJob<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,FT,STATUS,ROBOT,CACHE,USER>, FEEDBACK extends JeeslJobFeedback<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,FT,STATUS,ROBOT,CACHE,USER>,
								FT extends UtilsStatus<FT,L,D>,
								STATUS extends UtilsStatus<STATUS,L,D>,
								ROBOT extends JeeslJobRobot<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,FT,STATUS,ROBOT,CACHE,USER>,
								CACHE extends JeeslJobCache<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,FT,STATUS,ROBOT,CACHE,USER>,
								USER extends EjbWithEmail
								>
{
	final static Logger logger = LoggerFactory.getLogger(JobFactoryFactory.class);
	
//	final Class<L> cL;
//	final Class<D> cD;
//	final Class<CATEGORY> cCategory;
	private final Class<TEMPLATE> cTemplate;
	private final Class<JOB> cJob;
	
	private final Class<ROBOT> cRobot;
	private final Class<CACHE> cCache;
	
	private JobFactoryFactory(final Class<TEMPLATE> cTemplate, final Class<JOB> cJob, final Class<ROBOT> cRobot, final Class<CACHE> cCache)
	{       
		this.cTemplate = cTemplate;
		this.cJob = cJob;
		this.cRobot = cRobot;
		this.cCache = cCache;
	}
	
	public static <L extends UtilsLang,D extends UtilsDescription,
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
		JobFactoryFactory<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,FT,STATUS,ROBOT,CACHE,USER> factory(final Class<TEMPLATE> cTemplate, final Class<JOB> cJob, final Class<ROBOT> cRobot, final Class<CACHE> cCache)
	{
		return new JobFactoryFactory<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,FT,STATUS,ROBOT,CACHE,USER>(cTemplate,cJob,cRobot,cCache);
	}
	
	public EjbJobTemplateFactory<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,FT,STATUS,ROBOT,CACHE,USER> template()
	{
		return new EjbJobTemplateFactory<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,FT,STATUS,ROBOT,CACHE,USER>(cTemplate);
	}
	
	public EjbJobFactory<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,FT,STATUS,ROBOT,CACHE,USER> job()
	{
		return new EjbJobFactory<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,FT,STATUS,ROBOT,CACHE,USER>(cJob);
	}
	
	public EjbJobRobotFactory<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,FT,STATUS,ROBOT,CACHE,USER> robot()
	{
		return new EjbJobRobotFactory<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,FT,STATUS,ROBOT,CACHE,USER>(cRobot);
	}
	
	public EjbJobCacheFactory<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,FT,STATUS,ROBOT,CACHE,USER> cache()
	{
		return new EjbJobCacheFactory<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,FT,STATUS,ROBOT,CACHE,USER>(cCache);
	}
}