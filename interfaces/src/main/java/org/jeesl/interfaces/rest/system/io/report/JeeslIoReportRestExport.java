package org.jeesl.interfaces.rest.system.io.report;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jeesl.model.xml.jeesl.Container;

import net.sf.ahtutils.xml.report.Reports;

public interface JeeslIoReportRestExport
{
	@GET @Path("/system/io/report/category") @Produces(MediaType.APPLICATION_XML)
	Container exportSystemIoReportCategories();
	
	@GET @Path("/system/io/reports") @Produces(MediaType.APPLICATION_XML)
	Reports exportSystemIoReports();
	
	@GET @Path("/system/io/report/setting/filling") @Produces(MediaType.APPLICATION_XML)
	Container exportSystemIoReportSettingFilling();
	
	@GET @Path("/system/io/report/setting/transformation") @Produces(MediaType.APPLICATION_XML)
	Container exportSystemIoReportSettingTransformation();
	
	@GET @Path("/system/io/report/setting/implementation") @Produces(MediaType.APPLICATION_XML)
	Container exportSystemIoReportSettingImplementation();
	
//	@GET @Path("/system/io/report/setting/column/type") @Produces(MediaType.APPLICATION_XML)
//	Container exportSystemIoReportSettingColumnType();
}