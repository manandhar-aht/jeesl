package org.jeesl.api.facade.io;

import java.util.Collection;
import java.util.List;

import org.jeesl.interfaces.model.module.attribute.JeeslAttributeContainer;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeCriteria;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeData;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeItem;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeOption;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeSet;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public interface JeeslIoAttributeFacade <L extends UtilsLang, D extends UtilsDescription,
										CATEGORY extends UtilsStatus<CATEGORY,L,D>,
										CRITERIA extends JeeslAttributeCriteria<L,D,CATEGORY,TYPE>,
										TYPE extends UtilsStatus<TYPE,L,D>,
										OPTION extends JeeslAttributeOption<L,D,CRITERIA>,
										SET extends JeeslAttributeSet<L,D,CATEGORY,ITEM>,
										ITEM extends JeeslAttributeItem<CRITERIA,SET>,
										CONTAINER extends JeeslAttributeContainer<SET,DATA>,
										DATA extends JeeslAttributeData<CRITERIA,OPTION,CONTAINER>>
			extends UtilsFacade
{	
	List<CRITERIA> fAttributeCriteria(List<CATEGORY> categories, long refId);
	List<SET> fAttributeSets(List<CATEGORY> categories, long refId);
	List<DATA> fAttributeData(CONTAINER container);
	DATA fAttributeData(CRITERIA criteria, CONTAINER container) throws UtilsNotFoundException;
	List<DATA> fAttributeData(CRITERIA criteria, Collection<CONTAINER> containers);
	CONTAINER copy(CONTAINER container) throws UtilsConstraintViolationException, UtilsLockingException;
}