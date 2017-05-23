package net.sf.ahtutils.controller.factory.html;

import java.io.File;
import java.io.IOException;

import net.sf.ahtutils.controller.factory.AbstractFreemarkerFileFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.template.TemplateException;

public class CssPrototypeBuilder extends AbstractFreemarkerFileFactory
{
	final static Logger logger = LoggerFactory.getLogger(CssPrototypeBuilder.class);
		
	private File resourceDir;
	private String cssName;

	public CssPrototypeBuilder(File fTmpDir, File resourceDir, String cssName)
	{
		super(fTmpDir);
		this.resourceDir=resourceDir;
		this.cssName=cssName;
	}
		
	public void buildCss(String colorLight, String colorMedium, String colorDark)
	{
		fillModel(colorLight,colorMedium,colorDark);
		try
		{
			File fResource = new File(resourceDir,cssName);
			this.createFile(fResource, "html.ahtutils-maven/prototype.ftl");
		}
		catch (IOException e) {e.printStackTrace();}
		catch (TemplateException e) {e.printStackTrace();}
	}
	
	private void fillModel(String colorLight, String colorMedium, String colorDark)
	{
		logger.trace("fillModel");
		freemarkerNodeModel.clear();
		
		freemarkerNodeModel.put("colorLight", colorLight);
		freemarkerNodeModel.put("colorMedium",colorMedium);
		freemarkerNodeModel.put("colorDark", colorDark);
	}
}