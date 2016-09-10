package org.jeesl.web.rest.system;

import org.jeesl.interfaces.facade.JeeslSystemPropertyFacade;
import org.jeesl.interfaces.model.system.util.JeeslProperty;
import org.jeesl.interfaces.rest.system.property.JeeslSystemPropertyRestExport;
import org.jeesl.interfaces.rest.system.property.JeeslSystemPropertyRestImport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.db.xml.AhtStatusDbInit;
import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.factory.ejb.status.EjbStatusFactory;
import net.sf.ahtutils.factory.ejb.util.EjbPropertyFactory;
import net.sf.ahtutils.factory.xml.status.XmlTypeFactory;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.monitor.DataUpdateTracker;
import net.sf.ahtutils.web.rest.AbstractUtilsRest;
import net.sf.ahtutils.xml.aht.Container;
import net.sf.ahtutils.xml.status.Status;
import net.sf.ahtutils.xml.sync.DataUpdate;
import net.sf.ahtutils.xml.utils.Property;
import net.sf.ahtutils.xml.utils.Utils;

public class SystemPropertyRestService <L extends UtilsLang,D extends UtilsDescription,
										CATEGORY extends UtilsStatus<CATEGORY,L,D>,
										PROPERTY extends JeeslProperty<L,D>>
					extends AbstractUtilsRest<L,D>
					implements JeeslSystemPropertyRestExport,JeeslSystemPropertyRestImport
{
	final static Logger logger = LoggerFactory.getLogger(SystemPropertyRestService.class);
	
	private JeeslSystemPropertyFacade<L,D,CATEGORY,PROPERTY> fProperty;
	
	private final Class<CATEGORY> cCategory;
	private final Class<PROPERTY> cProperty;
	
	private EjbPropertyFactory<L,D,CATEGORY,PROPERTY> efProperty;
	
	private SystemPropertyRestService(JeeslSystemPropertyFacade<L,D,CATEGORY,PROPERTY> fNews,final String[] localeCodes, final Class<L> cL, final Class<D> cD, Class<CATEGORY> cCategory, final Class<PROPERTY> cProperty)
	{
		super(fNews,localeCodes,cL,cD);
		this.fProperty=fNews;
		
		this.cCategory=cCategory;
		this.cProperty=cProperty;
		
		efProperty = EjbPropertyFactory.factory(cProperty);
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
	public DataUpdate importSystemProperties(Utils utils)
	{
		DataUpdateTracker dut = new DataUpdateTracker(true);
		dut.setType(XmlTypeFactory.build(cProperty.getName(),JeeslProperty.class.getSimpleName()+"-DB Import"));
		
		for(Property property : utils.getProperty())
		{			
			PROPERTY ejb;			
			try
			{
				fProperty.valueStringForKey(property.getKey(),null);
			}
			catch (UtilsNotFoundException e1)
			{
				ejb = efProperty.build(property);
				dut.success();
				try
				{
					ejb = (PROPERTY)fUtils.persist(ejb);
				}
				catch (UtilsConstraintViolationException e) {dut.fail(e, true);}
			}
		}

		return dut.toDataUpdate();
	}

	@Override
	public Container exportSystemProperties() {
		// TODO Auto-generated method stub
		return null;
	}
}