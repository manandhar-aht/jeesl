package net.sf.ahtutils.exception.xml;

import java.io.Serializable;

public class UtilsXmlStructureException extends Exception implements Serializable
{
	private static final long serialVersionUID = 1;

	public UtilsXmlStructureException() 
	{ 
	} 
 
	public UtilsXmlStructureException(String s) 
	{ 
		super(s); 
	} 
}