package net.sf.ahtutils.interfaces.rest.system.revision;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.sf.ahtutils.xml.aht.Aht;
import net.sf.ahtutils.xml.survey.Correlation;
import net.sf.ahtutils.xml.survey.Survey;
import net.sf.ahtutils.xml.survey.Surveys;
import net.sf.ahtutils.xml.survey.Templates;

public interface UtilsRevisionRestExport
{
	@GET @Path("/survey/template/category") @Produces(MediaType.APPLICATION_XML)
	Aht exportSurveyTemplateCategory();
}