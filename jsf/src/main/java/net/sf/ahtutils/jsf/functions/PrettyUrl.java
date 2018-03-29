package net.sf.ahtutils.jsf.functions;

import org.jeesl.controller.processor.RestrictedCharacterProcessor;

public final class PrettyUrl
{
	private static RestrictedCharacterProcessor instance;

	public PrettyUrl()
    {
		
    }
    
    public static String prettyUrl(String input)
    {
    	if(instance==null)
    	{
    		instance = new RestrictedCharacterProcessor();
    		instance.setBlankReplace("-");
    	}
    	return instance.url(input);
    }   
}
