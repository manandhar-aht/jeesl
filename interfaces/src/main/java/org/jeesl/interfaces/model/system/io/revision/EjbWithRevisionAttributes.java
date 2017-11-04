package org.jeesl.interfaces.model.system.io.revision;

import java.util.List;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface EjbWithRevisionAttributes <RA extends JeeslRevisionAttribute<?,?,?,?,?,?,?,?,?,RA,?>>
		extends EjbWithId
{
	List<RA> getAttributes();
	void setAttributes(List<RA> attributes);
}