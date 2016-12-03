package org.jeesl.web.rest.system;

import org.jeesl.factory.xml.jeesl.XmlContainerFactory;
import org.jeesl.factory.xml.system.status.XmlStatusFactory;
import org.jeesl.interfaces.facade.JeeslTrainingFacade;
import org.jeesl.interfaces.rest.system.training.JeeslTrainingRestExport;
import org.jeesl.interfaces.rest.system.training.JeeslTrainingRestImport;
import org.jeesl.model.xml.jeesl.Container;
import org.jeesl.util.query.xml.StatusQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.db.xml.AhtStatusDbInit;
import net.sf.ahtutils.factory.ejb.status.EjbStatusFactory;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.xml.status.Status;
import net.sf.ahtutils.xml.sync.DataUpdate;

public class TrainingRestService <L extends UtilsLang,D extends UtilsDescription,
									TYPE extends UtilsStatus<TYPE,L,D>>
					implements JeeslTrainingRestExport,JeeslTrainingRestImport
{
	final static Logger logger = LoggerFactory.getLogger(TrainingRestService.class);
	
	private JeeslTrainingFacade<L,D,TYPE> fTraining;
	
	private final Class<L> cL;
	private final Class<D> cD;
	private final Class<TYPE> cType;

	private XmlStatusFactory xfStatus;
	
	private TrainingRestService(JeeslTrainingFacade<L,D,TYPE> fTraining,final Class<L> cL, final Class<D> cD, final Class<TYPE> cType)
	{
		this.fTraining=fTraining;
		this.cL=cL;
		this.cD=cD;
		
		this.cType=cType;
	
		xfStatus = new XmlStatusFactory(StatusQuery.get(StatusQuery.Key.StatusExport).getStatus());
	}
	
	public static <L extends UtilsLang,D extends UtilsDescription,
							TYPE extends UtilsStatus<TYPE,L,D>>
			TrainingRestService<L,D,TYPE>
			factory(JeeslTrainingFacade<L,D,TYPE> fTraining,final Class<L> cL, final Class<D> cD, final Class<TYPE> cType)
	{
		return new TrainingRestService<L,D,TYPE>(fTraining,cL,cD,cType);
	}
	
	@Override public Container exportSystemTrainingSlotType() {return XmlContainerFactory.buildStatusList(xfStatus.build(fTraining.allOrderedPosition(cType)));}

	@Override public DataUpdate importSystemTrainingSlotType(Container container){return importStatus(cType,cL,cD,container,null);}
	
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public <S extends UtilsStatus<S,L,D>, P extends UtilsStatus<P,L,D>> DataUpdate importStatus(Class<S> clStatus, Class<L> clLang, Class<D> clDescription, Container container, Class<P> clParent)
    {
    	for(Status xml : container.getStatus()){xml.setGroup(clStatus.getSimpleName());}
		AhtStatusDbInit asdi = new AhtStatusDbInit();
        asdi.setStatusEjbFactory(EjbStatusFactory.createFactory(clStatus, clLang, clDescription));
        asdi.setFacade(fTraining);
        DataUpdate dataUpdate = asdi.iuStatus(container.getStatus(), clStatus, clLang, clParent);
        asdi.deleteUnusedStatus(clStatus, clLang, clDescription);
        return dataUpdate;
    }
}