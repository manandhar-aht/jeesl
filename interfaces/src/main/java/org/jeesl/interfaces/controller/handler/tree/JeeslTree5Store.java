package org.jeesl.interfaces.controller.handler.tree;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslTree5Store<L1 extends EjbWithId, L2 extends EjbWithId, L3 extends EjbWithId, L4 extends EjbWithId, L5 extends EjbWithId> extends JeeslTree4Store<L1,L2,L3,L4>
{
	void storeTreeLevel5(L5 l5);
}