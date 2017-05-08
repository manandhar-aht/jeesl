package org.jeesl.connectors.tools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.jeesl.connectors.weap.WeapConnector;
import org.jeesl.connectors.weap.WeapResultValueRequest;

@Path("/rest")
@Produces("application/xml")
public class WeapRequestService
{
	@POST @Path("/request") @Consumes(MediaType.APPLICATION_XML)
	public WeapResultValueRequest getRequest(WeapResultValueRequest request)
	{
		System.out.println("Requesting Results for branches " +request.getBranches().get(0));
		Double result = 120.0;
		
		try {
			result = WeapConnector.getResultValue(request);
		} catch (IOException ex) {
			request.setErrorMessage(ex.getMessage());
			System.err.println(ex.getMessage());
		}
		
		request.setResult(result);
		return request;
	}
	
	@POST @Path("/scenario") @Consumes(MediaType.APPLICATION_XML)
	public void setScenario(String scenario)
	{
		System.out.println("Setting Scenario to " +scenario);
		Double result = 120.0;
		
		try {
			WeapConnector.setScenario(scenario);
		} catch (IOException ex) {
			System.err.println(ex.getMessage());
		}
	}
	
	@POST @Path("/area") @Consumes(MediaType.APPLICATION_XML)
	public void setArea(String area)
	{
		System.out.println("Setting Area to " +area);
		
		try {
			WeapConnector.setScenario(area);
		} catch (IOException ex) {
			System.err.println(ex.getMessage());
		}
	}
	
	@POST @Path("/calculate") @Consumes(MediaType.APPLICATION_XML)
	public void calculate()
	{
		System.out.println("Triggering calculation");
		
		try {
			WeapConnector.triggerCalculation();
		} catch (IOException ex) {
			System.err.println(ex.getMessage());
		}
	}
	
	@GET @Path("/baseYear")
	public Integer getBaseYear()
	{
		System.out.println("Requesting Base Year ");
		Integer baseYear = 1900;
		try {
			baseYear = WeapConnector.getBaseYear();
		} catch (IOException ex) {
			System.err.println(ex.getMessage());
		}
		return baseYear;
	}
	
	@GET @Path("/endYear")
	public Integer getEndYear()
	{
		System.out.println("Requesting End Year ");
		Integer endYear = 1900;
		try {
			endYear = WeapConnector.getEndYear();
		} catch (IOException ex) {
			System.err.println(ex.getMessage());
		}
		return endYear;
	}
	
	@GET @Path("/areas")
	public List<String> getAreas()
	{
		System.out.println("Requesting Areas");
		List<String> areas = new ArrayList<String>();
		try {
			areas = WeapConnector.getAreas();
		} catch (IOException ex) {
			System.err.println(ex.getMessage());
		}
		return areas;
	}
	
	@GET @Path("/scenarios")
	public List<String> getScenarios()
	{
		System.out.println("Requesting Scenarios");
		List<String> scenarios = new ArrayList<String>();
		try {
			scenarios = WeapConnector.getScenarios();
		} catch (IOException ex) {
			System.err.println(ex.getMessage());
		}
		return scenarios;
	}
	
	@GET @Path("/version")
	public String getVersion()
	{
		System.out.println("Requesting WEAP version");
		String version = "NOT CONNECTED";
		try {
			version = WeapConnector.getWeapVersion();
		} catch (IOException ex) {
			System.err.println(ex.getMessage());
		}
		return version;
	}
}