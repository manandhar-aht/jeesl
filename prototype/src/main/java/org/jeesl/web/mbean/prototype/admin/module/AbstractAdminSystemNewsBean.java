package org.jeesl.web.mbean.prototype.admin.module;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jeesl.api.facade.system.JeeslSystemNewsFacade;
import org.jeesl.factory.ejb.system.EjbSystemNewsFactory;
import org.jeesl.interfaces.model.system.news.JeeslSystemNews;
import org.jeesl.web.mbean.prototype.admin.AbstractAdminBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.bean.FacesMessageBean;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractAdminSystemNewsBean <L extends UtilsLang,D extends UtilsDescription,
									CATEGORY extends UtilsStatus<CATEGORY,L,D>,
									NEWS extends JeeslSystemNews<L,D,CATEGORY,NEWS,USER>,
									USER extends EjbWithId>
					extends AbstractAdminBean<L,D>
					implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminSystemNewsBean.class);
	
	protected JeeslSystemNewsFacade<L,D,CATEGORY,NEWS,USER> fNews;
	
	private Class<CATEGORY> cCategory;
	private Class<NEWS> cNews;
	
	private EjbSystemNewsFactory<L,D,CATEGORY,NEWS,USER> efNews;
	
	private List<CATEGORY> categories;public List<CATEGORY> getCategories() {return categories;}
	private List<NEWS> list; public List<NEWS> getList() {return list;}
	private Map<NEWS,Boolean> active; public Map<NEWS,Boolean> getActive() {return active;}
	private NEWS news; public NEWS getNews() {return news;} public void setNews(NEWS news) {this.news = news;}
	protected USER user;
	
	public AbstractAdminSystemNewsBean(final Class<L> cL, final Class<D> cD)
	{
		super(cL,cD);
	}

	protected void initSuper(String[] localeCodes, FacesMessageBean bMessage, JeeslSystemNewsFacade<L,D,CATEGORY,NEWS,USER> fNews, final Class<L> cL, final Class<D> cD, Class<CATEGORY> cCategory, Class<NEWS> cNews, Class<USER> cUser)
	{
		super.initAdmin(localeCodes,cL,cD,bMessage);
		this.fNews=fNews;
		this.cCategory=cCategory;
		this.cNews=cNews;
		categories = fNews.allOrderedPositionVisible(cCategory);
		active = new HashMap<NEWS,Boolean>();
		
		efNews = EjbSystemNewsFactory.factory(localeCodes,cL, cD, cNews);
		reloadNews();
	}
	
	private void reloadNews()
	{
		list = fNews.allOrdered(cNews,JeeslSystemNews.Attributes.validFrom.toString(),false);
		active.clear();
		for(NEWS n : list){active.put(n,false);}
		for(NEWS n : fNews.fActiveNews()){active.put(n,true);}
		
	}
	
	public void addNews() throws UtilsNotFoundException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.addEntity(cNews));}
		news = efNews.build(null,user);
	}
	
	public void selectNews() throws UtilsNotFoundException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.selectEntity(news));}
		news = fNews.find(cNews,news);
		news = efLang.persistMissingLangs(fNews,langs,news);
		news = efDescription.persistMissingLangs(fNews,langs,news);
	}
	
	public void saveNews() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.saveEntity(news));}
		if(news.getCategory()!=null){news.setCategory(fNews.find(cCategory, news.getCategory()));}
		news = fNews.save(news);
		reloadNews();
		bMessage.growlSuccessSaved();
	}
	
	public void rmNews() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.rmEntity(news));}
		fNews.rm(news);
		news=null;
		bMessage.growlSuccessRemoved();
		reloadNews();
	}
	
	public void cancelNews()
	{
		news = null;
	}
}