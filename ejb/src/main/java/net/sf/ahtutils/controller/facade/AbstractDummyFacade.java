package net.sf.ahtutils.controller.facade;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphic;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphicFigure;
import org.jeesl.interfaces.model.system.with.code.EjbWithCode;
import org.jeesl.interfaces.model.system.with.code.EjbWithNrString;
import org.jeesl.interfaces.model.util.date.EjbWithTimeline;
import org.jeesl.interfaces.model.util.date.EjbWithValidFrom;
import org.jeesl.interfaces.model.util.date.EjbWithValidFromUntil;
import org.jeesl.interfaces.model.util.date.EjbWithYear;
import org.jeesl.interfaces.model.with.EjbWithValidFromAndParent;
import org.jeesl.interfaces.model.with.parent.JeeslWithParentAttributeStatus;
import org.jeesl.interfaces.model.with.parent.JeeslWithParentAttributeType;
import org.jeesl.interfaces.model.with.status.JeeslWithCategory;
import org.jeesl.interfaces.model.with.status.JeeslWithContext;
import org.jeesl.interfaces.model.with.status.JeeslWithStatus;
import org.jeesl.interfaces.model.with.status.JeeslWithType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.controller.util.ParentPredicate;
import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.behaviour.EjbEquals;
import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbMergeable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.EjbWithEmail;
import net.sf.ahtutils.interfaces.model.with.EjbWithNr;
import net.sf.ahtutils.interfaces.model.with.code.EjbWithNonUniqueCode;
import net.sf.ahtutils.interfaces.model.with.code.EjbWithType;
import net.sf.ahtutils.interfaces.model.with.code.EjbWithTypeCode;
import net.sf.ahtutils.interfaces.model.with.parent.EjbWithParentAttributeResolver;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPosition;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionParent;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionType;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionTypeVisible;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionVisible;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionVisibleParent;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithName;
import net.sf.ahtutils.model.interfaces.with.EjbWithRecord;

public class AbstractDummyFacade implements UtilsFacade
{
	final static Logger logger = LoggerFactory.getLogger(AbstractDummyFacade.class);

	@Override
	public <T> T find(Class<T> type, long id) throws UtilsNotFoundException {
		
		return null;
	}
	
	@Override
	public <T extends EjbWithId> T find(Class<T> type, T t) {
		 
		return null;
	}

	@Override
	public <T extends EjbWithCode> T fByCode(Class<T> type, String code)
			throws UtilsNotFoundException {
		
		return null;
	}

	@Override
	public <T extends EjbWithName> T fByName(Class<T> type, String name)
			throws UtilsNotFoundException {
		
		return null;
	}

	@Override
	public <T extends EjbWithNr, P extends EjbWithId> T fByNr(Class<T> type,
			String parentName, P parent, long nr) throws UtilsNotFoundException {
		
		return null;
	}

	@Override
	public <T> List<T> all(Class<T> type) {
		
		return null;
	}

	@Override
	public <T extends EjbWithPosition> List<T> allOrderedPosition(Class<T> type) {
		
		return null;
	}

	@Override
	public <T extends EjbWithType> List<T> allForType(Class<T> clazz,
			String type) {
		
		return null;
	}

	@Override
	public <T extends EjbSaveable> T save(T o)
			throws UtilsConstraintViolationException, UtilsLockingException {
		
		return null;
	}

	@Override
	public <T> T persist(T o) throws UtilsConstraintViolationException {
		
		return null;
	}

	@Override
	public <T> T update(T o) throws UtilsConstraintViolationException,
			UtilsLockingException {
		
		return null;
	}

	@Override
	public <T extends EjbRemoveable> void rm(T o)
			throws UtilsConstraintViolationException {
		
		
	}

	@Override
	public <T extends EjbWithId, I extends EjbWithId> List<T> allForParent(
			Class<T> type, String p1Name, I p1) {
		
		return null;
	}

	@Override
	public <T extends EjbWithId, I extends EjbWithId> List<T> allForParent(
			Class<T> type, String p1Name, I p1, String p2Name, I p2) {
		
		return null;
	}

	@Override
	public <T extends EjbWithId, OR extends EjbWithId, AND extends EjbWithId> List<T> fForAndOrParents(
			Class<T> queryClass, List<ParentPredicate<AND>> lpAnd,
			List<ParentPredicate<OR>> lpOr) {
		
		return null;
	}

	@Override
	public <T extends EjbWithId, P extends EjbWithId, OR extends EjbWithId, AND extends EjbWithId> List<T> fForAndOrGrandParents(
			Class<T> queryClass, Class<P> parentClass, String parentName,
			List<ParentPredicate<AND>> lpAnd, List<ParentPredicate<OR>> lpOr) {
		
		return null;
	}

