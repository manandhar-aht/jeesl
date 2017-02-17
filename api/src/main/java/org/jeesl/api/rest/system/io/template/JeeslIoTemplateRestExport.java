package org.jeesl.api.rest.system.io.template;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jeesl.model.xml.system.io.template.Templates;

import net.sf.ahtutils.xml.aht.Aht;

public interface JeeslIoTemplateRestExport
{
	@GET @Path("/system/io/template/category") @Produces(MediaType.APPLICATION_XML)
	Aht exportSystemIoTemplateCategories();
	
	@GET @Path("/system/io/template/type") @Produces(MediaType.APPLICATION_XML)
	Aht exportSystemIoTemplateTypes();
	
	@GET @Path("/system/io/templates") @Produces(MediaType.APPLICATION_XML)
	Templates exportSystemIoTemplates();
}