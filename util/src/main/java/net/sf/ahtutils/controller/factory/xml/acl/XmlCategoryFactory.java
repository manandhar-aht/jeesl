package net.sf.ahtutils.controller.factory.xml.acl;

import org.jeesl.factory.xml.system.lang.XmlDescriptionsFactory;
import org.jeesl.factory.xml.system.lang.XmlLangsFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclCategoryGroup;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclCategoryProjectRole;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclCategoryUsecase;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclGroup;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclRole;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclView;
import net.sf.ahtutils.xml.access.Category;

public class XmlCategoryFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlCategoryFactory.class);
		
	private Category qC;
	private String lang;
	
	public XmlCategoryFactory(Category qC, String lang)
	{
		this.qC=qC;
		this.lang=lang;
	}
	
	public <L extends UtilsLang,D extends UtilsDescription,CU extends UtilsAclCategoryUsecase<L,D,CU,U>,CR extends UtilsAclCategoryGroup<L,D,CU,CR,U,R>,U extends UtilsAclView<L,D,CU,U>,R extends UtilsAclGroup<L,D,CU,CR,U,R>>
	Category getRoleCategory(CR aclRoleCategory)
	{
		Category rc = new Category();
		
		if(qC.isSetCode()){rc.setCode(aclRoleCategory.getCode());}
		
		if(qC.isSetLangs())
		{
			XmlLangsFactory<L> f = new XmlLangsFactory<L>(qC.getLangs());
			rc.setLangs(f.getUtilsLangs(aclRoleCategory.getName()));
		}
		
		if(qC.isSetDescriptions())
		{
			XmlDescriptionsFactory<D> f = new XmlDescriptionsFactory<D>(qC.getDescriptions());
			rc.setDescriptions(f.create(aclRoleCategory.getDescription()));
		}
		
		if(qC.isSetGroups())
		{
			XmlGroupsFactory f = new XmlGroupsFactory(qC.getGroups(), lang);
			rc.setGroups(f.getRoles(aclRoleCategory.getRoles()));
		}
		
		return rc;
	}
	
	public <L extends UtilsLang,D extends UtilsDescription,C extends UtilsAclCategoryProjectRole<L,D,C,R>,R extends UtilsAclRole<L,D,C,R>>
	Category getProjectRoleCategory(C aclPrc)
	{
		Category prc = new Category();
		
		if(qC.isSetCode()){prc.setCode(aclPrc.getCode());}
		
		if(qC.isSetLangs())
		{
			XmlLangsFactory<L> f = new XmlLangsFactory<L>(qC.getLangs());
			prc.setLangs(f.getUtilsLangs(aclPrc.getName()));
		}
		
		if(qC.isSetDescriptions())
		{
			XmlDescriptionsFactory<D> f = new XmlDescriptionsFactory<D>(qC.getDescriptions());
			prc.setDescriptions(f.create(aclPrc.getDescription()));
		}
		
		if(qC.isSetRoles())
		{
			logger.warn("Roles deactivated, because AclXX is deprecated");
		}
		
		return prc;
	}
	
	public <L extends UtilsLang,D extends UtilsDescription,CU extends UtilsAclCategoryUsecase<L,D,CU,U>,U extends UtilsAclView<L,D,CU,U>>
		Category getUsecaseCategory(CU category)
	{
		Category xml = new Category();
		
		if(qC.isSetCode()){xml.setCode(category.getCode());}
		
		if(qC.isSetLangs())
		{
			XmlLangsFactory<L> f = new XmlLangsFactory<L>(qC.getLangs());
			xml.setLangs(f.getUtilsLangs(category.getName()));
		}
		
		if(qC.isSetDescriptions())
		{
			XmlDescriptionsFactory<D> f = new XmlDescriptionsFactory<D>(qC.getDescriptions());
			xml.setDescriptions(f.create(category.getDescription()));
		}
		
		if(qC.isSetViews())
		{
			XmlViewsFactory f = new XmlViewsFactory(qC.getViews(), lang);
			xml.setViews(f.getUsecases(category.getUsecases()));
		}
		
		return xml;
	}
}