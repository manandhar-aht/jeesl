package net.sf.ahtutils.prototype.web.mbean.admin.security;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.bean.FacesMessageBean;
import net.sf.ahtutils.interfaces.facade.UtilsSecurityFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityAction;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityActionTemplate;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityCategory;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityRole;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityUsecase;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityView;
import net.sf.ahtutils.interfaces.model.system.security.UtilsUser;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractAdminSecurityActionBean <L extends UtilsLang,
											D extends UtilsDescription,
											C extends UtilsSecurityCategory<L,D,C,R,V,U,A,AT,USER>,
											R extends UtilsSecurityRole<L,D,C,R,V,U,A,AT,USER>,
											V extends UtilsSecurityView<L,D,C,R,V,U,A,AT,USER>,
											U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
											A extends UtilsSecurityAction<L,D,C,R,V,U,A,AT,USER>,
											AT extends UtilsSecurityActionTemplate<L,D,C,R,V,U,A,AT,USER>,
											USER extends UtilsUser<L,D,C,R,V,U,A,AT,USER>>
		extends AbstractAdminSecurityBean<L,D,C,R,V,U,A,AT,USER>
		implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminSecurityActionBean.class);

	private List<AT> templates;public List<AT> getTemplates(){return templates;}
	
	private AT template;public AT getTemplate(){return template;}public void setTemplate(AT template) {this.template = template;}
	
	public void initSuper(String[] langs, UtilsSecurityFacade<L,D,C,R,V,U,A,AT,USER> fSecurity, FacesMessageBean bMessage, final Class<L> cLang, final Class<D> cDescription, final Class<C> cCategory, final Class<R> cRole, final Class<V> cView, final Class<U> cUsecase, final Class<A> cAction, final Class<AT> cTemplate, final Class<USER> cUser)
	{
		categoryType = UtilsSecurityCategory.Type.action;
		initSecuritySuper(langs,fSecurity,bMessage,cLang,cDescription,cCategory,cRole,cView,cUsecase,cAction,cTemplate,cUser);
	}
	
	// SELECT
	public void selectCategory() throws UtilsNotFoundException
	{
		super.selectCategory();
		reloadTemplates();
		template=null;
	}
	
	private void reloadTemplates() throws UtilsNotFoundException
	{
		templates = fSecurity.allForCategory(cTemplate,cCategory,category.getCode());
		if(debugOnInfo){logger.info(AbstractLogMessage.reloaded(cTemplate, templates));}
	}

	public void selectTemplate()
	{
		logger.info(AbstractLogMessage.selectEntity(template));
		template = fSecurity.find(cTemplate, template);
		template = efLang.persistMissingLangs(fSecurity,langs,template);
		template = efDescription.persistMissingLangs(fSecurity,langs,template);
	}
	
	public void addTemplate() throws UtilsConstraintViolationException
	{
		logger.info(AbstractLogMessage.addEntity(cTemplate));
		template = efTemplate.build(category,"");
		template.setName(efLang.createEmpty(langs));
		template.setDescription(efDescription.createEmpty(langs));
	}
	
	
	//SAVE
	public void saveCategory() throws UtilsConstraintViolationException, UtilsLockingException
	{
		logger.info(AbstractLogMessage.saveEntity(category));
		category = fSecurity.save(category);
		reloadCategories();
		categorySaved();
	}
	
	public void saveTemplate() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.saveEntity(template));
		template.setCategory(fSecurity.find(cCategory, template.getCategory()));
		template = fSecurity.save(template);
		reloadTemplates();
	}
	
	
	protected void reorderTemplates() throws UtilsConstraintViolationException, UtilsLockingException
	{

	}
}