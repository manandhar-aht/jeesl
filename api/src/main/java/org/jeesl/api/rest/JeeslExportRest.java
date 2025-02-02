package org.jeesl.api.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jeesl.model.xml.system.revision.Entity;

import net.sf.ahtutils.exception.processing.UtilsConfigurationException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

@Path("/rest/jeesl/export")
public interface JeeslExportRest <L extends UtilsLang,D extends UtilsDescription> extends org.jeesl.interfaces.rest.JeeslExportRest<L,D>
{	
	@GET @Path("/status/{code}") @Produces(MediaType.APPLICATION_XML)
	<X extends UtilsStatus<X,L,D>> org.jeesl.model.xml.jeesl.Container exportStatus(@PathParam("code") String code) throws UtilsConfigurationException;
	
	@GET @Path("/revision/entity/{code}") @Produces(MediaType.APPLICATION_XML)
	Entity exportRevisionEntity(@PathParam("code") String code) throws UtilsConfigurationException;
}