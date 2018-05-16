package org.jeesl.web.mbean.prototype.system.io.dms;

import java.io.Serializable;

import org.jeesl.api.bean.JeeslTranslationBean;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.api.facade.io.JeeslIoDmsFacade;
import org.jeesl.controller.handler.sb.SbSingleHandler;
import org.jeesl.factory.builder.io.IoDmsFactoryBuilder;
import org.jeesl.factory.ejb.system.io.dms.EjbIoDmsDocumentFactory;
import org.jeesl.factory.ejb.system.io.dms.EjbIoDmsSectionFactory;
import org.jeesl.interfaces.bean.sb.SbSingleBean;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeContainer;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeItem;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeSet;
import org.jeesl.interfaces.model.system.io.dms.JeeslIoDms;
import org.jeesl.interfaces.model.system.io.dms.JeeslIoDmsDocument;
import org.jeesl.interfaces.model.system.io.dms.JeeslIoDmsLayer;
import org.jeesl.interfaces.model.system.io.dms.JeeslIoDmsSection;
import org.jeesl.interfaces.model.system.io.dms.JeeslIoDmsView;
import org.jeesl.interfaces.model.system.io.domain.JeeslDomainSet;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileContainer;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileStorage;
import org.jeesl.web.mbean.prototype.admin.AbstractAdminBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public abstract class AbstractDmsBean <L extends UtilsLang,D extends UtilsDescription,LOC extends UtilsStatus<LOC,L,D>,
													DMS extends JeeslIoDms<L,D,STORAGE,AS,DS,SECTION>,
													STORAGE extends JeeslFileStorage<L,D,?>,
													AS extends JeeslAttributeSet<L,D,?,?>,
													DS extends JeeslDomainSet<L,D,?>,
													SECTION extends JeeslIoDmsSection<L,D,SECTION>,
													F extends JeeslIoDmsDocument<L,SECTION,FC,AC>,
													VIEW extends JeeslIoDmsView<L,DMS>,
													LAYER extends JeeslIoDmsLayer<VIEW,AI>,
													FC extends JeeslFileContainer<?,?>,
													AI extends JeeslAttributeItem<?,AS>,
													AC extends JeeslAttributeContainer<?,?>>
					extends AbstractAdminBean<L,D>
					implements Serializable,SbSingleBean
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractDmsBean.class);
	
	protected JeeslIoDmsFacade<L,D,LOC,DMS,STORAGE,AS,DS,SECTION,F,VIEW,FC,AC> fDms;
	protected final IoDmsFactoryBuilder<L,D,LOC,DMS,STORAGE,SECTION,F,VIEW,LAYER> fbDms;
	
	protected final EjbIoDmsSectionFactory<SECTION> efSection;
	protected final EjbIoDmsDocumentFactory<SECTION,F> efFile;
	
	protected DMS dm; public DMS getDm() {return dm;} public void setDm(DMS dm) {this.dm = dm;}
	protected final SbSingleHandler<LOC> sbhLocale; public SbSingleHandler<LOC> getSbhLocale() {return sbhLocale;}

	public AbstractDmsBean(IoDmsFactoryBuilder<L,D,LOC,DMS,STORAGE,SECTION,F,VIEW,LAYER> fbDms)
	{
		super(fbDms.getClassL(),fbDms.getClassD());
		this.fbDms=fbDms;
		
		sbhLocale = new SbSingleHandler<LOC>(fbDms.getClassLocale(),this);
		efSection = fbDms.ejbSection();
		efFile = fbDms.ejbFile();
	}
	
	protected void initDms(JeeslTranslationBean bTranslation, JeeslFacesMessageBean bMessage,JeeslIoDmsFacade<L,D,LOC,DMS,STORAGE,AS,DS,SECTION,F,VIEW,FC,AC> fDms)
	{
		super.initJeeslAdmin(bTranslation,bMessage);
		this.fDms=fDms;
	}
	
	protected void initLocales()
	{
		localeCodes = new String[sbhLocale.getList().size()];
		for(int i=0;i<sbhLocale.getList().size();i++) {localeCodes[i]=sbhLocale.getList().get(i).getCode();}
	}
	
	@Override public void selectSbSingle(EjbWithId ejb) throws UtilsLockingException, UtilsConstraintViolationException
	{
		
	}
}