package net.sf.ahtutils.controller.facade;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

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
import net.sf.ahtutils.interfaces.model.date.EjbWithTimeline;
import net.sf.ahtutils.interfaces.model.date.EjbWithValidFrom;
import net.sf.ahtutils.interfaces.model.date.EjbWithYear;
import net.sf.ahtutils.interfaces.model.util.UtilsProperty;
import net.sf.ahtutils.interfaces.model.with.EjbWithEmail;
import net.sf.ahtutils.interfaces.model.with.EjbWithNr;
import net.sf.ahtutils.interfaces.model.with.code.EjbWithCode;
import net.sf.ahtutils.interfaces.model.with.code.EjbWithNonUniqueCode;
import net.sf.ahtutils.interfaces.model.with.code.EjbWithType;
import net.sf.ahtutils.interfaces.model.with.code.EjbWithTypeCode;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPosition;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionParent;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionType;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionTypeVisible;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionVisible;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionVisibleParent;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithName;
import net.sf.ahtutils.model.interfaces.with.EjbWithRecord;

public class AbstractUtilsFacadeBean implements UtilsFacade
{
	final static Logger logger = LoggerFactory.getLogger(AbstractUtilsFacadeBean.class);
	
	protected UtilsFacadeBean fUtils;
	
	@Override public <E extends EjbEquals<T>, T extends EjbWithId> boolean equalsAttributes(Class<T> c,E object){return fUtils.equalsAttributes(c,object);}
	

	// Persist
	@Override public <T extends EjbSaveable> void save(List<T> list) throws UtilsConstraintViolationException,UtilsLockingException {fUtils.save(list);}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Override public <T extends EjbSaveable> T saveTransaction(T o) throws UtilsConstraintViolationException,UtilsLockingException {return fUtils.save(o);}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Override public <T extends EjbSaveable> void saveTransaction(List<T> list) throws UtilsConstraintViolationException,UtilsLockingException {fUtils.save(list);}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Override public <T extends EjbMergeable> T mergeTransaction(T o) throws UtilsConstraintViolationException, UtilsLockingException {return fUtils.mergeTransaction(o);}

	@Override public <T extends EjbMergeable> T merge(T o) throws UtilsConstraintViolationException, UtilsLockingException{return fUtils.merge(o);}
	
	public <T extends EjbSaveable> T save(T o) throws UtilsConstraintViolationException,UtilsLockingException {return fUtils.save(o);}
	protected <T extends EjbWithId> T saveProtected(T o) throws UtilsConstraintViolationException,UtilsLockingException {return fUtils.saveProtected(o);}
	
	public <T extends Object> T persist(T o) throws UtilsConstraintViolationException {return fUtils.persist(o);}
	public <T extends Object> T update(T o) throws UtilsConstraintViolationException, UtilsLockingException {return fUtils.update(o);}
	
	// Remove
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public <T extends EjbRemoveable> void rmTransaction(T o) throws UtilsConstraintViolationException {rmProtected(o);}
	public <T extends EjbRemoveable> void rm(T o) throws UtilsConstraintViolationException {rmProtected(o);}
	public <T extends EjbRemoveable> void rm(List<T> list) throws UtilsConstraintViolationException {fUtils.rm(list);}
	protected <T extends Object> void rmProtected(T o) throws UtilsConstraintViolationException {fUtils.rmProtected(o);}
	
	// Finder
	public <T extends EjbWithId> T find(Class<T> type, T t) {return fUtils.find(type, t);}
	public <T extends Object> T find(Class<T> type, long id) throws UtilsNotFoundException {return fUtils.find(type, id);}
	public <T extends EjbWithName> T fByName(Class<T> type, String name) throws UtilsNotFoundException {return fUtils.fByName(type, name);}
	@Override public <T extends EjbWithEmail> T fByEmail(Class<T> clazz, String email) throws UtilsNotFoundException {return fUtils.fByEmail(clazz, email);}
	@Override public <T extends EjbWithId> List<T> find(Class<T> cl, List<Long> ids) {return fUtils.find(cl, ids);}
	@Override public <T extends EjbWithId> List<T> find(Class<T> cl, Set<Long> ids){return fUtils.find(cl, ids);}
	
	//Code
	@Override public <T extends EjbWithCode, E extends Enum<E>> T fByCode(Class<T> type, E code) throws UtilsNotFoundException {return fUtils.fByCode(type, code);}
	@Override public <T extends EjbWithCode> T fByCode(Class<T> type, String code) throws UtilsNotFoundException {return fUtils.fByCode(type, code);}
	@Override public <T extends EjbWithTypeCode> T fByTypeCode(Class<T> c, String type, String code) throws UtilsNotFoundException {return fUtils.fByTypeCode(c,type,code);}
	@Override public <T extends EjbWithNonUniqueCode> List<T> allByCode(Class<T> type, String code) {return fUtils.allByCode(type,code);}
		
