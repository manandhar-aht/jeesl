package org.jeesl.doc.er;

import java.io.File;
import java.io.IOException;

import net.sf.exlp.shell.spawn.Spawn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ErImageWriter
{
	final static Logger logger = LoggerFactory.getLogger(ErImageWriter.class);
	
	private String type;
	
	public ErImageWriter(String type)
	{
		this.type=type;
	}
	
	public void svg(File src, File dst) throws IOException, ClassNotFoundException
	{
		StringBuffer sb = new StringBuffer();
		sb.append("/usr/local/bin/").append(type);
		sb.append(" -Tsvg");
		sb.append(" ").append(src.getAbsolutePath());
		sb.append(" -o ");
		sb.append(dst.getAbsolutePath());
		
		logger.info(sb.toString());
		
		Spawn spawn = new Spawn(sb.toString());
		spawn.debug();
		spawn.run();
	}
}