	@Override
	public <T extends EjbWithId, P extends EjbWithId, OR1 extends EjbWithId, OR2 extends EjbWithId> List<T> fGrandParents(
			Class<T> queryClass, Class<P> parentClass, String parentName,
			List<ParentPredicate<OR1>> lpOr1, List<ParentPredicate<OR2>> lpOr2) {
		
		return null;
	}


	@Override
	public <T extends EjbWithRecord> List<T> allOrderedRecord(Class<T> type,
			boolean asc) {
		
		return null;
	}

	@Override
	public <T extends EjbWithRecord, AND extends EjbWithId, OR extends EjbWithId> List<T> allOrderedForParents(
			Class<T> queryClass, List<ParentPredicate<AND>> lpAnd,
			List<ParentPredicate<OR>> lpOr, boolean ascending) {
		
		return null;
	}

	@Override
	public <T> List<T> allOrdered(Class<T> cl, String by, boolean ascending) {
		
		return null;
	}

	@Override
	public <T extends EjbWithPositionVisible> List<T> allOrderedPositionVisible(
			Class<T> type) {
		
		return null;
	}

	@Override
	public <T extends EjbWithId, I extends EjbWithId> T oneForParent(
			Class<T> type, String p1Name, I p1) {
		
		return null;
	}

	@Override
	public <T, I extends EjbWithId> List<T> allOrderedParent(Class<T> cl,
			String by, boolean ascending, String p1Name, I p1) {
		
		return null;
	}

	@Override
	public <T extends EjbWithRecord, I extends EjbWithId> List<T> allOrderedParentRecordBetween(
			Class<T> cl, boolean ascending, String p1Name, I p1,
			Date from, Date to) {
		
		return null;
	}

	@Override
	public <T extends EjbWithId, P extends EjbWithId> T oneForParents(
			Class<T> cl, List<ParentPredicate<P>> parents)
			throws UtilsNotFoundException {
		
		return null;
	}

	@Override
	public <T extends EjbWithId, I extends EjbWithId> T oneForParents(
			Class<T> cl, String p1Name, I p1, String p2Name, I p2)
			throws UtilsNotFoundException {
		
		return null;
	}

	@Override
	public <T extends EjbWithRecord> List<T> inInterval(Class<T> clRecord,
			Date from, Date to) {
		
		return null;
	}
	
	@Override
	public <T extends EjbWithTimeline> List<T>
		between(Class<T> clTracker, Date from, Date to) {
		
		return null;
	}

	@Override
	public <T extends EjbWithTimeline, AND extends EjbWithId, OR extends EjbWithId> List<T>
		between(Class<T> clTimeline, Date from, Date to,List<ParentPredicate<AND>> lpAnd, List<ParentPredicate<OR>> lpOr) {
		
		return null;
	}

	@Override
	public <T extends EjbWithRecord> T fFirst(Class<T> clRecord) {
		
		return null;
	}

	@Override
	public <T extends EjbWithRecord> T fLast(Class<T> clRecord) {
		
		return null;
	}

	@Override
	public <T extends EjbWithYear, P extends EjbWithId> T fByYear(
			Class<T> type, String p1Name, P p, int year) {
		
		return null;
	}

	@Override
	public <T extends EjbWithId, I extends EjbWithId> T oneForParents(
			Class<T> cl, String p1Name, I p1, String p2Name, I p2,
			String p3Name, I p3) throws UtilsNotFoundException {
		
		return null;
	}

	@Override
	public <T extends EjbWithId, P extends EjbWithId> List<T> allForOrParents(
			Class<T> cl, List<ParentPredicate<P>> parents) {
		
		return null;
	}

	@Override
	public <T extends EjbWithEmail> T fByEmail(Class<T> clazz, String email)
			throws UtilsNotFoundException {
		
		return null;
	}

	@Override public <T extends EjbWithPositionVisibleParent, P extends EjbWithId> List<T> allOrderedPositionVisibleParent(Class<T> cl, P parent) {return null;}
	@Override public <T extends EjbWithNonUniqueCode> List<T> allByCode(Class<T> type, String code){return null;}
	@Override public <T extends EjbWithId, P extends EjbWithId, GP extends EjbWithId> List<T> allForGrandParent(Class<T> queryClass, Class<P> pClass, String pName, GP grandParent, String gpName){return null;}
	@Override public <T extends EjbWithValidFrom> T fFirstValidFrom(Class<T> type, String parentName, long id, Date validFrom) throws UtilsNotFoundException{return null;}

	@Override
	public <T extends EjbWithValidFrom> List<T> allOrderedValidFrom(Class<T> cl, boolean ascending)
	{
		
		return null;
	}

	@Override
	public <T extends EjbWithCode> List<T> allOrderedCode(Class<T> cl)
	{
		
		return null;
	}

	@Override
	public <T extends EjbSaveable> T saveTransaction(T o) throws UtilsConstraintViolationException, UtilsLockingException
	{
		
		return null;
	}

	@Override
	public <T extends EjbWithTypeCode> T fByTypeCode(Class<T> c, String tpye, String code) throws UtilsNotFoundException
	{
		
		return null;
	}

