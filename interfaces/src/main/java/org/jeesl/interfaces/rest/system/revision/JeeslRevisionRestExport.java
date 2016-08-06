package org.jeesl.interfaces.rest.system.revision;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jeesl.model.xml.system.revision.Entities;

import net.sf.ahtutils.xml.aht.Aht;

public interface JeeslRevisionRestExport
{
	@GET @Path("/system/revision/category") @Produces(MediaType.APPLICATION_XML)
	Aht exportSystemRevisionCategories();
	
	@GET @Path("/system/revision/entities") @Produces(MediaType.APPLICATION_XML)
	Entities exportSystemRevisionEntities();
}