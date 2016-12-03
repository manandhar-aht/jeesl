package org.jeesl.web.mbean.prototype.admin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.jeesl.interfaces.model.system.symbol.JeeslGraphic;
import org.jeesl.interfaces.model.system.symbol.JeeslGraphicStyle;
import org.jeesl.interfaces.model.system.symbol.JeeslGraphicType;
import org.jeesl.interfaces.model.system.with.EjbWithGraphic;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.factory.ejb.symbol.EjbGraphicFactory;
import net.sf.ahtutils.interfaces.bean.FacesMessageBean;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.status.UtilsStatusFixedCode;
import net.sf.ahtutils.interfaces.model.status.UtilsWithImage;
import net.sf.ahtutils.interfaces.model.status.UtilsWithSymbol;
import net.sf.ahtutils.interfaces.model.with.code.EjbWithCode;
import net.sf.ahtutils.interfaces.model.with.image.EjbWithImage;
import net.sf.ahtutils.interfaces.model.with.image.EjbWithImageAlt;
import net.sf.ahtutils.interfaces.model.with.parent.EjbWithParent;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPosition;
import net.sf.ahtutils.interfaces.web.UtilsJsfSecurityHandler;
import net.sf.ahtutils.jsf.util.PositionListReorderer;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;
import net.sf.exlp.util.io.StringUtil;

