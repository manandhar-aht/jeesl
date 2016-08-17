package org.jeesl.interfaces.controller.report;

import org.primefaces.model.TreeNode;

public interface JeeslTreeReport extends JeeslReport
{		
	void buildTree();
	TreeNode getTree();
}