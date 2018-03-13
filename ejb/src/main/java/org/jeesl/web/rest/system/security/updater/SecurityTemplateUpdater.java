package org.jeesl.web.rest.system.security.updater;

import org.jeesl.api.facade.system.JeeslSecurityFacade;
import org.jeesl.api.rest.system.security.JeeslSecurityRestTemplateImport;
import org.jeesl.controller.db.updater.JeeslDbCodeEjbUpdater;
import org.jeesl.factory.builder.system.SecurityFactoryBuilder;
import org.jeesl.factory.xml.system.io.sync.XmlDataUpdateFactory;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityView;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityTemplate;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityAction;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityUsecase;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityCategory;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityMenu;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.exception.processing.UtilsConfigurationException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.xml.security.Security;
import net.sf.ahtutils.xml.sync.DataUpdate;

public class SecurityTemplateUpdater <L extends UtilsLang,
 								D extends UtilsDescription, 
 								C extends JeeslSecurityCategory<L,D>,
 								R extends JeeslSecurityRole<L,D,C,V,U,A,USER>,
 								V extends JeeslSecurityView<L,D,C,R,U,A>,
 								U extends JeeslSecurityUsecase<L,D,C,R,V,A>,
 								A extends JeeslSecurityAction<L,D,R,V,U,AT>,
 								AT extends JeeslSecurityTemplate<L,D,C>,
 								M extends JeeslSecurityMenu<V,M>,
 								USER extends JeeslUser<R>>
		extends AbstractSecurityUpdater<L,D,C,R,V,U,A,AT,M,USER>
		implements JeeslSecurityRestTemplateImport
{
	final static Logger logger = LoggerFactory.getLogger(SecurityTemplateUpdater.class);
	
	private JeeslDbCodeEjbUpdater<R> updateRole;
	
	public SecurityTemplateUpdater(SecurityFactoryBuilder<L,D,C,R,V,U,A,AT,M,USER> fbSecurity,JeeslSecurityFacade<L,D,C,R,V,U,A,AT,USER> fSecurity)
	{       
        super(fbSecurity,fSecurity);
	}
	
	public DataUpdate iuSecurityTemplates(Security security)
	{
		updateRole = JeeslDbCodeEjbUpdater.createFactory(fbSecurity.getClassRole());
		updateRole.dbEjbs(fSecurity.all(fbSecurity.getClassRole()));

		DataUpdate du = XmlDataUpdateFactory.build();
		logger.warn("NYI iuSecurityTemplates");
/*		try
		{
			iuCategory(security, UtilsSecurityCategory.Type.role);
			du.setResult(XmlResultFactory.buildOk());
		}
		catch (UtilsConfigurationException e)
		{
			e.printStackTrace();
			du.setResult(XmlResultFactory.buildFail());
		}
		
		updateRole.remove(fSecurity);
		logger.trace("iuRoles finished");
*/
		return du;
	}
	
	@Override protected void iuChilds(C category, net.sf.ahtutils.xml.security.Category templates) throws UtilsConfigurationException
	{
		if(templates.isSetTemplates() && templates.getTemplates().isSetTemplate())
		{
			for(net.sf.ahtutils.xml.security.Template template : templates.getTemplates().getTemplate())
			{
				updateRole.handled(template.getCode());
				iuTemplate(category, template);
			}
		}
	}
	
	private void iuTemplate(C category, net.sf.ahtutils.xml.security.Template role) throws UtilsConfigurationException
	{
		AT ejb;
		try
		{
			ejb = fSecurity.fByCode(fbSecurity.getClassTemplate(),role.getCode());
			efLang.rmLang(fSecurity,ejb);
			efDescription.rmDescription(fSecurity,ejb);
		}
		catch (UtilsNotFoundException e)
		{
			try
			{
				ejb = fbSecurity.getClassTemplate().newInstance();
				ejb.setCategory(category);
				ejb.setCode(role.getCode());
				ejb = fSecurity.persist(ejb);				
			}
			catch (InstantiationException e2) {throw new UtilsConfigurationException(e2.getMessage());}
			catch (IllegalAccessException e2) {throw new UtilsConfigurationException(e2.getMessage());}
			catch (UtilsConstraintViolationException e2) {throw new UtilsConfigurationException(e2.getMessage());}
		}
		
		try
		{
			if(role.isSetVisible()){ejb.setVisible(role.isVisible());}else{ejb.setVisible(true);}
			if(role.isSetPosition()){ejb.setPosition(role.getPosition());}else{ejb.setPosition(0);}
			
			ejb.setName(efLang.getLangMap(role.getLangs()));
			ejb.setDescription(efDescription.create(role.getDescriptions()));
			ejb.setCategory(category);
			ejb=fSecurity.update(ejb);
		}
		catch (UtilsConstraintViolationException e) {logger.error("",e);}
		catch (UtilsLockingException e) {logger.error("",e);}
	}
}