public class AbstractOptionTableBean <L extends UtilsLang,
										D extends UtilsDescription,
										G extends JeeslGraphic<L,D,G,GT,GS>,
										GT extends UtilsStatus<GT,L,D>,
										GS extends UtilsStatus<GS,L,D>>
			extends AbstractAdminBean<L,D>
			implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(AbstractOptionTableBean.class);
	private static final long serialVersionUID = 1L;

	protected UtilsFacade fUtils;
	
	protected boolean allowSvg; public boolean isAllowSvg() {return allowSvg;}
	
	protected boolean supportsSymbol; public boolean getSupportsSymbol(){return supportsSymbol;}
	protected boolean supportsImage; public boolean getSupportsImage() {return supportsImage;}
	protected boolean supportsGraphic; public boolean getSupportsGraphic() {return supportsGraphic;}
		
	protected long index;
	protected Map<Long,Boolean> allowAdditionalElements; public Map<Long, Boolean> getAllowAdditionalElements(){return allowAdditionalElements;}
	
	protected Object category; public Object getCategory() {return category;} public void setCategory(Object category) {this.category = category;}
	protected Object status; public Object getStatus() {return status;} public void setStatus(Object status) {this.status = status;}
	private G graphic; public G getGraphic() {return graphic;} public void setGraphic(G graphic) {this.graphic = graphic;}

	@SuppressWarnings("rawtypes")
	protected Class cl,clParent;
	
	protected List<EjbWithPosition> categories; public List<EjbWithPosition> getCategories(){return categories;}
	protected List<EjbWithPosition> parents; public List<EjbWithPosition> getParents(){return parents;}
	protected List<EjbWithPosition> items; public List<EjbWithPosition> getItems() {return items;}
	
	protected Class<?> cStatus;
	
	private Class<GT> cGT;
	private Class<GS> cGS;
	
	protected long parentId; public long getParentId(){return parentId;}public void setParentId(long parentId){this.parentId = parentId;}
	
	protected EjbGraphicFactory<L,D,G,GT,GS> efGraphic;
	
	public AbstractOptionTableBean()
	{
		index=1;
		
		hasDeveloperAction = false;
		hasAdministratorAction = true;
		hasTranslatorAction = true;
		
		status = null;
		allowAdditionalElements = new Hashtable<Long,Boolean>();
		
		categories = new ArrayList<EjbWithPosition>();
	}
	
	protected void initUtils(String[] langs, UtilsFacade fUtils, FacesMessageBean bMessage, Class<L> cLang, Class<D> cDescription, Class<?> cStatus, Class<G> cG, Class<GT> cGT, Class<GS> cGS)
	{
		super.initAdmin(langs,cLang,cDescription,bMessage);
		this.fUtils=fUtils;
		this.cStatus=cStatus;
		this.cGT=cGT;
		this.cGS=cGS;
		
		efGraphic = EjbGraphicFactory.factory(cG);
	}
	
	protected void updateSecurity(UtilsJsfSecurityHandler jsfSecurityHandler, String viewCode)
	{
		super.updateSecurity2(jsfSecurityHandler, viewCode);
	}
	
	protected void updateUiForCategory()
	{
		supportsImage = UtilsWithImage.class.isAssignableFrom(cl);
		supportsGraphic = EjbWithGraphic.class.isAssignableFrom(cl);
		supportsSymbol = UtilsWithSymbol.class.isAssignableFrom(cl);		
		
		if(logger.isInfoEnabled())
		{
			logger.info("Image? "+supportsImage);
			logger.info("Graphic? "+supportsGraphic);
			logger.info("Symbol? "+supportsSymbol);
		} 
	}
	
	protected void debugUi(boolean debug)
	{
		super.debugUi(debug);
		if(debug)
		{
			logger.info(StringUtil.stars());
			logger.info("Option Tables Settings");
			logger.info("\tImage? "+supportsImage);
			logger.info("\tGraphic? "+supportsGraphic);
			logger.info("\tSymbol? "+supportsSymbol);
		}
	}
	
	public void selectCategory() throws ClassNotFoundException{selectCategory(true);}
	@SuppressWarnings("unchecked")
	public void selectCategory(boolean reset) throws ClassNotFoundException
	{
		logger.info("selectCategory "+((EjbWithCode)category).getCode() +" ("+ ((EjbWithImageAlt)category).getImageAlt()+") allowAdditionalElements:"+allowAdditionalElements.get(((EjbWithId)category).getId()));
		cl = Class.forName(((EjbWithImage)category).getImage());
		updateUiForCategory();
		
		uiAllowAdd = allowAdditionalElements.get(((EjbWithId)category).getId()) || hasDeveloperAction;
		
		if(((EjbWithImageAlt)category).getImageAlt()!=null)
		{
            clParent = Class.forName(((EjbWithImageAlt)category).getImageAlt()).asSubclass(cStatus);
            parents = fUtils.all(clParent);
            logger.info(cl.getSimpleName()+" "+parents.size());
		}
		else
		{
			clParent=null;
			parents=null;
		}
		reloadStatusEntries();
		if(reset){status = null;}
		debugUi(true);
	}
	
	@SuppressWarnings("unchecked")
	protected void reloadStatusEntries()
	{
		items = fUtils.allOrderedPosition(cl);
	}
	
	@SuppressWarnings("unchecked")
	public void add() throws UtilsConstraintViolationException, InstantiationException, IllegalAccessException, UtilsNotFoundException
	{
		logger.debug("add");
		uiAllowCode=true;
		
		status = cl.newInstance();
		((EjbWithId)status).setId(0);
		((EjbWithCode)status).setCode("enter code");
		((EjbWithLang<L>)status).setName(efLang.createEmpty(langs));
		((EjbWithDescription<D>)status).setDescription(efDescription.createEmpty(langs));
		
		if(supportsGraphic)
		{
			GT type = fUtils.fByCode(cGT, JeeslGraphicType.Code.symbol.toString());
			GS style = fUtils.fByCode(cGS, JeeslGraphicStyle.Code.circle.toString());
			graphic = efGraphic.buildSymbol(type, style);
			((EjbWithGraphic<L,D,G,GT,GS>)status).setGraphic(graphic);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void selectStatus() throws UtilsConstraintViolationException, UtilsNotFoundException, UtilsLockingException
	{
		status = fUtils.find(cl,(EjbWithId)status);
		status = fUtils.load(cl,(EjbWithId)status);
		logger.debug("selectStatus");
		status = efLang.persistMissingLangs(fUtils,langs,(EjbWithLang)status);
		status = efDescription.persistMissingLangs(fUtils,langs,(EjbWithDescription)status);
		
		if(((EjbWithParent)status).getParent()!=null)
		{
			parentId=((EjbWithParent)status).getParent().getId();
		}
		
		if(supportsGraphic)
		{
			if(((EjbWithGraphic<L,D,G,GT,GS>)status).getGraphic()==null)
			{
				logger.info("Need to create a graphic entity for this status");
				GT type = fUtils.fByCode(cGT, JeeslGraphicType.Code.symbol.toString());
				GS style = fUtils.fByCode(cGS, JeeslGraphicStyle.Code.circle.toString());
				graphic = fUtils.persist(efGraphic.buildSymbol(type, style));
				((EjbWithGraphic<L,D,G,GT,GS>)status).setGraphic(graphic);
				status = fUtils.update(status);
			}
			else 
			{
				graphic = ((EjbWithGraphic<L,D,G,GT,GS>)status).getGraphic();
			}
		}
		
		uiAllowCode = hasDeveloperAction || hasAdministratorAction;
		if(hasDeveloperAction){uiAllowCode=true;}
		else if(status instanceof UtilsStatusFixedCode)
		{
			for(String fixed : ((UtilsStatusFixedCode)status).getFixedCodes())
			{
				if(fixed.equals(((UtilsStatus)status).getCode()))
				{
					uiAllowCode=false;
				}
			}
		}
		debugUi(false);
		pageFlowPrimarySelect(status);
	}

	@SuppressWarnings("unchecked")
	public void save() throws ClassNotFoundException, UtilsNotFoundException
    {
		try
		{
            if(clParent!=null && parentId>0)
            {
            	((EjbWithParent)status).setParent((EjbWithCode)fUtils.find(clParent, parentId));
            }
        	if(supportsGraphic && graphic!=null)
            {
        		graphic.setType(fUtils.find(cGT, ((EjbWithGraphic<L,D,G,GT,GS>)status).getGraphic().getType()));
            	if(graphic.getStyle()!=null){graphic.setStyle(fUtils.find(cGS, ((EjbWithGraphic<L,D,G,GT,GS>)status).getGraphic().getStyle()));}
            	((EjbWithGraphic<L,D,G,GT,GS>)status).setGraphic(graphic);
            }

			status = fUtils.save((EjbSaveable)status);
			updateAppScopeBean2(status);
			selectCategory(false);
			bMessage.growlSuccessSaved();
		}
		catch (UtilsConstraintViolationException e) {bMessage.errorConstraintViolationInUse();}
		catch (UtilsLockingException e) {bMessage.errorConstraintViolationInUse();}
	}
	
	public void rm() throws ClassNotFoundException
	{
		try
		{
			fUtils.rm((EjbRemoveable)status);
			updateAppScopeBean2(status);
			status=null;
			selectCategory();
			bMessage.growlSuccessRemoved();
		}
		catch (UtilsConstraintViolationException e)
		{
			bMessage.errorConstraintViolationInUse();
		}
	}
	
	public void cancel()
	{
		status=null;
	}
	
	protected void updateAppScopeBean2(Object o){}
	
	public void reorder() throws UtilsConstraintViolationException, UtilsLockingException
	{
		logger.info("updateOrder "+items.size());
		PositionListReorderer.reorder(fUtils, items);
	}
	
	@SuppressWarnings("unchecked")
	public void handleFileUpload(FileUploadEvent event)
	{
		UploadedFile file = event.getFile();
		logger.info("Received file with a size of " +file.getSize());
		((EjbWithGraphic<L,D,G,GT,GS>)status).getGraphic().setData(file.getContents());  
	}
	
//	@Override
	@SuppressWarnings("unchecked")
	public void changeGraphicType()
	{
		((EjbWithGraphic<L,D,G,GT,GS>)status).getGraphic().setType(fUtils.find(cGT, ((EjbWithGraphic<L,D,G,GT,GS>)status).getGraphic().getType()));
		logger.info("changeGraphicType to "+((EjbWithGraphic<L,D,G,GT,GS>)status).getGraphic().getType().getCode());
	}
	
	//Revision
	public void pageFlowPrimarySelect(Object revision) {}
	public void pageFlowPrimaryCancel() {}
	public void pageFlowPrimarySave(Object revision) {}
	public void pageFlowPrimaryAdd() {}
}