package org.jeesl.web.mbean.prototype.admin.system.io;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.jeesl.api.facade.io.JeeslIoCmsFacade;
import org.jeesl.controller.handler.sb.SbSingleHandler;
import org.jeesl.factory.ejb.system.io.cms.EjbIoCmsFactory;
import org.jeesl.factory.ejb.util.EjbIdFactory;
import org.jeesl.interfaces.bean.sb.SbSingleBean;
import org.jeesl.interfaces.bean.sb.SbToggleBean;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCms;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsContent;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsElement;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsSection;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsVisiblity;
import org.jeesl.web.mbean.prototype.admin.AbstractAdminBean;
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
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public abstract class AbstractCmsBean <L extends UtilsLang,D extends UtilsDescription,
										CAT extends UtilsStatus<CAT,L,D>,
										CMS extends JeeslIoCms<L,D,CAT,CMS,V,S,E,T,C,M>,
										V extends JeeslIoCmsVisiblity<L,D,CAT,CMS,V,S,E,T,C,M>,
										S extends JeeslIoCmsSection<L,D,CAT,CMS,V,S,E,T,C,M>,
										E extends JeeslIoCmsElement<L,D,CAT,CMS,V,S,E,T,C,M>,
										T extends UtilsStatus<T,L,D>,
										C extends JeeslIoCmsContent<L,D,CAT,CMS,V,S,E,T,C,M>,
										M extends UtilsStatus<M,L,D>>
					extends AbstractAdminBean<L,D>
					implements Serializable,SbToggleBean,SbSingleBean
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractCmsBean.class);
	
	protected JeeslIoCmsFacade<L,D,CAT,CMS,V,S,E,T,C,M> fCms;
	
	private final Class<CMS> cCms;
	
	private final EjbIoCmsFactory<L,D,CAT,CMS,V,S,E,T,C,M> efCms;
	
	protected final SbSingleHandler<CMS> sbhCms; public SbSingleHandler<CMS> getSbhCms() {return sbhCms;}
//	protected SbMultiHandler<CMS> sbhCms; public SbMultiHandler<CMS> getSbhCms() {return sbhCms;}
	
	protected CMS cms; public CMS getCms() {return cms;} public void setCms(CMS cms) {this.cms = cms;}
	protected CAT category;
	
	private TreeNode root1;

     
    private TreeNode selectedNode1;
    public TreeNode getRoot1() {
        return root1;
    }
 
    public TreeNode getSelectedNode1() {
        return selectedNode1;
    }
 
    public void setSelectedNode1(TreeNode selectedNode1) {
        this.selectedNode1 = selectedNode1;
    }
     

	public AbstractCmsBean(final Class<L> cL, final Class<D> cD, final Class<CMS> cCms)
	{
		super(cL,cD);
		this.cCms=cCms;
		
		efCms = new EjbIoCmsFactory<L,D,CAT,CMS,V,S,E,T,C,M>(cCms);
		
		sbhCms = new SbSingleHandler<CMS>(cCms,this);
	}
	
	protected void initSuper(String[] langs, FacesMessageBean bMessage, JeeslIoCmsFacade<L,D,CAT,CMS,V,S,E,T,C,M> fCms)
	{
		super.initAdmin(langs,cL,cD,bMessage);
		this.fCms=fCms;
		reloadCms();
		
		root1 = new DefaultTreeNode("Root", null);
        TreeNode node0 = new DefaultTreeNode("Node 0", root1);
        TreeNode node1 = new DefaultTreeNode("Node 1", root1);
        TreeNode node2 = new DefaultTreeNode("Node 2", root1);
         
        TreeNode node00 = new DefaultTreeNode("Node 0.0", node0);
        TreeNode node01 = new DefaultTreeNode("Node 0.1", node0);
         
        TreeNode node10 = new DefaultTreeNode("Node 1.0", node1);
        TreeNode node11 = new DefaultTreeNode("Node 1.1", node1);
         
        TreeNode node000 = new DefaultTreeNode("Node 0.0.0", node00);
        TreeNode node001 = new DefaultTreeNode("Node 0.0.1", node00);
        TreeNode node010 = new DefaultTreeNode("Node 0.1.0", node01);
         
        TreeNode node100 = new DefaultTreeNode("Node 1.0.0", node10);
		
		
		sbhCms.silentCallback();
	}
	
	private void reloadCms()
	{
		sbhCms.setList(fCms.all(cCms));
	}
	
	@Override public void toggled(Class<?> c) throws UtilsLockingException, UtilsConstraintViolationException
	{
		logger.info(AbstractLogMessage.toggled(c));
	}
	
	@SuppressWarnings("unchecked")
	@Override public void selectSbSingle(EjbWithId ejb)
	{
		if(JeeslIoCms.class.isAssignableFrom(ejb.getClass()))
		{
			cms = (CMS)ejb;
			if(EjbIdFactory.isSaved(cms))
			{
				cms = efLang.persistMissingLangs(fCms,localeCodes,cms);
			}
			logger.info("Twice:"+sbhCms.getTwiceSelected()+" for "+cms.toString());
		}
		else
		{
			logger.info("NOT Assignable");
		}
	}
	
	public void addCms() throws UtilsNotFoundException
	{
//		if(debugOnInfo){logger.info(AbstractLogMessage.addEntity(cTemplate));}
		cms = efCms.build(null);
		cms.setName(efLang.createEmpty(localeCodes));
	}
	
	public void saveCms() throws UtilsConstraintViolationException, UtilsLockingException
	{
		logger.info("Save "+cms.toString());
		cms = fCms.save(cms);
		reloadCms();
	}
	
	public void onDragDrop(TreeDragDropEvent event) {
        TreeNode dragNode = event.getDragNode();
        TreeNode dropNode = event.getDropNode();
        int dropIndex = event.getDropIndex();
         
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Dragged " + dragNode.getData(), "Dropped on " + dropNode.getData() + " at " + dropIndex);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}