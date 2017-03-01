package org.jeesl.controller.facade;

import javax.persistence.EntityManager;

import org.jeesl.api.facade.system.JeeslJobFacade;
import org.jeesl.interfaces.model.system.job.JeeslJob;
import org.jeesl.interfaces.model.system.job.JeeslJobTemplate;

import net.sf.ahtutils.controller.facade.UtilsFacadeBean;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class JeeslSystemJobFacadeBean<L extends UtilsLang,D extends UtilsDescription,
									TEMPLATE extends JeeslJobTemplate<L,D,TEMPLATE,CATEGORY,TYPE,JOB,STATUS>,
									CATEGORY extends UtilsStatus<CATEGORY,L,D>,
									TYPE extends UtilsStatus<TYPE,L,D>,
									JOB extends JeeslJob<L,D,TEMPLATE,CATEGORY,TYPE,JOB,STATUS>,
									STATUS extends UtilsStatus<STATUS,L,D>
									>
					extends UtilsFacadeBean
					implements JeeslJobFacade<L,D,TEMPLATE,CATEGORY,TYPE,JOB,STATUS>
{	
	@SuppressWarnings("unused")
	private final Class<TEMPLATE> cTemplate;
	@SuppressWarnings("unused")
	private final Class<JOB> cJob;
	
	public JeeslSystemJobFacadeBean(EntityManager em,final Class<TEMPLATE> cTemplate,final Class<JOB> cJob)
	{
		super(em);
		this.cTemplate=cTemplate;
		this.cJob=cJob;
	}
}