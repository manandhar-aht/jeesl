package org.jeesl.doc.resources;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JeeslIconTranscoder
{	
	final static Logger logger = LoggerFactory.getLogger(JeeslIconTranscoder.class);

	public static final String ui = "ofx.aht-utils/user/ui/icons.xml";
	
	private File baseDir;
	
	public JeeslIconTranscoder(File baseDir)
	{
		this.baseDir=baseDir;
	}
	
	public void transcode(String resource)
	{
		logger.info("Using "+baseDir.toString());
	}
}