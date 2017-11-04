package net.sf.ahtutils.web.rest.security;

import org.jeesl.api.facade.system.JeeslSecurityFacade;
import org.jeesl.controller.db.updater.JeeslDbCodeEjbUpdater;
import org.jeesl.controller.monitor.DataUpdateTracker;
import org.jeesl.factory.xml.system.io.sync.XmlDataUpdateFactory;
import org.jeesl.factory.xml.system.io.sync.XmlResultFactory;
import org.jeesl.factory.xml.system.status.XmlTypeFactory;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityView;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityTemplate;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityAction;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityUsecase;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityCategory;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.exception.processing.UtilsConfigurationException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.xml.access.Access;
import net.sf.ahtutils.xml.access.Action;
import net.sf.ahtutils.xml.access.Category;
import net.sf.ahtutils.xml.access.View;
import net.sf.ahtutils.xml.sync.DataUpdate;

public class SecurityInitViews <L extends UtilsLang,
 								D extends UtilsDescription, 
 								C extends JeeslSecurityCategory<L,D>,
 								R extends JeeslSecurityRole<L,D,C,R,V,U,A,AT,USER>,
 								V extends JeeslSecurityView<L,D,C,R,U,A>,
 								U extends JeeslSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
 								A extends JeeslSecurityAction<L,D,C,R,V,U,A,AT,USER>,
 								AT extends JeeslSecurityTemplate<L,D,C,R,V,U,A,AT,USER>,
 								USER extends JeeslUser<L,D,C,R,V,U,A,AT,USER>>
		extends AbstractSecurityInit<L,D,C,R,V,U,A,AT,USER>
{
	final static Logger logger = LoggerFactory.getLogger(SecurityInitViews.class);
	
	private JeeslDbCodeEjbUpdater<V> updateView;
	private JeeslDbCodeEjbUpdater<A> updateAction;
	
	public SecurityInitViews(final Class<L> cL, final Class<D> cD,final Class<C> cC,final Class<R> cR, final Class<V> cV,final Class<U> cU,final Class<A> cA,final Class<AT> cT,final Class<USER> cUser,JeeslSecurityFacade<L,D,C,R,V,U,A,AT,USER> fAcl)
	{       
        super(cL,cD,cC,cR,cV,cU,cA,cT,cUser,fAcl);
	}
	
	@Deprecated public DataUpdate iuViews(Access access)
	{
		logger.trace("iuViews starting ...");
		updateView = JeeslDbCodeEjbUpdater.createFactory(cV);
		updateAction = JeeslDbCodeEjbUpdater.createFactory(cA);
		
		updateView.dbEjbs(fSecurity.all(cV));
		updateAction.dbEjbs(fSecurity.all(cA));

		DataUpdate du = XmlDataUpdateFactory.build();
		try
		{
			iuCategory(access, JeeslSecurityCategory.Type.view);
			du.setResult(XmlResultFactory.buildOk());
		}
		catch (UtilsConfigurationException e)
		{
			du.setResult(XmlResultFactory.buildFail());
			e.printStackTrace();
		}
		
		updateView.remove(fSecurity);
		updateAction.remove(fSecurity);
		logger.trace("iuViews finished");
		
		return du;
	}
	
	@Deprecated @Override protected void iuChilds(C aclCategory, Category category) throws UtilsConfigurationException
	{
		logger.info("iuChilds (access.views) "+category.getViews().getView().size());
		if(category.isSetViews() && category.getViews().isSetView())
		{
			for(View view : category.getViews().getView())
			{
				logger.trace("View: "+view.getCode());
				updateView.actualAdd(view.getCode());
				iuView(aclCategory, view);
			}
		}
	}
	
	@Deprecated private void iuView(C category, View view) throws UtilsConfigurationException
	{
		DataUpdateTracker dut = new DataUpdateTracker(true);
		dut.setType(XmlTypeFactory.build(cV.getName(),"DB Import/Update"));
		
		V ejb;
		try
		{
			ejb = fSecurity.fByCode(cV,view.getCode());
			ejbLangFactory.rmLang(fSecurity,ejb);
			ejbDescriptionFactory.rmDescription(fSecurity,ejb);
		}
		catch (UtilsNotFoundException e)
		{
			try
			{
				ejb = cV.newInstance();
				ejb.setCategory(category);
				ejb.setCode(view.getCode());
				ejb = fSecurity.persist(ejb);
			}
			catch (InstantiationException e2) {throw new UtilsConfigurationException(e2.getMessage());}
			catch (IllegalAccessException e2) {throw new UtilsConfigurationException(e2.getMessage());}
			catch (UtilsConstraintViolationException e2) {throw new UtilsConfigurationException(e2.getMessage());}	
		}
		
		try
		{
			if(view.isSetPublic()){ejb.setAccessPublic(view.isPublic());}else{ejb.setAccessPublic(false);}
			if(view.isSetOnlyLoginRequired()){ejb.setAccessLogin(view.isOnlyLoginRequired());}else{ejb.setAccessLogin(false);}
			if(view.isSetDocumentation()){ejb.setDocumentation(view.isDocumentation());}else{ejb.setDocumentation(false);}
			if(view.isSetVisible()){ejb.setVisible(view.isVisible());}else{ejb.setVisible(true);}
			if(view.isSetPosition()){ejb.setPosition(view.getPosition());}else{ejb.setPosition(0);}
			
			ejb.setName(ejbLangFactory.getLangMap(view.getLangs()));
			ejb.setDescription(ejbDescriptionFactory.create(view.getDescriptions()));
			ejb.setCategory(category);
			
			ejb.setPackageName(null);
			ejb.setViewPattern(null);
			ejb.setUrlBase(null);
			ejb.setUrlMapping(null);	
			
			if(view.isSetNavigation())
			{
				if(view.getNavigation().isSetPackage()){ejb.setPackageName(view.getNavigation().getPackage());}
				if(view.getNavigation().isSetViewPattern()){ejb.setViewPattern(view.getNavigation().getViewPattern().getValue());}
				if(view.getNavigation().isSetUrlMapping())
				{
					ejb.setUrlMapping(view.getNavigation().getUrlMapping().getValue());
					if(view.getNavigation().getUrlMapping().isSetUrl()){ejb.setUrlBase(view.getNavigation().getUrlMapping().getUrl());}
				}
			}
			
			ejb=fSecurity.update(ejb);

			logger.trace("Actions: "+view.getCode()+" "+view.isSetActions());
			if(view.isSetActions() && view.getActions().isSetAction())
			{
				for(Action action : view.getActions().getAction())
				{
					updateAction.actualAdd(action.getCode());
					iuAction(ejb, action);
				}
			}
			dut.success();
		}
		catch (UtilsConstraintViolationException e) {dut.fail(e,false); }
		catch (UtilsLockingException e) {dut.fail(e,false); }
	}
	
	@Deprecated private void iuAction(V ejbView, Action action) throws UtilsConfigurationException
	{
		A ebj;
		try
		{
			ebj = fSecurity.fByCode(cA,action.getCode());
			ejbLangFactory.rmLang(fSecurity,ebj);
			ejbDescriptionFactory.rmDescription(fSecurity,ebj);
		}
		catch (UtilsNotFoundException e)
		{
			try
			{
				ebj = cA.newInstance();
				ebj.setView(ejbView);
				ebj.setCode(action.getCode());
				ebj = fSecurity.persist(ebj);
			}
			catch (InstantiationException e2) {throw new UtilsConfigurationException(e2.getMessage());}
			catch (IllegalAccessException e2) {throw new UtilsConfigurationException(e2.getMessage());}
			catch (UtilsConstraintViolationException e2) {throw new UtilsConfigurationException(e2.getMessage());}	
		}
		
		try
		{
			ebj.setVisible(true);
			ebj.setPosition(0);
			
			ebj.setName(ejbLangFactory.getLangMap(action.getLangs()));
			ebj.setDescription(ejbDescriptionFactory.create(action.getDescriptions()));
			ebj.setView(ejbView);
			ebj=fSecurity.update(ebj);
		}
		catch (UtilsConstraintViolationException e) {logger.error("Action.Code:"+action.getCode(),e);}
		catch (UtilsLockingException e) {logger.error("",e);}
	}
}