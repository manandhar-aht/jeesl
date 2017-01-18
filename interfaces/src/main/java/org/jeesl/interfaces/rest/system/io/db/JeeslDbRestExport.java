package org.jeesl.interfaces.rest.system.io.db;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.sf.ahtutils.xml.aht.Aht;

public interface JeeslDbRestExport
{
	@GET @Path("/system/db/activity/state") @Produces(MediaType.APPLICATION_XML)
	Aht exportSystemDbActivityState();
}