	// All
	@Override public <T extends Object> List<T> all(Class<T> type) {return fUtils.all(type);}
	@Override public <T extends Object> List<T> all(Class<T> type,int maxResults) {return fUtils.all(type,maxResults);}
	public <T extends EjbWithType> List<T> allForType(Class<T> cl, String type) {return fUtils.allForType(cl, type);}
	
	// Ordering
	public <T extends EjbWithPosition> List<T> allOrderedPosition(Class<T> type) {return fUtils.allOrderedPosition(type);}
	@Override public <T extends EjbWithPositionType, E extends Enum<E>> List<T> allOrderedPosition(Class<T> type, E enu) {return fUtils.allOrderedPosition(type,enu);}
	@Override public <T extends EjbWithPositionTypeVisible, E extends Enum<E>> List<T> allOrderedPositionVisible(Class<T> type,E enu) {return fUtils.allOrderedPositionVisible(type,enu);}
	public <T extends EjbWithPositionVisible> List<T> allOrderedPositionVisible(Class<T> cl) {return fUtils.allOrderedPositionVisible(cl);}
	@Override public <T extends EjbWithPositionParent, P extends EjbWithId> List<T> allOrderedPositionParent(Class<T> cl, P parent) {return fUtils.allOrderedPositionParent(cl,parent);}
	@Override public <T extends EjbWithPositionVisibleParent, P extends EjbWithId> List<T> allOrderedPositionVisibleParent(Class<T> cl, P parent) {return fUtils.allOrderedPositionVisibleParent(cl,parent);}
	@Override public <T extends EjbWithCode> List<T> allOrderedCode(Class<T> cl) {return fUtils.allOrderedCode(cl);}
	public <T extends Object> List<T> allOrdered(Class<T> cl, String by, boolean ascending) {return fUtils.allOrdered(cl, by, ascending);}
	public <T extends EjbWithRecord> List<T> allOrderedRecord(Class<T> type, boolean ascending) {return fUtils.allOrderedRecord(type,ascending);}
	@Override public <T extends EjbWithRecord, I extends EjbWithId> List<T> allOrderedParentRecordBetween(Class<T> cl, boolean ascending, String p1Name, I p1,Date from, Date to) {return fUtils.allOrderedParentRecordBetween(cl, ascending, p1Name, p1, from, to);}
	@Override public <T extends EjbWithRecord, P extends EjbWithId> List<T> allOrderedParentsRecordBetween(Class<T> c, boolean ascending, String p1Name, List<P> parents, Date from, Date to){return fUtils.allOrderedParentsRecordBetween(c, ascending, p1Name, parents, from, to);}
	public <T, I extends EjbWithId> List<T> allOrderedParent(Class<T> cl,String by, boolean ascending, String p1Name, I p1) {return fUtils.allOrderedParent(cl, by, ascending, p1Name, p1);}
	
	// Parent
	public <T extends EjbWithId, I extends EjbWithId> List<T> allForParent(Class<T> type, String p1Name, I p1Object){return fUtils.allForParent(type, p1Name, p1Object);}
	public <T extends EjbWithId, I extends EjbWithId> List<T> allForParent(Class<T> type, String p1Name, I p1, String p2Name, I p2){return fUtils.allForParent(type, p1Name, p1, p2Name, p2);}
	public <T extends EjbWithId, I extends EjbWithId> T oneForParent(Class<T> cl, String p1Name, I p1) throws UtilsNotFoundException {return fUtils.oneForParent(cl, p1Name, p1);}
	public <T extends EjbWithNr, P extends EjbWithId> T fByNr(Class<T> type, String parentName, P parent, long nr) throws UtilsNotFoundException {return fUtils.fByNr(type, parentName, parent, nr);}
	public <T extends EjbWithId, P extends EjbWithId> T oneForParents(Class<T> cl, List<ParentPredicate<P>> parents) throws UtilsNotFoundException {return fUtils.oneForParents(cl, parents);}
	public <T extends EjbWithId, I extends EjbWithId> T oneForParents(Class<T> cl, String p1Name, I p1, String p2Name, I p2) throws UtilsNotFoundException {return fUtils.oneForParents(cl, p1Name, p1, p2Name, p2);}
	public <T extends EjbWithId, I extends EjbWithId> T oneForParents(Class<T> cl, String p1Name, I p1, String p2Name, I p2, String p3Name, I p3) throws UtilsNotFoundException {return fUtils.oneForParents(cl,p1Name,p1,p2Name,p2,p3Name,p3);}
	@Override public <T extends EjbWithId, P extends EjbWithId> List<T> allForOrParents(Class<T> cl, List<ParentPredicate<P>> parents) {return fUtils.allForOrParents(cl, parents);}
	@Override public <T extends EjbWithId, OR1 extends EjbWithId, OR2 extends EjbWithId> List<T> allForOrOrParents(Class<T> cl, List<ParentPredicate<OR1>> or1, List<ParentPredicate<OR2>> or2){return fUtils.allForOrOrParents(cl, or1,or2);}
	public <T extends EjbWithRecord, AND extends EjbWithId, OR extends EjbWithId> List<T> allOrderedForParents(Class<T> queryClass, List<ParentPredicate<AND>> lpAnd,List<ParentPredicate<OR>> lpOr, boolean ascending) {return fUtils.allOrderedForParents(queryClass, lpAnd, lpOr, ascending);}
	public <T extends EjbWithId, OR extends EjbWithId, AND extends EjbWithId> List<T> fForAndOrParents(Class<T> queryClass, List<ParentPredicate<AND>> lpAnd, List<ParentPredicate<OR>> lpOr) {return fUtils.fForAndOrParents(queryClass, lpAnd, lpOr);}
	
