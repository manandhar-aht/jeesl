package org.jeesl.web.mbean.prototype.system.io.dms;

import java.io.Serializable;
import java.util.List;

import org.jeesl.api.bean.JeeslTranslationBean;
import org.jeesl.api.facade.io.JeeslIoDmsFacade;
import org.jeesl.controller.handler.sb.SbSingleHandler;
import org.jeesl.factory.builder.io.IoDmsFactoryBuilder;
import org.jeesl.factory.ejb.util.EjbIdFactory;
import org.jeesl.interfaces.bean.sb.SbSingleBean;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeSet;
import org.jeesl.interfaces.model.system.io.dms.JeeslIoDms;
import org.jeesl.interfaces.model.system.io.dms.JeeslIoDmsSection;
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
import net.sf.ahtutils.interfaces.bean.FacesMessageBean;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public abstract class AbstractAdminDmsTreeBean <L extends UtilsLang,D extends UtilsDescription,
											DMS extends JeeslIoDms<L,D,AS,S>,
											AS extends JeeslAttributeSet<L,D,?,?>,
											S extends JeeslIoDmsSection<L,S>>
					extends AbstractDmsBean<L,D,DMS,AS,S>
					implements Serializable,SbSingleBean
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminDmsTreeBean.class);	

	protected final SbSingleHandler<DMS> sbhDms; public SbSingleHandler<DMS> getSbhDms() {return sbhDms;}
	
	private TreeNode tree; public TreeNode getTree() {return tree;}
	private TreeNode node; public TreeNode getNode() {return node;} public void setNode(TreeNode node) {this.node = node;}
	private S section; public S getSection() {return section;} public void setSection(S section) {this.section = section;}
	
	public AbstractAdminDmsTreeBean(IoDmsFactoryBuilder<L,D,DMS,AS,S> fbDms)
	{
		super(fbDms);
		sbhDms = new SbSingleHandler<DMS>(fbDms.getClassDms(),this);
	}
	
	protected void initDmsConfig(JeeslTranslationBean bTranslation, FacesMessageBean bMessage,JeeslIoDmsFacade<L,D,DMS,AS,S> fDms)
	{
		super.initDms(bTranslation,bMessage,fDms);
		initPageConfiguration();
		sbhDms.silentCallback();
	}
	protected abstract void initPageConfiguration();
	
	
	@Override @SuppressWarnings("unchecked")
	public void selectSbSingle(EjbWithId item) throws UtilsLockingException, UtilsConstraintViolationException
	{
		logger.info(AbstractLogMessage.selectEntity(item));
		this.dm = (DMS)item;
		reloadTree();
	}
	
	private void reloadTree()
	{
		if(debugOnInfo) {logger.info("Reloading Tree");}
		S root = fDms.load(dm.getRoot(),true);
		tree = new DefaultTreeNode(root, null);
		buildTree(tree,root.getSections());
	}
	
	private void buildTree(TreeNode parent, List<S> sections)
	{
		for(S s : sections)
		{
			TreeNode n = new DefaultTreeNode(s, parent);
			if(!s.getSections().isEmpty()) {buildTree(n,s.getSections());}
		}
	}
	
	public void onNodeExpand(NodeExpandEvent event) {if(debugOnInfo) {logger.info("Expanded "+event.getTreeNode().toString());}}
    public void onNodeCollapse(NodeCollapseEvent event) {if(debugOnInfo) {logger.info("Collapsed "+event.getTreeNode().toString());}}

    
	@SuppressWarnings("unchecked")
	public void onDragDrop(TreeDragDropEvent event) throws UtilsConstraintViolationException, UtilsLockingException
	{
        TreeNode dragNode = event.getDragNode();
        TreeNode dropNode = event.getDropNode();
        int dropIndex = event.getDropIndex();
        logger.info("Dragged " + dragNode.getData() + "Dropped on " + dropNode.getData() + " at " + dropIndex);
        
        logger.info("Childs of "+dropNode.getData());
        S parent = (S)dropNode.getData();
        int index=1;
        for(TreeNode n : dropNode.getChildren())
        {
        		S child =(S)n.getData();
        		S db = fDms.load(child,false);
        		efSection.update(db,child);
        		child.setSection(parent);
        		child.setPosition(index);
        		fDms.save(child);
        		index++;
        }  
    }

    @SuppressWarnings("unchecked")
	public void onSectionSelect(NodeSelectEvent event)
    {
    		logger.info("Selected "+event.getTreeNode().toString());
    		section = (S)event.getTreeNode().getData();
    		section = efLang.persistMissingLangs(fDms, localeCodes, section);
    		S db = fDms.load(section,false);
    		efSection.update(db,section);
    }

    
	public void addSection() 
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.addEntity(fbDms.getClassSection()));}
		section = efSection.build(dm.getRoot());
		section.setName(efLang.createEmpty(localeCodes));
	}
	
	public void saveSection() throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.saveEntity(section));}
		boolean appendToTree = EjbIdFactory.isUnSaved(section);		
		section = fDms.save(section);
		if(appendToTree) {new DefaultTreeNode(section, tree);}
//		reloadSection();
	}
}