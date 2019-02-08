package org.jeesl.api.facade.io;

import java.util.List;

import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntity;
import org.jeesl.interfaces.model.system.io.ssi.JeeslIoSsiData;
import org.jeesl.interfaces.model.system.io.ssi.JeeslIoSsiMapping;
import org.jeesl.interfaces.model.system.io.ssi.JeeslIoSsiSystem;
import org.jeesl.model.json.db.tuple.t1.Json1Tuples;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslIoSsiFacade <L extends UtilsLang,D extends UtilsDescription,
									SYSTEM extends JeeslIoSsiSystem,
									MAPPING extends JeeslIoSsiMapping<SYSTEM,ENTITY>,
									DATA extends JeeslIoSsiData<MAPPING,LINK>,
									LINK extends UtilsStatus<LINK,L,D>,
									ENTITY extends JeeslRevisionEntity<?,?,?,?,?>
									>
			extends UtilsFacade
{	
	MAPPING fMapping(Class<?> json, Class<?> ejb) throws UtilsNotFoundException;
	DATA fIoSsiData(MAPPING mapping, String code) throws UtilsNotFoundException;
	<T extends EjbWithId> DATA fIoSsiData(MAPPING mapping, T ejb) throws UtilsNotFoundException;
	List<DATA> fIoSsiData(MAPPING mapping, List<LINK> links);
	<A extends EjbWithId, B extends EjbWithId> List<DATA> fIoSsiData(MAPPING mapping, List<LINK> links, A a, B b);
	
	Json1Tuples<LINK> tpIoSsiLinkForMapping(MAPPING mapping);
	<A extends EjbWithId, B extends EjbWithId> Json1Tuples<LINK> tpIoSsiLinkForMapping(MAPPING mapping, A a, B b) ;
}