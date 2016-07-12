package net.sf.ahtutils.interfaces.rest.survey;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.sf.ahtutils.xml.aht.Aht;
import net.sf.ahtutils.xml.survey.Correlation;
import net.sf.ahtutils.xml.survey.Survey;
import net.sf.ahtutils.xml.survey.Templates;
import net.sf.ahtutils.xml.sync.DataUpdate;

public interface UtilsSurveyRestImport
{
	@POST @Path("/survey/template/category") @Produces(MediaType.APPLICATION_XML) @Consumes(MediaType.APPLICATION_XML)
	DataUpdate importSurveyTemplateCategory(Aht categories);
	
	@POST @Path("/survey/template/status") @Produces(MediaType.APPLICATION_XML) @Consumes(MediaType.APPLICATION_XML)
	DataUpdate importSurveyTemplateStatus(Aht status);
	
	@POST @Path("/survey/question/unit") @Produces(MediaType.APPLICATION_XML) @Consumes(MediaType.APPLICATION_XML)
	DataUpdate importSurveyUnits(Aht units);
	
	@POST  @Path("/survey/status") @Produces(MediaType.APPLICATION_XML) @Consumes(MediaType.APPLICATION_XML)
	DataUpdate importSurveyStatus(Aht status);
	
	@POST  @Path("/survey/templates") @Produces(MediaType.APPLICATION_XML) @Consumes(MediaType.APPLICATION_XML)
	DataUpdate importSurveyTemplates(Templates templates);
	
	@POST  @Path("/survey") @Produces(MediaType.APPLICATION_XML) @Consumes(MediaType.APPLICATION_XML)
	DataUpdate importSurvey(Survey survey);
	
	@POST  @Path("/survey/correlations") @Produces(MediaType.APPLICATION_XML) @Consumes(MediaType.APPLICATION_XML)
	DataUpdate importCorrelations(Correlation correlations);
}