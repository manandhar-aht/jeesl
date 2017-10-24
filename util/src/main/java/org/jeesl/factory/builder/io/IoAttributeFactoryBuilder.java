package org.jeesl.factory.builder.io;

import org.jeesl.factory.builder.AbstractFactoryBuilder;
import org.jeesl.factory.ejb.module.attribute.EjbAttributeCriteriaFactory;
import org.jeesl.factory.ejb.system.io.attribute.EjbAttributeItemFactory;
import org.jeesl.factory.ejb.system.io.attribute.EjbAttributeSetFactory;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeContainer;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeCriteria;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeData;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeItem;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class IoAttributeFactoryBuilder<L extends UtilsLang, D extends UtilsDescription,
									CATEGORY extends UtilsStatus<CATEGORY,L,D>,
									CRITERIA extends JeeslAttributeCriteria<L,D,CATEGORY,TYPE>,
									TYPE extends UtilsStatus<TYPE,L,D>,
									SET extends JeeslAttributeSet<L,D,CATEGORY,ITEM>,
									ITEM extends JeeslAttributeItem<CRITERIA,SET>,
									CONTAINER extends JeeslAttributeContainer<SET,DATA>,
									DATA extends JeeslAttributeData<CRITERIA,CONTAINER>>
				extends AbstractFactoryBuilder<L,D>
{
	final static Logger logger = LoggerFactory.getLogger(IoAttributeFactoryBuilder.class);

	private final Class<CATEGORY> cCat; public Class<CATEGORY> getClassCategory() {return cCat;}
	private final Class<CRITERIA> cCriteria; public Class<CRITERIA> getClassCriteria() {return cCriteria;}
	private final Class<TYPE> cType; public Class<TYPE> getClassType() {return cType;}
	private final Class<SET> cSet; public Class<SET> getClassSet() {return cSet;}
	private final Class<ITEM> cItem; public Class<ITEM> getClassItem() {return cItem;}
    
	public IoAttributeFactoryBuilder(final Class<L> cL, final Class<D> cD, final Class<CATEGORY> cCat, final Class<CRITERIA> cCriteria, final Class<TYPE> cType, final Class<SET> cSet, final Class<ITEM> cItem)
	{
		super(cL,cD);
		this.cCat=cCat;
		this.cCriteria=cCriteria;
		this.cType=cType;
		this.cSet=cSet;
		this.cItem=cItem;
	}
	
	public EjbAttributeCriteriaFactory<L,D,CATEGORY,CRITERIA,TYPE> ejbCriteria()
	{
		return new EjbAttributeCriteriaFactory<L,D,CATEGORY,CRITERIA,TYPE>(cCriteria);
	}
	
	public EjbAttributeSetFactory<L,D,CATEGORY,SET,ITEM> ejbSet()
	{
		return new EjbAttributeSetFactory<L,D,CATEGORY,SET,ITEM>(cSet);
	}
	
	public EjbAttributeItemFactory<CRITERIA,SET,ITEM> ejbItem()
	{
		return new EjbAttributeItemFactory<CRITERIA,SET,ITEM>(cItem);
	}
}