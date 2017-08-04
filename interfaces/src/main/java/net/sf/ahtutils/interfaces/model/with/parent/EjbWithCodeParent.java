package net.sf.ahtutils.interfaces.model.with.parent;

import org.jeesl.interfaces.model.system.with.code.EjbWithCode;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface EjbWithCodeParent <P extends EjbWithCode> extends EjbWithId
{
	P getParent();
	void setParent(P parent);
}