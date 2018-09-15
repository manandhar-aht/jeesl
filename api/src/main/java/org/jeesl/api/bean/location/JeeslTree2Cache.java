package org.jeesl.api.bean.location;

import java.util.List;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslTree2Cache<L1 extends EjbWithId, L2 extends EjbWithId> extends JeeslTree1Cache<L1>
{
	List<L2> getCachedChildsForL1(L1 ejb);
}