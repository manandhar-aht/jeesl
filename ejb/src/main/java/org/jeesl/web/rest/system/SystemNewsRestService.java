package org.jeesl.web.rest.system;

import org.jeesl.interfaces.facade.JeeslSystemNewsFacade;
import org.jeesl.interfaces.model.system.news.JeeslSystemNews;
import org.jeesl.interfaces.rest.system.news.JeeslSystemNewsRestExport;
import org.jeesl.interfaces.rest.system.news.JeeslSystemNewsRestImport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.controller.util.query.StatusQuery;
import net.sf.ahtutils.db.xml.AhtStatusDbInit;
import net.sf.ahtutils.factory.ejb.status.EjbStatusFactory;
import net.sf.ahtutils.factory.xml.status.XmlStatusFactory;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.xml.aht.Aht;
import net.sf.ahtutils.xml.status.Status;
import net.sf.ahtutils.xml.sync.DataUpdate;

public class SystemNewsRestService <L extends UtilsLang,D extends UtilsDescription,
									CATEGORY extends UtilsStatus<CATEGORY,L,D>,
									NEWS extends JeeslSystemNews<L,D,CATEGORY,NEWS,USER>,
									USER extends EjbWithId>
					implements JeeslSystemNewsRestExport,JeeslSystemNewsRestImport
{
	final static Logger logger = LoggerFactory.getLogger(SystemNewsRestService.class);
	
	private JeeslSystemNewsFacade<L,D,CATEGORY,NEWS,USER> fNews;
	
	private final Class<L> cL;
	private final Class<D> cD;
	private final Class<CATEGORY> cCategory;
	
	@SuppressWarnings("unused")
	private final Class<NEWS> cNews;

	private XmlStatusFactory xfStatus;
	
//	private EjbLangFactory<L> efLang;
//	private EjbDescriptionFactory<D> efDescription;
	
	private SystemNewsRestService(JeeslSystemNewsFacade<L,D,CATEGORY,NEWS,USER> fNews,final Class<L> cL, final Class<D> cD, Class<CATEGORY> cCategory, final Class<NEWS> cNews)
	{
		this.fNews=fNews;
		this.cL=cL;
		this.cD=cD;
		
		this.cCategory=cCategory;
		this.cNews=cNews;
	
		xfStatus = new XmlStatusFactory(StatusQuery.get(StatusQuery.Key.StatusExport).getStatus());
	}
	
	public static <L extends UtilsLang,D extends UtilsDescription,
					CATEGORY extends UtilsStatus<CATEGORY,L,D>,
					NEWS extends JeeslSystemNews<L,D,CATEGORY,NEWS,USER>,
					USER extends EjbWithId>
		SystemNewsRestService<L,D,CATEGORY,NEWS,USER>
			factory(JeeslSystemNewsFacade<L,D,CATEGORY,NEWS,USER> fNews,final Class<L> cL, final Class<D> cD, Class<CATEGORY> cCategory, final Class<NEWS> cNews)
	{
		return new SystemNewsRestService<L,D,CATEGORY,NEWS,USER>(fNews,cL,cD,cCategory,cNews);
	}
	
	@Override public Aht exportSystemNewsCategories()
	{
		Aht aht = new Aht();
		for(CATEGORY ejb : fNews.allOrderedPosition(cCategory)){aht.getStatus().add(xfStatus.build(ejb));}
		return aht;
	}
	
	@Override public DataUpdate importSystemNewsCategories(Aht categories){return importStatus(cCategory,categories,null);}

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public <S extends UtilsStatus<S,L,D>, P extends UtilsStatus<P,L,D>> DataUpdate importStatus(Class<S> clStatus, Aht container, Class<P> clParent)
    {
    	for(Status xml : container.getStatus()){xml.setGroup(clStatus.getSimpleName());}
		AhtStatusDbInit asdi = new AhtStatusDbInit();
        asdi.setStatusEjbFactory(EjbStatusFactory.createFactory(clStatus, cL, cD));
        asdi.setFacade(fNews);
        DataUpdate dataUpdate = asdi.iuStatus(container.getStatus(), clStatus, cL, clParent);
        asdi.deleteUnusedStatus(clStatus, cL, cD);
        return dataUpdate;
    }
}