package net.sf.ahtutils.interfaces.controller.report;

import org.primefaces.model.TreeNode;

public interface UtilsTreeReport extends UtilsReport
{		
	void buildTree();
	TreeNode getTree();
}