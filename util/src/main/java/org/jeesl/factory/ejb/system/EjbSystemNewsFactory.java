package org.jeesl.factory.ejb.system;

import org.jeesl.interfaces.model.system.news.JeeslSystemNews;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.factory.ejb.status.EjbDescriptionFactory;
import net.sf.ahtutils.factory.ejb.status.EjbLangFactory;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class EjbSystemNewsFactory<L extends UtilsLang,D extends UtilsDescription,
								CATEGORY extends UtilsStatus<CATEGORY,L,D>,
								NEWS extends JeeslSystemNews<L,D,CATEGORY,NEWS,USER>,
								USER extends EjbWithId>
{
	final static Logger logger = LoggerFactory.getLogger(EjbSystemNewsFactory.class);
	
	private String[] localeCodes;
	
	final Class<NEWS> cNews;
	
	private EjbLangFactory<L> efLang;
	private EjbDescriptionFactory<D> efDescription;
    
	public EjbSystemNewsFactory(String[] localeCodes, final Class<L> cL,final Class<D> cD,final Class<NEWS> cNews)
	{  
		this.localeCodes=localeCodes;
        this.cNews = cNews;
		efLang = EjbLangFactory.createFactory(cL);
		efDescription = EjbDescriptionFactory.createFactory(cD);
	}
	
	public static <L extends UtilsLang,D extends UtilsDescription,
					CATEGORY extends UtilsStatus<CATEGORY,L,D>,
					NEWS extends JeeslSystemNews<L,D,CATEGORY,NEWS,USER>,
					USER extends EjbWithId>
		EjbSystemNewsFactory<L,D,CATEGORY,NEWS,USER> factory(String[] localeCodes, final Class<L> cL, final Class<D> cD, final Class<NEWS> cNews)
	{
		return new EjbSystemNewsFactory<L,D,CATEGORY,NEWS,USER>(localeCodes,cL,cD,cNews);
	}
    
	public NEWS build(CATEGORY category, USER user)
	{
		NEWS ejb = null;
		try
		{
			ejb = cNews.newInstance();
			ejb.setCategory(category);
			ejb.setAuthor(user);
			ejb.setName(efLang.createEmpty(localeCodes));
			ejb.setDescription(efDescription.createEmpty(localeCodes));
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
}