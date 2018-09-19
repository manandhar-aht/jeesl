package org.jeesl.interfaces.controller.handler.tree;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslTree1Store<L1 extends EjbWithId>
{
	void storeTreeLevel1(L1 l1);
}