package org.jeesl.web.mbean.prototype.system.io.attribute;

import java.io.Serializable;

import org.jeesl.api.facade.io.JeeslIoAttributeFacade;
import org.jeesl.controller.handler.sb.SbMultiHandler;
import org.jeesl.factory.builder.AttributeFactoryBuilder;
import org.jeesl.interfaces.bean.sb.SbToggleBean;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeContainer;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeCriteria;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeData;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeItem;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeSet;
import org.jeesl.web.mbean.prototype.admin.AbstractAdminBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	private final AttributeFactoryBuilder<L,D,CATEGORY,CRITERIA,TYPE,SET,ITEM,CONTAINER,DATA> fbAttribute;
	
	private final SbMultiHandler<CATEGORY> sbhCategory; public SbMultiHandler<CATEGORY> getSbhCategory() {return sbhCategory;}
	
	public AbstractAdminIoAttributePoolBean(AttributeFactoryBuilder<L,D,CATEGORY,CRITERIA,TYPE,SET,ITEM,CONTAINER,DATA> fbAttribute)
	{
		super(fbAttribute.getClassL(),fbAttribute.getClassD());
		this.fbAttribute=fbAttribute;
		sbhCategory = new SbMultiHandler<CATEGORY>(fbAttribute.getClassCat(),this);
	}
	
	protected void initAttributePool(String[] langs, FacesMessageBean bMessage, JeeslIoAttributeFacade<L,D,CATEGORY,CRITERIA,TYPE,SET,ITEM,CONTAINER,DATA> fAttribute)
	{
		super.initAdmin(langs,cL,cD,bMessage);
		this.fAttribute=fAttribute;
		sbhCategory.setList(fAttribute.allOrderedPositionVisible(fbAttribute.getClassCat()));
	}
	
	public void toggled(Class<?> c)
	{
		logger.info(AbstractLogMessage.toggled(c));
		
	}
	
	private void clear(boolean clearMail)
	{
		
	}
}