package org.jeesl.api.facade.system;

import java.util.List;

import org.jeesl.interfaces.model.system.job.JeeslJob;
import org.jeesl.interfaces.model.system.job.JeeslJobRobot;
import org.jeesl.interfaces.model.system.job.JeeslJobTemplate;

import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public interface JeeslJobFacade <L extends UtilsLang,D extends UtilsDescription,
								TEMPLATE extends JeeslJobTemplate<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,STATUS,ROBOT>,
								CATEGORY extends UtilsStatus<CATEGORY,L,D>,
								TYPE extends UtilsStatus<TYPE,L,D>,
								JOB extends JeeslJob<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,STATUS,ROBOT>,
								FEEDBACK extends UtilsStatus<FEEDBACK,L,D>,
								STATUS extends UtilsStatus<STATUS,L,D>,
								ROBOT extends JeeslJobRobot<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FEEDBACK,STATUS,ROBOT>
								>
			extends UtilsFacade
{	
	List<JOB> fJobs(List<CATEGORY> categories, List<TYPE> type, List<STATUS> status);
}