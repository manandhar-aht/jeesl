package org.jeesl.web.mbean.prototype.system.news;

import java.io.Serializable;
import java.util.List;

import org.jeesl.api.facade.system.JeeslSystemNewsFacade;
import org.jeesl.interfaces.model.system.news.JeeslSystemNews;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class AbstractNewsBean <L extends UtilsLang,D extends UtilsDescription,
								CATEGORY extends UtilsStatus<CATEGORY,L,D>,
								NEWS extends JeeslSystemNews<L,D,CATEGORY,NEWS,USER>,
								USER extends EjbWithId>
					implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractNewsBean.class);
	
	private JeeslSystemNewsFacade<L,D,CATEGORY,NEWS,USER> fNews;
	
	private List<NEWS> news; public List<NEWS> getNews() {return news;}

	protected void init(JeeslSystemNewsFacade<L,D,CATEGORY,NEWS,USER> fNews)
	{	
		this.fNews=fNews;
	}

	protected void reload()
	{
		news = fNews.fActiveNews();
	}
}