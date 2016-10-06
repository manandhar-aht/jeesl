package org.jeesl.util.ejb.acl;

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
import net.sf.ahtutils.model.interfaces.acl.UtilsAclCategoryProjectRole;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclRole;
import net.sf.ahtutils.xml.access.Access;
import net.sf.ahtutils.xml.access.Category;
import net.sf.ahtutils.xml.access.Role;

@Deprecated
public class AclInitRoles <L extends UtilsLang,
								D extends UtilsDescription, 
								C extends UtilsAclCategoryProjectRole<L,D,C,R>,
								R extends UtilsAclRole<L,D,C,R>>
{
	final static Logger logger = LoggerFactory.getLogger(AclInitRoles.class);
	public static enum ExtractId {aclUseCases,aclRoles,aclRoleAutoAssign,aclProjectRoles}
	
    final Class<L> langClass;
    final Class<D> descriptionClass;
    
    final Class<C> categoryClass;
    final Class<R> roleClass;
	
	private AhtAclFacade fAcl;
	private EjbLangFactory<L> ejbLangFactory;
	
	public static <L extends UtilsLang,D extends UtilsDescription,C extends UtilsAclCategoryProjectRole<L,D,C,R>,R extends UtilsAclRole<L,D,C,R>>
		AclInitRoles<L,D,C,R>
		factory(final Class<L> langClass,final Class<D> descriptionClass,final Class<C> categoryClass,final Class<R> roleClass,AhtAclFacade fAcl)
	{
		return new AclInitRoles<L,D,C,R>(langClass, descriptionClass, categoryClass, roleClass,fAcl);
	}
	
	private AclInitRoles(final Class<L> langClass, final Class<D> descriptionClass,final Class<C> categoryClass,final Class<R> roleClass,AhtAclFacade fAcl)
	{       
        this.langClass = langClass;
        this.descriptionClass = descriptionClass;
        this.categoryClass = categoryClass;
        this.roleClass = roleClass;
        
        this.fAcl=fAcl;
		
		ejbLangFactory = EjbLangFactory.createFactory(langClass);
	}
	
	public void iuRoles(Access access) throws UtilsConfigurationException
	{
		logger.debug("i/u "+Category.class.getSimpleName()+" with "+access.getCategory().size()+" categories");
		
		JeeslDbCodeEjbUpdater<C> updateCategory = JeeslDbCodeEjbUpdater.createFactory(categoryClass);
		JeeslDbCodeEjbUpdater<R> updateRole = JeeslDbCodeEjbUpdater.createFactory(roleClass);
		
		updateCategory.dbEjbs(fAcl.all(categoryClass));
		updateRole.dbEjbs(fAcl.all(roleClass));

		for(Category category : access.getCategory())
		{
			updateCategory.actualAdd(category.getCode());
			
			C aclCategory;
			try
			{
				aclCategory = fAcl.fByCode(categoryClass,category.getCode());
				
				Map<String,L> langMap = aclCategory.getName();
				Map<String,D> descMap = aclCategory.getDescription();
				aclCategory.setName(null);
				aclCategory.setDescription(null);
				
				try{aclCategory=(C)fAcl.update(aclCategory);}
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
			}
			catch (UtilsNotFoundException e)
			{
				try
				{
					aclCategory = categoryClass.newInstance();
					aclCategory.setCode(category.getCode());
					aclCategory = (C)fAcl.persist(aclCategory);
				}
				catch (InstantiationException e2) {throw new UtilsConfigurationException(e2.getMessage());}
				catch (IllegalAccessException e2) {throw new UtilsConfigurationException(e2.getMessage());}
				catch (UtilsConstraintViolationException e2) {throw new UtilsConfigurationException(e2.getMessage());}	
			}
			
			try
			{
				aclCategory.setName(ejbLangFactory.getLangMap(category.getLangs()));
//				aclCategory.setDescription(ejbFactory.getDescriptionMap(category.getDescriptions()));
				aclCategory=(C)fAcl.update(aclCategory);
				
				if(category.isSetRoles() && category.getRoles().isSetRole())
				{
					for(Role role : category.getRoles().getRole())
					{
						updateRole.actualAdd(role.getCode());
						initUpdateProjectRole(aclCategory, role);
					}
				}
			}
			catch (UtilsConstraintViolationException e) {logger.error("",e);}
			catch (UtilsLockingException e) {logger.error("",e);}
		}
		
		updateCategory.remove(fAcl);
		updateRole.remove(fAcl);
		logger.trace("initUpdateUsecaseCategories finished");
	}
	
	public void initUpdateProjectRole(C category, Role role) throws UtilsConfigurationException
	{
		R aclRole;
		try
		{
			aclRole = fAcl.fByCode(roleClass,role.getCode());
			
			Map<String,L> langMap = aclRole.getName();
			Map<String,D> descMap = aclRole.getDescription();
			aclRole.setName(null);
			aclRole.setDescription(null);
			
			try{aclRole=(R)fAcl.update(aclRole);}
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
		}
		catch (UtilsNotFoundException e)
		{
			try
			{
				aclRole = roleClass.newInstance();
				aclRole.setCategory(category);
				aclRole.setCode(role.getCode());
				aclRole = (R)fAcl.persist(aclRole);
			}
			catch (InstantiationException e2) {throw new UtilsConfigurationException(e2.getMessage());}
			catch (IllegalAccessException e2) {throw new UtilsConfigurationException(e2.getMessage());}
			catch (UtilsConstraintViolationException e2) {throw new UtilsConfigurationException(e2.getMessage());}	
		}
		
		try
		{
			aclRole.setName(ejbLangFactory.getLangMap(role.getLangs()));
//			aclRole.setDescription(ejbFactory.getDescriptionMap(role.getDescriptions()));
			aclRole.setCategory(category);
			aclRole=(R)fAcl.update(aclRole);
		}
		catch (UtilsConstraintViolationException e) {logger.error("",e);}
		catch (UtilsLockingException e) {logger.error("",e);}
	}
}