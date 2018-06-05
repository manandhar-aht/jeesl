package org.jeesl.controller.handler;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jeesl.api.bean.JeeslAttributeBean;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
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

	private final boolean debugOnInfo = true;
	private boolean showDescription; public boolean isShowDescription() {return showDescription;}
	
	private final JeeslIoAttributeFacade<L,D,CATEGORY,CRITERIA,TYPE,OPTION,SET,ITEM,CONTAINER,DATA> fAttribute;
	private final JeeslAttributeBean<L,D,CATEGORY,CRITERIA,TYPE,OPTION,SET,ITEM,CONTAINER,DATA> bAttribute;
	private final AttributeBean<CONTAINER> bean;
	
	private final IoAttributeFactoryBuilder<L,D,CATEGORY,CRITERIA,TYPE,OPTION,SET,ITEM,CONTAINER,DATA> fbAttribute;
	private final EjbAttributeContainerFactory<SET,CONTAINER> efContainer;
	private final EjbAttributeDataFactory<CRITERIA,OPTION,CONTAINER,DATA> efData;
	
	private final Map<CRITERIA,DATA> data; public Map<CRITERIA,DATA> getData() {return data;}
	private final Map<CRITERIA,String[]> options; public Map<CRITERIA,String[]> getOptions() {return options;}
	private final Map<CONTAINER,Map<CRITERIA,DATA>> containers; public Map<CONTAINER, Map<CRITERIA, DATA>> getContainers() {return containers;}
	
	private SET attributeSet; public SET getAttributeSet() {return attributeSet;}
	private CONTAINER container;
	
	public AttributeHandler(JeeslFacesMessageBean bMessage,
			final JeeslIoAttributeFacade<L,D,CATEGORY,CRITERIA,TYPE,OPTION,SET,ITEM,CONTAINER,DATA> fAttribute,
			final JeeslAttributeBean<L,D,CATEGORY,CRITERIA,TYPE,OPTION,SET,ITEM,CONTAINER,DATA> bAttribute,
			final IoAttributeFactoryBuilder<L,D,CATEGORY,CRITERIA,TYPE,OPTION,SET,ITEM,CONTAINER,DATA> fbAttribute,
			final AttributeBean<CONTAINER> bean)
	{
		this.fAttribute=fAttribute;
		this.bAttribute=bAttribute;
		this.fbAttribute=fbAttribute;
		this.bean=bean;
		
		showDescription = false;
		
		efContainer = fbAttribute.ejbContainer();
		efData = fbAttribute.ejbData();
		
		data = new HashMap<CRITERIA,DATA>();
		options = new HashMap<CRITERIA,String[]>();
		containers = new HashMap<CONTAINER,Map<CRITERIA,DATA>>();
	}
	
	public void toggleDescription()
	{
		showDescription = !showDescription;
		if(debugOnInfo) {logger.info("Changed Description to "+showDescription);}
	}
	
	public <E extends Enum<E>> void init(E code)
	{
		try {init(fAttribute.fByCode(fbAttribute.getClassSet(), code));}
		catch (UtilsNotFoundException e) {e.printStackTrace();}
	}
	public  void init(SET attributeSet)
	{
		this.attributeSet = attributeSet;
		if(debugOnInfo) {logger.info("Initialized with Attribute Set: "+this.attributeSet.toString());}
	}
	
	public void reset()
	{
		data.clear();
		options.clear();
		container = null;
	}
	
	public void reloadData()
	{
		data.clear();
		options.clear();
		
		if(debugOnInfo) {logger.info(this.getClass().getSimpleName()+" loading data for container: "+container.toString());}
		for(DATA d : fAttribute.fAttributeData(container))
		{
			if(debugOnInfo)
			{
				StringBuilder sb = new StringBuilder();
				sb.append("\t ").append(d.getCriteria().getPosition()).append(" ").append(d.getCriteria().getCode());
				if(d.getCriteria().getType().getCode().equals("selectMany")) {sb.append(" selectMany:").append(d.getValueOptions().size());}
				logger.info(sb.toString());
			}
			data.put(d.getCriteria(),d);
			if(d.getValueOptions().isEmpty()) {options.put(d.getCriteria(),new String[0]);}
			else
			{
				String[] tmp = new String[d.getValueOptions().size()];
				for(int i=0;i<d.getValueOptions().size();i++)
				{
					tmp[i]=d.getValueOptions().get(i).getCode();
				}
				options.put(d.getCriteria(),tmp);
			}
		}
	}
	
	public void save() throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(debugOnInfo){logger.info(this.getClass().getName()+" saveData");}
		if(bean!=null) {bean.save(this);}
	}
	
	public <W extends JeeslWithAttributeContainer<CONTAINER>> void prepare(W ejb)
	{
		if(ejb!=null && ejb.getAttributeContainer()!=null)
		{
			container = ejb.getAttributeContainer();
			prepare();
		}
		else
		{
			container=null;
			prepare(container);
		}
	}
	
	public void prepare(CONTAINER container)
	{
		if(container!=null)
		{
			this.container=container;
		}
		else
		{
			this.container = efContainer.build(attributeSet);
		}		
		prepare();
	}
	
	private void prepare()
	{
		data.clear();
		if(EjbIdFactory.isSaved(container))
		{
			reloadData();
		}
		if(bAttribute.getMapCriteria().containsKey(attributeSet))
		{
			if(debugOnInfo) {logger.info(this.getClass().getSimpleName()+" preparing for "+attributeSet.getCode()+" with "+bAttribute.getMapCriteria().get(attributeSet).size()+" "+fbAttribute.getClassCriteria().getSimpleName());}
			for(CRITERIA c : bAttribute.getMapCriteria().get(attributeSet))
			{
				if(!data.containsKey(c))
				{
					data.put(c, efData.build(container, c));
				}
			}
		}
		else
		{
			logger.warn("The "+fbAttribute.getClassSet().getSimpleName()+" "+attributeSet.getCode()+" is not cached in "+bAttribute.getClass().getSimpleName());
		}
		if(debugOnInfo) {logger.info(this.getClass().getSimpleName()+" prepared for "+attributeSet.getCode()+" with "+data.size()+" "+fbAttribute.getClassData());}
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
			if(options.containsKey(d.getCriteria()))
			{
				
				String[] tmp = options.get(d.getCriteria());
				List<OPTION> l = new ArrayList<OPTION>();
				for(String s : tmp)
				{
					for(OPTION o : bAttribute.getMapOption().get(d.getCriteria()))
					{
						if(o.getCode().equals(s)) {l.add(o);}
					}
					logger.info("\t"+s);
				}
				if(debugOnInfo) {logger.info(this.getClass().getSimpleName()+" Saving options "+d.getCriteria().getPosition()+" "+d.getCriteria().getCode()+" options:"+l.size());}
				if(l.isEmpty()){d.setValueOptions(null);}
				else {d.setValueOptions(l);}
			}
			else {d.setValueOptions(null);}
			fAttribute.save(d);
		}
		reloadData();
		return container;
	}
	
	public <W extends JeeslWithAttributeContainer<CONTAINER>> void loadContainers(List<W> list)
	{
		containers.clear();
		if(debugOnInfo) {logger.info("Loading "+list.size()+" "+fbAttribute.getClassContainer().getSimpleName());}
		for(W w : list)
		{
			Map<CRITERIA,DATA> map = new HashMap<CRITERIA,DATA>();
			for(DATA d : fAttribute.fAttributeData(w.getAttributeContainer()))
			{
				map.put(d.getCriteria(),d);
			}
			containers.put(w.getAttributeContainer(), map);
		}
	}
}