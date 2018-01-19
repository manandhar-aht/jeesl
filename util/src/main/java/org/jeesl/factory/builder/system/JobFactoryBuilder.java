package org.jeesl.factory.builder.system;

import org.jeesl.factory.builder.AbstractFactoryBuilder;
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

public class JobFactoryBuilder<L extends UtilsLang,D extends UtilsDescription,
								TEMPLATE extends JeeslJobTemplate<L,D,TEMPLATE,CATEGORY,TYPE>,
								CATEGORY extends UtilsStatus<CATEGORY,L,D>,
								TYPE extends UtilsStatus<TYPE,L,D>,
								JOB extends JeeslJob<TEMPLATE,FEEDBACK,STATUS,USER>,
								PRIORITY extends UtilsStatus<PRIORITY,L,D>,
								FEEDBACK extends JeeslJobFeedback<JOB,FT,USER>,	
								FT extends UtilsStatus<FT,L,D>,
								STATUS extends UtilsStatus<STATUS,L,D>,
								ROBOT extends JeeslJobRobot<L,D>,
								CACHE extends JeeslJobCache<TEMPLATE>,
								USER extends EjbWithEmail
								>
				extends AbstractFactoryBuilder<L,D>
{
	final static Logger logger = LoggerFactory.getLogger(JobFactoryBuilder.class);
	
	private final Class<TEMPLATE> cTemplate; public Class<TEMPLATE> getClassTemplate(){return cTemplate;}
	private final Class<CATEGORY> cCategory; public Class<CATEGORY> getClassCategory(){return cCategory;}
	private final Class<TYPE> cType; public Class<TYPE> getClassType(){return cType;}
	private final Class<JOB> cJob; public Class<JOB> getClassJob(){return cJob;}
	private final Class<STATUS> cStatus; public Class<STATUS> getClassStatus(){return cStatus;}
	private final Class<ROBOT> cRobot; public Class<ROBOT> getClassRobot(){return cRobot;}
	private final Class<CACHE> cCache; public Class<CACHE> getClassCache(){return cCache;}
	
	public JobFactoryBuilder(final Class<L> cL, final Class<D> cD, final Class<TEMPLATE> cTemplate, final Class<CATEGORY> cCategory, final Class<TYPE> cType, final Class<JOB> cJob, final Class<STATUS> cStatus, final Class<ROBOT> cRobot, final Class<CACHE> cCache)
	{
		super(cL,cD);
		this.cTemplate = cTemplate;
		this.cCategory=cCategory;
		this.cType=cType;
		this.cJob = cJob;
		this.cStatus=cStatus;
		this.cRobot = cRobot;
		this.cCache = cCache;
	}
		
	public EjbJobTemplateFactory<L,D,TEMPLATE,CATEGORY,TYPE> template()
	{
		return new EjbJobTemplateFactory<L,D,TEMPLATE,CATEGORY,TYPE>(cTemplate);
	}
	
	public EjbJobFactory<L,D,TEMPLATE,CATEGORY,TYPE,JOB,PRIORITY,FEEDBACK,FT,STATUS,ROBOT,CACHE,USER> job()
	{
		return new EjbJobFactory<L,D,TEMPLATE,CATEGORY,TYPE,JOB,PRIORITY,FEEDBACK,FT,STATUS,ROBOT,CACHE,USER>(cJob);
	}
	
	public EjbJobRobotFactory<L,D,TEMPLATE,CATEGORY,TYPE,JOB,PRIORITY,FEEDBACK,FT,STATUS,ROBOT,CACHE,USER> robot()
	{
		return new EjbJobRobotFactory<L,D,TEMPLATE,CATEGORY,TYPE,JOB,PRIORITY,FEEDBACK,FT,STATUS,ROBOT,CACHE,USER>(cRobot);
	}
	
	public EjbJobCacheFactory<L,D,TEMPLATE,CATEGORY,TYPE,JOB,PRIORITY,FEEDBACK,FT,STATUS,ROBOT,CACHE,USER> cache()
	{
		return new EjbJobCacheFactory<L,D,TEMPLATE,CATEGORY,TYPE,JOB,PRIORITY,FEEDBACK,FT,STATUS,ROBOT,CACHE,USER>(cCache);
	}
}