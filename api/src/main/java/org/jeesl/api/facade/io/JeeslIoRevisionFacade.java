package org.jeesl.api.facade.io;

import java.util.Date;
import java.util.List;

import org.jeesl.interfaces.model.system.io.revision.EjbWithRevisionAttributes;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionAttribute;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntity;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntityMapping;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionScope;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionView;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionViewMapping;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.json.system.revision.JsonRevision;

public interface JeeslIoRevisionFacade <L extends UtilsLang,D extends UtilsDescription,
									RC extends UtilsStatus<RC,L,D>,
									RV extends JeeslRevisionView<L,D,RVM>,
									RVM extends JeeslRevisionViewMapping<RV,RE,REM>,
									RS extends JeeslRevisionScope<L,D,RC,RA>,
									RST extends UtilsStatus<RST,L,D>,
									RE extends JeeslRevisionEntity<L,D,RC,REM,RA>,
									REM extends JeeslRevisionEntityMapping<RS,RST,RE>,
									RA extends JeeslRevisionAttribute<L,D,RE,RAT>,
									RAT extends UtilsStatus<RAT,L,D>>
			extends UtilsFacade
{	
	public static int typeCreate = 0;
	
	public static enum Scope{live,revision}
	
	RV load(Class<RV> cView, RV view);
	RS load(Class<RS> cScope, RS scope);
	RE load(Class<RE> cEntity, RE entity);
	
	List<RS> findScopes(Class<RS> cScope, Class<RC> cCategory, List<RC> categories, boolean showInvisibleScopes);
	List<RE> findEntities(Class<RE> cEntity, Class<RC> cCategory, List<RC> categories, boolean showInvisibleEntities);
	
	void rm(Class<RVM> cMappingView, RVM mapping) throws UtilsConstraintViolationException;
	
	<W extends EjbWithRevisionAttributes<RA>> RA save(Class<W> cW, W entity, RA attribute) throws UtilsLockingException, UtilsConstraintViolationException;
	<W extends EjbWithRevisionAttributes<RA>> void rm(Class<W> cW, W entity, RA attribute) throws UtilsLockingException, UtilsConstraintViolationException;
	
	<T extends EjbWithId> T jpaTree(Class<T> c, String jpa, long id) throws UtilsNotFoundException;
	
	<T extends EjbWithId> List<T> revisions(Class<T> c, List<Long> ids);
	
	<T extends EjbWithId> List<Long> ids(Class<T> c, JeeslIoRevisionFacade.Scope scope);
	<T extends EjbWithId> List<JsonRevision> findCreated(Class<T> c, Date from, Date to);
	
}