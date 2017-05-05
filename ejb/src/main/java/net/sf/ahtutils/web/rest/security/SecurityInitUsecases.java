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
import net.sf.ahtutils.factory.xml.sync.XmlResultFactory;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityAction;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityActionTemplate;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityCategory;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityRole;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityUsecase;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityView;
import net.sf.ahtutils.interfaces.model.system.security.UtilsUser;
import net.sf.ahtutils.interfaces.rest.security.UtilsSecurityUsecaseImport;
import net.sf.ahtutils.xml.security.Security;
import net.sf.ahtutils.xml.sync.DataUpdate;

public class SecurityInitUsecases <L extends UtilsLang,
 								D extends UtilsDescription, 
 								C extends UtilsSecurityCategory<L,D,C,R,V,U,A,AT,USER>,
 								R extends UtilsSecurityRole<L,D,C,R,V,U,A,AT,USER>,
 								V extends UtilsSecurityView<L,D,C,R,V,U,A,AT,USER>,
 								U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
 								A extends UtilsSecurityAction<L,D,C,R,V,U,A,AT,USER>,
 								AT extends UtilsSecurityActionTemplate<L,D,C,R,V,U,A,AT,USER>,
 								USER extends UtilsUser<L,D,C,R,V,U,A,AT,USER>>
		extends AbstractSecurityInit<L,D,C,R,V,U,A,AT,USER>
		implements UtilsSecurityUsecaseImport
{
	final static Logger logger = LoggerFactory.getLogger(SecurityInitUsecases.class);
	
	private JeeslDbCodeEjbUpdater<U> updateUsecases;
	
	public SecurityInitUsecases(final Class<L> cL, final Class<D> cD,final Class<C> cC,final Class<R> cR, final Class<V> cV,final Class<U> cU,final Class<A> cA,final Class<AT> cT,final Class<USER> cUser,JeeslSecurityFacade<L,D,C,R,V,U,A,AT,USER> fAcl)
	{       
        super(cL,cD,cC,cR,cV,cU,cA,cT,cUser,fAcl);
	}
	
	@Override public DataUpdate iuSecurityUsecases(Security usecases)
	{
		logger.trace("iuSecurityUsecases starting ..."+fSecurity.allForType(cC,UtilsSecurityCategory.Type.usecase.toString()).size());
		updateUsecases = JeeslDbCodeEjbUpdater.createFactory(cU);
		updateUsecases.dbEjbs(fSecurity.all(cU));

		DataUpdate du = XmlDataUpdateFactory.build();
		try
		{
			iuCategory(usecases, UtilsSecurityCategory.Type.usecase);
			du.setResult(XmlResultFactory.buildOk());
		}
		catch (UtilsConfigurationException e)
		{
			e.printStackTrace();
			du.setResult(XmlResultFactory.buildFail());
		}
		
		logger.trace("Before: UC "+fSecurity.all(cU).size());
		updateUsecases.remove(fSecurity);
		logger.trace("After: UC "+fSecurity.all(cU).size());
		logger.trace("iuSecurityUsecases finished "+fSecurity.allForType(cC,UtilsSecurityCategory.Type.usecase.toString()).size());

		return du;
	}
	
	@Override protected void iuChilds(C aclCategory, net.sf.ahtutils.xml.security.Category category) throws UtilsConfigurationException
	{
		logger.trace("iuChilds "+category.getCode());
		if(category.isSetUsecases() && category.getUsecases().isSetUsecase())
		{
			logger.trace("iuChilds "+category.getCode()+ " "+category.getUsecases().getUsecase().size());
			for(net.sf.ahtutils.xml.security.Usecase usecase : category.getUsecases().getUsecase())
			{
				updateUsecases.actualAdd(usecase.getCode());
				iuUsecase(aclCategory, usecase);
			}
		}
	}
	
	private void iuUsecase(C category, net.sf.ahtutils.xml.security.Usecase usecase) throws UtilsConfigurationException
	{
		U ebj;
		try
		{
			ebj = fSecurity.fByCode(cU,usecase.getCode());
			ejbLangFactory.rmLang(fSecurity,ebj);
			ejbDescriptionFactory.rmDescription(fSecurity,ebj);
		}
		catch (UtilsNotFoundException e)
		{
			try
			{
				ebj = cU.newInstance();
				ebj.setCategory(category);
				ebj.setCode(usecase.getCode());
				ebj = fSecurity.persist(ebj);
			}
			catch (InstantiationException e2) {throw new UtilsConfigurationException(e2.getMessage());}
			catch (IllegalAccessException e2) {throw new UtilsConfigurationException(e2.getMessage());}
			catch (UtilsConstraintViolationException e2) {throw new UtilsConfigurationException(e2.getMessage());}	
		}
		
		try
		{
			if(usecase.isSetVisible()){ebj.setVisible(usecase.isVisible());}else{ebj.setVisible(true);}
			if(usecase.isSetPosition()){ebj.setPosition(usecase.getPosition());}else{ebj.setPosition(0);}
			
			ebj.setName(ejbLangFactory.getLangMap(usecase.getLangs()));
			ebj.setDescription(ejbDescriptionFactory.create(usecase.getDescriptions()));
			ebj.setCategory(category);
			ebj=fSecurity.update(ebj);
			
			ebj = iuListViewsSecurity(ebj,usecase.getViews());
			ebj = iuListActions(ebj, usecase.getActions());
		}
		catch (UtilsConstraintViolationException e) {logger.error("",e);}
		catch (UtilsNotFoundException e) {throw new UtilsConfigurationException(e.getMessage());}
		catch (UtilsLockingException e) {logger.error("",e);}
	}
}