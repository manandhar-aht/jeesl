package org.jeesl.web.mbean.prototype.admin.system.security;

import java.io.Serializable;
import java.util.List;

import org.jeesl.api.facade.system.JeeslSecurityFacade;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityView;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityTemplate;
import org.jeesl.interfaces.model.system.security.user.UtilsUser;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityAction;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityUsecase;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityCategory;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.bean.FacesMessageBean;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.jsf.util.PositionListReorderer;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractAdminSecurityActionBean <L extends UtilsLang, D extends UtilsDescription,
											C extends JeeslSecurityCategory<L,D,C,R,V,U,A,AT,USER>,
											R extends JeeslSecurityRole<L,D,C,R,V,U,A,AT,USER>,
											V extends JeeslSecurityView<L,D,C,R,V,U,A,AT,USER>,
											U extends JeeslSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
											A extends JeeslSecurityAction<L,D,C,R,V,U,A,AT,USER>,
											AT extends JeeslSecurityTemplate<L,D,C,R,V,U,A,AT,USER>,
											USER extends UtilsUser<L,D,C,R,V,U,A,AT,USER>>
		extends AbstractAdminSecurityBean<L,D,C,R,V,U,A,AT,USER>
		implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminSecurityActionBean.class);

	private List<AT> templates;public List<AT> getTemplates(){return templates;}
	
	private AT template;public AT getTemplate(){return template;}public void setTemplate(AT template) {this.template = template;}
	
	public AbstractAdminSecurityActionBean(final Class<L> cL, final Class<D> cD, final Class<C> cCategory, final Class<R> cRole, final Class<V> cView, final Class<U> cUsecase, final Class<A> cAction, final Class<AT> cTemplate, final Class<USER> cUser)
	{
		super(cL,cD,cCategory,cRole,cView,cUsecase,cAction,cTemplate,cUser);
	}
	
	public void initSuper(String[] langs, JeeslSecurityFacade<L,D,C,R,V,U,A,AT,USER> fSecurity, FacesMessageBean bMessage)
	{
		categoryType = JeeslSecurityCategory.Type.action;
		initSecuritySuper(langs,fSecurity,bMessage);
	}
	
	@Override public void categorySelected() throws UtilsNotFoundException
	{
		reloadTemplates();
		template=null;
	}
	@Override protected void categorySaved() throws UtilsNotFoundException
	{
		reloadTemplates();
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
		template = efTemplate.build(category,"",templates);
		template.setName(efLang.createEmpty(langs));
		template.setDescription(efDescription.createEmpty(langs));
	}
	
	public void saveTemplate() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.saveEntity(template));
		template.setCategory(fSecurity.find(cCategory, template.getCategory()));
		template = fSecurity.save(template);
		reloadTemplates();
	}
	
	
	protected void reorderTemplates() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fSecurity, templates);}
}