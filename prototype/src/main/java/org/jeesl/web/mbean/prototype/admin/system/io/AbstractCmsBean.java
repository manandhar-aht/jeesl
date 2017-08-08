package org.jeesl.web.mbean.prototype.admin.system.io;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.jeesl.api.facade.io.JeeslIoCmsFacade;
import org.jeesl.controller.handler.op.OpStatusSelectionHandler;
import org.jeesl.controller.handler.sb.SbSingleHandler;
import org.jeesl.factory.ejb.system.io.cms.EjbIoCmsElementFactory;
import org.jeesl.factory.ejb.system.io.cms.EjbIoCmsFactory;
import org.jeesl.factory.ejb.system.io.cms.EjbIoCmsSectionFactory;
import org.jeesl.factory.ejb.util.EjbIdFactory;
import org.jeesl.interfaces.bean.op.OpEntityBean;
import org.jeesl.interfaces.bean.sb.SbSingleBean;
import org.jeesl.interfaces.bean.sb.SbToggleBean;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCms;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsContent;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsElement;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsSection;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsVisiblity;
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
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.bean.FacesMessageBean;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.jsf.util.PositionListReorderer;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public abstract class AbstractCmsBean <L extends UtilsLang,D extends UtilsDescription,
										CAT extends UtilsStatus<CAT,L,D>,
										CMS extends JeeslIoCms<L,D,CAT,CMS,V,S,E,T,C,M,LOC>,
										V extends JeeslIoCmsVisiblity<L,D,CAT,CMS,V,S,E,T,C,M,LOC>,
										S extends JeeslIoCmsSection<L,D,CAT,CMS,V,S,E,T,C,M,LOC>,
										E extends JeeslIoCmsElement<L,D,CAT,CMS,V,S,E,T,C,M,LOC>,
										T extends UtilsStatus<T,L,D>,
										C extends JeeslIoCmsContent<L,D,CAT,CMS,V,S,E,T,C,M,LOC>,
										M extends UtilsStatus<M,L,D>,
										LOC extends UtilsStatus<LOC,L,D>>
					extends AbstractAdminBean<L,D>
					implements Serializable,SbToggleBean,SbSingleBean,OpEntityBean
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractCmsBean.class);
	
	protected JeeslIoCmsFacade<L,D,CAT,CMS,V,S,E,T,C,M,LOC> fCms;
	
	private final Class<CAT> cCat;
	private final Class<CMS> cCms;
	private final Class<S> cSection;
	private final Class<E> cElement;
	private final Class<T> cType;
	private final Class<LOC> cLoc;
	
	private String currentLocaleCode;
	protected String[] cmsLocales; public String[] getCmsLocales() {return cmsLocales;}
	
	protected final EjbIoCmsFactory<L,D,CAT,CMS,V,S,E,T,C,M,LOC> efCms;
	private final EjbIoCmsSectionFactory<L,D,CAT,CMS,V,S,E,T,C,M,LOC> efS;
	private final EjbIoCmsElementFactory<L,D,CAT,CMS,V,S,E,T,C,M,LOC> efElement;
	
	protected final SbSingleHandler<CMS> sbhCms; public SbSingleHandler<CMS> getSbhCms() {return sbhCms;}
	private final SbSingleHandler<LOC> sbhLocale; public SbSingleHandler<LOC> getSbhLocale() {return sbhLocale;}
	private final OpStatusSelectionHandler<LOC> opLocale; public OpStatusSelectionHandler<LOC> getOpLocale() {return opLocale;}

	private List<E> elements; public List<E> getElements() {return elements;}
	private final List<T> types; public List<T> getTypes() {return types;}

	protected CMS cms; public CMS getCms() {return cms;} public void setCms(CMS cms) {this.cms = cms;}
	protected CAT category;
	private S section; public S getSection() {return section;} public void setSection(S section) {this.section = section;}
	protected E element; public E getElement() {return element;} public void setElement(E element) {this.element = element;}

	private TreeNode tree; public TreeNode getTree() {return tree;}
    private TreeNode node; public TreeNode getNode() {return node;} public void setNode(TreeNode node) {this.node = node;}

	public AbstractCmsBean(final Class<L> cL, final Class<D> cD, final Class<CAT> cCat, final Class<CMS> cCms, final Class<S> cSection, final Class<E> cElement, final Class<T> cType, final Class<LOC> cLoc)
	{
		super(cL,cD);
		this.cCat=cCat;
		this.cCms=cCms;
		this.cSection=cSection;
		this.cElement=cElement;
		this.cType=cType;
		this.cLoc=cLoc;
		
		efCms = new EjbIoCmsFactory<L,D,CAT,CMS,V,S,E,T,C,M,LOC>(cCms);
		efS = new EjbIoCmsSectionFactory<L,D,CAT,CMS,V,S,E,T,C,M,LOC>(cSection);
		efElement = new EjbIoCmsElementFactory<L,D,CAT,CMS,V,S,E,T,C,M,LOC>(cElement);
		
		sbhCms = new SbSingleHandler<CMS>(cCms,this);
		sbhLocale = new SbSingleHandler<LOC>(cLoc,this);
		opLocale = new OpStatusSelectionHandler<LOC>(this);
		
		types = new ArrayList<T>();
	}
	
	protected void initSuper(String[] langs, String currentLocaleCode, List<LOC> locales, FacesMessageBean bMessage, JeeslIoCmsFacade<L,D,CAT,CMS,V,S,E,T,C,M,LOC> fCms)
	{
		super.initAdmin(langs,cL,cD,bMessage);
		this.currentLocaleCode=currentLocaleCode;
		this.fCms=fCms;
		
		opLocale.setOpList(locales);
		
		types.addAll(fCms.allOrderedPositionVisible(cType));
	}
	
	protected <EN extends Enum<EN>> void initCategory(EN code)
	{
		try {category = fCms.fByCode(cCat, code);}
		catch (UtilsNotFoundException e) {logger.error(e.getMessage());}
	}
	
	protected abstract void reloadCmsDocuments();
	protected void reloadCmsDocumentsForCategory()
	{
		sbhCms.setList(fCms.allForCategory(cCms,category));
		logger.info(AbstractLogMessage.reloaded(cCms, sbhCms.getList()));
	}
	
	@Override public void toggled(Class<?> c) throws UtilsLockingException, UtilsConstraintViolationException
	{
		logger.info(AbstractLogMessage.toggled(c));
	}
	
	@SuppressWarnings("unchecked")
	@Override public void selectSbSingle(EjbWithId ejb)
	{
		if(ejb==null) {cms=null;}
		else if(JeeslIoCms.class.isAssignableFrom(ejb.getClass()))
		{
			cms = (CMS)ejb;
			if(EjbIdFactory.isSaved(cms))
			{
				cms = efLang.persistMissingLangs(fCms,localeCodes,cms);
				reloadCms();
				reloadTree();
			}
			logger.info("Twice:"+sbhCms.getTwiceSelected()+" for "+cms.toString());
		}
		else
		{
			logger.info("NOT Assignable");
		}
		reset(true);
	}
	
	private void reset(boolean rElement)
	{
		if(rElement) {element=null;}
	}
	
	protected abstract void addDocument();
	public void addDocumentForCategory()
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.addEntity(cCms));}
		cms = efCms.build(category,efS.build());
		cms.setName(efLang.createEmpty(localeCodes));
	}
	
	protected abstract void saveDocument() throws UtilsConstraintViolationException, UtilsLockingException;
	public void saveCms() throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.saveEntity(cms));}
		cms = fCms.save(cms);
		savedCms();
		reloadCms();
	}
	protected void savedCms() throws UtilsLockingException, UtilsConstraintViolationException
	{
		sbhCms.selectSbSingle(cms);
		reloadCmsDocuments();
		reloadTree();
	}
	
	public void selectDocument()
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.selectEntity(cms));}
		reloadCms();
	}
	
	private void reloadCms()
	{
		cms = fCms.find(cCms,cms);
		opLocale.setTbList(cms.getLocales());
		
		cmsLocales = new String[cms.getLocales().size()];
		for(int i=0;i<cms.getLocales().size();i++) {cmsLocales[i]=cms.getLocales().get(i).getCode();}
		
		sbhLocale.setList(cms.getLocales());
		sbhLocale.setSelection(null);
		for(LOC l : cms.getLocales())
		{
			if(l.getCode().equals(currentLocaleCode)) {sbhLocale.setSelection(l);break;}
		}
		if(!sbhLocale.isSelected() && !cms.getLocales().isEmpty()) {sbhLocale.setSelection(cms.getLocales().get(0));}
	}
	
	private void reloadTree()
	{
		S root = fCms.load(cms.getRoot(),true);
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
        		S db = fCms.load(child,false);
        		efS.update(db,child);
        		child.setSection(parent);
        		child.setPosition(index);
        		fCms.save(child);
        		index++;
        }  
    }
	
	public void onNodeExpand(NodeExpandEvent event)
	{
		logger.info("Expanded "+event.getTreeNode().toString());
    }
 
    public void onNodeCollapse(NodeCollapseEvent event)
    {
    		logger.info("Collapsed "+event.getTreeNode().toString());
    }
	
    @SuppressWarnings("unchecked")
	public void onSectionSelect(NodeSelectEvent event)
    {
    		logger.info("Selected "+event.getTreeNode().toString());
    		section = (S)event.getTreeNode().getData();
    		section = efLang.persistMissingLangs(fCms, cmsLocales, section);
    		S db = fCms.load(section,false);
    		efS.update(db,section);
    		reloadSection();
    		reset(true);
    }
    
	@Override public void addOpEntity(EjbWithId item) throws UtilsLockingException, UtilsConstraintViolationException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.addOpEntity(item));}
		if(cLoc.isAssignableFrom(item.getClass()))
		{
			LOC locale = (LOC)item;
			if(!cms.getLocales().contains(locale))
			{
				cms.getLocales().add(locale);
				cms = fCms.save(cms);
				reloadCms();
			}
		}
	}
	@Override public void rmOpEntity(EjbWithId item) throws UtilsLockingException, UtilsConstraintViolationException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.rmOpEntity(item));}
		if(cLoc.isAssignableFrom(item.getClass()))
		{
			LOC locale = (LOC)item;
			if(cms.getLocales().contains(locale))
			{
				cms.getLocales().remove(locale);
				cms = fCms.save(cms);
				reloadCms();
			}
		}
	}
    
	public void addSection() 
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.addEntity(cSection));}
		section = efS.build(cms.getRoot());
		section.setName(efLang.createEmpty(cmsLocales));
		for(String k : section.getName().keySet()){section.getName().get(k).setLang("XXX");}
	}
	
	public void saveSection() throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.saveEntity(section));}
		boolean appendToTree = EjbIdFactory.isUnSaved(section);
		
		section = fCms.save(section);
		if(appendToTree) {new DefaultTreeNode(section, tree);}
		reloadSection();
	}
	
	protected void reloadSection()
	{
		elements = fCms.allForParent(cElement,section);
		elements = fCms.fCmsElements(section);
	}
	
	public void addElement() 
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.addEntity(cElement));}
		element = efElement.build(section,elements);
	}
	
	public void selectElement() 
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.selectEntity(element));}
	}
	
	public void saveElement() throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.saveEntity(element));}
		element.setType(fCms.find(cType,element.getType()));
		element = fCms.save(element);
		reloadSection();
	}
	protected abstract void savedElement();
	
	
	protected void reorderDocuments() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fCms, sbhCms.getList());}
	protected void reorderElements() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fCms, elements);}
}