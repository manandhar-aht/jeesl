package org.jeesl.interfaces.rest.survey;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jeesl.model.json.system.status.JsonContainer;

@Path("/rest/survey")
public interface JeeslSurveyJsonRest
{	
	@GET @Path("/json/question/units") @Produces(MediaType.APPLICATION_JSON)
	JsonContainer surveyQuestionUnitsJson();
}