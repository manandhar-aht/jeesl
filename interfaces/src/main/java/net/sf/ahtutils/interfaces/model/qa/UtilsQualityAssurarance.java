package net.sf.ahtutils.interfaces.model.qa;

import java.util.List;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityAction;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityCategory;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityRole;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityUsecase;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityView;
import net.sf.ahtutils.interfaces.model.system.security.UtilsUser;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface UtilsQualityAssurarance<L extends UtilsLang,
D extends UtilsDescription,
C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,
R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,
V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,
U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,
A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,
USER extends UtilsUser<L,D,C,R,V,U,A,USER>,
STAFF extends UtilsQaStaff<L,D,C,R,V,U,A,USER,STAFF,GROUP,QA,QASD,QASS,QAC,QAT,QAU,QAR,QASH,QATD,QATI,QATC,QATS,QARS,QAUS>,
GROUP extends UtilsQaGroup<L,D,C,R,V,U,A,USER,STAFF,GROUP,QA,QASD,QASS,QAC,QAT,QAU,QAR,QASH,QATD,QATI,QATC,QATS,QARS,QAUS>,
QA extends UtilsQualityAssurarance<L,D,C,R,V,U,A,USER,STAFF,GROUP,QA,QASD,QASS,QAC,QAT,QAU,QAR,QASH,QATD,QATI,QATC,QATS,QARS,QAUS>,
QASD extends UtilsQaSchedule<L,D,C,R,V,U,A,USER,STAFF,GROUP,QA,QASD,QASS,QAC,QAT,QAU,QAR,QASH,QATD,QATI,QATC,QATS,QARS,QAUS>,
QASS extends UtilsQaScheduleSlot<L,D,C,R,V,U,A,USER,STAFF,GROUP,QA,QASD,QASS,QAC,QAT,QAU,QAR,QASH,QATD,QATI,QATC,QATS,QARS,QAUS>,
QAC extends UtilsQaCategory<L,D,C,R,V,U,A,USER,STAFF,GROUP,QA,QASD,QASS,QAC,QAT,QAU,QAR,QASH,QATD,QATI,QATC,QATS,QARS,QAUS>,
QAT extends UtilsQaTest<L,D,C,R,V,U,A,USER,STAFF,GROUP,QA,QASD,QASS,QAC,QAT,QAU,QAR,QASH,QATD,QATI,QATC,QATS,QARS,QAUS>,
QAU extends UtilsQaUsability<L,D,C,R,V,U,A,USER,STAFF,GROUP,QA,QASD,QASS,QAC,QAT,QAU,QAR,QASH,QATD,QATI,QATC,QATS,QARS,QAUS>,
QAR extends UtilsQaResult<L,D,C,R,V,U,A,USER,STAFF,GROUP,QA,QASD,QASS,QAC,QAT,QAU,QAR,QASH,QATD,QATI,QATC,QATS,QARS,QAUS>,
QASH extends UtilsQaStakeholder<L,D,C,R,V,U,A,USER,STAFF,GROUP,QA,QASD,QASS,QAC,QAT,QAU,QAR,QASH,QATD,QATI,QATC,QATS,QARS,QAUS>,
QATD extends UtilsQaTestDiscussion<L,D,C,R,V,U,A,USER,STAFF,GROUP,QA,QASD,QASS,QAC,QAT,QAU,QAR,QASH,QATD,QATI,QATC,QATS,QARS,QAUS>,
QATI extends UtilsQaTestInfo<L,D,C,R,V,U,A,USER,STAFF,GROUP,QA,QASD,QASS,QAC,QAT,QAU,QAR,QASH,QATD,QATI,QATC,QATS,QARS,QAUS>,
QATC extends UtilsStatus<QATC,L,D>,
QATS extends UtilsStatus<QATS,L,D>,
QARS extends UtilsStatus<QARS,L,D>,
QAUS extends UtilsStatus<QAUS,L,D>>
			extends EjbWithId
{
	List<QAC> getCategories();
	void setCategories(List<QAC> categories);
	
	List<STAFF> getStaff();
	void setStaff(List<STAFF> staff);
	
	List<QASH> getStakeholders();
	void setStakeholders(List<QASH> stakeholders);
}