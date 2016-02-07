package net.sf.ahtutils.interfaces.facade;

import java.util.List;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.system.io.UtilsIoTemplate;

public interface UtilsIoFacade <L extends UtilsLang,D extends UtilsDescription,
								IOT extends UtilsIoTemplate<L,D,IOT,IOTT,IOTC>,
								IOTT extends UtilsStatus<IOTT,L,D>,
								IOTC extends UtilsStatus<IOTC,L,D>>
			extends UtilsFacade
{	
	List<IOT> findTemplates(Class<IOT> cTemplate, Class<IOTT> cTemplateType, Class<IOTC> cTemplateCategory, List<IOTT> types, List<IOTC> categories);
}