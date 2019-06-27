package org.jeesl.api.facade.system;

import java.util.Date;

import org.jeesl.interfaces.model.system.property.JeeslProperty;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public interface JeeslSystemPropertyFacade <L extends UtilsLang,D extends UtilsDescription,
											C extends UtilsStatus<C,L,D>,
											P extends JeeslProperty<L,D,C,P>>
			extends UtilsFacade
{	
	String valueStringForKey(String key, String defaultValue) throws UtilsNotFoundException;
	Integer valueIntForKey(String key, Integer defaultValue) throws UtilsNotFoundException;
	Long valueLongForKey(String key, Long defaultValue) throws UtilsNotFoundException;
	Boolean valueBooleanForKey(String key, Boolean defaultValue) throws UtilsNotFoundException;
	Date valueDateForKey(String key, Date defaultValue) throws UtilsNotFoundException;
}