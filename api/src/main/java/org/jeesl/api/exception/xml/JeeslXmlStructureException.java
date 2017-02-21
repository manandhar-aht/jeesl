package org.jeesl.api.exception.xml;

import java.io.Serializable;

public class JeeslXmlStructureException extends Exception implements Serializable
{
	private static final long serialVersionUID = 1;

	public JeeslXmlStructureException() 
	{ 
	} 
 
	public JeeslXmlStructureException(String s) 
	{ 
		super(s); 
	} 
}