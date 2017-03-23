package org.jeesl.web.mbean.prototype.admin;

import java.io.Serializable;

import org.jeesl.jsf.util.NullNumberBinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.factory.ejb.status.EjbDescriptionFactory;
import net.sf.ahtutils.factory.ejb.status.EjbLangFactory;
import net.sf.ahtutils.interfaces.bean.FacesMessageBean;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.web.UtilsJsfSecurityHandler;
import net.sf.exlp.util.io.StringUtil;

public class AbstractAdminBean <L extends UtilsLang,D extends UtilsDescription>
					implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminBean.class);
	
	protected FacesMessageBean bMessage;
	
	protected boolean debugOnInfo;
	protected String[] langs; public String[] getLangs() {return langs;}
	protected EjbLangFactory<L> efLang;
	protected EjbDescriptionFactory<D> efDescription;
	
	protected boolean hasDeveloperAction; public boolean isHasDeveloperAction() {return hasDeveloperAction;}
	protected boolean hasAdministratorAction; public boolean isHasAdministratorAction() {return hasAdministratorAction;}
	protected boolean hasTranslatorAction;
	
	protected NullNumberBinder nnb; public NullNumberBinder getNnb() {return nnb;} public void setNnb(NullNumberBinder nnb) {this.nnb = nnb;}

	public AbstractAdminBean()
	{
		debugOnInfo = false;
		
		hasDeveloperAction = true;
		hasAdministratorAction = true;
		hasTranslatorAction = true;
		
		uiAllowAdd = true;
		uiAllowSave = true;
		uiAllowRemove = true;
		uiAllowReorder = true;
		uiAllowCode = true;
		
		uiShowInvisible = true;
		uiShowDocumentation = true;
		
		nnb = new NullNumberBinder();
	}
	
	protected void initAdmin(String[] langs, final Class<L> cLang, final Class<D> cDescription, FacesMessageBean bMessage)
	{	
		this.langs=langs;
		this.bMessage=bMessage;
		
		efLang = new EjbLangFactory<L>(cLang);
		efDescription = new EjbDescriptionFactory<D>(cDescription);
	}
	
	//Security Handling
	protected boolean uiAllowAdd;public boolean isUiAllowAdd() {return uiAllowAdd;}
	protected boolean uiAllowSave; public boolean getUiAllowSave() {return uiAllowSave;}
	protected boolean uiAllowRemove; public boolean isUiAllowRemove() {return uiAllowRemove;}
	protected boolean uiAllowReorder; public boolean getUiAllowReorder() {return uiAllowReorder;}
	protected boolean uiAllowCode;public boolean isUiAllowCode() {return uiAllowCode;}
	protected boolean uiShowInvisible; public boolean isUiShowInvisible() {return uiShowInvisible;}
	protected boolean uiShowDocumentation; public boolean isUiShowDocumentation() {return uiShowDocumentation;}
	
	@SuppressWarnings("rawtypes")
	protected void updateSecurity2(UtilsJsfSecurityHandler jsfSecurityHandler, String viewCode)
	{
		String actionDeveloper = viewCode+".Developer";
		String actionAdministrator = viewCode+".Administrator";
		String actionTranslator = viewCode+".Translator";
		
		hasDeveloperAction = jsfSecurityHandler.allow(actionDeveloper);
		hasAdministratorAction = jsfSecurityHandler.allow(actionAdministrator);
		hasTranslatorAction = jsfSecurityHandler.allow(actionTranslator);
		
		uiAllowAdd = hasDeveloperAction || hasAdministratorAction;
		uiAllowSave = hasDeveloperAction || hasAdministratorAction || hasTranslatorAction;
		uiAllowRemove = hasDeveloperAction || hasAdministratorAction;
		uiAllowReorder = hasDeveloperAction || hasAdministratorAction;
		uiAllowCode = hasDeveloperAction;
		
		uiShowInvisible = hasDeveloperAction;
		uiShowDocumentation = hasDeveloperAction;
	}
	
	protected void debugUi(boolean debug)
	{
		if(debug)
		{
			logger.info(StringUtil.stars());
			logger.info("UI Settings");
			logger.info("\t"+uiAllowAdd+" allowStatusAdd");
			logger.info("\t"+uiAllowReorder+" uiAllowReorder");
			logger.info("\t"+uiAllowCode+" uiAllowCode");
			logger.info("\t"+uiAllowSave+" uiAllowSave");
			logger.info("\t"+uiAllowRemove+" uiAllowRemove");
		}
	}
	
	protected void debugSecurity(boolean debug)
	{
		if(debug)
		{
			logger.info(StringUtil.stars());
			logger.info("Security Settings");
			logger.info("\t"+hasDeveloperAction+" hasDeveloperAction");
			logger.info("\t"+hasAdministratorAction+" hasAdministratorAction");
			logger.info("\t"+hasTranslatorAction+" hasTranslatorAction");
		}
	}
}