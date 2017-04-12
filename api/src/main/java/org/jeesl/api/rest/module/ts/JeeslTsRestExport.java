package org.jeesl.api.rest.module.ts;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jeesl.model.xml.jeesl.Container;

public interface JeeslTsRestExport
{
	@GET @Path("/module/ts/unit") @Produces(MediaType.APPLICATION_XML)
	Container exportTsUnit();
	
	@GET @Path("/module/ts/unit/si") @Produces(MediaType.APPLICATION_XML)
	Container exportTsUnitSi();
}