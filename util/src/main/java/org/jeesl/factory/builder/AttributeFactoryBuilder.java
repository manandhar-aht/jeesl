package org.jeesl.factory.builder;

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

public class AttributeFactoryBuilder<L extends UtilsLang, D extends UtilsDescription,
									CATEGORY extends UtilsStatus<CATEGORY,L,D>,
									CRITERIA extends JeeslAttributeCriteria<L,D,CATEGORY,TYPE>,
									TYPE extends UtilsStatus<TYPE,L,D>,
									SET extends JeeslAttributeSet<L,D,CATEGORY,ITEM>,
									ITEM extends JeeslAttributeItem<SET,CRITERIA>,
									CONTAINER extends JeeslAttributeContainer<SET,DATA>,
									DATA extends JeeslAttributeData<CRITERIA,CONTAINER>>
				extends AbstractFactoryBuilder<L,D>
{
	final static Logger logger = LoggerFactory.getLogger(AttributeFactoryBuilder.class);
	

	private final Class<CATEGORY> cCat; public Class<CATEGORY> getClassCat() {return cCat;}
    
	public AttributeFactoryBuilder(final Class<L> cL, final Class<D> cD, final Class<CATEGORY> cCat)
	{
		super(cL,cD);
		this.cCat=cCat;
	}
	
	public static <L extends UtilsLang, D extends UtilsDescription,
					CATEGORY extends UtilsStatus<CATEGORY,L,D>,
					CRITERIA extends JeeslAttributeCriteria<L,D,CATEGORY,TYPE>,
					TYPE extends UtilsStatus<TYPE,L,D>,
					SET extends JeeslAttributeSet<L,D,CATEGORY,ITEM>,
					ITEM extends JeeslAttributeItem<SET,CRITERIA>,
					CONTAINER extends JeeslAttributeContainer<SET,DATA>,
					DATA extends JeeslAttributeData<CRITERIA,CONTAINER>>
	AttributeFactoryBuilder<L,D,CATEGORY,CRITERIA,TYPE,SET,ITEM,CONTAINER,DATA> factory(final Class<L> cL, final Class<D> cD, final Class<CATEGORY> cCat)
	{
		return new AttributeFactoryBuilder<L,D,CATEGORY,CRITERIA,TYPE,SET,ITEM,CONTAINER,DATA>(cL,cD,cCat);
	}
}