package org.jeesl.factory.ejb.system.job;

import java.util.Date;

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

public class EjbJobFactory <L extends UtilsLang,D extends UtilsDescription,
									TEMPLATE extends JeeslJobTemplate<L,D,CATEGORY,TYPE,PRIORITY>,
									CATEGORY extends UtilsStatus<CATEGORY,L,D>,
									TYPE extends UtilsStatus<TYPE,L,D>,
									JOB extends JeeslJob<TEMPLATE,PRIORITY,FEEDBACK,STATUS,USER>,
									PRIORITY extends UtilsStatus<PRIORITY,L,D>,
									FEEDBACK extends JeeslJobFeedback<JOB,FT,USER>,
									FT extends UtilsStatus<FT,L,D>,
									STATUS extends UtilsStatus<STATUS,L,D>,
									ROBOT extends JeeslJobRobot<L,D>,
									CACHE extends JeeslJobCache<TEMPLATE>,
									USER extends EjbWithEmail
									>
{
	final static Logger logger = LoggerFactory.getLogger(EjbJobFactory.class);
	
	private final Class<JOB> cJob;

	public EjbJobFactory(final Class<JOB> cJob)
	{
        this.cJob = cJob;
	}
 
	public JOB build(USER user, TEMPLATE template, STATUS status, String code, String name)
	{
		JOB ejb = null;
		try
		{
			ejb = cJob.newInstance();
			ejb.setTemplate(template);
			ejb.setStatus(status);
			ejb.setPriority(template.getPriority());
			ejb.setRecordCreation(new Date());
			ejb.setCode(code);
			ejb.setName(name);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
}