package net.sf.ahtutils.db.shell.postgres;

import java.io.BufferedWriter;
import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PostgresCopy
{
	final static Logger logger = LoggerFactory.getLogger(PostgresCopy.class);
	
	public static String nul = "\\N";
	
	public static void write(BufferedWriter bw, String... data) throws IOException
	{
		bw.write(StringUtils.join(data, "\t"));
		bw.newLine();
	}
	
	public static String string(String s)
	{
		if(s==null || s.isEmpty())
		{
			return nul;
		}
		else{return s;}
	}
	
	public static String bool(Boolean value)
	{
		if(value==null) {return nul;}
		else
		{
			if(value){return "t";}
			else{return "f";}
		}
	}
	
	public static String doubl(Double value)
	{
		if(value==null) {return nul;}
		else
		{
			return value.toString();
		}
	}
}