package org.jeesl.web.mbean.prototype.system.io.attribute;

import java.io.Serializable;
import java.util.List;

import org.jeesl.api.bean.JeeslAttributeBean;
import org.jeesl.api.facade.io.JeeslIoAttributeFacade;
import org.jeesl.controller.handler.sb.SbMultiHandler;
import org.jeesl.factory.builder.AttributeFactoryBuilder;
import org.jeesl.factory.ejb.module.attribute.EjbAttributeCriteriaFactory;
import org.jeesl.interfaces.bean.sb.SbToggleBean;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeContainer;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeCriteria;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeData;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeItem;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeSet;
import org.jeesl.web.mbean.prototype.admin.AbstractAdminBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.interfaces.bean.FacesMessageBean;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractAdminIoAttributePoolBean <L extends UtilsLang, D extends UtilsDescription,
												CATEGORY extends UtilsStatus<CATEGORY,L,D>,
												CRITERIA extends JeeslAttributeCriteria<L,D,CATEGORY,TYPE>,
												TYPE extends UtilsStatus<TYPE,L,D>,
												SET extends JeeslAttributeSet<L,D,CATEGORY,ITEM>,
												ITEM extends JeeslAttributeItem<SET,CRITERIA>,
												CONTAINER extends JeeslAttributeContainer<SET,DATA>,
												DATA extends JeeslAttributeData<CRITERIA,CONTAINER>>
					extends AbstractAdminBean<L,D>
					implements Serializable,SbToggleBean
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminIoAttributePoolBean.class);
	
	private JeeslIoAttributeFacade<L,D,CATEGORY,CRITERIA,TYPE,SET,ITEM,CONTAINER,DATA> fAttribute;
	private JeeslAttributeBean<L,D,CATEGORY,CRITERIA,TYPE,SET,ITEM,CONTAINER,DATA> bAttribute;
	private final AttributeFactoryBuilder<L,D,CATEGORY,CRITERIA,TYPE,SET,ITEM,CONTAINER,DATA> fbAttribute;
	
	private final SbMultiHandler<CATEGORY> sbhCategory; public SbMultiHandler<CATEGORY> getSbhCategory() {return sbhCategory;}
	
	private final EjbAttributeCriteriaFactory<L,D,CATEGORY,CRITERIA,TYPE> efCriteria;
	
	private List<CRITERIA> criterias; public List<CRITERIA> getCriterias() {return criterias;}

	private CRITERIA criteria; public CRITERIA getCriteria() {return criteria;} public void setCriteria(CRITERIA criteria) {this.criteria = criteria;}

	public AbstractAdminIoAttributePoolBean(AttributeFactoryBuilder<L,D,CATEGORY,CRITERIA,TYPE,SET,ITEM,CONTAINER,DATA> fbAttribute)
	{
		super(fbAttribute.getClassL(),fbAttribute.getClassD());
		this.fbAttribute=fbAttribute;
		efCriteria = fbAttribute.ejbCriteria();
		sbhCategory = new SbMultiHandler<CATEGORY>(fbAttribute.getClassCategory(),this);
	}
	
	protected void initAttributePool(String[] localeCodes, FacesMessageBean bMessage, JeeslAttributeBean<L,D,CATEGORY,CRITERIA,TYPE,SET,ITEM,CONTAINER,DATA> bAttribute, JeeslIoAttributeFacade<L,D,CATEGORY,CRITERIA,TYPE,SET,ITEM,CONTAINER,DATA> fAttribute)
	{
		super.initAdmin(localeCodes,cL,cD,bMessage);
		this.fAttribute=fAttribute;
		this.bAttribute=bAttribute;
		sbhCategory.setList(fAttribute.allOrderedPositionVisible(fbAttribute.getClassCategory()));
		sbhCategory.toggleNone();
	}
	
	public void toggled(Class<?> c)
	{
		logger.info(AbstractLogMessage.toggled(c));
		if(fbAttribute.getClassCategory().isAssignableFrom(c))
		{
			reloadCriterias();
		}
	}
	
	public void resetCriteria() {reset(true);}
	private void reset(boolean rCriteria)
	{
		if(rCriteria) {criteria=null;}
	}
	
	private void reloadCriterias()
	{
		criterias = fAttribute.all(fbAttribute.getClassCriteria());
		if(debugOnInfo) {logger.info(AbstractLogMessage.reloaded(fbAttribute.getClassCriteria(),criterias));}
	}
	
	public void addCriteria()
	{
		if(debugOnInfo) {logger.info(AbstractLogMessage.addEntity(fbAttribute.getClassCriteria()));}
		criteria = efCriteria.build(null,null);
		criteria.setName(efLang.createEmpty(localeCodes));
		criteria.setDescription(efDescription.createEmpty(localeCodes));
	}
	
	public void saveCriteria() throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(debugOnInfo) {logger.info(AbstractLogMessage.saveEntity(criteria));}
		criteria.setCategory(fAttribute.find(fbAttribute.getClassCategory(),criteria.getCategory()));
		criteria.setType(fAttribute.find(fbAttribute.getClassType(),criteria.getType()));
		criteria = fAttribute.save(criteria);
		reloadCriterias();
	}
	
	public void selectCriteria()
	{
		if(debugOnInfo) {logger.info(AbstractLogMessage.selectEntity(criteria));}
		criteria = efLang.persistMissingLangs(fAttribute,localeCodes,criteria);
		criteria = efDescription.persistMissingLangs(fAttribute,localeCodes,criteria);
	}
}