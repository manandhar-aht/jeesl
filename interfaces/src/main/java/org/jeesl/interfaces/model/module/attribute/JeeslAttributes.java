package org.jeesl.interfaces.model.module.attribute;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslAttributes <L extends UtilsLang, D extends UtilsDescription,
									CATEGORY extends UtilsStatus<CATEGORY,L,D>,
									ATTRIBUTES extends JeeslAttributes<L,D,CATEGORY,ATTRIBUTES,DATA,CRITERIA,TYPE>,
									DATA extends JeeslAttributeData<L,D,CATEGORY,ATTRIBUTES,DATA,CRITERIA,TYPE>,
									CRITERIA extends JeeslAttributeCriteria<L,D,CATEGORY,ATTRIBUTES,DATA,CRITERIA,TYPE>,
									TYPE extends UtilsStatus<TYPE,L,D>>
		extends EjbWithId
{

}