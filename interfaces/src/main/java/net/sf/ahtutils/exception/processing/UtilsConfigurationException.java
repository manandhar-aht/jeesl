package net.sf.ahtutils.exception.processing;

import java.io.Serializable;

public class UtilsConfigurationException extends Exception implements Serializable
{
	private static final long serialVersionUID = 1;

	public UtilsConfigurationException() 
	{ 
	} 
 
	public UtilsConfigurationException(String s) 
	{ 
		super(s); 
	} 
}