package org.jeesl.web.mbean.prototype.system.io.cms;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.jeesl.api.facade.io.JeeslIoCmsFacade;
import org.jeesl.controller.handler.op.OpStatusSelectionHandler;
import org.jeesl.controller.handler.sb.SbSingleHandler;
import org.jeesl.factory.builder.io.IoCmsFactoryBuilder;
import org.jeesl.factory.ejb.system.io.cms.EjbIoCmsContentFactory;
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
import net.sf.exlp.util.io.StringUtil;

public abstract class AbstractCmsBean <L extends UtilsLang,D extends UtilsDescription,LOC extends UtilsStatus<LOC,L,D>,
										CAT extends UtilsStatus<CAT,L,D>,
										CMS extends JeeslIoCms<L,D,CAT,S,LOC>,
										V extends JeeslIoCmsVisiblity,
										S extends JeeslIoCmsSection<L,S>,
										E extends JeeslIoCmsElement<L,D,CAT,CMS,V,S,EC,ET,C,MT,LOC>,
										EC extends UtilsStatus<EC,L,D>,
										ET extends UtilsStatus<ET,L,D>,
										C extends JeeslIoCmsContent<L,D,V,S,E,EC,ET,C,MT,LOC>,
										MT extends UtilsStatus<MT,L,D>
										>
					extends AbstractAdminBean<L,D>
					implements Serializable,SbToggleBean,SbSingleBean,OpEntityBean
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractCmsBean.class);
	
	protected JeeslIoCmsFacade<L,D,CAT,CMS,V,S,E,EC,ET,C,MT,LOC> fCms;
	
	private final Class<CAT> cCat;
	private final Class<CMS> cCms;
	private final Class<S> cSection;
	private final Class<E> cElement;
	private final Class<EC> cElementCategory;
	private final Class<ET> cType;
	private final Class<MT> cMarkup;
	private final Class<LOC> cLoc;
	
	private String currentLocaleCode;
	protected String[] cmsLocales; public String[] getCmsLocales() {return cmsLocales;}
	
	protected final EjbIoCmsFactory<L,D,CAT,CMS,V,S,E,EC,ET,C,MT,LOC> efCms;
	private final EjbIoCmsSectionFactory<L,S> efS;
	private final EjbIoCmsElementFactory<L,D,CAT,CMS,V,S,E,EC,ET,C,MT,LOC> efElement;
	private final EjbIoCmsContentFactory<L,D,CAT,CMS,V,S,E,EC,ET,C,MT,LOC> efContent;
	
	protected final SbSingleHandler<CMS> sbhCms; public SbSingleHandler<CMS> getSbhCms() {return sbhCms;}
	private final SbSingleHandler<LOC> sbhLocale; public SbSingleHandler<LOC> getSbhLocale() {return sbhLocale;}
	private final OpStatusSelectionHandler<LOC> opLocale; public OpStatusSelectionHandler<LOC> getOpLocale() {return opLocale;}

	private List<E> elements; public List<E> getElements() {return elements;}
	private List<EC> elementCategories; public List<EC> getElementCategories() {return elementCategories;}
	private List<ET> types; public List<ET> getTypes() {return types;}

	protected CMS cms; public CMS getCms() {return cms;} public void setCms(CMS cms) {this.cms = cms;}
	protected CAT category;
	private C content; public C getContent() {return content;} public void setContent(C content) {this.content = content;}

	protected EC elementCategory; public EC getElementCategory() {return elementCategory;} public void setElementCategory(EC elementCategory) {this.elementCategory = elementCategory;}
	
	private S section; public S getSection() {return section;} public void setSection(S section) {this.section = section;}
	protected E element; public E getElement() {return element;} public void setElement(E element) {this.element = element;}
	
	private MT markupHtml;
	
	private TreeNode tree; public TreeNode getTree() {return tree;}
    private TreeNode node; public TreeNode getNode() {return node;} public void setNode(TreeNode node) {this.node = node;}

	public AbstractCmsBean(IoCmsFactoryBuilder<L,D,CAT,CMS,V,S,E,EC,ET,C,MT,LOC> fbCms,
			final Class<LOC> cLoc, final Class<CAT> cCat, final Class<CMS> cCms, final Class<S> cSection, final Class<E> cElement, Class<EC> cElementCategory, final Class<ET> cType, final Class<C> cContent, final Class<MT> cMarkup)
	{
		super(fbCms.getClassL(),fbCms.getClassD());
		this.cLoc=cLoc;
		this.cCat=cCat;
		this.cCms=cCms;
		this.cSection=cSection;
		this.cElement=cElement;
		this.cElementCategory=cElementCategory;
		this.cType=cType;
		this.cMarkup=cMarkup;
		
		efCms = new EjbIoCmsFactory<L,D,CAT,CMS,V,S,E,EC,ET,C,MT,LOC>(cCms);
		efS = new EjbIoCmsSectionFactory<L,S>(cSection);
		efElement = new EjbIoCmsElementFactory<L,D,CAT,CMS,V,S,E,EC,ET,C,MT,LOC>(cElement);
		efContent = EjbIoCmsContentFactory.factory(cContent);
		
		sbhCms = new SbSingleHandler<CMS>(cCms,this);
		sbhLocale = new SbSingleHandler<LOC>(cLoc,this);
		opLocale = new OpStatusSelectionHandler<LOC>(this);
		
		types = new ArrayList<ET>();
	}
	
	protected void initSuper(String[] langs, String currentLocaleCode, List<LOC> locales, FacesMessageBean bMessage, JeeslIoCmsFacade<L,D,CAT,CMS,V,S,E,EC,ET,C,MT,LOC> fCms)
	{
		super.initAdmin(langs,cL,cD,bMessage);
		this.currentLocaleCode=currentLocaleCode;
		this.fCms=fCms;
		
		opLocale.setOpList(locales);
		
		elementCategories=fCms.allOrderedPositionVisible(cElementCategory);
		types.addAll(fCms.allOrderedPositionVisible(cType));
		
		try {markupHtml = fCms.fByCode(cMarkup, JeeslIoCmsContent.Markup.html);}
		catch (UtilsNotFoundException e) {e.printStackTrace();}
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
				cmsSelected();
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
	
	protected void cmsSelected(){}
	
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
		logger.info("SBHLocale.selection==null:"+(sbhLocale.getSelection()==null));
	}
	
	private void reloadTree()
	{
		S root = fCms.load(cms.getRoot(),true);
		
		if(debugOnInfo)
		{
			logger.info(StringUtil.stars());
			if(sbhLocale.getSelection()!=null)
			{
				for(S s : root.getSections())
				{
					logger.info(s.toString()+" "+s.getName().get(sbhLocale.getSelection().getCode()).getLang());
				}
			}
			else {logger.info("No Sections, because sbhLocale is null");}
		}
		
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
        		S db = fCms.load(child,false);
        		efS.update(db,child);
        		child.setSection(parent);
        		child.setPosition(index);
        		fCms.save(child);
        		index++;
        }  
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
    
	@SuppressWarnings("unchecked")
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
	@SuppressWarnings("unchecked")
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
		selectedElement();
	}
	
	public void changeElementCategory()
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.selectOneMenuChange(elementCategory));}
	}
	
	public void saveElement() throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.saveEntity(element));}
		element.setType(fCms.find(cType,element.getType()));
		element = fCms.save(element);
		reloadSection();
		selectedElement();
	}
	
	public void deleteElement() throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.saveEntity(element));}
		fCms.rm(element);
		reset(true);
		reloadSection();
	}
	
	protected void selectedElement()
	{
		if(element.getType().getCode().equals(JeeslIoCmsElement.Type.paragraph.toString()))
		{
			if(!element.getContent().containsKey(sbhLocale.getSelection().getCode()))
			{
				element.getContent().put(sbhLocale.getSelection().getCode(), efContent.build(element,sbhLocale.getSelection(), "", markupHtml));
			}
		}
	}
	
	public void saveParagraph() throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.saveEntity(element)+" "+element.getContent().size());}
		
		element = fCms.save(element);
	}
	
	protected void reorderDocuments() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fCms, sbhCms.getList());}
	protected void reorderElements() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fCms, elements);}
}