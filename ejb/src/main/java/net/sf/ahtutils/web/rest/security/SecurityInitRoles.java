package net.sf.ahtutils.web.rest.security;

import net.sf.ahtutils.db.ejb.AhtDbEjbUpdater;
import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.exception.processing.UtilsConfigurationException;
import net.sf.ahtutils.factory.xml.sync.XmlDataUpdateFactory;
import net.sf.ahtutils.factory.xml.sync.XmlResultFactory;
import net.sf.ahtutils.interfaces.facade.UtilsSecurityFacade;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityAction;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityCategory;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityRole;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityUsecase;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityView;
import net.sf.ahtutils.interfaces.model.security.UtilsUser;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.rest.security.UtilsSecurityRoleImport;
import net.sf.ahtutils.xml.security.Security;
import net.sf.ahtutils.xml.security.Usecase;
import net.sf.ahtutils.xml.security.Usecases;
import net.sf.ahtutils.xml.sync.DataUpdate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SecurityInitRoles <L extends UtilsLang,
 								D extends UtilsDescription, 
 								C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,
 								R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,
 								V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,
 								U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,
 								A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,
 								USER extends UtilsUser<L,D,C,R,V,U,A,USER>>
		extends AbstractSecurityInit<L,D,C,R,V,U,A,USER>
		implements UtilsSecurityRoleImport
{
	final static Logger logger = LoggerFactory.getLogger(SecurityInitRoles.class);
	
	private AhtDbEjbUpdater<R> updateRole;
	
	public SecurityInitRoles(final Class<L> cL, final Class<D> cD,final Class<C> cC,final Class<R> cR, final Class<V> cV,final Class<U> cU,final Class<A> cA,final Class<USER> cUser,UtilsSecurityFacade<L,D,C,R,V,U,A,USER> fAcl)
	{       
        super(cL,cD,cC,cR,cV,cU,cA,cUser,fAcl);
	}
	
	public DataUpdate iuSecurityRoles(Security security)
	{
		updateRole = AhtDbEjbUpdater.createFactory(cR);
		updateRole.dbEjbs(fSecurity.all(cR));

		DataUpdate du = XmlDataUpdateFactory.build();
		try
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

		return du;
	}
	
	@Override protected void iuChilds(C aclCategory, net.sf.ahtutils.xml.security.Category category) throws UtilsConfigurationException
	{
		if(category.isSetRoles() && category.getRoles().isSetRole())
		{
			for(net.sf.ahtutils.xml.security.Role role : category.getRoles().getRole())
			{
				updateRole.actualAdd(role.getCode());
				iuRole(aclCategory, role);
			}
		}
	}
	
	private void iuRole(C category, net.sf.ahtutils.xml.security.Role role) throws UtilsConfigurationException
	{
		R ejb;
		try
		{
			ejb = fSecurity.fByCode(cR,role.getCode());
			ejbLangFactory.rmLang(fSecurity,ejb);
			ejbDescriptionFactory.rmDescription(fSecurity,ejb);
		}
		catch (UtilsNotFoundException e)
		{
			try
			{
				ejb = cR.newInstance();
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
			
			ejb.setName(ejbLangFactory.getLangMap(role.getLangs()));
			ejb.setDescription(ejbDescriptionFactory.create(role.getDescriptions()));
			ejb.setCategory(category);
			ejb=fSecurity.update(ejb);

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
				U ejbUsecase = fSecurity.fByCode(cU, usecase.getCode());
				ejb.getUsecases().add(ejbUsecase);
			}
			ejb = fSecurity.update(ejb);
		}
		return ejb;
	}
}