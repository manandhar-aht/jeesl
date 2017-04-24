package org.jeesl.factory.ejb.system.job;

import org.jeesl.interfaces.model.system.job.JeeslJob;
import org.jeesl.interfaces.model.system.job.JeeslJobConsumer;
import org.jeesl.interfaces.model.system.job.JeeslJobTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class EjbJobRobotFactory <L extends UtilsLang,D extends UtilsDescription,
									TEMPLATE extends JeeslJobTemplate<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,STATUS,CONSUMER>,
									CATEGORY extends UtilsStatus<CATEGORY,L,D>,
									TYPE extends UtilsStatus<TYPE,L,D>,
									JOB extends JeeslJob<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,STATUS,CONSUMER>,
									FEEDBACK extends UtilsStatus<FEEDBACK,L,D>,
									STATUS extends UtilsStatus<STATUS,L,D>,
									CONSUMER extends JeeslJobConsumer<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,STATUS,CONSUMER>
									>
{
	final static Logger logger = LoggerFactory.getLogger(EjbJobRobotFactory.class);
	
	private final Class<CONSUMER> cConsumer;

	public EjbJobRobotFactory(final Class<CONSUMER> cConsumer)
	{
        this.cConsumer = cConsumer;
	}
 
	public CONSUMER build()
	{
		CONSUMER ejb = null;
		try
		{
			ejb = cConsumer.newInstance();

		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
}