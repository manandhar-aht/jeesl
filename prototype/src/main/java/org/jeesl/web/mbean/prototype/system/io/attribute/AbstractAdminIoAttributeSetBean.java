package org.jeesl.web.mbean.prototype.system.io.attribute;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.jeesl.api.bean.JeeslAttributeBean;
import org.jeesl.api.facade.io.JeeslIoAttributeFacade;
import org.jeesl.factory.builder.io.IoAttributeFactoryBuilder;
import org.jeesl.interfaces.bean.sb.SbToggleBean;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeContainer;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeCriteria;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeData;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeItem;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeOption;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeSet;
import org.jeesl.util.comparator.ejb.system.io.attribute.AttributeSetComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.interfaces.bean.FacesMessageBean;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.jsf.util.PositionListReorderer;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public abstract class AbstractAdminIoAttributeSetBean <L extends UtilsLang, D extends UtilsDescription,
												CATEGORY extends UtilsStatus<CATEGORY,L,D>,
												CRITERIA extends JeeslAttributeCriteria<L,D,CATEGORY,TYPE>,
												TYPE extends UtilsStatus<TYPE,L,D>,
												OPTION extends JeeslAttributeOption<L,D,CRITERIA>,
												SET extends JeeslAttributeSet<L,D,CATEGORY,ITEM>,
												ITEM extends JeeslAttributeItem<CRITERIA,SET>,
												CONTAINER extends JeeslAttributeContainer<SET,DATA>,
												DATA extends JeeslAttributeData<CRITERIA,OPTION,CONTAINER>>
					extends AbstractAdminIoAttributeBean<L,D,CATEGORY,CRITERIA,TYPE,OPTION,SET,ITEM,CONTAINER,DATA>
					implements Serializable,SbToggleBean
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminIoAttributeSetBean.class);
		
	private List<CRITERIA> criterias; public List<CRITERIA> getCriterias() {return criterias;}
	private List<SET> sets; public List<SET> getSets() {return sets;}
	private List<ITEM> items; public List<ITEM> getItems() {return items;}
	
	private SET set; public SET getSet() {return set;} public void setSet(SET set) {this.set = set;}
	private ITEM item; public ITEM getItem() {return item;} public void setItem(ITEM item) {this.item = item;}
	
	private final Comparator<SET> comparatorSet;
	
	public AbstractAdminIoAttributeSetBean(IoAttributeFactoryBuilder<L,D,CATEGORY,CRITERIA,TYPE,OPTION,SET,ITEM,CONTAINER,DATA> fbAttribute)
	{
		super(fbAttribute);
		comparatorSet = new AttributeSetComparator<CATEGORY,SET>().factory(AttributeSetComparator.Type.position);
	}
	
	protected void initAttributeSet(String[] localeCodes, FacesMessageBean bMessage, JeeslAttributeBean<L,D,CATEGORY,CRITERIA,TYPE,OPTION,SET,ITEM,CONTAINER,DATA> bAttribute, JeeslIoAttributeFacade<L,D,CATEGORY,CRITERIA,TYPE,OPTION,SET,ITEM,CONTAINER,DATA> fAttribute)
	{
		super.initAttribute(localeCodes,bMessage,bAttribute,fAttribute);
		
		reloadSets();
	}
	
	public void toggled(Class<?> c)
	{
		logger.info(AbstractLogMessage.toggled(c));
		if(fbAttribute.getClassCategory().isAssignableFrom(c))
		{
			reloadSets();
		}
	}
	
	public void resetSet() {reset(true,true);}
	private void reset(boolean rSet, boolean rItem)
	{
		if(rSet) {set=null;}
		if(rItem){item=null;}
	}
	
	private void reloadSets()
	{
		criterias = fAttribute.allForParents(fbAttribute.getClassCriteria(), sbhCategory.getSelected());
		sets = fAttribute.allForParents(fbAttribute.getClassSet(), sbhCategory.getSelected());
		if(debugOnInfo) {logger.info(AbstractLogMessage.reloaded(fbAttribute.getClassSet(),sets));}
	}
	
	public void addSet()
	{
		if(debugOnInfo) {logger.info(AbstractLogMessage.addEntity(fbAttribute.getClassCriteria()));}
		set = efSet.build(null);
		set.setName(efLang.createEmpty(localeCodes));
		set.setDescription(efDescription.createEmpty(localeCodes));
	}
	
	public void saveSet() throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(debugOnInfo) {logger.info(AbstractLogMessage.saveEntity(set));}
		set.setCategory(fAttribute.find(fbAttribute.getClassCategory(),set.getCategory()));
		set = fAttribute.save(set);
		reloadSets();
		reloadItems();
	}
	
	public void deleteSet() throws UtilsConstraintViolationException
	{
		if(debugOnInfo) {logger.info(AbstractLogMessage.rmEntity(set));}
		fAttribute.rm(set);
		reloadSets();
		reset(true,true);
	}
	
	public void selectSet()
	{
		if(debugOnInfo) {logger.info(AbstractLogMessage.selectEntity(set));}
		set = efLang.persistMissingLangs(fAttribute,localeCodes,set);
		set = efDescription.persistMissingLangs(fAttribute,localeCodes,set);
		reloadItems();
		reset(false,true);
	}
	
	private void reloadItems()
	{
		items = fAttribute.allForParent(fbAttribute.getClassItem(), set);
		if(debugOnInfo) {logger.info(AbstractLogMessage.reloaded(fbAttribute.getClassItem(),items));}
	}
	
	public void addItem()
	{
		if(debugOnInfo) {logger.info(AbstractLogMessage.addEntity(fbAttribute.getClassItem()));}
		item = efItem.build(null,set,items);
	}
	
	public void saveItem() throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(debugOnInfo) {logger.info(AbstractLogMessage.saveEntity(item));}
		item.setItemSet(fAttribute.find(fbAttribute.getClassSet(),item.getItemSet()));
		item.setCriteria(fAttribute.find(fbAttribute.getClassCriteria(),item.getCriteria()));
		item = fAttribute.save(item);
		reloadItems();
	}
	
	public void deleteItem() throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(debugOnInfo) {logger.info(AbstractLogMessage.rmEntity(item));}
		fAttribute.rm(item);
		reset(false,true);
		reloadItems();
	}
	
	public void selectItem()
	{
		if(debugOnInfo) {logger.info(AbstractLogMessage.selectEntity(item));}
	}
	
	public void reorderSets() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fAttribute, fbAttribute.getClassSet(), sets);Collections.sort(sets, comparatorSet);}
	public void reorderItems() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fAttribute, items);}
}