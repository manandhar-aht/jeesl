package org.jeesl.web.rest.system;

import org.jeesl.interfaces.facade.JeeslIoReportFacade;
import org.jeesl.interfaces.rest.system.io.report.JeeslIoReportRestExport;
import org.jeesl.interfaces.rest.system.io.report.JeeslIoReportRestImport;
import org.jeesl.model.xml.jeesl.Container;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.controller.util.query.StatusQuery;
import net.sf.ahtutils.db.xml.AhtStatusDbInit;
import net.sf.ahtutils.factory.ejb.status.EjbStatusFactory;
import net.sf.ahtutils.factory.xml.status.XmlStatusFactory;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.xml.status.Status;
import net.sf.ahtutils.xml.sync.DataUpdate;

public class IoReportRestService <L extends UtilsLang,D extends UtilsDescription,
									CATEGORY extends UtilsStatus<CATEGORY,L,D>,
									TYPE extends UtilsStatus<TYPE,L,D>>
					implements JeeslIoReportRestExport,JeeslIoReportRestImport
{
	final static Logger logger = LoggerFactory.getLogger(IoReportRestService.class);
	
	private JeeslIoReportFacade<L,D,CATEGORY,TYPE> fReport;
	
	private final Class<L> cL;
	private final Class<D> cD;
	private final Class<CATEGORY> cCategory;
	private final Class<TYPE> cType;

	private XmlStatusFactory xfStatus;
	
//	private EjbLangFactory<L> efLang;
//	private EjbDescriptionFactory<D> efDescription;
	
	private IoReportRestService(JeeslIoReportFacade<L,D,CATEGORY,TYPE> fReport,final Class<L> cL, final Class<D> cD, Class<CATEGORY> cCategory, final Class<TYPE> cType)
	{
		this.fReport=fReport;
		this.cL=cL;
		this.cD=cD;
		
		this.cCategory=cCategory;
		this.cType=cType;
	
		xfStatus = new XmlStatusFactory(StatusQuery.get(StatusQuery.Key.StatusExport).getStatus());
	}
	
	public static <L extends UtilsLang,D extends UtilsDescription,
					CATEGORY extends UtilsStatus<CATEGORY,L,D>,
					TYPE extends UtilsStatus<TYPE,L,D>>
	IoReportRestService<L,D,CATEGORY,TYPE>
			factory(JeeslIoReportFacade<L,D,CATEGORY,TYPE> fReport,final Class<L> cL, final Class<D> cD, Class<CATEGORY> cCategory, final Class<TYPE> cType)
	{
		return new IoReportRestService<L,D,CATEGORY,TYPE>(fReport,cL,cD,cCategory,cType);
	}
	
	@Override public Container exportSystemIoReportCategories()
	{
		Container aht = new Container();
		for(CATEGORY ejb : fReport.allOrderedPosition(cCategory)){aht.getStatus().add(xfStatus.build(ejb));}
		return aht;
	}
	
	@Override public Container exportSystemIoReportGrouping()
	{
		Container aht = new Container();
		for(TYPE ejb : fReport.allOrderedPosition(cType)){aht.getStatus().add(xfStatus.build(ejb));}
		return aht;
	}

	
	@Override public DataUpdate importSystemIoReportCategories(Container categories){return importStatus(cCategory,cL,cD,categories,null);}
	@Override public DataUpdate importSystemIoReportGrouping(Container types){return importStatus(cType,cL,cD,types,null);}
	
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public <S extends UtilsStatus<S,L,D>, P extends UtilsStatus<P,L,D>> DataUpdate importStatus(Class<S> clStatus, Class<L> clLang, Class<D> clDescription, Container container, Class<P> clParent)
    {
    	for(Status xml : container.getStatus()){xml.setGroup(clStatus.getSimpleName());}
		AhtStatusDbInit asdi = new AhtStatusDbInit();
        asdi.setStatusEjbFactory(EjbStatusFactory.createFactory(clStatus, clLang, clDescription));
        asdi.setFacade(fReport);
        DataUpdate dataUpdate = asdi.iuStatus(container.getStatus(), clStatus, clLang, clParent);
        asdi.deleteUnusedStatus(clStatus, clLang, clDescription);
        return dataUpdate;
    }
}