package org.jeesl.connectors.tools;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
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
		/*
		try {
			result = WeapConnector.getResultValue(rvr);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		*/
		request.setResult(result);
		return request;
	}
}