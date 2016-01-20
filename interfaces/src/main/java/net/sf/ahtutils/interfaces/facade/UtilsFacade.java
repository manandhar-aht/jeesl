package net.sf.ahtutils.interfaces.facade;

import java.util.Date;
import java.util.List;

import net.sf.ahtutils.controller.util.ParentPredicate;
import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.model.behaviour.EjbEquals;
import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbMergeable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.date.EjbWithTimeline;
import net.sf.ahtutils.interfaces.model.date.EjbWithValidFrom;
import net.sf.ahtutils.interfaces.model.date.EjbWithYear;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityAction;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityCategory;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityRole;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityUsecase;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityView;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
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
import net.sf.ahtutils.model.interfaces.idm.UtilsUser;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithName;
import net.sf.ahtutils.model.interfaces.with.EjbWithRecord;

public interface UtilsFacade extends UtilsIdFacade
{
	
	//NAME
	<T extends EjbWithName> T fByName(Class<T> type, String name) throws UtilsNotFoundException;

	//EQUALS
	<E extends EjbEquals<T>,T extends EjbWithId> boolean equalsAttributes(Class<T> c,E object); 
	
	//CODE
	<T extends EjbWithCode, E extends Enum<E>> T fByCode(Class<T> type, E code) throws UtilsNotFoundException;
	<T extends EjbWithCode> T fByCode(Class<T> type, String code) throws UtilsNotFoundException;
	<T extends EjbWithTypeCode> T fByTypeCode(Class<T> c, String type, String code) throws UtilsNotFoundException;
	<T extends EjbWithNonUniqueCode> List<T> allByCode(Class<T> type, String code);
	
	<T extends EjbWithNr, P extends EjbWithId> T fByNr(Class<T> type, String parentName, P parent, long nr) throws UtilsNotFoundException;
	
	<T extends UtilsProperty> String valueStringForKey(Class<T> type, String key, String defaultValue) throws UtilsNotFoundException;
	<T extends UtilsProperty> Integer valueIntForKey(Class<T> type, String key, Integer defaultValue) throws UtilsNotFoundException;
	<T extends UtilsProperty> Long valueLongForKey(Class<T> type, String key, Long defaultValue) throws UtilsNotFoundException;
	<T extends UtilsProperty> Boolean valueBooleanForKey(Class<T> type, String key, Boolean defaultValue) throws UtilsNotFoundException;
	<T extends UtilsProperty> Date valueDateForKey(Class<T> type, String key, Date defaultValue) throws UtilsNotFoundException;
	
	<T extends EjbWithType> List<T> allForType(Class<T> clazz, String type);
	
	// ORDERING
	<T extends Object> List<T> allOrdered(Class<T> cl, String by, boolean ascending);
	<T extends Object,I extends EjbWithId> List<T> allOrderedParent(Class<T> cl, String by, boolean ascending,String p1Name, I p1);
	<T extends EjbWithCode> List<T> allOrderedCode(Class<T> cl);
	<T extends EjbWithPosition> List<T> allOrderedPosition(Class<T> type);
	<T extends EjbWithPositionType, E extends Enum<E>> List<T> allOrderedPosition(Class<T> type, E enu);
	<T extends EjbWithPositionTypeVisible, E extends Enum<E>> List<T> allOrderedPositionVisible(Class<T> type, E enu);
	<T extends EjbWithPositionVisible> List<T> allOrderedPositionVisible(Class<T> type);
	<T extends EjbWithPositionParent, P extends EjbWithId> List<T> allOrderedPositionParent(Class<T> cl, P parent);
	<T extends EjbWithPositionVisible, P extends EjbWithId> List<T> allOrderedPositionVisibleParent(Class<T> cl, P parent);
	<T extends EjbWithRecord> List<T> allOrderedRecord(Class<T> type, boolean ascending);
	<T extends EjbWithRecord, AND extends EjbWithId, OR extends EjbWithId> List<T> allOrderedForParents(Class<T> queryClass, List<ParentPredicate<AND>> lpAnd, List<ParentPredicate<OR>> lpOr,boolean ascending);
	<T extends EjbWithValidFrom> List<T> allOrderedValidFrom(Class<T> cl, boolean ascending);
	
	//Persist
	<T extends Object> T persist(T o) throws UtilsConstraintViolationException;
	<T extends Object> T update(T o) throws UtilsConstraintViolationException,UtilsLockingException;
	
	<T extends EjbMergeable> T merge(T o) throws UtilsConstraintViolationException, UtilsLockingException;
	<T extends EjbMergeable> T mergeTransaction(T o) throws UtilsConstraintViolationException, UtilsLockingException;
	
