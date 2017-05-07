package org.jeesl.controller.facade.module;

import javax.persistence.EntityManager;

import org.jeesl.api.facade.module.JeeslFeedbackFacade;
import org.jeesl.interfaces.model.module.feedback.JeeslFeedback;
import org.jeesl.interfaces.model.module.feedback.JeeslFeedbackThread;

import net.sf.ahtutils.controller.facade.UtilsFacadeBean;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.EjbWithEmail;

public class JeeslFeedbackFacadeBean<L extends UtilsLang, D extends UtilsDescription,
										THREAD extends JeeslFeedbackThread<L,D,THREAD,FEEDBACK,STYLE,TYPE,USER>,
										FEEDBACK extends JeeslFeedback<L,D,THREAD,FEEDBACK,STYLE,TYPE,USER>,
										STYLE extends UtilsStatus<STYLE,L,D>,
										TYPE extends UtilsStatus<TYPE,L,D>,
										USER extends EjbWithEmail>
					extends UtilsFacadeBean
					implements JeeslFeedbackFacade<L,D,THREAD,FEEDBACK,STYLE,TYPE,USER>
{	

	private final Class<THREAD> cThread;
		
	public JeeslFeedbackFacadeBean(EntityManager em, final Class<THREAD> cThread)
	{
		super(em);
		this.cThread=cThread;
	}

	@Override public THREAD load(THREAD thread)
	{
		thread = em.find(cThread, thread.getId());
		thread.getFeedbacks().size();
		return thread;
	}
}