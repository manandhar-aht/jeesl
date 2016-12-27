package org.jeesl.interfaces.rest.system.io.report;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jeesl.model.xml.jeesl.Container;

import net.sf.ahtutils.xml.report.Reports;
import net.sf.ahtutils.xml.sync.DataUpdate;

public interface JeeslIoReportRestImport
{
	@POST @Path("/system/io/report/category") @Produces(MediaType.APPLICATION_XML) @Consumes(MediaType.APPLICATION_XML)
	DataUpdate importSystemIoReportCategories(Container container);
	
	@POST @Path("/system/io/reports") @Produces(MediaType.APPLICATION_XML) @Consumes(MediaType.APPLICATION_XML)
	DataUpdate importSystemIoReports(Reports reports);
	
	@POST @Path("/system/io/report/setting/filling") @Produces(MediaType.APPLICATION_XML) @Consumes(MediaType.APPLICATION_XML)
	DataUpdate importSystemIoReportSettingFilling(Container container);
	
	@POST @Path("/system/io/report/setting/transformation") @Produces(MediaType.APPLICATION_XML) @Consumes(MediaType.APPLICATION_XML)
	DataUpdate importSystemIoReportSettingTransformation(Container container);
	
	@POST @Path("/system/io/report/setting/implementation") @Produces(MediaType.APPLICATION_XML) @Consumes(MediaType.APPLICATION_XML)
	DataUpdate importSystemIoReportSettingImplementation(Container container);
	
	@POST @Path("/system/io/report/row/type") @Produces(MediaType.APPLICATION_XML) @Consumes(MediaType.APPLICATION_XML)
	DataUpdate importSystemIoReportRowType(Container container);
}