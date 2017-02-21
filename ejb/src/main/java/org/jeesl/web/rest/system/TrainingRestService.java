package org.jeesl.web.rest.system;

import org.jeesl.api.facade.module.JeeslTrainingFacade;
import org.jeesl.api.rest.system.training.JeeslTrainingRestExport;
import org.jeesl.api.rest.system.training.JeeslTrainingRestImport;
import org.jeesl.model.xml.jeesl.Container;
import org.jeesl.web.rest.AbstractJeeslRestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.xml.sync.DataUpdate;

public class TrainingRestService <L extends UtilsLang,D extends UtilsDescription,
									TYPE extends UtilsStatus<TYPE,L,D>>
					extends AbstractJeeslRestService<L,D>
					implements JeeslTrainingRestExport,JeeslTrainingRestImport
{
	final static Logger logger = LoggerFactory.getLogger(TrainingRestService.class);
	
	private JeeslTrainingFacade<L,D,TYPE> fTraining;
	
	private final Class<TYPE> cType;
	
	private TrainingRestService(JeeslTrainingFacade<L,D,TYPE> fTraining,final Class<L> cL, final Class<D> cD, final Class<TYPE> cType)
	{
		super(fTraining,cL,cD);
		this.fTraining=fTraining;
		
		this.cType=cType;
	}
	
	public static <L extends UtilsLang,D extends UtilsDescription,
							TYPE extends UtilsStatus<TYPE,L,D>>
			TrainingRestService<L,D,TYPE>
			factory(JeeslTrainingFacade<L,D,TYPE> fTraining,final Class<L> cL, final Class<D> cD, final Class<TYPE> cType)
	{
		return new TrainingRestService<L,D,TYPE>(fTraining,cL,cD,cType);
	}
	
	@Override public Container exportSystemTrainingSlotType() {return xfContainer.build(fTraining.allOrderedPosition(cType));}

	@Override public DataUpdate importSystemTrainingSlotType(Container container){return importStatus(cType,container,null);}
}