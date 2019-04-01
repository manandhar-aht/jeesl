package org.jeesl.web.mbean.prototype.system.page;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.jeesl.api.bean.JeeslTranslationBean;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.api.facade.system.JeeslSecurityFacade;
import org.jeesl.factory.builder.system.SecurityFactoryBuilder;
import org.jeesl.factory.ejb.system.security.EjbSecurityMenuFactory;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityMenu;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityView;
import org.jeesl.web.mbean.prototype.admin.AbstractAdminBean;
import org.primefaces.event.NodeCollapseEvent;
import org.primefaces.event.NodeExpandEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.TreeDragDropEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public abstract class AbstractSystemPageBean <L extends UtilsLang, D extends UtilsDescription,
											V extends JeeslSecurityView<L,D,?,?,?,?>,
											M extends JeeslSecurityMenu<V,M>>
		extends AbstractAdminBean<L,D>
		implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractSystemPageBean.class);
	
	private final SecurityFactoryBuilder<L,D,?,?,V,?,?,?,M,?> fbSecurity;
	private final EjbSecurityMenuFactory<V,M> efMenu;
	
	private JeeslSecurityFacade<L,D,?,?,V,?,?,?,M,?> fSecurity;
	
	private TreeNode tree; public TreeNode getTree() {return tree;}
	private TreeNode node; public TreeNode getNode() {return node;} public void setNode(TreeNode node) {this.node = node;}
	private M menu; public M getMenu() {return menu;}
	
	public AbstractSystemPageBean(SecurityFactoryBuilder<L,D,?,?,V,?,?,?,M,?> fbSecurity)
	{
		super(fbSecurity.getClassL(),fbSecurity.getClassD());
		this.fbSecurity=fbSecurity;
		efMenu = fbSecurity.ejbMenu(fbSecurity.getClassMenu());
	}
	
	public void postConstructSystemPage(JeeslSecurityFacade<L,D,?,?,V,?,?,?,M,?> fSecurity, JeeslTranslationBean<L,D,?> bTranslation, JeeslFacesMessageBean bMessage)
	{
		reload();
	}
		
	public void reload()
    {
		List<M> list = fSecurity.all(fbSecurity.getClassMenu());
		Map<M,List<M>> map = efMenu.toMapChild(list);
	    tree = new DefaultTreeNode(null, null);
	    buildTree(tree, efMenu.toListRoot(list),map);
    }
	    
	private void buildTree(TreeNode parent, List<M> items, Map<M,List<M>> map)
	{
		for(M menu : items)
		{
			TreeNode n = new DefaultTreeNode(menu, parent);
			if(map.containsKey(menu))
			{
				buildTree(n, map.get(menu),map);
			}
		}
	}
	
	public void onNodeExpand(NodeExpandEvent event) {if(debugOnInfo) {logger.info("Expanded "+event.getTreeNode().toString());}}
    public void onNodeCollapse(NodeCollapseEvent event) {if(debugOnInfo) {logger.info("Collapsed "+event.getTreeNode().toString());}}
	
    @SuppressWarnings("unchecked")
	public void onNodeSelect(NodeSelectEvent event)
    {
    		logger.info("Selected "+event.getTreeNode().toString());
    		menu = (M)event.getTreeNode().getData();
    }
}