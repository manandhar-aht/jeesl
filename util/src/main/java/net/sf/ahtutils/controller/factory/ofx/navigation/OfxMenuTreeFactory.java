package net.sf.ahtutils.controller.factory.ofx.navigation;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

import net.sf.ahtutils.xml.navigation.Menu;
import net.sf.ahtutils.xml.navigation.MenuItem;
import net.sf.exlp.util.io.StringIO;

import org.metachart.xml.graph.Node;
import org.metachart.xml.graph.Tree;
import org.openfuxml.factory.ConfigurationProviderFacotry;
import org.openfuxml.media.cross.NoOpCrossMediaManager;
import org.openfuxml.renderer.latex.content.graph.LatexTreeRenderer;
import org.openfuxml.util.configuration.settings.OfxDefaultSettingsManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OfxMenuTreeFactory
{
	final static Logger logger = LoggerFactory.getLogger(OfxMenuTreeFactory.class);
	
	public OfxMenuTreeFactory()
	{

	}
	
	public void saveDescription(File f, Menu menu)
	{
		try
		{
			logger.debug("Saving Reference to "+f);
			LatexTreeRenderer renderer = new LatexTreeRenderer(ConfigurationProviderFacotry.build(new NoOpCrossMediaManager(),new OfxDefaultSettingsManager()));
//			renderer.render(create(menu));
			StringWriter actual = new StringWriter();
			renderer.write(actual);
			StringIO.writeTxt(f, actual.toString());
			
		}
//		catch (OfxAuthoringException e) {logger.error("Something went wrong during ofx/latex transformation ",e);}
		catch (IOException e) {logger.error("Cannot save the file to "+f.getAbsolutePath(),e);}
	}
	
	public Tree create(Menu menu)
	{
		Tree tree = new Tree();
		
		if(menu.getMenuItem().size()>1)
		{
			Node node = new Node();
			for(MenuItem mi : menu.getMenuItem())
			{
				node.getNode().add(createNode(mi));
			}
			tree.setNode(node);
		}
		else
		{
			tree.setNode(createNode(menu.getMenuItem().get(0)));
		}
		return tree;
	}
	
	private Node createNode(MenuItem mi)
	{
		Node node = new Node();
		for(MenuItem child : mi.getMenuItem())
		{
			node.getNode().add(createNode(child));
		}
		node.setLabel(mi.getName());
		return node;
	}

}