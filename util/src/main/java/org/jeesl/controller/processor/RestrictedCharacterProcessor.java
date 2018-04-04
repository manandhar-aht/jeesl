package org.jeesl.controller.processor;

public final class RestrictedCharacterProcessor
{
	private static RestrictedCharacterProcessor instance;
	
	private String blankReplace;  public void setBlankReplace(String blankReplace) {this.blankReplace = blankReplace;}

	public RestrictedCharacterProcessor()
    {
		blankReplace = "-";
    }
    
    public static String prettyUrl(String input)
    {
    	if(instance==null){instance = new RestrictedCharacterProcessor();}
    	return instance.url(input);
    }
    
    public String url(String input)
    {
    	input=input.replace(" ", blankReplace);
    	input=input.replace("----", "-");
    	input=input.replace("---", "-");
    	input=input.replace("-", "-");
    	input=input.replace(":-", ":");
    	input=input.replace("ä", "ae");
    	input=input.replace("ö", "oe");
    	input=input.replace("ü", "ue");
    	input=input.replace("?", "S");
    	input=input.replace("/", "-");
        return input;
    }
    
    
    public static String prettyFile(String input)
    {
    	if(instance==null){instance = new RestrictedCharacterProcessor();}
    	return instance.file(input);
    }
    
    public String file(String input)
    {
    	input=input.replace("<", "-");
    	input=input.replace(">", "-");
    	input=input.replace(":", "-");
    	input=input.replace("\"", "-");
    	input=input.replace("/", "-");
    	input=input.replace("\\", "-");
    	input=input.replace("|", "-");
    	input=input.replace("?", "-");
    	input=input.replace("*", "-");
        return input;
    }
    
   
}
