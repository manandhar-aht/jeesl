package org.jeesl.web.mbean.prototype.system.io.dms;

import java.io.Serializable;
import java.util.List;

import org.jeesl.api.bean.JeeslTranslationBean;
import org.jeesl.api.facade.io.JeeslIoDmsFacade;
import org.jeesl.controller.handler.sb.SbSingleHandler;
import org.jeesl.factory.builder.io.IoDmsFactoryBuilder;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeContainer;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeSet;
import org.jeesl.interfaces.model.system.io.dms.JeeslIoDms;
import org.jeesl.interfaces.model.system.io.dms.JeeslIoDmsFile;
import org.jeesl.interfaces.model.system.io.dms.JeeslIoDmsSection;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileContainer;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileStorage;
import org.primefaces.event.NodeCollapseEvent;
import org.primefaces.event.NodeExpandEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.interfaces.bean.FacesMessageBean;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public abstract class AbstractDmsUploadBean <L extends UtilsLang,D extends UtilsDescription,LOC extends UtilsStatus<LOC,L,D>,
											DMS extends JeeslIoDms<L,D,STORAGE,AS,S>,
											STORAGE extends JeeslFileStorage<L,D,?>,
											AS extends JeeslAttributeSet<L,D,?,?>,
											S extends JeeslIoDmsSection<L,S>,
											F extends JeeslIoDmsFile<L,S,FC,AC>,
											FC extends JeeslFileContainer<?,?>,
											
											AC extends JeeslAttributeContainer<?,?>
	//										AD extends JeeslAttributeData<?,?,AC>
>
					extends AbstractDmsBean<L,D,LOC,DMS,STORAGE,AS,S,F,FC,AC>
					implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractDmsUploadBean.class);	

	protected final SbSingleHandler<DMS> sbhDms; public SbSingleHandler<DMS> getSbhDms() {return sbhDms;}
	
//	private AttributeHandler<L,D,IoAttributeCategory,IoAttributeCriteria,IoAttributeType,IoAttributeOption,IoAttributeSet,IoAttributeItem,AC,IoAttributeData> handler; public AttributeHandler<L,D,IoAttributeCategory,IoAttributeCriteria,IoAttributeType,IoAttributeOption,IoAttributeSet,IoAttributeItem,AC,IoAttributeData> getHandler() {return handler;}

	//private final IoAttributeFactoryBuilder<L,D,?,?,?,?,AS,?,AC,AD> fbAttribute;
	
	private TreeNode tree; public TreeNode getTree() {return tree;}
	private TreeNode node; public TreeNode getNode() {return node;} public void setNode(TreeNode node) {this.node = node;}
	private S section; public S getSection() {return section;} public void setSection(S section) {this.section = section;}
	
	public AbstractDmsUploadBean(IoDmsFactoryBuilder<L,D,LOC,DMS,STORAGE,AS,S,F> fbDms)
	{
		super(fbDms);
		sbhDms = new SbSingleHandler<DMS>(fbDms.getClassDms(),this);
	}
	
	protected void initDmsUpload(JeeslTranslationBean bTranslation, FacesMessageBean bMessage,JeeslIoDmsFacade<L,D,LOC,DMS,STORAGE,AS,S,F,FC,AC> fDms)
	{
		super.initDms(bTranslation,bMessage,fDms);
		initPageConfiguration();
		super.initLocales();
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
	public void onSectionSelect(NodeSelectEvent event)
    {
    		logger.info("Selected "+event.getTreeNode().toString());
    		section = (S)event.getTreeNode().getData();
    		section = efLang.persistMissingLangs(fDms, localeCodes, section);
    		S db = fDms.load(section,false);
    		efSection.update(db,section);
    }

    

}