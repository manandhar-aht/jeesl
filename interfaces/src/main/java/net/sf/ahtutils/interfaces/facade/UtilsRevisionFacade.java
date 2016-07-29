package net.sf.ahtutils.interfaces.facade;

import java.util.List;

import org.jeesl.interfaces.model.system.revision.EjbWithRevisionAttributes;
import org.jeesl.interfaces.model.system.revision.UtilsRevisionAttribute;
import org.jeesl.interfaces.model.system.revision.UtilsRevisionEntity;
import org.jeesl.interfaces.model.system.revision.UtilsRevisionEntityMapping;
import org.jeesl.interfaces.model.system.revision.UtilsRevisionScope;
import org.jeesl.interfaces.model.system.revision.UtilsRevisionView;
import org.jeesl.interfaces.model.system.revision.UtilsRevisionViewMapping;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface UtilsRevisionFacade <L extends UtilsLang,D extends UtilsDescription,
									RC extends UtilsStatus<RC,L,D>,
									RV extends UtilsRevisionView<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
									RVM extends UtilsRevisionViewMapping<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
									RS extends UtilsRevisionScope<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
									RST extends UtilsStatus<RST,L,D>,
									RE extends UtilsRevisionEntity<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
									REM extends UtilsRevisionEntityMapping<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
									RA extends UtilsRevisionAttribute<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
									RAT extends UtilsStatus<RAT,L,D>>
			extends UtilsFacade
{	
	RV load(Class<RV> cView, RV view);
	RS load(Class<RS> cScope, RS scope);
	RE load(Class<RE> cEntity, RE entity);
	
	List<RS> findScopes(Class<RS> cScope, Class<RC> cCategory, List<RC> categories, boolean showInvisibleScopes);
	List<RE> findEntities(Class<RE> cEntity, Class<RC> cCategory, List<RC> categories, boolean showInvisibleEntities);
	
	void rm(Class<RVM> cMappingView, RVM mapping) throws UtilsConstraintViolationException;
	
	<W extends EjbWithRevisionAttributes<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>> RA save(Class<W> cW, W entity, RA attribute) throws UtilsLockingException, UtilsConstraintViolationException;
	<W extends EjbWithRevisionAttributes<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>> void rm(Class<W> cW, W entity, RA attribute) throws UtilsLockingException, UtilsConstraintViolationException;
	
	<T extends EjbWithId> T jpaTree(Class<T> c, String jpa, long id) throws UtilsNotFoundException;
	
	<T extends EjbWithId> List<T> revisions(Class<T> c, List<Long> ids);
}