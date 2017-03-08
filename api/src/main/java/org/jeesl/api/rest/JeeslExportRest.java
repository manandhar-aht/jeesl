package org.jeesl.api.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.sf.ahtutils.exception.processing.UtilsConfigurationException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public interface JeeslExportRest
{	
	@GET @Path("/status/{code}") @Produces(MediaType.APPLICATION_XML)
	<S extends UtilsStatus<S,L,D>,L extends UtilsLang, D extends UtilsDescription> org.jeesl.model.xml.jeesl.Container exportStatus(@PathParam("code") String code) throws UtilsConfigurationException;
}