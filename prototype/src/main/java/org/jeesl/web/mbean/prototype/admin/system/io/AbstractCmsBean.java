package org.jeesl.web.mbean.prototype.admin.system.io;

import java.io.Serializable;

import org.jeesl.api.facade.io.JeeslIoCmsFacade;
import org.jeesl.controller.handler.sb.SbSingleHandler;
import org.jeesl.interfaces.bean.sb.SbSingleBean;
import org.jeesl.interfaces.bean.sb.SbToggleBean;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCms;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsElement;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsSection;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsVisiblity;
import org.jeesl.web.mbean.prototype.admin.AbstractAdminBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.bean.FacesMessageBean;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public abstract class AbstractCmsBean <L extends UtilsLang,D extends UtilsDescription,
										C extends UtilsStatus<C,L,D>,
										CMS extends JeeslIoCms<L,D,C,CMS,V,S,E,T>,
										V extends JeeslIoCmsVisiblity<L,D,C,CMS,V,S,E,T>,
										S extends JeeslIoCmsSection<L,D,C,CMS,V,S,E,T>,
										E extends JeeslIoCmsElement<L,D,C,CMS,V,S,E,T>,
										T extends UtilsStatus<T,L,D>>
					extends AbstractAdminBean<L,D>
					implements Serializable,SbToggleBean,SbSingleBean
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractCmsBean.class);
	
	protected JeeslIoCmsFacade<L,D,C,CMS,V,S,E,T> fCms;
	
	private final Class<CMS> cCms;
	
	protected final SbSingleHandler<CMS> sbhCms; public SbSingleHandler<CMS> getSbhCms() {return sbhCms;}
//	protected SbMultiHandler<CMS> sbhCms; public SbMultiHandler<CMS> getSbhCms() {return sbhCms;}
	
	public AbstractCmsBean(final Class<L> cL, final Class<D> cD, final Class<CMS> cCms)
	{
		super(cL,cD);
		this.cCms=cCms;
		sbhCms = new SbSingleHandler<CMS>(cCms,this);
	}
	
	protected void initSuper(String[] langs, FacesMessageBean bMessage, JeeslIoCmsFacade<L,D,C,CMS,V,S,E,T> fCms)
	{
		super.initAdmin(langs,cL,cD,bMessage);
		this.fCms=fCms;
		reloadCms();
	}
	
	private void reloadCms()
	{
		sbhCms.setList(fCms.all(cCms));
		sbhCms.selectDefault();
	}
	
	@Override public void toggled(Class<?> c) throws UtilsLockingException, UtilsConstraintViolationException
	{
		logger.info(AbstractLogMessage.toggled(c));
		
	}
	
	@Override public void selectSbSingle(EjbWithId ejb)
	{
		
	}
	
	//*************************************************************************************
	
	
	public void addCms() throws UtilsNotFoundException
	{
//		if(debugOnInfo){logger.info(AbstractLogMessage.addEntity(cTemplate));}
		
	}
}