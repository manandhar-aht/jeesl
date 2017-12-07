package org.jeesl.web.mbean.prototype.system.io.dms;

import java.io.Serializable;

import org.jeesl.api.bean.JeeslTranslationBean;
import org.jeesl.api.facade.io.JeeslIoDmsFacade;
import org.jeesl.factory.builder.io.IoDmsFactoryBuilder;
import org.jeesl.factory.ejb.system.io.dms.EjbIoDmsSectionFactory;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeSet;
import org.jeesl.interfaces.model.system.io.dms.JeeslIoDms;
import org.jeesl.interfaces.model.system.io.dms.JeeslIoDmsSection;
import org.jeesl.web.mbean.prototype.admin.AbstractAdminBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.bean.FacesMessageBean;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public abstract class AbstractDmsBean <L extends UtilsLang,D extends UtilsDescription,
													DMS extends JeeslIoDms<L,D,AS,S>,
													AS extends JeeslAttributeSet<L,D,?,?>,
													S extends JeeslIoDmsSection<L,S>>
					extends AbstractAdminBean<L,D>
					implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractDmsBean.class);
	
	protected JeeslIoDmsFacade<L,D,DMS,AS,S> fDms;
	protected final IoDmsFactoryBuilder<L,D,DMS,AS,S> fbDms;
	
	protected final EjbIoDmsSectionFactory<S> efSection;
	
	protected DMS dm; public DMS getDm() {return dm;} public void setDm(DMS dm) {this.dm = dm;}

	public AbstractDmsBean(IoDmsFactoryBuilder<L,D,DMS,AS,S> fbDms)
	{
		super(fbDms.getClassL(),fbDms.getClassD());
		this.fbDms=fbDms;
		efSection = fbDms.ejbSection();
	}
	
	protected void initDms(JeeslTranslationBean bTranslation, FacesMessageBean bMessage,JeeslIoDmsFacade<L,D,DMS,AS,S> fDms)
	{
		super.initAdmin(bTranslation.getLangKeys().toArray(new String[0]),cL,cD,bMessage);
		this.fDms=fDms;
	}
}