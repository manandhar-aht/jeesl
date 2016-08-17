package org.jeesl.interfaces.controller.report;

public interface JeeslJsonReport extends JeeslReport
{		
	public String getJsonStream() throws Exception;
	public void buildJson();
}