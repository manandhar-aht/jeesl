package net.sf.ahtutils.interfaces.model.issue;

import java.util.List;

import org.jeesl.interfaces.model.system.with.code.EjbWithCode;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithName;

public interface UtilsTask<T extends UtilsTask<T>> extends EjbWithId,EjbWithCode,EjbWithName
{	
	T getParent();
	void setParent(T parentTask);
	
	List<T> getChilds();
	void setChilds(List<T> childs);
}
