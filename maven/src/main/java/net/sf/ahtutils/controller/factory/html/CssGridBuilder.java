package net.sf.ahtutils.controller.factory.html;

import java.io.File;
import java.io.IOException;

import net.sf.ahtutils.controller.factory.AbstractFreemarkerFileFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.template.TemplateException;

public class CssGridBuilder extends AbstractFreemarkerFileFactory
{
	final static Logger logger = LoggerFactory.getLogger(CssGridBuilder.class);
		
	private File resourceDir;

	public CssGridBuilder(File fTmpDir, File resourceDir)
	{
		super(fTmpDir);
		this.resourceDir=resourceDir;
	}

	public void buildCss()
	{
		for(int slot=50;slot<=100;slot=slot+5)
		{
			for(int gutter=1;gutter<=5;gutter++)
			{
				buildCss(slot, gutter);
			}
		}
		buildCss(54, 5); //erp
		buildCss(73, 5); //lis
	}
	
	public void buildVcsTestFile(File vcsFile)
	{
		fillModel(70,5);
		try
		{
			this.createFile(vcsFile, "html.ahtutils-maven/grid.ftl");
		}
		catch (IOException e) {e.printStackTrace();}
		catch (TemplateException e) {e.printStackTrace();}
	}
	
	protected void buildCss(int slot, int gutter)
	{
		fillModel(slot,gutter);
		try
		{
			File fResource = new File(resourceDir,"grid-"+slot+"-"+gutter+".css");
			this.createFile(fResource, "html.ahtutils-maven/grid.ftl");
		}
		catch (IOException e) {e.printStackTrace();}
		catch (TemplateException e) {e.printStackTrace();}
	}
	
	private void fillModel(int slot, int gutter)
	{
		logger.debug("fillModel for slot="+slot+" gutter="+gutter);
		freemarkerNodeModel.clear();
		
		freemarkerNodeModel.put("width", ""+((slot+(2*gutter))*12));
		
		freemarkerNodeModel.put("gutter", ""+gutter);
		freemarkerNodeModel.put("doublegutter", ""+gutter*2);
		
		for(int i=6;i<=10;i++)
		{
			freemarkerNodeModel.put("op"+i+"Width", ""+((slot+(2*gutter))*i));
		}
		
		createSlots(slot, gutter);
		createBlockSlots(slot, gutter);
	}
	
	private void createSlots(int slot, int gutter)
	{
		int margin = gutter*2;
		for(int i=1;i<=12;i++)
		{
			int px = i*slot;
			px = px + (i-1)*margin;

/*  add extra 10px to auGrid_12 - avoid last element from going to next	line (for later use)*/			
//			if (i<=11){px = px + (i-1)*margin;}
//  		if (i>=12){px = px + (i-1)*margin+10;}
			
			freemarkerNodeModel.put("slot"+i, ""+px);
		}
	}
	

	/**
	 * creating smaller slots for blocks 
	 * @param blockslot
	 * @param gutter
	 */
	private void createBlockSlots(int blockslot, int gutter)
	{
		int margin = gutter*2;
		for(int i=1;i<=12;i++)
		{
// for testusing 4px smaler blocks: //	int px = i*blockslot-4;
			
			int px = i*blockslot;
			px = px + (i-1)*margin;
			freemarkerNodeModel.put("blockslot"+i, ""+px);
		}
	}
}