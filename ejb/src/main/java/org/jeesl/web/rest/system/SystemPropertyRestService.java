package org.jeesl.web.rest.system;

import org.jeesl.interfaces.facade.JeeslSystemPropertyFacade;
import org.jeesl.interfaces.model.system.util.JeeslProperty;
import org.jeesl.interfaces.rest.system.property.JeeslSystemPropertyRestExport;
import org.jeesl.interfaces.rest.system.property.JeeslSystemPropertyRestImport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.db.xml.AhtStatusDbInit;
import net.sf.ahtutils.factory.ejb.status.EjbStatusFactory;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.web.rest.AbstractUtilsRest;
import net.sf.ahtutils.xml.aht.Container;
import net.sf.ahtutils.xml.status.Status;
import net.sf.ahtutils.xml.sync.DataUpdate;

public class SystemPropertyRestService <L extends UtilsLang,D extends UtilsDescription,
										CATEGORY extends UtilsStatus<CATEGORY,L,D>,
										PROPERTY extends JeeslProperty<L,D>>
					extends AbstractUtilsRest<L,D>
					implements JeeslSystemPropertyRestExport,JeeslSystemPropertyRestImport
{
	final static Logger logger = LoggerFactory.getLogger(SystemPropertyRestService.class);
	
	private JeeslSystemPropertyFacade<L,D,CATEGORY,PROPERTY> fProperty;
	
	private final Class<CATEGORY> cCategory;
	
	@SuppressWarnings("unused")
	private final Class<PROPERTY> cProperty;
	
	private SystemPropertyRestService(JeeslSystemPropertyFacade<L,D,CATEGORY,PROPERTY> fNews,final String[] localeCodes, final Class<L> cL, final Class<D> cD, Class<CATEGORY> cCategory, final Class<PROPERTY> cProperty)
	{
		super(fNews,localeCodes,cL,cD);
		this.fProperty=fNews;
		
		this.cCategory=cCategory;
		this.cProperty=cProperty;
	}
	
	public static <L extends UtilsLang,D extends UtilsDescription,
					CATEGORY extends UtilsStatus<CATEGORY,L,D>,
					PROPERTY extends JeeslProperty<L,D>>
		SystemPropertyRestService<L,D,CATEGORY,PROPERTY>
			factory(JeeslSystemPropertyFacade<L,D,CATEGORY,PROPERTY> fNews, final String[] localeCodes, final Class<L> cL, final Class<D> cD, Class<CATEGORY> cCategory, final Class<PROPERTY> cProperty)
	{
		return new SystemPropertyRestService<L,D,CATEGORY,PROPERTY>(fNews,localeCodes,cL,cD,cCategory,cProperty);
	}
	
	@Override public Container exportSystemPropertyCategories() {return super.exportContainer(cCategory);}
	@Override public DataUpdate importSystemPropertyCategories(Container categories){return importStatus2(cCategory,null,categories);}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
    public <S extends UtilsStatus<S,L,D>, P extends UtilsStatus<P,L,D>> DataUpdate importStatus2(Class<S> clStatus, Class<P> clParent, Container container)
    {
    	for(Status xml : container.getStatus()){xml.setGroup(clStatus.getSimpleName());}
		AhtStatusDbInit asdi = new AhtStatusDbInit();
        asdi.setStatusEjbFactory(EjbStatusFactory.createFactory(clStatus, cL, cD));
        asdi.setFacade(fProperty);
        DataUpdate dataUpdate = asdi.iuStatus(container.getStatus(), clStatus, cL, clParent);
        asdi.deleteUnusedStatus(clStatus, cL, cD);
        return dataUpdate;
    }

	@Override
	public DataUpdate importSystemProperties(Container values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Container exportSystemProperties() {
		// TODO Auto-generated method stub
		return null;
	}
}