package org.jeesl.interfaces.rest;

import javax.ws.rs.PathParam;

import org.jeesl.model.xml.system.revision.Entity;

import net.sf.ahtutils.exception.processing.UtilsConfigurationException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public interface JeeslExportRest <L extends UtilsLang,D extends UtilsDescription>
{	
	<X extends UtilsStatus<X,L,D>> org.jeesl.model.xml.jeesl.Container exportStatus(String code) throws UtilsConfigurationException;
	Entity exportRevisionEntity(@PathParam("code") String code) throws UtilsConfigurationException;
}