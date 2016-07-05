package net.sf.ahtutils.db.shell.postgres;

import java.io.BufferedWriter;
import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PostgresCopy
{
	final static Logger logger = LoggerFactory.getLogger(PostgresCopy.class);
	
	public static void write(BufferedWriter bw, String... data) throws IOException
	{
		bw.write(StringUtils.join(data, "\t"));
		bw.newLine();
	}
	
	public static String convertNull(String string)
	{
		if(string.isEmpty())
		{
			return "\\N";
		}
		else
		{
			return string;
		}
	}
}