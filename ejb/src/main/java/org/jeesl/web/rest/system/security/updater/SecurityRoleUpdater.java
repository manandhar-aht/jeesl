package org.jeesl.web.rest.system.security.updater;

import org.jeesl.api.facade.system.JeeslSecurityFacade;
import org.jeesl.api.rest.system.security.JeeslSecurityRestRoleImport;
import org.jeesl.controller.db.updater.JeeslDbCodeEjbUpdater;
import org.jeesl.factory.builder.system.SecurityFactoryBuilder;
import org.jeesl.factory.xml.system.io.sync.XmlDataUpdateFactory;
import org.jeesl.factory.xml.system.io.sync.XmlResultFactory;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityAction;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityCategory;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityMenu;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityTemplate;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityUsecase;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityView;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.exception.processing.UtilsConfigurationException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.xml.security.Security;
import net.sf.ahtutils.xml.security.Usecase;
import net.sf.ahtutils.xml.security.Usecases;
import net.sf.ahtutils.xml.sync.DataUpdate;

public class SecurityRoleUpdater <L extends UtilsLang,D extends UtilsDescription, 
 								C extends JeeslSecurityCategory<L,D>,
 								R extends JeeslSecurityRole<L,D,C,V,U,A,USER>,
 								V extends JeeslSecurityView<L,D,C,R,U,A>,
 								U extends JeeslSecurityUsecase<L,D,C,R,V,A>,
 								A extends JeeslSecurityAction<L,D,R,V,U,AT>,
 								AT extends JeeslSecurityTemplate<L,D,C>,
 								M extends JeeslSecurityMenu<V,M>,
 								USER extends JeeslUser<R>>
		extends AbstractSecurityUpdater<L,D,C,R,V,U,A,AT,M,USER>
		implements JeeslSecurityRestRoleImport
{
	final static Logger logger = LoggerFactory.getLogger(SecurityRoleUpdater.class);
	
	private JeeslDbCodeEjbUpdater<R> updateRole;
	
	public SecurityRoleUpdater(SecurityFactoryBuilder<L,D,C,R,V,U,A,AT,M,USER> fbSecurity, JeeslSecurityFacade<L,D,C,R,V,U,A,AT,USER> fSecurity)
	{       
        super(fbSecurity,fSecurity);
	}
	
	@Override protected void iuChilds(C aclCategory, net.sf.ahtutils.xml.security.Category category) throws UtilsConfigurationException
	{
		if(category.isSetRoles() && category.getRoles().isSetRole())
		{
			for(net.sf.ahtutils.xml.security.Role role : category.getRoles().getRole())
			{
				updateRole.handled(role.getCode());
				iuRole(aclCategory, role);
			}
		}
	}
	
	public DataUpdate iuSecurityRoles(Security security)
	{
		updateRole = JeeslDbCodeEjbUpdater.createFactory(fbSecurity.getClassRole());
		updateRole.dbEjbs(fSecurity.all(fbSecurity.getClassRole()));

		DataUpdate du = XmlDataUpdateFactory.build();
		try
		{
			iuCategory(security, JeeslSecurityCategory.Type.role);
			du.setResult(XmlResultFactory.buildOk());
		}
		catch (UtilsConfigurationException e)
		{
			e.printStackTrace();
			du.setResult(XmlResultFactory.buildFail());
		}
		
		updateRole.remove(fSecurity);
		logger.trace("iuRoles finished");

		return du;
	}
	

	
	private void iuRole(C category, net.sf.ahtutils.xml.security.Role role) throws UtilsConfigurationException
	{
		R ejb;
		try
		{
			ejb = fSecurity.fByCode(fbSecurity.getClassRole(),role.getCode());
			efLang.rmLang(fSecurity,ejb);
			efDescription.rmDescription(fSecurity,ejb);
		}
		catch (UtilsNotFoundException e)
		{
			try
			{
				ejb = fbSecurity.getClassRole().newInstance();
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
			if(role.isSetDocumentation()){ejb.setDocumentation(role.isDocumentation());}else{ejb.setDocumentation(false);}
			if(role.isSetVisible()){ejb.setVisible(role.isVisible());}else{ejb.setVisible(true);}
			if(role.isSetPosition()){ejb.setPosition(role.getPosition());}else{ejb.setPosition(0);}
			
			ejb.setName(efLang.getLangMap(role.getLangs()));
			ejb.setDescription(efDescription.create(role.getDescriptions()));
			ejb.setCategory(category);
			ejb = fSecurity.update(ejb);

			ejb = fSecurity.load(fbSecurity.getClassRole(), ejb);
			ejb = iuListViewsSecurity(ejb, role.getViews());
			ejb = iuListActions(ejb, role.getActions());
			ejb = iuUsecasesForRole(ejb, role.getUsecases());
		}
		catch (UtilsConstraintViolationException e) {logger.error("",e);}
		catch (UtilsNotFoundException e) {throw new UtilsConfigurationException(e.getMessage());}
		catch (UtilsLockingException e) {logger.error("",e);}
	}
	
	private R iuUsecasesForRole(R ejb, Usecases usecases) throws UtilsConstraintViolationException, UtilsNotFoundException, UtilsLockingException
	{
		ejb.getUsecases().clear();
		ejb = fSecurity.update(ejb);
		if(usecases!=null)
		{
			for(Usecase usecase : usecases.getUsecase())
			{
				U ejbUsecase = fSecurity.fByCode(fbSecurity.getClassUsecase(), usecase.getCode());
				ejb.getUsecases().add(ejbUsecase);
			}
			ejb = fSecurity.update(ejb);
		}
		return ejb;
	}
}