package org.jeesl.interfaces.controller.handler.location;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslLocation4Store<L1 extends EjbWithId, L2 extends EjbWithId, L3 extends EjbWithId, L4 extends EjbWithId> extends JeeslLocation3Store<L1,L2,L3>
{
	void setL4(L4 l4);
}