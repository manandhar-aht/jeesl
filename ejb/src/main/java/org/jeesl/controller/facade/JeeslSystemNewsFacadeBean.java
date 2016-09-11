package org.jeesl.controller.facade;

import java.util.List;

import javax.persistence.EntityManager;

import org.jeesl.interfaces.facade.JeeslSystemNewsFacade;
import org.jeesl.interfaces.model.system.news.JeeslSystemNews;

import net.sf.ahtutils.controller.facade.UtilsFacadeBean;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class JeeslSystemNewsFacadeBean<L extends UtilsLang,D extends UtilsDescription,
										CATEGORY extends UtilsStatus<CATEGORY,L,D>,
										NEWS extends JeeslSystemNews<L,D,CATEGORY,NEWS,USER>,
										USER extends EjbWithId>
					extends UtilsFacadeBean
					implements JeeslSystemNewsFacade<L,D,CATEGORY,NEWS,USER>
{	
	private final Class<NEWS> cNews;
	
	public JeeslSystemNewsFacadeBean(EntityManager em, final Class<NEWS> cNews)
	{
		super(em);
		this.cNews=cNews;
	}

	@Override
	public List<NEWS> fActiveNews()
	{
		return this.all(cNews);
	}
}