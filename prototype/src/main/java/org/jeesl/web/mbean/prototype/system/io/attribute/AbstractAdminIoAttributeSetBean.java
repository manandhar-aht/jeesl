package org.jeesl.web.mbean.prototype.system.io.attribute;

import java.io.Serializable;
import java.util.List;

import org.jeesl.api.bean.JeeslAttributeBean;
import org.jeesl.api.facade.io.JeeslIoAttributeFacade;
import org.jeesl.factory.builder.AttributeFactoryBuilder;
import org.jeesl.interfaces.bean.sb.SbToggleBean;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeContainer;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeCriteria;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeData;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeItem;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.interfaces.bean.FacesMessageBean;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractAdminIoAttributeSetBean <L extends UtilsLang, D extends UtilsDescription,
												CATEGORY extends UtilsStatus<CATEGORY,L,D>,
												CRITERIA extends JeeslAttributeCriteria<L,D,CATEGORY,TYPE>,
												TYPE extends UtilsStatus<TYPE,L,D>,
												SET extends JeeslAttributeSet<L,D,CATEGORY,ITEM>,
												ITEM extends JeeslAttributeItem<CRITERIA,SET>,
												CONTAINER extends JeeslAttributeContainer<SET,DATA>,
												DATA extends JeeslAttributeData<CRITERIA,CONTAINER>>
					extends AbstractAdminIoAttributeBean<L,D,CATEGORY,CRITERIA,TYPE,SET,ITEM,CONTAINER,DATA>
					implements Serializable,SbToggleBean
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminIoAttributeSetBean.class);
		
	private List<CRITERIA> criterias; public List<CRITERIA> getCriterias() {return criterias;}
	private List<SET> sets; public List<SET> getSets() {return sets;}
	private List<ITEM> items; public List<ITEM> getItems() {return items;}
	
	private SET set; public SET getSet() {return set;} public void setSet(SET set) {this.set = set;}
	private ITEM item; public ITEM getItem() {return item;} public void setItem(ITEM item) {this.item = item;}
	
	public AbstractAdminIoAttributeSetBean(AttributeFactoryBuilder<L,D,CATEGORY,CRITERIA,TYPE,SET,ITEM,CONTAINER,DATA> fbAttribute) {super(fbAttribute);}
	
	protected void initAttributeSet(String[] localeCodes, FacesMessageBean bMessage, JeeslAttributeBean<L,D,CATEGORY,CRITERIA,TYPE,SET,ITEM,CONTAINER,DATA> bAttribute, JeeslIoAttributeFacade<L,D,CATEGORY,CRITERIA,TYPE,SET,ITEM,CONTAINER,DATA> fAttribute)
	{
		super.initAttribute(localeCodes,bMessage,bAttribute,fAttribute);
		criterias = fAttribute.allOrderedPositionVisible(fbAttribute.getClassCriteria());
	}
	
	public void toggled(Class<?> c)
	{
		logger.info(AbstractLogMessage.toggled(c));
		if(fbAttribute.getClassCategory().isAssignableFrom(c))
		{
			reloadSets();
		}
	}
	
	public void resetSet() {reset(true);}
	private void reset(boolean rSet)
	{
		if(rSet) {set=null;}
	}
	
	private void reloadSets()
	{
		sets = fAttribute.all(fbAttribute.getClassSet());
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
	
	public void selectSet()
	{
		if(debugOnInfo) {logger.info(AbstractLogMessage.selectEntity(set));}
		set = efLang.persistMissingLangs(fAttribute,localeCodes,set);
		set = efDescription.persistMissingLangs(fAttribute,localeCodes,set);
		reloadItems();
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
	
	public void selectItem()
	{
		if(debugOnInfo) {logger.info(AbstractLogMessage.selectEntity(item));}
	}
}