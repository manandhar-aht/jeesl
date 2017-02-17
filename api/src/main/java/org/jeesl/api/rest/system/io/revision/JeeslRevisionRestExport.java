package org.jeesl.api.rest.system.io.revision;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jeesl.model.xml.jeesl.Container;
import org.jeesl.model.xml.system.revision.Entities;

public interface JeeslRevisionRestExport
{
	@GET @Path("/system/io/revision/category") @Produces(MediaType.APPLICATION_XML)
	Container exportSystemRevisionCategories();
	
	@GET @Path("/system/io/revision/attribute/type") @Produces(MediaType.APPLICATION_XML)
	Container exportSystemIoRevisionAttributeTypes();
	
	@GET @Path("/system/io/revision/scope/type") @Produces(MediaType.APPLICATION_XML)
	Container exportSystemIoRevisionScopeTypes();
	
	@GET @Path("/system/revision/entities") @Produces(MediaType.APPLICATION_XML)
	Entities exportSystemRevisionEntities();
}