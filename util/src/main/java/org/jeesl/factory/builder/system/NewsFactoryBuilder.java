package org.jeesl.factory.builder.system;

import org.jeesl.factory.builder.AbstractFactoryBuilder;
import org.jeesl.factory.ejb.system.EjbSystemNewsFactory;
import org.jeesl.interfaces.model.system.news.JeeslSystemNews;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class NewsFactoryBuilder<L extends UtilsLang,D extends UtilsDescription,
								CATEGORY extends UtilsStatus<CATEGORY,L,D>,
								NEWS extends JeeslSystemNews<L,D,CATEGORY,NEWS,USER>,
								USER extends EjbWithId>
		extends AbstractFactoryBuilder<L,D>
{
	final static Logger logger = LoggerFactory.getLogger(NewsFactoryBuilder.class);
	
	private final Class<CATEGORY> cCategory; public Class<CATEGORY> getClassCategory() {return cCategory;}
	private final Class<NEWS> cNews; public Class<NEWS> getClassNews() {return cNews;}

	public NewsFactoryBuilder(final Class<L> cL,final Class<D> cD,
								final Class<CATEGORY> cCategory,
								final Class<NEWS> cNews)
	{       
		super(cL,cD);
		this.cNews=cNews;
		this.cCategory=cCategory;
	}

	public EjbSystemNewsFactory<L,D,CATEGORY,NEWS,USER> news(String[] localeCodes)
	{
		return new EjbSystemNewsFactory<L,D,CATEGORY,NEWS,USER>(localeCodes,cL,cD,cNews);
	}
}