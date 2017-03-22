package org.jeesl.api.rest.module.calendar;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jeesl.model.xml.jeesl.Container;

import net.sf.ahtutils.xml.sync.DataUpdate;

public interface JeeslCalendarRestImport
{
	@POST @Path("/module/calendar/type") @Produces(MediaType.APPLICATION_XML) @Consumes(MediaType.APPLICATION_XML)
	DataUpdate importCalendarType(Container types);
	
	@POST @Path("/module/calendar/item/type") @Produces(MediaType.APPLICATION_XML) @Consumes(MediaType.APPLICATION_XML)
	DataUpdate importCalendarItemType(Container types);
}