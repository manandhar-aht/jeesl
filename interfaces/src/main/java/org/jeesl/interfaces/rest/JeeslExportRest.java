package org.jeesl.interfaces.rest;

import net.sf.ahtutils.exception.processing.UtilsConfigurationException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public interface JeeslExportRest <L extends UtilsLang,D extends UtilsDescription>
{	
	<X extends UtilsStatus<X,L,D>> org.jeesl.model.xml.jeesl.Container exportStatus(String code) throws UtilsConfigurationException;
}