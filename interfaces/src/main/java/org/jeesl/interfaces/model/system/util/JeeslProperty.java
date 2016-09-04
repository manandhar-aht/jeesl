package org.jeesl.interfaces.model.system.util;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslProperty <L extends UtilsLang, D extends UtilsDescription>
		extends EjbSaveable,EjbWithLang<L>,EjbWithDescription<D>
{
	public String getKey();
	public void setKey(String key);
	
	public String getValue();
	public void setValue(String value);
	
	public boolean isFrozen();
	public void setFrozen(boolean frozen);
	
	public Integer getPosition();
	public void setPosition(Integer position);
	
	public Boolean getDocumentation();
	public void setDocumentation(Boolean documentation);
}