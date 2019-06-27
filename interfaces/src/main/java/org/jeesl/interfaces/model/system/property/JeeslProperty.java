package org.jeesl.interfaces.model.system.property;

import java.io.Serializable;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslProperty <L extends UtilsLang, D extends UtilsDescription,
								C extends UtilsStatus<C,L,D>,
								P extends JeeslProperty<L,D,C,P>>
		extends Serializable,EjbSaveable,EjbWithLang<L>,EjbWithDescription<D>
{
	C getCategory();
	void setCategory(C category);
	
	String getKey();
	void setKey(String key);
	
	String getValue();
	void setValue(String value);
	
	boolean isFrozen();
	void setFrozen(boolean frozen);
	
	Integer getPosition();
	void setPosition(Integer position);
	
	Boolean getDocumentation();
	void setDocumentation(Boolean documentation);
}