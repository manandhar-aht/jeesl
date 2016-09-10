package org.jeesl.interfaces.facade;

import java.util.Date;

import org.jeesl.interfaces.model.system.util.JeeslProperty;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public interface JeeslSystemPropertyFacade <L extends UtilsLang,D extends UtilsDescription,
											CATEGORY extends UtilsStatus<CATEGORY,L,D>,
											P extends JeeslProperty<L,D>>
			extends UtilsFacade
{	
	String valueStringForKey(String key, String defaultValue) throws UtilsNotFoundException;
	Integer valueIntForKey(String key, Integer defaultValue) throws UtilsNotFoundException;
	Long valueLongForKey(String key, Long defaultValue) throws UtilsNotFoundException;
	Boolean valueBooleanForKey(String key, Boolean defaultValue) throws UtilsNotFoundException;
	Date valueDateForKey(String key, Date defaultValue) throws UtilsNotFoundException;
}