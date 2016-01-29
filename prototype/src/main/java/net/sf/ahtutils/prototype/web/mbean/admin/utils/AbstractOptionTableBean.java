package net.sf.ahtutils.prototype.web.mbean.admin.utils;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.factory.ejb.status.EjbDescriptionFactory;
import net.sf.ahtutils.factory.ejb.status.EjbLangFactory;
import net.sf.ahtutils.factory.ejb.symbol.EjbGraphicFactory;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.symbol.UtilsGraphic;
import net.sf.ahtutils.interfaces.model.symbol.UtilsSymbol;
import net.sf.ahtutils.interfaces.model.with.UtilsWithGraphic;
import net.sf.ahtutils.interfaces.web.UtilsJsfSecurityHandler;
import net.sf.exlp.util.io.StringUtil;

public class AbstractOptionTableBean <L extends UtilsLang,
										D extends UtilsDescription,
										G extends UtilsGraphic<L,D,GT,GS>,
										GT extends UtilsStatus<GT,L,D>,
										GS extends UtilsStatus<GS,L,D>>
										implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(AbstractOptionTableBean.class);
	private static final long serialVersionUID = 1L;

	protected boolean allowStatusAdd;public boolean isAllowStatusAdd() {return allowStatusAdd;}
	protected boolean allowStatusReorder;public boolean isAllowStatusReorder() {return allowStatusReorder;}
	protected boolean allowCodeChange;public boolean isAllowCodeChange() {return allowCodeChange;}
	protected boolean allowSave; public boolean isAllowSave() {return allowSave;}
	protected boolean allowRemove; public boolean isAllowRemove() {return allowRemove;}
	protected boolean allowSvg; public boolean isAllowSvg() {return allowSvg;}
	
	protected boolean supportsGraphic; public boolean getSupportsGraphic() {return supportsGraphic;}
	protected boolean supportsSymbol; public boolean getSupportsSymbol(){return supportsSymbol;}
	
	protected boolean hasDeveloperAction;public boolean isHasDeveloperAction() {return hasDeveloperAction;}
	protected boolean hasAdministratorAction,hasTranslatorAction;
	
	protected long index;
	protected Map<Long,Boolean> allowAdditionalElements; public Map<Long, Boolean> getAllowAdditionalElements(){return allowAdditionalElements;}
	
	@SuppressWarnings("rawtypes")
	protected Class cl;
	
	protected long parentId; public long getParentId(){return parentId;}public void setParentId(long parentId){this.parentId = parentId;}
	
	protected EjbLangFactory<L> efLang;
	protected EjbDescriptionFactory<D> efDescription;
	protected EjbGraphicFactory<L,D,G,GT,GS> efGraphic;
	
	public AbstractOptionTableBean()
	{
		index=1;
		
		hasDeveloperAction = false;
		hasAdministratorAction = true;
		hasTranslatorAction = true;
		
		allowStatusReorder = true;
		allowSave = true;
		allowRemove = true;
		
		allowAdditionalElements = new Hashtable<Long,Boolean>();
	}
	
	protected void initUtils(Class<L> cL, Class<D> cD, Class<G> cG, Class<GT> cGT, Class<GS> cGS)
	{
		efLang = EjbLangFactory.createFactory(cL);
		efDescription = EjbDescriptionFactory.createFactory(cD);
		efGraphic = EjbGraphicFactory.factory(cG);
	}
	
	protected void updateSecurity(UtilsJsfSecurityHandler jsfSecurityHandler, String viewCode)
	{
		String actionDeveloper = viewCode+".Developer";
		String actionAdministrator = viewCode+".Administrator";
		String actionTranslator = viewCode+".Translator";
		
		hasDeveloperAction = jsfSecurityHandler.allow(actionDeveloper);
		hasAdministratorAction = jsfSecurityHandler.allow(actionAdministrator);
		hasTranslatorAction = jsfSecurityHandler.allow(actionTranslator);
		
		allowStatusReorder = hasDeveloperAction || hasAdministratorAction;
		allowSave = hasDeveloperAction || hasAdministratorAction || hasTranslatorAction;
		allowRemove = hasDeveloperAction || hasAdministratorAction;
		
		if(logger.isInfoEnabled())
		{
			
		}
	}
	
	protected void updateUiForCategory()
	{
		supportsGraphic = UtilsWithGraphic.class.isAssignableFrom(cl);
		supportsSymbol = UtilsSymbol.class.isAssignableFrom(cl);		
		
		if(logger.isInfoEnabled())
		{
			logger.info("Graphic? "+supportsGraphic);
			logger.info("Symbol? "+supportsGraphic);
		} 
	}
	
	public void reorder() throws UtilsConstraintViolationException, UtilsLockingException {}
	
	public void debugSecurity(boolean debug)
	{
		if(debug)
		{
			logger.info(StringUtil.stars());
			logger.info("Security");
			logger.info("\t"+hasDeveloperAction+" hasDeveloperAction");
			logger.info("\t"+hasAdministratorAction+" hasAdministratorAction");
			logger.info("\t"+hasTranslatorAction+" hasTranslatorAction");
		}
	}
	
	public void debugUi(boolean debug)
	{
		if(debug)
		{
			logger.info(StringUtil.stars());
			logger.info("Page Actions");
			logger.info("\t"+allowStatusAdd+" allowStatusAdd");
			logger.info("\t"+allowStatusReorder+" allowStatusReorder");
			logger.info("\t"+allowCodeChange+" allowCodeChange");
			logger.info("\t"+allowSave+" allowSave");
			logger.info("\t"+allowRemove+" allowRemove");
		}
	}
}