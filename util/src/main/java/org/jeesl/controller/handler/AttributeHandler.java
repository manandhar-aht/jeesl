package org.jeesl.controller.handler;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.jeesl.api.bean.JeeslAttributeBean;
import org.jeesl.api.facade.io.JeeslIoAttributeFacade;
import org.jeesl.factory.builder.io.IoAttributeFactoryBuilder;
import org.jeesl.factory.ejb.system.io.attribute.EjbAttributeContainerFactory;
import org.jeesl.factory.ejb.system.io.attribute.EjbAttributeDataFactory;
import org.jeesl.factory.ejb.util.EjbIdFactory;
import org.jeesl.interfaces.bean.AttributeBean;
import org.jeesl.interfaces.controller.handler.JeeslAttributeHandler;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeContainer;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeCriteria;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeData;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeItem;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeOption;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeSet;
import org.jeesl.interfaces.model.module.attribute.JeeslWithAttributeContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.bean.FacesMessageBean;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class AttributeHandler<L extends UtilsLang, D extends UtilsDescription,
								CATEGORY extends UtilsStatus<CATEGORY,L,D>,
								CRITERIA extends JeeslAttributeCriteria<L,D,CATEGORY,TYPE>,
								TYPE extends UtilsStatus<TYPE,L,D>,
								OPTION extends JeeslAttributeOption<L,D,CRITERIA>,
								SET extends JeeslAttributeSet<L,D,CATEGORY,ITEM>,
								ITEM extends JeeslAttributeItem<CRITERIA,SET>,
								CONTAINER extends JeeslAttributeContainer<SET,DATA>,
								DATA extends JeeslAttributeData<CRITERIA,OPTION,CONTAINER>>
	implements Serializable,JeeslAttributeHandler<CONTAINER>
{
	final static Logger logger = LoggerFactory.getLogger(AttributeHandler.class);
	private static final long serialVersionUID = 1L;

	private boolean debugOnInfo = true;
	
	private final JeeslIoAttributeFacade<L,D,CATEGORY,CRITERIA,TYPE,OPTION,SET,ITEM,CONTAINER,DATA> fAttribute;
	private final JeeslAttributeBean<L,D,CATEGORY,CRITERIA,TYPE,OPTION,SET,ITEM,CONTAINER,DATA> bAttribute;
	private final FacesMessageBean bMessage;
	private final AttributeBean<CONTAINER> bean;
	
	private final IoAttributeFactoryBuilder<L,D,CATEGORY,CRITERIA,TYPE,OPTION,SET,ITEM,CONTAINER,DATA> fbAttribute;
	private final EjbAttributeContainerFactory<SET,CONTAINER> efContainer;
	private final EjbAttributeDataFactory<CRITERIA,OPTION,CONTAINER,DATA> efData;
	
	private final Map<CRITERIA,DATA> data; public Map<CRITERIA, DATA> getData() {return data;}
	private SET attributeSet; public SET getAttributeSet() {return attributeSet;}
	private CONTAINER container;
	
	
	public AttributeHandler(FacesMessageBean bMessage,
			final JeeslIoAttributeFacade<L,D,CATEGORY,CRITERIA,TYPE,OPTION,SET,ITEM,CONTAINER,DATA> fAttribute,
			final JeeslAttributeBean<L,D,CATEGORY,CRITERIA,TYPE,OPTION,SET,ITEM,CONTAINER,DATA> bAttribute,
			final IoAttributeFactoryBuilder<L,D,CATEGORY,CRITERIA,TYPE,OPTION,SET,ITEM,CONTAINER,DATA> fbAttribute,
			final AttributeBean bean)
	{
		this.bMessage=bMessage;
		this.fAttribute=fAttribute;
		this.bAttribute=bAttribute;
		this.fbAttribute=fbAttribute;
		this.bean=bean;
		
		efContainer = fbAttribute.ejbContainer();
		efData = fbAttribute.ejbData();
		data = new HashMap<CRITERIA,DATA>();
	}
	
	public <E extends Enum<E>> void init(E code)
	{
		try {attributeSet = fAttribute.fByCode(fbAttribute.getClassSet(), code);}
		catch (UtilsNotFoundException e) {e.printStackTrace();}
	}
	
	public void reloadData()
	{
		data.clear();
		for(DATA d : fAttribute.fAttributeData(container))
		{
			data.put(d.getCriteria(),d);
		}
	}
	
	public <W extends JeeslWithAttributeContainer<CONTAINER>> void prepare(W ejb)
	{
		data.clear();
		if(ejb.getAttributeContainer()==null)
		{
			container = efContainer.build(attributeSet);
		}
		else
		{
			container = ejb.getAttributeContainer();
		}
		
		if(EjbIdFactory.isSaved(container))
		{
			reloadData();
		}
		if(bAttribute.getMapCriteria().containsKey(attributeSet))
		{
			for(CRITERIA c : bAttribute.getMapCriteria().get(attributeSet))
			{
				if(!data.containsKey(c))
				{
					data.put(c, efData.build(container, c));
				}
			}
		}
		if(debugOnInfo)
		{
			logger.info(this.getClass().getName()+" prepared for "+attributeSet.getCode()+" with "+data.size()+" "+fbAttribute.getClassData());
		}
	}
	
	public void save() throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(debugOnInfo)
		{
			logger.info(this.getClass().getName()+" saveData");
		}
		
		if(bean!=null) {bean.save(this);}
	}
	
	public CONTAINER saveContainer() throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(EjbIdFactory.isUnSaved(container))
		{
			container = fAttribute.save(container);
		}
		for(DATA d : data.values())
		{
			d.setContainer(container);
			fAttribute.save(d);
		}
		reloadData();
		return container;
	}

	
//	public void save(CORRELATION correlation, SECTION section) throws UtilsConstraintViolationException, UtilsLockingException
	{
		
	}
}