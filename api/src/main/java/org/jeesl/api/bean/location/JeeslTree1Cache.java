package org.jeesl.api.bean.location;

import java.util.List;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslTree1Cache<L1 extends EjbWithId>
{
	List<L1> getCachedL1();
}