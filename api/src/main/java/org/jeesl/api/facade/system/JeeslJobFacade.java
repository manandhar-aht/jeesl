package org.jeesl.api.facade.system;

import java.util.List;

import org.jeesl.interfaces.model.system.job.JeeslJob;
import org.jeesl.interfaces.model.system.job.JeeslJobCache;
import org.jeesl.interfaces.model.system.job.JeeslJobRobot;
import org.jeesl.interfaces.model.system.job.JeeslJobTemplate;

import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.EjbWithEmail;

public interface JeeslJobFacade <L extends UtilsLang,D extends UtilsDescription,
								TEMPLATE extends JeeslJobTemplate<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FT,STATUS,ROBOT,CACHE,USER>,
								CATEGORY extends UtilsStatus<CATEGORY,L,D>,
								TYPE extends UtilsStatus<TYPE,L,D>,
								JOB extends JeeslJob<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FT,STATUS,ROBOT,CACHE,USER>,
								FT extends UtilsStatus<FT,L,D>,
								STATUS extends UtilsStatus<STATUS,L,D>,
								ROBOT extends JeeslJobRobot<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FT,STATUS,ROBOT,CACHE,USER>,
								CACHE extends JeeslJobCache<L,D,TEMPLATE,CATEGORY,TYPE,JOB,FT,STATUS,ROBOT,CACHE,USER>,
								USER extends EjbWithEmail
								>
			extends UtilsFacade
{	
	List<JOB> fJobs(List<CATEGORY> categories, List<TYPE> type, List<STATUS> status);
}