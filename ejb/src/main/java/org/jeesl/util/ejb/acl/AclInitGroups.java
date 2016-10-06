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
import net.sf.ahtutils.model.interfaces.acl.UtilsAclCategoryGroup;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclCategoryUsecase;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclGroup;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclView;
import net.sf.ahtutils.xml.access.Access;
import net.sf.ahtutils.xml.access.Category;
import net.sf.ahtutils.xml.access.Group;
import net.sf.ahtutils.xml.access.Groups;
import net.sf.ahtutils.xml.access.View;

@Deprecated
public class AclInitGroups <S extends UtilsStatus<S,L,D>,
							L extends UtilsLang,
							D extends UtilsDescription,
							CU extends UtilsAclCategoryUsecase<L,D,CU,U>,
							U extends UtilsAclView<L,D,CU,U>,
							CR extends UtilsAclCategoryGroup<L,D,CU,CR,U,R>,
							R extends UtilsAclGroup<L,D,CU,CR,U,R>>
{
	final static Logger logger = LoggerFactory.getLogger(AclInitGroups.class);
	public static enum ExtractId {aclUseCases,aclRoles,aclRoleAutoAssign,aclProjectRoles}
	
	final Class<S> statusClass;
    final Class<L> langClass;
    final Class<D> descriptionClass;
    
    final Class<CU> categoryUsecaseClass;
    final Class<U> usecaseClass;
    
    final Class<CR> categoryRoleClass;
    final Class<R> roleClass;
	
	private AhtAclFacade fAcl;
	private EjbLangFactory<L> ejbLangFactory;
	
	public static <S extends UtilsStatus<S,L,D>,L extends UtilsLang,D extends UtilsDescription,
				   CU extends UtilsAclCategoryUsecase<L,D,CU,U>, U extends UtilsAclView<L,D,CU,U>,
				   CR extends UtilsAclCategoryGroup<L,D,CU,CR,U,R>,R extends UtilsAclGroup<L,D,CU,CR,U,R>>
		AclInitGroups<S, L, D,CU,U,CR,R>
		factory(final Class<S> statusClass,final Class<L> langClass,final Class<D> descriptionClass,
				final Class<CU> categoryUsecaseClass,final Class<U> usecaseClass,
				final Class<CR> categoryRoleClass,final Class<R> roleClass,
				AhtAclFacade fAcl)
	{
		return new AclInitGroups<S, L, D, CU, U,CR,R>(statusClass,langClass,descriptionClass,categoryUsecaseClass,usecaseClass,categoryRoleClass,roleClass,fAcl);
	}
	
	private AclInitGroups(final Class<S> statusClass, final Class<L> langClass, final Class<D> descriptionClass,
			final Class<CU> categoryUsecaseClass, final Class<U> usecaseClass,
			final Class<CR> categoryRoleClass,final Class<R> roleClass,
			AhtAclFacade fAcl)
	{       
		this.statusClass = statusClass;
        this.langClass = langClass;
        this.descriptionClass = descriptionClass;
        this.categoryUsecaseClass = categoryUsecaseClass;
        this.usecaseClass = usecaseClass;
        this.categoryRoleClass=categoryRoleClass;
        this.roleClass=roleClass;
        
        this.fAcl=fAcl;
		
		ejbLangFactory = EjbLangFactory.createFactory(langClass);
	}
	
	public void iuGroups(Access access) throws FileNotFoundException, UtilsConfigurationException
	{
		JeeslDbCodeEjbUpdater<CR> updateRoleCategory = JeeslDbCodeEjbUpdater.createFactory(categoryRoleClass);
		JeeslDbCodeEjbUpdater<R> updateRole = JeeslDbCodeEjbUpdater.createFactory(roleClass);
		
		updateRoleCategory.dbEjbs(fAcl.all(categoryRoleClass));
		updateRole.dbEjbs(fAcl.all(roleClass));
		
		logger.debug("i/u "+categoryRoleClass+" with "+access.getCategory()+" categories");
		for(Category category : access.getCategory())
		{
			updateRoleCategory.actualAdd(category.getCode());
			CR aclRoleCategory;
			try
			{
				aclRoleCategory = fAcl.fByCode(categoryRoleClass,category.getCode());
				
				Map<String,L> langMap = aclRoleCategory.getName();
				Map<String,D> descMap = aclRoleCategory.getDescription();
				aclRoleCategory.setName(null);
				aclRoleCategory.setDescription(null);
				
				try{aclRoleCategory=(CR)fAcl.update(aclRoleCategory);}
				catch (UtilsConstraintViolationException e) {logger.error("",e);}
				catch (UtilsLockingException e) {logger.error("",e);}
				
				for(L lang : langMap.values())
				{
					try {fAcl.rm(lang);}
					catch (UtilsConstraintViolationException e)  {logger.error("",e);}
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
					aclRoleCategory = categoryRoleClass.newInstance();
					aclRoleCategory.setCode(category.getCode());
					aclRoleCategory = (CR)fAcl.persist(aclRoleCategory);
					
				}
				catch (InstantiationException e2) {throw new UtilsConfigurationException(e2.getMessage());}
				catch (IllegalAccessException e2) {throw new UtilsConfigurationException(e2.getMessage());}
				catch (UtilsConstraintViolationException e2) {throw new UtilsConfigurationException(e2.getMessage());}	
			}
			
			try
			{
				aclRoleCategory.setName(ejbLangFactory.getLangMap(category.getLangs()));
//				aclRoleCategory.setDescription(ejbFactory.getDescriptionMap(category.getDescriptions()));
				aclRoleCategory=(CR)fAcl.update(aclRoleCategory);
				if(category.isSetGroups() && category.getGroups().isSetGroup())
				{
					initUpdateRole(aclRoleCategory, category.getGroups(), false);
				}
			}
			catch (UtilsConstraintViolationException e) {logger.error("",e);}
			catch (UtilsLockingException e) {logger.error("",e);}
		}
		updateRoleCategory.remove(fAcl);
	}
	

	private void initUpdateRole(CR aclRoleCategory, Groups group, boolean refRoles) throws UtilsConfigurationException
	{
		logger.warn("No removing of Roles");
		for(Group role : group.getGroup())
		{
			initUpdateRole(aclRoleCategory, role, refRoles);
		}
	}
	
	private void initUpdateRole(CR roleCategory, Group role, boolean refRoles) throws UtilsConfigurationException
	{
		logger.trace("initUpdateRole "+roleCategory.getCode()+"-"+role.getCode()+" (refRolse="+refRoles+")");
		R aclRole;
		try
		{
			aclRole = fAcl.fByCode(roleClass,role.getCode());
			
			if(!refRoles)
			{
				Map<String,L> langMap = aclRole.getName();
				Map<String,D> descMap = aclRole.getDescription();
				aclRole.setName(null);
				aclRole.setDescription(null);
				aclRole.setUsecases(null);
				
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
		}
		catch (UtilsNotFoundException e)
		{
			if(refRoles){logger.error("Ref.Role "+role.getCode()+" not found! Exit");System.exit(-1);}
			try
			{
				aclRole = roleClass.newInstance();
				aclRole.setCode(role.getCode());
				aclRole = (R)fAcl.persist(aclRole);
			}
			catch (InstantiationException e2) {throw new UtilsConfigurationException(e2.getMessage());}
			catch (IllegalAccessException e2) {throw new UtilsConfigurationException(e2.getMessage());}
			catch (UtilsConstraintViolationException e2) {throw new UtilsConfigurationException(e2.getMessage());}	
			
		}
		
		if(!refRoles)
		{
			try
			{
				aclRole.setName(ejbLangFactory.getLangMap(role.getLangs()));
//				aclRole.setDescription(ejbFactory.getDescriptionMap(role.getDescriptions()));
				aclRole.setCategory(roleCategory);
				
				if(role.isSetViews() && role.getViews().isSetView())
				{
					for(View usecase : role.getViews().getView())
					{
						try {aclRole.getUsecases().add(fAcl.fByCode(usecaseClass,usecase.getCode()));}
						catch (UtilsNotFoundException e) {logger.error("",e);}
					}
				}
				aclRole=(R)fAcl.update(aclRole);
			}
			catch (UtilsConstraintViolationException e) {logger.error("",e);}
			catch (UtilsLockingException e) {logger.error("",e);}
		}
		else
		{
			logger.debug("Ref.Roles for "+aclRole.getCode());
			aclRole.setRoles(null);
			try{aclRole=(R)fAcl.update(aclRole);}
			catch (UtilsConstraintViolationException e) {logger.error("",e);}
			catch (UtilsLockingException e) {logger.error("",e);}
			
			if(role.isSetGroups() && role.getGroups().isSetGroup())
			{
				for(Group subRole : role.getGroups().getGroup())
				{
					try
					{
						R aclSubRole = fAcl.fByCode(roleClass,subRole.getCode());
						aclRole.getRoles().add(aclSubRole);
					}
					catch (UtilsNotFoundException e) {{logger.error("Sub.Role "+subRole.getCode()+" not found! Exit.");System.exit(-1);}}
				}
				try{aclRole=(R)fAcl.update(aclRole);}
				catch (UtilsConstraintViolationException e) {logger.error("",e);}
				catch (UtilsLockingException e) {logger.error("",e);}
			}
		}
	}
}