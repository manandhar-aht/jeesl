package org.jeesl.api.facade.system;

import java.util.Date;
import java.util.List;

import org.jeesl.interfaces.model.system.io.fr.JeeslFileContainer;
import org.jeesl.interfaces.model.system.job.JeeslJob;
import org.jeesl.interfaces.model.system.job.JeeslJobCache;
import org.jeesl.interfaces.model.system.job.JeeslJobFeedback;
import org.jeesl.interfaces.model.system.job.JeeslJobRobot;
import org.jeesl.interfaces.model.system.job.JeeslJobTemplate;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.EjbWithEmail;

public interface JeeslJobFacade <L extends UtilsLang,D extends UtilsDescription,
								TEMPLATE extends JeeslJobTemplate<L,D,CATEGORY,TYPE,PRIORITY>,
								CATEGORY extends UtilsStatus<CATEGORY,L,D>,
								TYPE extends UtilsStatus<TYPE,L,D>,
								JOB extends JeeslJob<TEMPLATE,PRIORITY,FEEDBACK,STATUS,USER>,
								PRIORITY extends UtilsStatus<PRIORITY,L,D>,
								FEEDBACK extends JeeslJobFeedback<JOB,FT,USER>,
								FT extends UtilsStatus<FT,L,D>,
								STATUS extends UtilsStatus<STATUS,L,D>,
								ROBOT extends JeeslJobRobot<L,D>,
								CACHE extends JeeslJobCache<TEMPLATE,CONTAINER>,
								CONTAINER extends JeeslFileContainer<?,?>,
								USER extends EjbWithEmail
								>
			extends UtilsFacade
{	
	<E extends Enum<E>> TEMPLATE fJobTemplate(E type, String code) throws UtilsNotFoundException;
	List<JOB> fJobs(List<CATEGORY> categories, List<TYPE> type, List<STATUS> status, Date from, Date to);
	
	JOB fActiveJob(TEMPLATE template, String code) throws UtilsNotFoundException;
	CACHE fJobCache(TEMPLATE template, String code) throws UtilsNotFoundException;
	CACHE uJobCache(TEMPLATE template, String code, byte[] data) throws UtilsConstraintViolationException, UtilsLockingException;
	JOB cJob(USER user, List<FEEDBACK> feedbacks, TEMPLATE template, String code, String name, String jsonFilter) throws UtilsNotFoundException, UtilsConstraintViolationException, UtilsLockingException;
}