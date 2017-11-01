package org.jeesl.web.mbean.prototype.system.io.attribute;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jeesl.api.bean.JeeslAttributeBean;
import org.jeesl.api.facade.io.JeeslIoAttributeFacade;
import org.jeesl.factory.builder.io.IoAttributeFactoryBuilder;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeContainer;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeCriteria;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeData;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeItem;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeOption;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public abstract class AbstractAppAttributeBean <L extends UtilsLang, D extends UtilsDescription,
											CATEGORY extends UtilsStatus<CATEGORY,L,D>,
											CRITERIA extends JeeslAttributeCriteria<L,D,CATEGORY,TYPE>,
											TYPE extends UtilsStatus<TYPE,L,D>,
											OPTION extends JeeslAttributeOption<L,D,CRITERIA>,
											SET extends JeeslAttributeSet<L,D,CATEGORY,ITEM>,
											ITEM extends JeeslAttributeItem<CRITERIA,SET>,
											CONTAINER extends JeeslAttributeContainer<SET,DATA>,
											DATA extends JeeslAttributeData<CRITERIA,CONTAINER>>
					implements Serializable,
								JeeslAttributeBean<L,D,CATEGORY,CRITERIA,TYPE,OPTION,SET,ITEM,CONTAINER,DATA>
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAppAttributeBean.class);

	private JeeslIoAttributeFacade<L,D,CATEGORY,CRITERIA,TYPE,OPTION,SET,ITEM,CONTAINER,DATA> fAttribute;
	private final IoAttributeFactoryBuilder<L,D,CATEGORY,CRITERIA,TYPE,OPTION,SET,ITEM,CONTAINER,DATA> fbAttribute;

	public AbstractAppAttributeBean(IoAttributeFactoryBuilder<L,D,CATEGORY,CRITERIA,TYPE,OPTION,SET,ITEM,CONTAINER,DATA> fbAttribute)
	{
		this.fbAttribute=fbAttribute;
		categories = new ArrayList<CATEGORY>();
		types = new ArrayList<TYPE>();
		mapCriteria = new HashMap<SET,List<CRITERIA>>();
	}
	
	public void initSuper(JeeslIoAttributeFacade<L,D,CATEGORY,CRITERIA,TYPE,OPTION,SET,ITEM,CONTAINER,DATA> fAttribute)
	{
		this.fAttribute=fAttribute;
		
		reloadCategories();
		reloadTypes();
		reloadCategories();
		reloadCriteria();
		
	}
	
	private final List<CATEGORY> categories;
	@Override public List<CATEGORY> getCategories(){return categories;}
	@Override public void reloadCategories() {categories.clear();categories.addAll(fAttribute.allOrderedPositionVisible(fbAttribute.getClassCategory()));}
	
	private final List<TYPE> types;
	@Override public List<TYPE> getTypes(){return types;}
	@Override public void reloadTypes()
	{
		types.clear();
		for(TYPE type : fAttribute.allOrderedPositionVisible(fbAttribute.getClassType()))
		{
			boolean add=false;
			for(JeeslAttributeCriteria.Types t : JeeslAttributeCriteria.Types.values())
			{
				if(type.getCode().equals(t.toString())) {add=true;}
			}
			if(add) {types.add(type);}
		}
	}
	
	private final Map<SET,List<CRITERIA>> mapCriteria;
	@Override  public Map<SET, List<CRITERIA>> getMapCriteria() {return mapCriteria;}
	private void reloadCriteria()
	{
		mapCriteria.clear();
		for(SET s : fAttribute.all(fbAttribute.getClassSet()))
		{
			List<CRITERIA> list = new ArrayList<CRITERIA>();
			
			for(ITEM item : fAttribute.allOrderedPositionVisibleParent(fbAttribute.getClassItem(),s))
			{
				list.add(item.getCriteria());
			}
			mapCriteria.put(s, list);
		}
	}
		
	protected String statistics()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("Statistics");
		for(SET s : mapCriteria.keySet())
		{
			sb.append(s.getCode()+" "+mapCriteria.get(s).size());
		}
		return sb.toString();
	}
	
	
}