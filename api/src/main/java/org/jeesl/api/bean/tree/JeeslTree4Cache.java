package org.jeesl.api.bean.tree;

import java.util.List;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslTree4Cache<L1 extends EjbWithId, L2 extends EjbWithId, L3 extends EjbWithId, L4 extends EjbWithId> extends JeeslTree3Cache<L1,L2,L3>
{
	List<L4> getCachedChildsForL3(L3 ejb);
}