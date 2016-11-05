package org.jeesl.interfaces.rest.system.io.report;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jeesl.model.xml.jeesl.Container;

import net.sf.ahtutils.xml.sync.DataUpdate;

public interface JeeslIoReportRestImport
{
	@POST @Path("/system/io/report/category") @Produces(MediaType.APPLICATION_XML) @Consumes(MediaType.APPLICATION_XML)
	DataUpdate importSystemIoReportCategories(Container container);
	
	@POST @Path("/system/io/report/type") @Produces(MediaType.APPLICATION_XML) @Consumes(MediaType.APPLICATION_XML)
	DataUpdate importSystemIoReportGrouping(Container container);
	
	@POST @Path("/system/io/report/colum/aggregation") @Produces(MediaType.APPLICATION_XML) @Consumes(MediaType.APPLICATION_XML)
	DataUpdate importSystemIoReportColumAggegation(Container container);
}