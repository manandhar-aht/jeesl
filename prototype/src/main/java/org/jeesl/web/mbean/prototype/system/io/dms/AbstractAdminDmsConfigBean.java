package org.jeesl.web.mbean.prototype.system.io.dms;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.jeesl.api.bean.JeeslTranslationBean;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.api.facade.io.JeeslIoDmsFacade;
import org.jeesl.factory.builder.io.IoAttributeFactoryBuilder;
import org.jeesl.factory.builder.io.IoDmsFactoryBuilder;
import org.jeesl.factory.ejb.system.io.dms.EjbIoDmsFactory;
import org.jeesl.factory.ejb.system.io.dms.EjbIoDmsViewFactory;
import org.jeesl.interfaces.bean.sb.SbToggleBean;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeContainer;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeItem;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeSet;
import org.jeesl.interfaces.model.system.io.dms.JeeslIoDms;
import org.jeesl.interfaces.model.system.io.dms.JeeslIoDmsDocument;
import org.jeesl.interfaces.model.system.io.dms.JeeslIoDmsLayer;
import org.jeesl.interfaces.model.system.io.dms.JeeslIoDmsSection;
import org.jeesl.interfaces.model.system.io.dms.JeeslIoDmsView;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileContainer;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public abstract class AbstractAdminDmsConfigBean <L extends UtilsLang,D extends UtilsDescription,LOC extends UtilsStatus<LOC,L,D>,
													DMS extends JeeslIoDms<L,D,STORAGE,AS,S>,
													STORAGE extends JeeslFileStorage<L,D,?>,
													AS extends JeeslAttributeSet<L,D,?,?>,
													S extends JeeslIoDmsSection<L,D,S>,
													F extends JeeslIoDmsDocument<L,S,FC,AC>,
													VIEW extends JeeslIoDmsView<L,DMS>,
													LAYER extends JeeslIoDmsLayer<VIEW,AI>,
													FC extends JeeslFileContainer<?,?>,
													AI extends JeeslAttributeItem<?,AS>,
													AC extends JeeslAttributeContainer<?,?>>
					extends AbstractDmsBean<L,D,LOC,DMS,STORAGE,AS,S,F,VIEW,LAYER,FC,AI,AC>
					implements Serializable,SbToggleBean
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminDmsConfigBean.class);
	
	private final IoAttributeFactoryBuilder<L,D,?,?,?,?,AS,?,?,?> fbAttribute;
	
	private final EjbIoDmsFactory<DMS> efDms;
	private final EjbIoDmsViewFactory<DMS,VIEW> efView;
	
	private List<DMS> dms; public List<DMS> getDms() {return dms;}
	protected final List<AS> sets; public List<AS> getSets() {return sets;}
	protected final List<STORAGE> storages; public List<STORAGE> getStorages() {return storages;}
	private List<VIEW> dmsViews; public List<VIEW> getDmsViews() {return dmsViews;}

	private VIEW dmsView; public VIEW getDmsView() {return dmsView;} public void setDmsView(VIEW dmsView) {this.dmsView = dmsView;}

	public AbstractAdminDmsConfigBean(IoDmsFactoryBuilder<L,D,LOC,DMS,STORAGE,S,F,VIEW,LAYER> fbDms, final IoAttributeFactoryBuilder<L,D,?,?,?,?,AS,?,?,?> fbAttribute)
	{
		super(fbDms);
		this.fbAttribute=fbAttribute;
		efDms = fbDms.ejbDms();
		efView = fbDms.ejbView();
		
		sets = new ArrayList<AS>();
		storages = new ArrayList<STORAGE>();
	}
	
	protected void initDmsConfig(JeeslTranslationBean bTranslation, JeeslFacesMessageBean bMessage,JeeslIoDmsFacade<L,D,LOC,DMS,STORAGE,AS,S,F,VIEW,FC,AC> fDms)
	{
		super.initDms(bTranslation,bMessage,fDms);
		initPageConfiguration();
		reloadDms();
	}
	protected abstract void initPageConfiguration();
	
	public void toggled(Class<?> c)
	{
		logger.info(AbstractLogMessage.toggled(c));
		
	}
	
	public void resetAll() {reset(true,true);}
	public void resetSet() {reset(true,true);}
	private void reset(boolean rSet, boolean rItem)
	{

	}
	
	private void reloadDms()
	{
		dms = fDms.all(fbDms.getClassDms());
		if(debugOnInfo) {logger.info(AbstractLogMessage.reloaded(fbDms.getClassDms(),dms));}
	}
	
	public void addDm()
	{
		if(debugOnInfo) {logger.info(AbstractLogMessage.addEntity(fbDms.getClassDms()));}
		dm = efDms.build();
		dm.setName(efLang.createEmpty(localeCodes));
//		dm.setDescription(efDescription.createEmpty(localeCodes));
		dm.setRoot(efSection.build(null));
	}
	
	public void saveDm() throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(debugOnInfo) {logger.info(AbstractLogMessage.saveEntity(dm));}
		dm.setSet(fDms.find(fbAttribute.getClassSet(),dm.getSet()));
		dm.setStorage(fDms.find(fbDms.getClassStorage(),dm.getStorage()));
		
		dm = fDms.save(dm);
		reloadDms();
	}
	
	public void deleteDm() throws UtilsConstraintViolationException
	{
		if(debugOnInfo) {logger.info(AbstractLogMessage.rmEntity(dm));}
//		fAttribute.rm(set);
//		reloadSets();
//		reset(true,true);
	}
	
	public void selectDm()
	{
		if(debugOnInfo) {logger.info(AbstractLogMessage.selectEntity(dm));}
		reloadDm();
//		set = efLang.persistMissingLangs(fAttribute,localeCodes,set);
//		set = efDescription.persistMissingLangs(fAttribute,localeCodes,set);
//		reloadItems();
//		reset(false,true);
	}
	
	private void reloadDm()
	{
		dm = fDms.find(fbDms.getClassDms(),dm);
		dmsViews = fDms.allForParent(fbDms.getClassView(), dm);
	}
	
	public void addView()
	{
		if(debugOnInfo) {logger.info(AbstractLogMessage.addEntity(fbDms.getClassView()));}
		dmsView = efView.build(dm, dmsViews);
		dmsView.setName(efLang.createEmpty(localeCodes));
	}
	
	public void saveView() throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(debugOnInfo) {logger.info(AbstractLogMessage.saveEntity(dmsView));}

		dmsView = fDms.save(dmsView);
		reloadDm();
	}
	
//	public void reorderSets() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fAttribute, fbAttribute.getClassSet(), sets);Collections.sort(sets, comparatorSet);}
//	public void reorderItems() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fAttribute, items);}
}