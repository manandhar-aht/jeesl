package net.sf.ahtutils.model.interfaces.with;

import java.util.Map;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;

public interface EjbWithDescription<D extends UtilsDescription>
{	
	public Map<String,D> getDescription();
	public void setDescription(Map<String,D> description);
}