package org.jeesl.web.rest.system.security.updater;

import org.jeesl.api.facade.system.JeeslSecurityFacade;
import org.jeesl.api.rest.system.security.JeeslSecurityRestUsecaseImport;
import org.jeesl.controller.db.updater.JeeslDbCodeEjbUpdater;
import org.jeesl.factory.builder.system.SecurityFactoryBuilder;
import org.jeesl.factory.xml.system.io.sync.XmlDataUpdateFactory;
import org.jeesl.factory.xml.system.io.sync.XmlResultFactory;
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

public class SecurityUsecaseUpdater <L extends UtilsLang,
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
		implements JeeslSecurityRestUsecaseImport
{
	final static Logger logger = LoggerFactory.getLogger(SecurityUsecaseUpdater.class);
	
	private JeeslDbCodeEjbUpdater<U> updateUsecases;
	
	public SecurityUsecaseUpdater(SecurityFactoryBuilder<L,D,C,R,V,U,A,AT,M,USER> fbSecurity, JeeslSecurityFacade<L,D,C,R,V,U,A,AT,USER> fSecurity)
	{       
        super(fbSecurity,fSecurity);
	}
	
	@Override public DataUpdate iuSecurityUsecases(Security usecases)
	{
		logger.trace("iuSecurityUsecases starting ..."+fSecurity.allForType(fbSecurity.getClassCategory(),JeeslSecurityCategory.Type.usecase.toString()).size());
		updateUsecases = JeeslDbCodeEjbUpdater.createFactory(fbSecurity.getClassUsecase());
		updateUsecases.dbEjbs(fSecurity.all(fbSecurity.getClassUsecase()));

		DataUpdate du = XmlDataUpdateFactory.build();
		try
		{
			iuCategory(usecases, JeeslSecurityCategory.Type.usecase);
			du.setResult(XmlResultFactory.buildOk());
		}
		catch (UtilsConfigurationException e)
		{
			e.printStackTrace();
			du.setResult(XmlResultFactory.buildFail());
		}
		
		logger.trace("Before: UC "+fSecurity.all(fbSecurity.getClassUsecase()).size());
		updateUsecases.remove(fSecurity);
		logger.trace("After: UC "+fSecurity.all(fbSecurity.getClassUsecase()).size());
		logger.trace("iuSecurityUsecases finished "+fSecurity.allForType(fbSecurity.getClassCategory(),JeeslSecurityCategory.Type.usecase.toString()).size());

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
				updateUsecases.handled(usecase.getCode());
				iuUsecase(aclCategory, usecase);
			}
		}
	}
	
	private void iuUsecase(C category, net.sf.ahtutils.xml.security.Usecase usecase) throws UtilsConfigurationException
	{
		U ebj;
		try
		{
			ebj = fSecurity.fByCode(fbSecurity.getClassUsecase(),usecase.getCode());
			efLang.rmLang(fSecurity,ebj);
			efDescription.rmDescription(fSecurity,ebj);
		}
		catch (UtilsNotFoundException e)
		{
			try
			{
				ebj = fbSecurity.getClassUsecase().newInstance();
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
			
			ebj.setName(efLang.getLangMap(usecase.getLangs()));
			ebj.setDescription(efDescription.create(usecase.getDescriptions()));
			ebj.setCategory(category);
			ebj=fSecurity.update(ebj);
			
			ebj = fSecurity.load(fbSecurity.getClassUsecase(), ebj);
			ebj = iuListViewsSecurity(ebj,usecase.getViews());
			ebj = iuListActions(ebj, usecase.getActions());
		}
		catch (UtilsConstraintViolationException e) {logger.error("",e);}
		catch (UtilsNotFoundException e) {throw new UtilsConfigurationException(e.getMessage());}
		catch (UtilsLockingException e) {logger.error("",e);}
	}
}