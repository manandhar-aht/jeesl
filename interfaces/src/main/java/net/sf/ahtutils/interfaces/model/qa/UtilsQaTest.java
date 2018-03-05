package net.sf.ahtutils.interfaces.model.qa;

import java.util.Date;
import java.util.List;

import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityView;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityTemplate;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityAction;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityUsecase;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityCategory;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.jeesl.interfaces.model.system.with.code.EjbWithCode;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithName;

public interface UtilsQaTest<L extends UtilsLang,
D extends UtilsDescription,
C extends JeeslSecurityCategory<L,D>,
R extends JeeslSecurityRole<L,D,C,V,U,A,USER>,
V extends JeeslSecurityView<L,D,C,R,U,A>,
U extends JeeslSecurityUsecase<L,D,C,R,V,A>,
A extends JeeslSecurityAction<L,D,R,V,U,AT>,
AT extends JeeslSecurityTemplate<L,D,C>,
USER extends JeeslUser<R>,
STAFF extends UtilsQaStaff<R,USER,GROUP,QA,QASH>,
GROUP extends UtilsQaGroup<STAFF,QA,QASS>,
QA extends UtilsQualityAssurarance<STAFF,QAC,QASH>,
QASD extends UtilsQaSchedule<QA,QASS>,
QASS extends UtilsQaScheduleSlot<GROUP,QASD>,
QAC extends UtilsQaCategory<QA,QAT>,
QAT extends UtilsQaTest<L,D,C,R,V,U,A,AT,USER,STAFF,GROUP,QA,QASD,QASS,QAC,QAT,QAU,QAR,QASH,QATD,QATI,QATC,QATS,QARS,QAUS>,
QAU extends UtilsQaUsability,
QAR extends UtilsQaResult<STAFF,QAT,QARS>,
QASH extends UtilsQaStakeholder<QA>,
QATD extends UtilsQaTestDiscussion<STAFF,QAT>,
QATI extends UtilsQaTestInfo<L,D,C,R,V,U,A,AT,USER,STAFF,GROUP,QA,QASD,QASS,QAC,QAT,QAU,QAR,QASH,QATD,QATI,QATC,QATS,QARS,QAUS>,
QATC extends UtilsStatus<QATC,L,D>,
QATS extends UtilsStatus<QATS,L,D>,
QARS extends UtilsStatus<QARS,L,D>,
QAUS extends UtilsStatus<QAUS,L,D>>
			extends EjbWithId,EjbWithName,EjbWithCode
{
	QAC getCategory();
	void setCategory(QAC category);
	
	String getReference();
	void setReference(String reference);

	String getUrl();
	void setUrl(String url);
	
	String getDescription();
	void setDescription(String description);
	
	String getPreCondition();
    void setPreCondition(String preCondition);
    
    String getSteps();
	void setSteps(String steps);
	
	String getExpectedResult();
	void setExpectedResult(String expectedResult);
	
	List<QATD> getDiscussions();
	void setDiscussions(List<QATD> discussions);
	
	List<QAR> getResults();
	void setResults(List<QAR> results);
	
	QATS getClientStatus();
	void setClientStatus(QATS clientStatus);
	
	QATS getDeveloperStatus();
	void setDeveloperStatus(QATS developerStatus);
	
	QATI getInfo();
	void setInfo(QATI info);
	
	Double getWeight();
	void setWeight(Double weight);
	
	Date getRecord();
	void setRecord(Date record);
	
	Integer getDuration();
	void setDuration(Integer duration);
	
	Boolean getVisible();
	void setVisible(Boolean visible);
	
	List<GROUP> getGroups();
	void setGroups(List<GROUP> groups);
}