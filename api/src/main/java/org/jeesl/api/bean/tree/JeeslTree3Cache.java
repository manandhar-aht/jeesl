package org.jeesl.api.bean.tree;

import java.util.List;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslTree3Cache<L1 extends EjbWithId, L2 extends EjbWithId, L3 extends EjbWithId> extends JeeslTree2Cache<L1,L2>
{
	List<L3> getCachedChildsForL2(L2 ejb);
}