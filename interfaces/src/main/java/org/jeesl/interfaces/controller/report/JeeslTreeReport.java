package org.jeesl.interfaces.controller.report;

import org.jeesl.interfaces.model.system.io.report.JeeslIoReport;
import org.primefaces.model.TreeNode;

public interface JeeslTreeReport <REPORT extends JeeslIoReport<?,?,?,?>>
			extends JeeslReport
{		
	void buildTree();
	TreeNode getTree();
}