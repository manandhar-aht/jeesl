package net.sf.ahtutils.interfaces.controller.report;

public interface UtilsJsonReport extends UtilsReport
{		
	public String getJsonStream() throws Exception;
	public void buildJson();
}