package org.jeesl.api.facade.system;

import org.jeesl.model.xml.jeesl.Container;

import net.sf.ahtutils.exception.processing.UtilsConfigurationException;

public interface JeeslExportRestFacade
{	
	public final static String url = "http://192.168.202.26:8080/jeesl";
//	public final static String url = "http://www.jeesl.org/jeesl/rest";
	
	Container exportJeeslReferenceRest(String code) throws UtilsConfigurationException;
}