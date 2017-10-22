package org.jeesl.api.facade.io;

import org.jeesl.interfaces.model.module.attribute.JeeslAttributeContainer;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeCriteria;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeData;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeItem;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeSet;

import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public interface JeeslIoAttributeFacade <L extends UtilsLang, D extends UtilsDescription,
										CATEGORY extends UtilsStatus<CATEGORY,L,D>,
										CRITERIA extends JeeslAttributeCriteria<L,D,CATEGORY,TYPE>,
										TYPE extends UtilsStatus<TYPE,L,D>,
										SET extends JeeslAttributeSet<L,D,CATEGORY,ITEM>,
										ITEM extends JeeslAttributeItem<CRITERIA,SET>,
										CONTAINER extends JeeslAttributeContainer<SET,DATA>,
										DATA extends JeeslAttributeData<CRITERIA,CONTAINER>>
			extends UtilsFacade
{	
	
}