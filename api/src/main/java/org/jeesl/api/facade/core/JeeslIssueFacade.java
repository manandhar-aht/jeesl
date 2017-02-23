package org.jeesl.api.facade.core;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.issue.UtilsTask;
import net.sf.ahtutils.interfaces.model.with.EjbWithTask;

public interface JeeslIssueFacade extends UtilsFacade
{	
	<T extends UtilsTask<T>, WT extends EjbWithTask<T>> T fTask(Class<T> clTask, Class<WT>  clWithTask, WT ejb) throws UtilsNotFoundException;
	<T extends UtilsTask<T>, WT extends EjbWithTask<T>> T fcTask(Class<T> clTask, Class<WT>  clWithTask, WT ejb);
	<T extends UtilsTask<T>> T load(Class<T> clTask, T task);
}