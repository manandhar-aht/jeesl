package org.jeesl.doc.resources;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.batik.transcoder.TranscoderException;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.io.IOUtils;
import org.codehaus.plexus.util.FileUtils;
import org.openfuxml.media.transcode.Svg2PngTranscoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.doc.UtilsDocumentation;
import net.sf.ahtutils.exception.processing.UtilsConfigurationException;
import net.sf.exlp.util.io.FileIO;
import net.sf.exlp.util.io.resourceloader.MultiResourceLoader;

public class JeeslIconBuilder
{	
	final static Logger logger = LoggerFactory.getLogger(JeeslMsgBuilder.class);
	
	private MultiResourceLoader mrl;
	private File baseIcon;
	
	public JeeslIconBuilder(Configuration config)
	{
		mrl = new MultiResourceLoader();
		baseIcon = new File(config.getString(UtilsDocumentation.keyBaseIconDir));
		logger.info("Using msg.dir ("+UtilsDocumentation.keyBaseIconDir+"): "+baseIcon.getAbsolutePath());
		
	}
	
	public void svg() throws UtilsConfigurationException 
	{
		build("jeesl/svg/icon/ui","control",   "add","cancel");
	}
		
	private void build(String resourceDir, String targetDir, String... items) throws UtilsConfigurationException 
	{
		for(String item : items)
		{
			buildIcon(resourceDir,targetDir,item);
		}
	}
	
	private void buildIcon(String resourceDir, String targetDir, String item) throws UtilsConfigurationException 
	{
		try
		{
			InputStream is = mrl.searchIs(resourceDir+"/"+targetDir+"/"+item+".svg");
			File fTarget = new File(baseIcon,targetDir+File.separator+item+".png");
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			
			logger.info("Writing to "+fTarget);
			Svg2PngTranscoder.transcode(12, is, baos);
			FileIO.writeFileIfDiffers(baos.toByteArray(), fTarget);
			
		}
		catch (FileNotFoundException e) {throw new UtilsConfigurationException(e.getMessage());}
		catch (IOException e) {throw new UtilsConfigurationException(e.getMessage());}
		catch (TranscoderException e) {throw new UtilsConfigurationException(e.getMessage());}
	}
	
	public void png() throws UtilsConfigurationException
	{
		File base = new File("../../jeesl/prototype/src/main/resources/META-INF/resources/jeeslPrototypeGfx/12/ui");
		copy(FileUtils.normalize(base.getAbsolutePath()),"control",   "delete","clone","download","filter","move","remove","save","search","upload");		
		copy(FileUtils.normalize(base.getAbsolutePath()),"security",   "check-mark","documentation","x-mark");
	}
	
	private void copy(String resourceDir, String targetDir, String... items) throws UtilsConfigurationException 
	{
		for(String item : items)
		{
			copyIcon(resourceDir,targetDir,item);
		}
	}
	
	private void copyIcon(String resourceDir, String targetDir, String item) throws UtilsConfigurationException 
	{
		try
		{
			InputStream is = mrl.searchIs(resourceDir+"/"+targetDir+"/"+item+".png");
			File fTarget = new File(baseIcon,targetDir+File.separator+item+".png");
			
			logger.info("Writing to "+fTarget);
			FileIO.writeFileIfDiffers(IOUtils.toByteArray(is), fTarget);
			
		}
		catch (FileNotFoundException e) {throw new UtilsConfigurationException(e.getMessage());}
		catch (IOException e) {throw new UtilsConfigurationException(e.getMessage());}
	}
}