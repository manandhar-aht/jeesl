package net.sf.ahtutils.factory.pf;

import org.jeesl.model.json.JsonFlatFigure;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.finance.Figures;
import net.sf.ahtutils.xml.finance.Finance;

public class PfTreeNodeFactory
{
	final static Logger logger = LoggerFactory.getLogger(PfTreeNodeFactory.class);
	
	public static TreeNode build(Figures figures) 
	{
		TreeNode tree = new DefaultTreeNode(new JsonFlatFigure(), null);
		
		for(Figures f : figures.getFigures())
		{
			build(tree,f);
		}
		return tree;
	}
	
	private static void build(TreeNode parent, Figures figures)
	{
		JsonFlatFigure json = new JsonFlatFigure();
		if(figures.isSetLabel()){json.setG1(figures.getLabel());}
		if(figures.isSetFinance())
		{
			for(Finance f : figures.getFinance())
			{
				if(f.isSetNr() && f.getNr()==1){json.setD1(f.getValue()+"");}
				if(f.isSetNr() && f.getNr()==2){json.setD2(f.getValue()+"");}
			}
		}
		TreeNode node = new DefaultTreeNode(json, parent);
		for(Figures f : figures.getFigures())
		{
			build(node,f);
		}
	}
}