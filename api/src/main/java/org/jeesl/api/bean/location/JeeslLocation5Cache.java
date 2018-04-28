package org.jeesl.api.bean.location;

import java.util.List;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslLocation5Cache<L1 extends EjbWithId, L2 extends EjbWithId, L3 extends EjbWithId, L4 extends EjbWithId, L5 extends EjbWithId> extends JeeslLocation4Cache<L1,L2,L3,L4>
{
	List<L5> cacheL5(L4 ejb);
}