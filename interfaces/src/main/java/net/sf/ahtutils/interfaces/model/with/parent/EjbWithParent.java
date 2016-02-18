package net.sf.ahtutils.interfaces.model.with.parent;

import net.sf.ahtutils.interfaces.model.with.code.EjbWithCode;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface EjbWithParent extends EjbWithId
{
	public <P extends EjbWithCode> P getParent();
	public <P extends EjbWithCode> void setParent(P parent);
}