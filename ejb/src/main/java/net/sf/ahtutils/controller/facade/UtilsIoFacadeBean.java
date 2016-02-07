package net.sf.ahtutils.controller.facade;

import java.util.List;

import javax.persistence.EntityManager;

import net.sf.ahtutils.controller.util.ParentPredicate;
import net.sf.ahtutils.interfaces.facade.UtilsIoFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.system.io.UtilsIoTemplate;

public class UtilsIoFacadeBean<L extends UtilsLang,D extends UtilsDescription,
								IOT extends UtilsIoTemplate<L,D,IOT,IOTT,IOTC>,
								IOTT extends UtilsStatus<IOTT,L,D>,
								IOTC extends UtilsStatus<IOTC,L,D>>
					extends UtilsFacadeBean
					implements UtilsIoFacade<L,D,IOT,IOTT,IOTC>
{	
	public UtilsIoFacadeBean(EntityManager em)
	{
		super(em);
	}
	
	@Override public List<IOT> findTemplates(Class<IOT> cTemplate, Class<IOTT> cTemplateType, Class<IOTC> cTemplateCategory, List<IOTT> types, List<IOTC> categories)
	{
		List<ParentPredicate<IOTT>> ppType = ParentPredicate.createFromList(cTemplateType,"type",types);
		List<ParentPredicate<IOTC>> ppCategory = ParentPredicate.createFromList(cTemplateCategory,"category",categories);
		return allForOrOrParents(cTemplate,ppType,ppCategory);
	}
}