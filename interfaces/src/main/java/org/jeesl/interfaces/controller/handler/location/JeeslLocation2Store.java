package org.jeesl.interfaces.controller.handler.location;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslLocation2Store<L1 extends EjbWithId, L2 extends EjbWithId> extends JeeslLocation1Store<L1>
{
	void setL2(L2 l2);
}