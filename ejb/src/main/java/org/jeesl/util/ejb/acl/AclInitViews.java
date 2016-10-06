package org.jeesl.util.ejb.acl;

import java.io.FileNotFoundException;
import java.util.Map;

import org.jeesl.util.ejb.JeeslDbCodeEjbUpdater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.controller.interfaces.AhtAclFacade;
import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.exception.processing.UtilsConfigurationException;
import net.sf.ahtutils.factory.ejb.status.EjbLangFactory;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclCategoryUsecase;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclView;
import net.sf.ahtutils.xml.access.Access;
import net.sf.ahtutils.xml.access.Category;
import net.sf.ahtutils.xml.access.View;

@Deprecated
public class AclInitViews <S extends UtilsStatus<S,L,D>,
							L extends UtilsLang,
							D extends UtilsDescription,
							CU extends UtilsAclCategoryUsecase<L,D,CU,U>,
							U extends UtilsAclView<L,D,CU,U>>
{
	final static Logger logger = LoggerFactory.getLogger(AclInitViews.class);
	public static enum ExtractId {aclUseCases,aclRoles,aclRoleAutoAssign,aclProjectRoles}
	
	final Class<S> statusClass;
    final Class<L> langClass;
    final Class<D> descriptionClass;
    
    final Class<CU> categoryUsecaseClass;
    final Class<U> usecaseClass;
		
	private AhtAclFacade fAcl;
	private EjbLangFactory<L> ejbLangFactory;
	
	public static <S extends UtilsStatus<S,L,D>,
				   L extends UtilsLang,
				   D extends UtilsDescription,
				   CU extends UtilsAclCategoryUsecase<L,D,CU,U>,
				   U extends UtilsAclView<L,D,CU,U>>
		AclInitViews<S, L, D,CU,U>
		factory(final Class<S> statusClass,
				final Class<L> langClass,
				final Class<D> descriptionClass,
				final Class<CU> categoryUsecaseClass,
				final Class<U> usecaseClass,
				AhtAclFacade fAcl)
	{
		return new AclInitViews<S, L, D, CU, U>(statusClass, langClass, descriptionClass, categoryUsecaseClass, usecaseClass,fAcl);
	}
	
	private AclInitViews(final Class<S> statusClass, final Class<L> langClass, final Class<D> descriptionClass,
			final Class<CU> categoryUsecaseClass, final Class<U> usecaseClass,
			AhtAclFacade fAcl)
	{       
		this.statusClass = statusClass;
        this.langClass = langClass;
        this.descriptionClass = descriptionClass;
        this.categoryUsecaseClass = categoryUsecaseClass;
        this.usecaseClass = usecaseClass;
        
        this.fAcl=fAcl;
		
		ejbLangFactory = EjbLangFactory.createFactory(langClass);
	}
	
	public void iuUsecases(Access access) throws FileNotFoundException, UtilsConfigurationException
	{
		logger.debug("i/u "+Category.class.getSimpleName()+" with "+access.getCategory().size()+" categories");
		
		JeeslDbCodeEjbUpdater<CU> ejbUccUpdate = JeeslDbCodeEjbUpdater.createFactory(categoryUsecaseClass);
		JeeslDbCodeEjbUpdater<U> ejbUcUpdate = JeeslDbCodeEjbUpdater.createFactory(usecaseClass);
		
		ejbUccUpdate.dbEjbs(fAcl.all(categoryUsecaseClass));
		ejbUcUpdate.dbEjbs(fAcl.all(usecaseClass));

		for(Category category : access.getCategory())
		{
			logger.debug("Processing category: "+category.getCode());
			ejbUccUpdate.actualAdd(category.getCode());
			
			CU usecaseCategory;
			try
			{
				usecaseCategory = fAcl.fByCode(categoryUsecaseClass, category.getCode());
				
				logger.trace("removing existing langs: "+category.getCode());
				Map<String,L> langMap = usecaseCategory.getName();
				Map<String,D> descMap = usecaseCategory.getDescription();
				usecaseCategory.setName(null);
				usecaseCategory.setDescription(null);
				
				try{usecaseCategory=(CU)fAcl.update(usecaseCategory);}
				catch (UtilsConstraintViolationException e) {logger.error("",e);}
				catch (UtilsLockingException e) {logger.error("",e);}
				
				for(L lang : langMap.values())
				{
					try {fAcl.rm(lang);}
					catch (UtilsConstraintViolationException e) {logger.error("",e);}
				}
				
				for(D desc : descMap.values())
				{
					try {fAcl.rm(desc);}
					catch (UtilsConstraintViolationException e) {logger.error("",e);}
				}
				logger.trace("removed existing langs: "+category.getCode());
			}
			catch (UtilsNotFoundException e)
			{
				try
				{
					usecaseCategory = categoryUsecaseClass.newInstance();
					usecaseCategory.setCode(category.getCode());
					usecaseCategory = fAcl.persist(usecaseCategory);
				}
				catch (InstantiationException e2) {throw new UtilsConfigurationException(e2.getMessage());}
				catch (IllegalAccessException e2) {throw new UtilsConfigurationException(e2.getMessage());}
				catch (UtilsConstraintViolationException e2) {throw new UtilsConfigurationException(e2.getMessage());}	
			}
			
			try
			{
				usecaseCategory.setName(ejbLangFactory.getLangMap(category.getLangs()));
//				usecaseCategory.setDescription(ejbFactory.getDescriptionMap(category.getDescriptions()));
				usecaseCategory=fAcl.update(usecaseCategory);
				
				if(category.isSetViews() && category.getViews().isSetView())
				{
					for(View usecase : category.getViews().getView())
					{
						ejbUcUpdate.actualAdd(usecase.getCode());
						initUpdateUsecase(usecaseCategory, usecase);
					}
				}
			}
			catch (UtilsConstraintViolationException e) {logger.error("",e);}
			catch (UtilsLockingException e) {logger.error("",e);}
		}
		
		ejbUcUpdate.remove(fAcl);
		ejbUccUpdate.remove(fAcl);
		
		logger.trace("initUpdateUsecaseCategories finished");
	}
	
	public void initUpdateUsecase(CU usecaseCategory, View usecase) throws UtilsConfigurationException
	{
		U aclUsecase;
		try
		{
			aclUsecase = fAcl.fByCode(usecaseClass, usecase.getCode());
			
			Map<String,L> langMap = aclUsecase.getName();
			Map<String,D> descMap = aclUsecase.getDescription();
			aclUsecase.setName(null);
			aclUsecase.setDescription(null);
			
			try{aclUsecase=fAcl.update(aclUsecase);}
			catch (UtilsLockingException e) {logger.error("",e);}
			catch (UtilsConstraintViolationException e) {logger.error("",e);}
			
			for(L lang : langMap.values())
			{
				try {fAcl.rm(lang);}
				catch (UtilsConstraintViolationException e) {logger.error("",e);}
			}
			
			for(D desc : descMap.values())
			{
				try {fAcl.rm(desc);}
				catch (UtilsConstraintViolationException e) {logger.error("",e);}
			}
		}
		catch (UtilsNotFoundException e)
		{
			try
			{
				aclUsecase = usecaseClass.newInstance();
				aclUsecase.setCode(usecase.getCode());
				aclUsecase = fAcl.persist(aclUsecase);
			}
			catch (InstantiationException e2) {throw new UtilsConfigurationException(e2.getMessage());}
			catch (IllegalAccessException e2) {throw new UtilsConfigurationException(e2.getMessage());}
			catch (UtilsConstraintViolationException e2) {throw new UtilsConfigurationException(e2.getMessage());}	
		}
		
		try
		{
			aclUsecase.setName(ejbLangFactory.getLangMap(usecase.getLangs()));
//			aclUsecase.setDescription(ejbFactory.getDescriptionMap(usecase.getDescriptions()));
			aclUsecase.setCategory(usecaseCategory);
			aclUsecase=fAcl.update(aclUsecase);
		}
		catch (UtilsConstraintViolationException e) {logger.error("",e);}
		catch (UtilsLockingException e) {logger.error("",e);}
	}
}