package org.jeesl.interfaces.rest.system.io.report;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jeesl.model.xml.jeesl.Container;

public interface JeeslIoReportRestExport
{
	@GET @Path("/system/io/report/category") @Produces(MediaType.APPLICATION_XML)
	Container exportSystemIoReportCategories();
	
	@GET @Path("/system/io/report/grouping") @Produces(MediaType.APPLICATION_XML)
	Container exportSystemIoReportGrouping();
	
	@GET @Path("/system/io/report/colum/aggregation") @Produces(MediaType.APPLICATION_XML)
	Container exportSystemIoReportColumAggegation();
}