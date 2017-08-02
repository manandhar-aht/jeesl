package org.jeesl.web.mbean.prototype.admin.system.io;

import java.io.Serializable;

import org.jeesl.api.facade.io.JeeslIoCmsFacade;
import org.jeesl.controller.handler.sb.SbSingleHandler;
import org.jeesl.factory.ejb.system.io.cms.EjbIoCmsFactory;
import org.jeesl.factory.ejb.util.EjbIdFactory;
import org.jeesl.interfaces.bean.sb.SbSingleBean;
import org.jeesl.interfaces.bean.sb.SbToggleBean;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCms;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsContent;
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
										CAT extends UtilsStatus<CAT,L,D>,
										CMS extends JeeslIoCms<L,D,CAT,CMS,V,S,E,T,C,M>,
										V extends JeeslIoCmsVisiblity<L,D,CAT,CMS,V,S,E,T,C,M>,
										S extends JeeslIoCmsSection<L,D,CAT,CMS,V,S,E,T,C,M>,
										E extends JeeslIoCmsElement<L,D,CAT,CMS,V,S,E,T,C,M>,
										T extends UtilsStatus<T,L,D>,
										C extends JeeslIoCmsContent<L,D,CAT,CMS,V,S,E,T,C,M>,
										M extends UtilsStatus<M,L,D>>
					extends AbstractAdminBean<L,D>
					implements Serializable,SbToggleBean,SbSingleBean
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractCmsBean.class);
	
	protected JeeslIoCmsFacade<L,D,CAT,CMS,V,S,E,T,C,M> fCms;
	
	private final Class<CMS> cCms;
	
	private final EjbIoCmsFactory<L,D,CAT,CMS,V,S,E,T,C,M> efCms;
	
	protected final SbSingleHandler<CMS> sbhCms; public SbSingleHandler<CMS> getSbhCms() {return sbhCms;}
//	protected SbMultiHandler<CMS> sbhCms; public SbMultiHandler<CMS> getSbhCms() {return sbhCms;}
	
	protected CMS cms; public CMS getCms() {return cms;} public void setCms(CMS cms) {this.cms = cms;}
	protected CAT category;
	
	public AbstractCmsBean(final Class<L> cL, final Class<D> cD, final Class<CMS> cCms)
	{
		super(cL,cD);
		this.cCms=cCms;
		
		efCms = new EjbIoCmsFactory<L,D,CAT,CMS,V,S,E,T,C,M>(cCms);
		
		sbhCms = new SbSingleHandler<CMS>(cCms,this);
	}
	
	protected void initSuper(String[] langs, FacesMessageBean bMessage, JeeslIoCmsFacade<L,D,CAT,CMS,V,S,E,T,C,M> fCms)
	{
		super.initAdmin(langs,cL,cD,bMessage);
		this.fCms=fCms;
		reloadCms();
		sbhCms.silentCallback();
	}
	
	private void reloadCms()
	{
		sbhCms.setList(fCms.all(cCms));
	}
	
	@Override public void toggled(Class<?> c) throws UtilsLockingException, UtilsConstraintViolationException
	{
		logger.info(AbstractLogMessage.toggled(c));
	}
	
	@SuppressWarnings("unchecked")
	@Override public void selectSbSingle(EjbWithId ejb)
	{
		if(JeeslIoCms.class.isAssignableFrom(ejb.getClass()))
		{
			cms = (CMS)ejb;
			if(EjbIdFactory.isSaved(cms))
			{
				cms = efLang.persistMissingLangs(fCms,localeCodes,cms);
			}
			logger.info("Twice:"+sbhCms.getTwiceSelected()+" for "+cms.toString());
		}
		else
		{
			logger.info("NOT Assignable");
		}
	}
	
	public void addCms() throws UtilsNotFoundException
	{
//		if(debugOnInfo){logger.info(AbstractLogMessage.addEntity(cTemplate));}
		cms = efCms.build(null);
		cms.setName(efLang.createEmpty(localeCodes));
	}
	
	public void saveCms() throws UtilsConstraintViolationException, UtilsLockingException
	{
		logger.info("Save "+cms.toString());
		cms = fCms.save(cms);
		reloadCms();
	}
}