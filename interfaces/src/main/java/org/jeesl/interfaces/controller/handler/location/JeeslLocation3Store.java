package org.jeesl.interfaces.controller.handler.location;

import org.jeesl.interfaces.controller.handler.tree.JeeslTree2Store;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslLocation3Store<L1 extends EjbWithId, L2 extends EjbWithId, L3 extends EjbWithId> extends JeeslTree2Store<L1,L2>
{
	void setL3(L3 l3);
}