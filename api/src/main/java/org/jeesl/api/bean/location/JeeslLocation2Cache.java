package org.jeesl.api.bean.location;

import java.util.List;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslLocation2Cache<L1 extends EjbWithId, L2 extends EjbWithId>
{
	List<L2> cacheL2(L1 ejb);
}