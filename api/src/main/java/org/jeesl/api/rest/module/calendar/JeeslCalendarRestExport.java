package org.jeesl.api.rest.module.calendar;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jeesl.model.xml.jeesl.Container;

public interface JeeslCalendarRestExport
{
	@GET @Path("/module/calendar/type") @Produces(MediaType.APPLICATION_XML)
	Container exportCalendarType();
	
	@GET @Path("/module/calendar/item/type") @Produces(MediaType.APPLICATION_XML)
	Container exportCalendarItemType();
}