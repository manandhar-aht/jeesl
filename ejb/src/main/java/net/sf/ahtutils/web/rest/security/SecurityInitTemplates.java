package net.sf.ahtutils.web.rest.security;

import org.jeesl.api.facade.system.JeeslSecurityFacade;
import org.jeesl.controller.db.updater.JeeslDbCodeEjbUpdater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.exception.processing.UtilsConfigurationException;
import net.sf.ahtutils.factory.xml.sync.XmlDataUpdateFactory;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityAction;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityActionTemplate;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityCategory;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityRole;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityUsecase;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityView;
import net.sf.ahtutils.interfaces.model.system.security.UtilsUser;
import net.sf.ahtutils.interfaces.rest.security.UtilsSecurityTemplateImport;
import net.sf.ahtutils.xml.security.Security;
import net.sf.ahtutils.xml.security.Usecase;
import net.sf.ahtutils.xml.security.Usecases;
import net.sf.ahtutils.xml.sync.DataUpdate;

public class SecurityInitTemplates <L extends UtilsLang,
 								D extends UtilsDescription, 
 								C extends UtilsSecurityCategory<L,D,C,R,V,U,A,AT,USER>,
 								R extends UtilsSecurityRole<L,D,C,R,V,U,A,AT,USER>,
 								V extends UtilsSecurityView<L,D,C,R,V,U,A,AT,USER>,
 								U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
 								A extends UtilsSecurityAction<L,D,C,R,V,U,A,AT,USER>,
 								AT extends UtilsSecurityActionTemplate<L,D,C,R,V,U,A,AT,USER>,
 								USER extends UtilsUser<L,D,C,R,V,U,A,AT,USER>>
		extends AbstractSecurityInit<L,D,C,R,V,U,A,AT,USER>
		implements UtilsSecurityTemplateImport
{
	final static Logger logger = LoggerFactory.getLogger(SecurityInitTemplates.class);
	
	private JeeslDbCodeEjbUpdater<R> updateRole;
	
	public SecurityInitTemplates(final Class<L> cL, final Class<D> cD,final Class<C> cC,final Class<R> cR, final Class<V> cV,final Class<U> cU,final Class<A> cA,final Class<AT> cT,final Class<USER> cUser,JeeslSecurityFacade<L,D,C,R,V,U,A,AT,USER> fAcl)
	{       
        super(cL,cD,cC,cR,cV,cU,cA,cT,cUser,fAcl);
	}
	
	public DataUpdate iuSecurityTemplates(Security security)
	{
		updateRole = JeeslDbCodeEjbUpdater.createFactory(cR);
		updateRole.dbEjbs(fSecurity.all(cR));

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
				updateRole.actualAdd(template.getCode());
				iuTemplate(category, template);
			}
		}
	}
	
	private void iuTemplate(C category, net.sf.ahtutils.xml.security.Template role) throws UtilsConfigurationException
	{
		AT ejb;
		try
		{
			ejb = fSecurity.fByCode(cT,role.getCode());
			ejbLangFactory.rmLang(fSecurity,ejb);
			ejbDescriptionFactory.rmDescription(fSecurity,ejb);
		}
		catch (UtilsNotFoundException e)
		{
			try
			{
				ejb = cT.newInstance();
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
		}
		catch (UtilsConstraintViolationException e) {logger.error("",e);}
		catch (UtilsLockingException e) {logger.error("",e);}
	}
}