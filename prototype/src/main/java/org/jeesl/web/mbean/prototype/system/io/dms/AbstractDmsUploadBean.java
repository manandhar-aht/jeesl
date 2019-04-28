package org.jeesl.web.mbean.prototype.system.io.dms;

import java.io.Serializable;
import java.util.List;

import org.jeesl.api.bean.JeeslAttributeBean;
import org.jeesl.api.bean.JeeslTranslationBean;
import org.jeesl.api.bean.callback.JeeslFileRepositoryCallback;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.api.facade.io.JeeslIoAttributeFacade;
import org.jeesl.api.facade.io.JeeslIoDmsFacade;
import org.jeesl.api.facade.io.JeeslIoFrFacade;
import org.jeesl.controller.handler.AttributeHandler;
import org.jeesl.controller.handler.sb.SbSingleHandler;
import org.jeesl.factory.builder.io.IoAttributeFactoryBuilder;
import org.jeesl.factory.builder.io.IoDmsFactoryBuilder;
import org.jeesl.factory.builder.io.IoFileRepositoryFactoryBuilder;
import org.jeesl.interfaces.bean.AttributeBean;
import org.jeesl.interfaces.controller.handler.JeeslAttributeHandler;
import org.jeesl.interfaces.controller.handler.JeeslFileRepositoryHandler;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeContainer;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeCriteria;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeData;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeItem;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeOption;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeSet;
import org.jeesl.interfaces.model.system.io.dms.JeeslIoDms;
import org.jeesl.interfaces.model.system.io.dms.JeeslIoDmsDocument;
import org.jeesl.interfaces.model.system.io.dms.JeeslIoDmsLayer;
import org.jeesl.interfaces.model.system.io.dms.JeeslIoDmsSection;
import org.jeesl.interfaces.model.system.io.dms.JeeslIoDmsView;
import org.jeesl.interfaces.model.system.io.domain.JeeslDomainSet;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileContainer;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileMeta;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileStorage;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileType;
import org.primefaces.event.NodeCollapseEvent;
import org.primefaces.event.NodeExpandEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.jsf.util.PositionListReorderer;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public abstract class AbstractDmsUploadBean <L extends UtilsLang,D extends UtilsDescription,LOC extends UtilsStatus<LOC,L,D>,
											DMS extends JeeslIoDms<L,D,FSTORAGE,ASET,DS,S>,
											S extends JeeslIoDmsSection<L,D,S>,
											FILE extends JeeslIoDmsDocument<L,S,FCONTAINER,ACONTAINER>,
											VIEW extends JeeslIoDmsView<L,D,DMS>,
											LAYER extends JeeslIoDmsLayer<VIEW,AITEM>,
											
											FSTORAGE extends JeeslFileStorage<L,D,FENGINE>,
											FENGINE extends UtilsStatus<FENGINE,L,D>,
											FCONTAINER extends JeeslFileContainer<FSTORAGE,FMETA>,
											FMETA extends JeeslFileMeta<D,FCONTAINER,FTYPE>,
											FTYPE extends JeeslFileType<FTYPE,L,D,?>,
											
											DS extends JeeslDomainSet<L,D,?>,
											
											ACATEGORY extends UtilsStatus<ACATEGORY,L,D>,
											ACRITERIA extends JeeslAttributeCriteria<L,D,ACATEGORY,ATYPE>,
											ATYPE extends UtilsStatus<ATYPE,L,D>,
											AOPTION extends JeeslAttributeOption<L,D,ACRITERIA>,
											ASET extends JeeslAttributeSet<L,D,ACATEGORY,AITEM>,
											AITEM extends JeeslAttributeItem<ACRITERIA,ASET>,
											ACONTAINER extends JeeslAttributeContainer<ASET,ADATA>,
											ADATA extends JeeslAttributeData<ACRITERIA,AOPTION,ACONTAINER>
>
					extends AbstractDmsBean<L,D,LOC,DMS,FSTORAGE,ASET,DS,S,FILE,VIEW,LAYER,FCONTAINER,AITEM,ACONTAINER>
					implements Serializable,AttributeBean<ACONTAINER>,JeeslFileRepositoryCallback
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractDmsUploadBean.class);	

	private JeeslIoAttributeFacade<L,D,ACATEGORY,ACRITERIA,ATYPE,AOPTION,ASET,AITEM,ACONTAINER,ADATA> fAttribute; 
	private JeeslIoFrFacade<L,D,FSTORAGE,FENGINE,FCONTAINER,FMETA,FTYPE> fFr;
	
	private final IoAttributeFactoryBuilder<L,D,ACATEGORY,ACRITERIA,ATYPE,AOPTION,ASET,AITEM,ACONTAINER,ADATA> fbAttribute;
