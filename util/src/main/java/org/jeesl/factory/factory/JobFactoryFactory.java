package org.jeesl.factory.factory;

import org.jeesl.factory.ejb.system.job.EjbJobTemplateFactory;
import org.jeesl.interfaces.model.system.job.JeeslJob;
import org.jeesl.interfaces.model.system.job.JeeslJobTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class JobFactoryFactory<L extends UtilsLang,D extends UtilsDescription,
								TEMPLATE extends JeeslJobTemplate<L,D,TEMPLATE,CATEGORY,TYPE,JOB,STATUS>,
								CATEGORY extends UtilsStatus<CATEGORY,L,D>,
								TYPE extends UtilsStatus<TYPE,L,D>,
								JOB extends JeeslJob<L,D,TEMPLATE,CATEGORY,TYPE,JOB,STATUS>,
								STATUS extends UtilsStatus<STATUS,L,D>
								>
{
	final static Logger logger = LoggerFactory.getLogger(JobFactoryFactory.class);
	
//	final Class<L> cL;
//	final Class<D> cD;
//	final Class<CATEGORY> cCategory;
	private final Class<TEMPLATE> cTemplate;
	
	private JobFactoryFactory(final Class<TEMPLATE> cTemplate)
	{       
		this.cTemplate = cTemplate;
	}
	
	public static <L extends UtilsLang,D extends UtilsDescription,
					TEMPLATE extends JeeslJobTemplate<L,D,TEMPLATE,CATEGORY,TYPE,JOB,STATUS>,
					CATEGORY extends UtilsStatus<CATEGORY,L,D>,
					TYPE extends UtilsStatus<TYPE,L,D>,
					JOB extends JeeslJob<L,D,TEMPLATE,CATEGORY,TYPE,JOB,STATUS>,
					STATUS extends UtilsStatus<STATUS,L,D>
					>
		JobFactoryFactory<L,D,TEMPLATE,CATEGORY,TYPE,JOB,STATUS> factory(final Class<TEMPLATE> cTemplate)
	{
		return new JobFactoryFactory<L,D,TEMPLATE,CATEGORY,TYPE,JOB,STATUS>(cTemplate);
	}
	
	public EjbJobTemplateFactory<L,D,TEMPLATE,CATEGORY,TYPE,JOB,STATUS> mail()
	{
		return new EjbJobTemplateFactory<L,D,TEMPLATE,CATEGORY,TYPE,JOB,STATUS>(cTemplate);
	}
}