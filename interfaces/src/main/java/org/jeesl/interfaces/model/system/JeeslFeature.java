package org.jeesl.interfaces.model.system;

import org.jeesl.interfaces.model.system.with.code.EjbWithCode;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionVisible;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslFeature
		extends EjbWithId,EjbSaveable,
					EjbWithCode,EjbWithPositionVisible
{
	String getName();
	void setName(String name);
	
	String getRemark();
	void setRemark(String remark);
}