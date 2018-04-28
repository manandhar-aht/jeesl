package org.jeesl.api.bean.location;

import java.util.List;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslLocation3Cache<L1 extends EjbWithId, L2 extends EjbWithId, L3 extends EjbWithId> extends JeeslLocation2Cache<L1,L2>
{
	List<L3> cacheL3(L2 ejb);
}