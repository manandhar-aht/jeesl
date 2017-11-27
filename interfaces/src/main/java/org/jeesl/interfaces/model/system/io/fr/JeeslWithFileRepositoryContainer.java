package org.jeesl.interfaces.model.system.io.fr;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslWithFileRepositoryContainer <CONTAINER extends JeeslFileContainer<?,?>>
		extends EjbWithId
{
	CONTAINER getFrContainer();
	void setFrContainer(CONTAINER frContainer);
}