	<T extends EjbSaveable> T save(T o) throws UtilsConstraintViolationException,UtilsLockingException;
	<T extends EjbSaveable> T saveTransaction(T o) throws UtilsConstraintViolationException,UtilsLockingException;
	<T extends EjbSaveable> void save(List<T> list) throws UtilsConstraintViolationException,UtilsLockingException;
	<T extends EjbSaveable> void saveTransaction(List<T> list) throws UtilsConstraintViolationException,UtilsLockingException;
	
	<T extends EjbRemoveable> void rmTransaction(T o) throws UtilsConstraintViolationException;
	<T extends EjbRemoveable> void rm(T o) throws UtilsConstraintViolationException;
	
	//Parent
	<T extends EjbWithId, I extends EjbWithId> List<T> allForParent(Class<T> type, String p1Name, I p1);
	<T extends EjbWithId, I extends EjbWithId> T oneForParent(Class<T> cl, String p1Name, I p1) throws UtilsNotFoundException;
	<T extends EjbWithId, I extends EjbWithId> T oneForParents(Class<T> cl, String p1Name, I p1, String p2Name, I p2) throws UtilsNotFoundException;
	<T extends EjbWithId, I extends EjbWithId> T oneForParents(Class<T> cl, String p1Name, I p1, String p2Name, I p2, String p3Name, I p3) throws UtilsNotFoundException;
	<T extends EjbWithId, P extends EjbWithId> T oneForParents(Class<T> cl, List<ParentPredicate<P>> parents) throws UtilsNotFoundException;
	<T extends EjbWithId, I extends EjbWithId> List<T> allForParent(Class<T> type, String p1Name, I p1, String p2Name, I p2);
	<T extends EjbWithId, P extends EjbWithId> List<T> allForOrParents(Class<T> cl, List<ParentPredicate<P>> parents);
	<T extends EjbWithId, OR1 extends EjbWithId, OR2 extends EjbWithId> List<T> allForOrOrParents(Class<T> cl, List<ParentPredicate<OR1>> or1, List<ParentPredicate<OR2>> or2);
	<T extends EjbWithId, OR extends EjbWithId, AND extends EjbWithId> List<T> fForAndOrParents(Class<T> queryClass, List<ParentPredicate<AND>> lpAnd, List<ParentPredicate<OR>> lpOr);
	<T extends EjbWithId, P extends EjbWithId, GP extends EjbWithId> List<T> allForGrandParent(Class<T> queryClass, Class<P> pClass, String pName, GP grandParent, String gpName);
	<T extends EjbWithId, P extends EjbWithId, OR extends EjbWithId, AND extends EjbWithId> List<T> fForAndOrGrandParents(Class<T> queryClass, Class<P> parentClass, String parentName, List<ParentPredicate<AND>> lpAnd, List<ParentPredicate<OR>> lpOr);
	<T extends EjbWithId, P extends EjbWithId, OR1 extends EjbWithId, OR2 extends EjbWithId> List<T> fGrandParents(Class<T> queryClass, Class<P> parentClass, String parentName, List<ParentPredicate<OR1>> lpOr1, List<ParentPredicate<OR2>> lpOr2);
	
	//Record
	<T extends EjbWithRecord,I extends EjbWithId> List<T> allOrderedParentRecordBetween(Class<T> cl, boolean ascending,String p1Name, I p1,Date from, Date to);
	<T extends EjbWithRecord> List<T> inInterval(Class<T> clRecord, Date from, Date to);
	<T extends EjbWithRecord> T fFirst(Class<T> clRecord);
	<T extends EjbWithRecord> T fLast(Class<T> clRecord);
	
	//ValidFrom
	<T extends EjbWithValidFrom> T fFirstValidFrom(Class<T> type, String parentName, long id, Date validFrom) throws UtilsNotFoundException;
	
	//Timeline
	<T extends EjbWithTimeline> List<T> between(Class<T> clTracker, Date from, Date to);
	<T extends EjbWithTimeline, AND extends EjbWithId, OR extends EjbWithId> List<T> between(Class<T> clTimeline,Date from, Date to, List<ParentPredicate<AND>> lpAnd, List<ParentPredicate<OR>> lpOr);
	
	//Year
	<T extends EjbWithYear,P extends EjbWithId> T fByYear(Class<T> type, String p1Name, P p, int year) throws UtilsNotFoundException;
	
	//User
	<L extends UtilsLang,D extends UtilsDescription,C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>, USER extends UtilsUser<L,D,C,R,V,U,A,USER>> List<USER> likeNameFirstLast(Class<USER> c, String query);
	<T extends EjbWithEmail> T fByEmail(Class<T> clazz, String email) throws UtilsNotFoundException;
}