	@Override
	public <E extends EjbEquals<T>, T extends EjbWithId> boolean equalsAttributes(Class<T> c,E object)
	{
		
		return false;
	}

	@Override
	public <T extends EjbMergeable> T mergeTransaction(T o) throws UtilsConstraintViolationException, UtilsLockingException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends EjbMergeable> T merge(T o) throws UtilsConstraintViolationException, UtilsLockingException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends EjbWithId, OR1 extends EjbWithId, OR2 extends EjbWithId> List<T> allForOrOrParents(Class<T> cl, List<ParentPredicate<OR1>> or1, List<ParentPredicate<OR2>> or2)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends EjbWithId> List<T> find(Class<T> type, List<Long> ids)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends EjbWithId> List<T> find(Class<T> type, Set<Long> ids)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends EjbSaveable> void save(List<T> list)
			throws UtilsConstraintViolationException, UtilsLockingException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T extends EjbSaveable> void saveTransaction(List<T> list)
			throws UtilsConstraintViolationException, UtilsLockingException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T extends EjbWithCode, E extends Enum<E>> T fByCode(Class<T> type, E code) throws UtilsNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends EjbWithPositionType, E extends Enum<E>> List<T> allOrderedPosition(Class<T> type, E enu) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends EjbWithPositionTypeVisible, E extends Enum<E>> List<T> allOrderedPositionVisible(Class<T> type,
			E enu) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> List<T> all(Class<T> type, int limit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override public <T extends EjbWithPositionParent, P extends EjbWithId> List<T> allOrderedPositionParent(Class<T> cl, P parent) {return null;}
	@Override public <T extends EjbRemoveable> void rmTransaction(T o) throws UtilsConstraintViolationException {}

	@Override
	public <T extends EjbWithRecord, P extends EjbWithId> List<T> allOrderedParentsRecordBetween(Class<T> cl,
			boolean ascending, String p1Name, List<P> parents, Date from, Date to) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override public <T extends EjbRemoveable> void rm(List<T> list) throws UtilsConstraintViolationException {}

	@Override
	public <T extends EjbWithId, I extends EjbWithId> List<T> allForParent(Class<T> type, String p1Name, I p1,
			int maxResults) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends EjbWithValidFromUntil> T oneInRange(Class<T> c, Date record) throws UtilsNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends EjbWithParentAttributeResolver, I extends EjbWithId> List<T> allForParent(Class<T> type, I p1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends EjbWithParentAttributeResolver, I extends EjbWithId> T oneForParent(Class<T> cl, I p1)
			throws UtilsNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends EjbRemoveable> void rm(Set<T> set) throws UtilsConstraintViolationException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public <T extends EjbWithName> List<T> allOrderedName(Class<T> cl) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends EjbRemoveable> void rmTransaction(List<T> list) throws UtilsConstraintViolationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T extends EjbWithParentAttributeResolver, I extends EjbWithId> List<T> allForParents(Class<T> type,
			List<I> parents) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <L extends UtilsLang, D extends UtilsDescription, S extends EjbWithId, G extends JeeslGraphic<L,D,GT,F,FS>, GT extends UtilsStatus<GT, L, D>, F extends JeeslGraphicFigure<L,D,G,GT,F,FS>, FS extends UtilsStatus<FS, L, D>> S loadGraphic(
			Class<S> cS, S status) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <L extends UtilsLang, D extends UtilsDescription, C extends UtilsStatus<C, L, D>, W extends JeeslWithCategory<C>> List<W> allForCategory(Class<W> w, C c) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends EjbWithNrString> T fByNr(Class<T> type, String nr) throws UtilsNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <L extends UtilsLang, D extends UtilsDescription, S extends UtilsStatus<S, L, D>, W extends JeeslWithStatus<S>> List<W> allForStatus(
			Class<W> w, S status) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <L extends UtilsLang, D extends UtilsDescription, T extends UtilsStatus<T,L,D>, W extends JeeslWithType<T>> List<W> allForType(
			Class<W> w, T type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends EjbWithId> long maxId(Class<T> c) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <T extends EjbWithValidFromAndParent, P extends EjbWithId> T fFirstValidFrom(Class<T> c, P parent,
			Date validFrom) throws UtilsNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends JeeslWithParentAttributeStatus<STATUS>, P extends EjbWithId, STATUS extends UtilsStatus<STATUS, ?, ?>> List<T> allForParentStatus(
			Class<T> type, P parent, List<STATUS> status) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends JeeslWithParentAttributeType<TYPE>, P extends EjbWithId, TYPE extends UtilsStatus<TYPE, ?, ?>> List<T> allForParentType(
			Class<T> type, P parent, List<TYPE> type2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override public <C extends UtilsStatus<C,?,?>, W extends JeeslWithContext<C>> List<W> allForContext(Class<W> w, C category) {return null;}

}