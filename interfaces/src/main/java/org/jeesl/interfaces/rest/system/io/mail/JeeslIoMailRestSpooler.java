package org.jeesl.interfaces.rest.system.io.mail;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jeesl.model.xml.jeesl.Container;

public interface JeeslIoMailRestSpooler
{
	@GET @Path("/system/io/mail/category") @Produces(MediaType.APPLICATION_XML)
	Container exportSystemIoMailCategories();
}