package org.jeesl.interfaces.controller.handler.location;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslLocation5Store<L1 extends EjbWithId, L2 extends EjbWithId, L3 extends EjbWithId, L4 extends EjbWithId, L5 extends EjbWithId> extends JeeslLocation4Store<L1,L2,L3,L4>
{
	void setL5(L5 l5);
}