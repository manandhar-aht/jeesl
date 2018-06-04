package org.jeesl.web.mbean.prototype.system.io.attribute;

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
import org.jeesl.util.comparator.pojo.BooleanComparator;
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
											DATA extends JeeslAttributeData<CRITERIA,OPTION,CONTAINER>>
					implements JeeslAttributeBean<L,D,CATEGORY,CRITERIA,TYPE,OPTION,SET,ITEM,CONTAINER,DATA>
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
		mapTableHeader = new HashMap<SET,List<CRITERIA>>();
		mapOption = new HashMap<CRITERIA,List<OPTION>>();
	}
	
	public void initSuper(JeeslIoAttributeFacade<L,D,CATEGORY,CRITERIA,TYPE,OPTION,SET,ITEM,CONTAINER,DATA> fAttribute)
	{
		this.fAttribute=fAttribute;
		
		reloadCategories();
		reloadTypes();
		reloadCategories();
		reloadCriteria();
		reloadOptions();
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
	
	private final Map<SET,List<CRITERIA>> mapCriteria; @Override  public Map<SET,List<CRITERIA>> getMapCriteria() {return mapCriteria;}
	private final Map<SET,List<CRITERIA>> mapTableHeader; @Override  public Map<SET,List<CRITERIA>> getMapTableHeader() {return mapTableHeader;}
	private void reloadCriteria()
	{
		mapCriteria.clear();
		mapTableHeader.clear();
		
		for(SET s : fAttribute.all(fbAttribute.getClassSet()))
		{
			updateSet(s);
		}
	}
	
	public void updateSet(SET s)
	{
		List<CRITERIA> listCriteria = new ArrayList<CRITERIA>();
		List<CRITERIA> listTable = new ArrayList<CRITERIA>();
		
		for(ITEM item : fAttribute.allOrderedPositionVisibleParent(fbAttribute.getClassItem(),s))
		{
			listCriteria.add(item.getCriteria());
			if(BooleanComparator.active(item.getTableHeader())){listTable.add(item.getCriteria());}
		}
		mapCriteria.put(s, listCriteria);
		mapTableHeader.put(s, listTable);
	}
	
	/* 
	 * Updates the Hashmap where the given criteria is used
	 */
	@Override public void updateCriteria(CRITERIA criteria)
	{
		for(List<CRITERIA> list : mapCriteria.values())
		{
			int index = -1;
			for(int i=0;i<list.size();i++){if(list.get(i).equals(criteria)){index=i;break;}}
			if(index>=0){list.set(index,criteria);}
		}
		for(List<CRITERIA> list : mapTableHeader.values())
		{
			int index = -1;
			for(int i=0;i<list.size();i++){if(list.get(i).equals(criteria)){index=i;break;}}
			if(index>=0){list.set(index,criteria);}
		}
		
	}
	
	private final Map<CRITERIA,List<OPTION>> mapOption;
	@Override public Map<CRITERIA,List<OPTION>> getMapOption() {return mapOption;}
	private void reloadOptions()
	{
		mapOption.clear();
		for(OPTION o : fAttribute.allOrderedPosition(fbAttribute.getClassOption()))
		{
			if(!mapOption.containsKey(o.getCriteria())){mapOption.put(o.getCriteria(),new ArrayList<OPTION>());}
			mapOption.get(o.getCriteria()).add(o);
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