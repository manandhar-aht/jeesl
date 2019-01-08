package org.jeesl.api.facade.system;

import org.jeesl.model.xml.jeesl.Container;

import net.sf.ahtutils.exception.processing.UtilsConfigurationException;
import net.sf.ahtutils.xml.report.Reports;

public interface JeeslExportRestFacade
{	
	public final static String urlJeesl = "http://www.jeesl.org/jeesl";
	public final static String urlGeojsf = "http://www.geojsf.org/geojsf";
	
	public final static String packageJeesl = "org.jeesl";
	public final static String packageGeojsf = "org.geojsf";
	
	Container exportJeeslReferenceRest(String code) throws UtilsConfigurationException;
	Reports exportIoReport(String code);
}