//	private final IoFileRepositoryFactoryBuilder<L,D,FSTORAGE,FENGINE,FCONTAINER,FMETA,FTYPE> fbFr;
	
	protected final SbSingleHandler<DMS> sbhDms; public SbSingleHandler<DMS> getSbhDms() {return sbhDms;}

	private AttributeHandler<L,D,ACATEGORY,ACRITERIA,ATYPE,AOPTION,ASET,AITEM,ACONTAINER,ADATA> attributeHandler; public AttributeHandler<L,D,ACATEGORY,ACRITERIA,ATYPE,AOPTION,ASET,AITEM,ACONTAINER,ADATA> getAttributeHandler() {return attributeHandler;}
	private JeeslFileRepositoryHandler<FSTORAGE,FCONTAINER,FMETA> fileHandler; public JeeslFileRepositoryHandler<FSTORAGE,FCONTAINER,FMETA> getFileHandler() {return fileHandler;}

	private List<FILE> files; public List<FILE> getFiles() {return files;} public void setFiles(List<FILE> files) {this.files = files;}

	private TreeNode tree; public TreeNode getTree() {return tree;}
	private TreeNode node; public TreeNode getNode() {return node;} public void setNode(TreeNode node) {this.node = node;}
	private S section; public S getSection() {return section;} public void setSection(S section) {this.section = section;}
	private FILE file; public FILE getFile() {return file;} public void setFile(FILE file) {this.file = file;}

	public AbstractDmsUploadBean(final IoDmsFactoryBuilder<L,D,LOC,DMS,FSTORAGE,S,FILE,VIEW,LAYER> fbDms,
								final IoAttributeFactoryBuilder<L,D,ACATEGORY,ACRITERIA,ATYPE,AOPTION,ASET,AITEM,ACONTAINER,ADATA> fbAttribute,
								final IoFileRepositoryFactoryBuilder<L,D,LOC,FSTORAGE,FENGINE,FCONTAINER,FMETA,FTYPE> fbFr)
	{
		super(fbDms);
		this.fbAttribute=fbAttribute;
//		this.fbFr=fbFr;
		sbhDms = new SbSingleHandler<DMS>(fbDms.getClassDms(),this);
	}
	
	protected void postConstructDmsUpload(JeeslTranslationBean<L,D,LOC> bTranslation, JeeslFacesMessageBean bMessage,
								JeeslIoDmsFacade<L,D,LOC,DMS,FSTORAGE,ASET,DS,S,FILE,VIEW,FCONTAINER,ACONTAINER> fDms,
								JeeslIoFrFacade<L,D,FSTORAGE,FENGINE,FCONTAINER,FMETA,FTYPE> fFr,
								JeeslIoAttributeFacade<L,D,ACATEGORY,ACRITERIA,ATYPE,AOPTION,ASET,AITEM,ACONTAINER,ADATA> fAttribute,
								JeeslAttributeBean<L,D,ACATEGORY,ACRITERIA,ATYPE,AOPTION,ASET,AITEM,ACONTAINER,ADATA> bAttribute,
								JeeslFileRepositoryHandler<FSTORAGE,FCONTAINER,FMETA> fileHandler
								)
	{
		super.initDms(bTranslation,bMessage,fDms);
		this.fFr=fFr;
		this.fAttribute=fAttribute;
		this.fileHandler=fileHandler;
		
		initPageConfiguration();
		super.initLocales();
		attributeHandler = fbAttribute.handler(bMessage,fAttribute,bAttribute,this);
//		fileHandler = fbFr.handler(fFr,this);
		sbhDms.silentCallback();
	}
	protected abstract void initPageConfiguration();
	
	
	@Override @SuppressWarnings("unchecked")
	public void selectSbSingle(EjbWithId item) throws UtilsLockingException, UtilsConstraintViolationException
	{
		logger.info(AbstractLogMessage.selectEntity(item));
		this.dm = (DMS)item;
		attributeHandler.init(dm.getSet());
		fileHandler.setStorage(dm.getStorage());
		reloadTree();
		reset(true,true);
	}
	
	private void reset(boolean rSection, boolean rFile)
	{
		if(rSection) {section=null;}
		if(rFile) {file=null;}
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
		reloadFiles();
		reset(false,true);
    }
    
    private void reloadFiles()
    {
    	files = fDms.allForParent(fbDms.getClassFile(),section);
    }

    public void addFile() throws UtilsConstraintViolationException, UtilsLockingException
    {
		if(debugOnInfo) {logger.info(AbstractLogMessage.addEntity(fbDms.getClassFile()));}
		file = efFile.build(section, files);
		file.setName(efLang.createEmpty(sbhLocale.getList()));
		attributeHandler.prepare(file);
		fileHandler.init(file,false);
    }
    	
    public void saveFile() throws UtilsConstraintViolationException, UtilsLockingException
    {
		if(debugOnInfo) {logger.info(AbstractLogMessage.saveEntity(file));}
		file = fDms.save(file);
		reloadFiles();
		fileHandler.init(file,false);
		attributeHandler.prepare(file);
    }
    
    public void selectFile() throws UtilsConstraintViolationException, UtilsLockingException
    {
		if(debugOnInfo) {logger.info(AbstractLogMessage.selectEntity((file)));}
		file = efLang.persistMissingLangs(fDms, sbhLocale.getList(), file);
		attributeHandler.prepare(file);
		fileHandler.init(file,false);
    }
    
    public void deleteFile() throws UtilsConstraintViolationException, UtilsLockingException
    {
		if(debugOnInfo) {logger.info(AbstractLogMessage.rmEntity(file));}
		
		if(fileHandler.getMetas()!=null && fileHandler.getMetas().size()>0)
		{
			bMessage.errorConstraintViolationInUse();
			return;
		}
		
		if(file.getAttributeContainer()!=null)	
		{
			ACONTAINER container = file.getAttributeContainer();
			fAttribute.rm(container);
			file.setAttributeContainer(null);
		}
		fFr.rm(file);
		reset(false,true);
		reloadFiles();
    }
    
	@Override
	public void save(JeeslAttributeHandler<ACONTAINER> handler) throws UtilsConstraintViolationException, UtilsLockingException
	{
		file.setAttributeContainer(handler.saveContainer());
		file = fAttribute.save(file);
	}
	
	@Override public void fileRepositoryContainerSaved(EjbWithId id) throws UtilsConstraintViolationException, UtilsLockingException
	{
		file.setFrContainer(fileHandler.getContainer());
		file = fFr.save(file);
	}
	
	public void reorderFiles() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fDms, files);}

	public void resetFile()
	{
		if(debugOnInfo){logger.info("Action canceled.");}
	}
}