	// GrandParent
	@Override public <T extends EjbWithId, P extends EjbWithId, GP extends EjbWithId> List<T> allForGrandParent(Class<T> queryClass, Class<P> pClass, String pName, GP grandParent, String gpName) {return fUtils.allForGrandParent(queryClass,pClass,pName,grandParent,gpName);}
	public <T extends EjbWithId, P extends EjbWithId, OR extends EjbWithId, AND extends EjbWithId> List<T> fForAndOrGrandParents(Class<T> queryClass, Class<P> parentClass, String parentName, List<ParentPredicate<AND>> lpAnd, List<ParentPredicate<OR>> lpOr){return fUtils.fForAndOrGrandParents(queryClass, parentClass, parentName, lpAnd, lpOr);}
	public <T extends EjbWithId, P extends EjbWithId, OR1 extends EjbWithId, OR2 extends EjbWithId> List<T> fGrandParents(Class<T> queryClass, Class<P> parentClass, String parentName, List<ParentPredicate<OR1>> lpOr1, List<ParentPredicate<OR2>> lpOr2){return fUtils.fGrandParents(queryClass, parentClass, parentName, lpOr1, lpOr2);}
	
	// Property Store
	public <T extends UtilsProperty> String valueStringForKey(Class<T> type, String key, String defaultValue) throws UtilsNotFoundException {return fUtils.valueStringForKey(type, key, defaultValue);}
	public <T extends UtilsProperty> Integer valueIntForKey(Class<T> type, String key, Integer defaultValue) throws UtilsNotFoundException {return fUtils.valueIntForKey(type, key, defaultValue);}
	public <T extends UtilsProperty> Long valueLongForKey(Class<T> type, String key, Long defaultValue) throws UtilsNotFoundException {return fUtils.valueLongForKey(type, key, defaultValue);}
	public <T extends UtilsProperty> Boolean valueBooleanForKey(Class<T> type, String key, Boolean defaultValue) throws UtilsNotFoundException {return fUtils.valueBooleanForKey(type, key, defaultValue);}
	public <T extends UtilsProperty> Date valueDateForKey(Class<T> type,String key, Date defaultValue) throws UtilsNotFoundException {return fUtils.valueDateForKey(type, key, defaultValue);}

	// ValidFrom
	@Override public <T extends EjbWithValidFrom> T fFirstValidFrom(Class<T> type, String parentName, long id, Date validFrom) throws UtilsNotFoundException {return fUtils.fFirstValidFrom(type, parentName, id, validFrom);}
	@Override public <T extends EjbWithValidFrom> List<T> allOrderedValidFrom(Class<T> cl, boolean ascending){return fUtils.allOrderedValidFrom(cl,ascending);}
	
	//Record
	public <T extends EjbWithRecord> List<T> inInterval(Class<T> clRecord, Date from, Date to) {return fUtils.inInterval(clRecord, from, to);}
	public <T extends EjbWithRecord> T fFirst(Class<T> clRecord) {return fUtils.fFirst(clRecord);}
	public <T extends EjbWithRecord> T fLast(Class<T> clRecord) {return fUtils.fLast(clRecord);}
	
	//Timeline
	public <T extends EjbWithTimeline> List<T> between(Class<T> clTimeline, Date from, Date to) {return fUtils.between(clTimeline, from, to);}
	public <T extends EjbWithTimeline, AND extends EjbWithId, OR extends EjbWithId> List<T> between(Class<T> clTimeline, Date from, Date to, List<ParentPredicate<AND>> lpAnd, List<ParentPredicate<OR>> lpOr) {return fUtils.between(clTimeline, from, to, lpAnd, lpOr);}
	
	//Year
	public <T extends EjbWithYear, P extends EjbWithId> T fByYear(Class<T> type, String p1Name, P p, int year) throws UtilsNotFoundException {return fUtils.fByYear(type, p1Name, p, year);}

	//@Override public <L extends UtilsLang, D extends UtilsDescription, C extends UtilsSecurityCategory<L, D, C, R, V, U, A, USER>, R extends UtilsSecurityRole<L, D, C, R, V, U, A, USER>, V extends UtilsSecurityView<L, D, C, R, V, U, A, USER>, U extends UtilsSecurityUsecase<L, D, C, R, V, U, A, USER>, A extends UtilsSecurityAction<L, D, C, R, V, U, A, USER>, USER extends UtilsUser<L, D, C, R, V, U, A, USER>> List<USER> likeNameFirstLast(Class<USER> c, String query) {return fUtils.likeNameFirstLast(c,query);}
}