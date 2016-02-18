package net.sf.ahtutils.prototype.web.mbean.admin.utils;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

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
import net.sf.ahtutils.interfaces.model.graphic.UtilsGraphic;
import net.sf.ahtutils.interfaces.model.graphic.UtilsGraphicStyle;
import net.sf.ahtutils.interfaces.model.graphic.UtilsGraphicType;
import net.sf.ahtutils.interfaces.model.graphic.UtilsWithGraphic;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.status.UtilsWithImage;
import net.sf.ahtutils.interfaces.model.status.UtilsWithSymbol;
import net.sf.ahtutils.interfaces.model.with.code.EjbWithCode;
import net.sf.ahtutils.interfaces.model.with.parent.EjbWithParent;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPosition;
import net.sf.ahtutils.interfaces.web.UtilsJsfSecurityHandler;
import net.sf.ahtutils.jsf.util.PositionListReorderer;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;
import net.sf.ahtutils.prototype.web.mbean.admin.AbstractAdminBean;
import net.sf.exlp.util.io.StringUtil;

public class AbstractOptionTableBean <L extends UtilsLang,
										D extends UtilsDescription,
										G extends UtilsGraphic<L,D,G,GT,GS>,
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
	
	protected Object status; public Object getStatus() {return status;} public void setStatus(Object status) {this.status = status;}
	
	@SuppressWarnings("rawtypes")
	protected Class cl,clParent;
	
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
		supportsGraphic = UtilsWithGraphic.class.isAssignableFrom(cl);
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
	
	public void selectCategory(boolean reset) throws ClassNotFoundException{}
	
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
			GT type = fUtils.fByCode(cGT, UtilsGraphicType.Code.symbol.toString());
			GS style = fUtils.fByCode(cGS, UtilsGraphicStyle.Code.circle.toString());
			G g = efGraphic.buildSymbol(type, style);
			((UtilsWithGraphic<L,D,G,GT,GS>)status).setGraphic(g);
		}
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
        	if(supportsGraphic && ((UtilsWithGraphic<L,D,G,GT,GS>)status).getGraphic()!=null)
            {
        		((UtilsWithGraphic<L,D,G,GT,GS>)status).getGraphic().setType(fUtils.find(cGT, ((UtilsWithGraphic<L,D,G,GT,GS>)status).getGraphic().getType()));
            	if(((UtilsWithGraphic<L,D,G,GT,GS>)status).getGraphic().getStyle()!=null){((UtilsWithGraphic<L,D,G,GT,GS>)status).getGraphic().setStyle(fUtils.find(cGS, ((UtilsWithGraphic<L,D,G,GT,GS>)status).getGraphic().getStyle()));}
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
		}
		catch (UtilsConstraintViolationException e)
		{
//			addMessage(new FacesMessage(FacesMessage.SEVERITY_WARN,"WARN", e.getMessage()));
		}
	}
	
	public void selectCategory() throws ClassNotFoundException{}
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
		((UtilsWithGraphic<L,D,G,GT,GS>)status).getGraphic().setData(file.getContents());  
	}
	
//	@Override
	@SuppressWarnings("unchecked")
	public void changeGraphicType()
	{
		((UtilsWithGraphic<L,D,G,GT,GS>)status).getGraphic().setType(fUtils.find(cGT, ((UtilsWithGraphic<L,D,G,GT,GS>)status).getGraphic().getType()));
		logger.info("changeGraphicType to "+((UtilsWithGraphic<L,D,G,GT,GS>)status).getGraphic().getType().getCode());
	}
}