package net.sf.ahtutils.interfaces.facade;

import java.util.List;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionAttribute;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionEntity;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionEntityMapping;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionScope;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionView;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionViewMapping;

public interface UtilsRevisionFacade <L extends UtilsLang,D extends UtilsDescription,
									RC extends UtilsStatus<RC,L,D>,
									RV extends UtilsRevisionView<L,D,RC,RV,RVM,RS,RE,REM,RA,RAT>,
									RVM extends UtilsRevisionViewMapping<L,D,RC,RV,RVM,RS,RE,REM,RA,RAT>,
									RS extends UtilsRevisionScope<L,D,RC,RV,RVM,RS,RE,REM,RA,RAT>,
									RE extends UtilsRevisionEntity<L,D,RC,RV,RVM,RS,RE,REM,RA,RAT>,
									REM extends UtilsRevisionEntityMapping<L,D,RC,RV,RVM,RS,RE,REM,RA,RAT>,
									RA extends UtilsRevisionAttribute<L,D,RC,RV,RVM,RS,RE,REM,RA,RAT>,
									RAT extends UtilsStatus<RAT,L,D>>
			extends UtilsFacade
{	
	RV load(Class<RV> cView, RV view);
	RS load(Class<RS> cScope, RS scope);
	RE load(Class<RE> cEntity, RE entity);
	
	List<RS> findScopes(Class<RS> cScope, Class<RC> cCategory, List<RC> categories, boolean showInvisibleScopes);
	List<RE> findEntities(Class<RE> cEntity, Class<RC> cCategory, List<RC> categories, boolean showInvisibleEntities);
	
	void rm(Class<RVM> cMappingView, RVM mapping) throws UtilsConstraintViolationException;
	
	RA save(Class<RE> cEntity, RE entity, RA attribute) throws UtilsLockingException, UtilsConstraintViolationException;
	RA save(Class<RS> cScope, RS scope, RA attribute) throws UtilsLockingException, UtilsConstraintViolationException;
	void rm(Class<RE> cEntity, RE entity, RA attribute) throws UtilsLockingException, UtilsConstraintViolationException;
	void rm(Class<RS> cScope, RS scop, RA attribute) throws UtilsLockingException, UtilsConstraintViolationException;
}