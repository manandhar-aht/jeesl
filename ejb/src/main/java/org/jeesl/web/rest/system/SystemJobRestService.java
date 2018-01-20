package org.jeesl.web.rest.system;

import org.jeesl.api.facade.system.JeeslJobFacade;
import org.jeesl.api.rest.system.job.JeeslJobRest;
import org.jeesl.api.rest.system.job.JeeslJobRestExport;
import org.jeesl.api.rest.system.job.JeeslJobRestImport;
import org.jeesl.factory.xml.module.job.XmlJobsFactory;
import org.jeesl.interfaces.model.system.job.JeeslJob;
import org.jeesl.interfaces.model.system.job.JeeslJobCache;
import org.jeesl.interfaces.model.system.job.JeeslJobFeedback;
import org.jeesl.interfaces.model.system.job.JeeslJobRobot;
import org.jeesl.interfaces.model.system.job.JeeslJobTemplate;
import org.jeesl.model.xml.jeesl.Container;
import org.jeesl.model.xml.module.job.Jobs;
import org.jeesl.web.rest.AbstractJeeslRestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.EjbWithEmail;
import net.sf.ahtutils.xml.sync.DataUpdate;

public class SystemJobRestService <L extends UtilsLang,D extends UtilsDescription,
							TEMPLATE extends JeeslJobTemplate<L,D,CATEGORY,TYPE,PRIORITY>,
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
					extends AbstractJeeslRestService<L,D>
					implements JeeslJobRestExport,JeeslJobRestImport,JeeslJobRest
{
	final static Logger logger = LoggerFactory.getLogger(SystemJobRestService.class);
	
	private JeeslJobFacade<L,D,TEMPLATE,CATEGORY,TYPE,JOB,PRIORITY,FEEDBACK,FT,STATUS,ROBOT,CACHE,USER> fJob;
	
	private final Class<CATEGORY> cCategory;
	private final Class<TYPE> cType;
	private final Class<STATUS> cStatus;
	private final Class<FT> cFeedbackType;
	
	private SystemJobRestService(JeeslJobFacade<L,D,TEMPLATE,CATEGORY,TYPE,JOB,PRIORITY,FEEDBACK,FT,STATUS,ROBOT,CACHE,USER> fJob,final Class<L> cL, final Class<D> cD, Class<CATEGORY> cCategory, final Class<TYPE> cType, final Class<FT> cFeedbackType, final Class<STATUS> cStatus)
	{
		super(fJob,cL,cD);
		this.fJob=fJob;
		this.cCategory=cCategory;
		this.cType=cType;
		this.cFeedbackType=cFeedbackType;
		this.cStatus=cStatus;
	}
	
	public static <L extends UtilsLang,D extends UtilsDescription,
					TEMPLATE extends JeeslJobTemplate<L,D,CATEGORY,TYPE,PRIORITY>,
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
	SystemJobRestService<L,D,TEMPLATE,CATEGORY,TYPE,JOB,PRIORITY,FEEDBACK,FT,STATUS,ROBOT,CACHE,USER>
		factory(JeeslJobFacade<L,D,TEMPLATE,CATEGORY,TYPE,JOB,PRIORITY,FEEDBACK,FT,STATUS,ROBOT,CACHE,USER> fJob,final Class<L> cL, final Class<D> cD, Class<CATEGORY> cCategory, final Class<TYPE> cType, final Class<FT> cFeedbackType, final Class<STATUS> cStatus)
	{
		return new SystemJobRestService<L,D,TEMPLATE,CATEGORY,TYPE,JOB,PRIORITY,FEEDBACK,FT,STATUS,ROBOT,CACHE,USER>(fJob,cL,cD,cCategory,cType,cFeedbackType,cStatus);
	}
	
	@Override public Container exportSystemJobCategories() {return xfContainer.build(fJob.allOrderedPosition(cCategory));}
	@Override public Container exportSystemJobType() {return xfContainer.build(fJob.allOrderedPosition(cType));}
	@Override public Container exportSystemJobStatus() {return xfContainer.build(fJob.allOrderedPosition(cStatus));}
	@Override public Container exportSystemJobFeedback() {return xfContainer.build(fJob.allOrderedPosition(cFeedbackType));}
	
	@Override public DataUpdate importSystemJobCategories(Container container){return importStatus(cCategory,container,null);}
	@Override public DataUpdate importSystemJobType(Container container){return importStatus(cType,container,null);}
	@Override public DataUpdate importSystemJobStatus(Container container){return importStatus(cStatus,container,null);}
	@Override public DataUpdate importSystemJobFeedback(Container container){return importStatus(cFeedbackType,container,null);}

	@Override public Jobs grab(String type, int max)
	{
		logger.info("TYEP: "+type+" MAX:"+max);
		Jobs xml = XmlJobsFactory.build();
		
		return xml;
	}
}