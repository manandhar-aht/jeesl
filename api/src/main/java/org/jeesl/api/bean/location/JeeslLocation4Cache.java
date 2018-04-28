package org.jeesl.api.bean.location;

import java.util.List;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslLocation4Cache<L1 extends EjbWithId, L2 extends EjbWithId, L3 extends EjbWithId, L4 extends EjbWithId> extends JeeslLocation3Cache<L1,L2,L3>
{
	List<L4> cacheL4(L3 ejb);
}