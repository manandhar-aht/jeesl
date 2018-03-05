package org.jeesl.factory.xml.dev.qa;

import org.jeesl.api.facade.module.JeeslQaFacade;
import org.jeesl.factory.xml.system.status.XmlStatusFactory;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityView;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityTemplate;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityAction;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityUsecase;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityCategory;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.factory.xml.status.XmlStatementFactory;
import net.sf.ahtutils.interfaces.model.qa.UtilsQaCategory;
import net.sf.ahtutils.interfaces.model.qa.UtilsQaGroup;
import net.sf.ahtutils.interfaces.model.qa.UtilsQaResult;
import net.sf.ahtutils.interfaces.model.qa.UtilsQaSchedule;
import net.sf.ahtutils.interfaces.model.qa.UtilsQaScheduleSlot;
import net.sf.ahtutils.interfaces.model.qa.UtilsQaStaff;
import net.sf.ahtutils.interfaces.model.qa.UtilsQaStakeholder;
import net.sf.ahtutils.interfaces.model.qa.UtilsQaTest;
import net.sf.ahtutils.interfaces.model.qa.UtilsQaTestDiscussion;
import net.sf.ahtutils.interfaces.model.qa.UtilsQaTestInfo;
import net.sf.ahtutils.interfaces.model.qa.UtilsQaUsability;
import net.sf.ahtutils.interfaces.model.qa.UtilsQualityAssurarance;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.xml.qa.Test;

public class XmlTestFactory<L extends UtilsLang, D extends UtilsDescription,
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
							QATI extends UtilsQaTestInfo<QATC>,
							QATC extends UtilsStatus<QATC,L,D>,
							QATS extends UtilsStatus<QATS,L,D>,
							QARS extends UtilsStatus<QARS,L,D>,
							QAUS extends UtilsStatus<QAUS,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(XmlTestFactory.class);
		
	private Test q;
	
	private Class<QAT> cQAT;
	
	private XmlStatusFactory<QATS,L,D> xfDeveloperStatus;
	
	private JeeslQaFacade<L,D,C,R,V,U,A,AT,USER,STAFF,GROUP,QA,QASD,QASS,QAC,QAT,QAU,QAR,QASH,QATD,QATI,QATC,QATS,QARS,QAUS> fQa;
	public void lazyLoader(JeeslQaFacade<L,D,C,R,V,U,A,AT,USER,STAFF,GROUP,QA,QASD,QASS,QAC,QAT,QAU,QAR,QASH,QATD,QATI,QATC,QATS,QARS,QAUS> fQa,Class<QAT> cQAT)
	{
		this.fQa=fQa;
		this.cQAT=cQAT;
	}
	
	public XmlTestFactory(Test q)
	{
		this.q=q;
		if(q.isSetStatus()){xfDeveloperStatus = new XmlStatusFactory<QATS,L,D>(null,q.getStatus());}
	}
	
	public static Test build()
	{
		Test xml = new Test();

		return xml;
	}
	
	public Test build(QAT test)
	{
		if(fQa!=null){test = fQa.load(cQAT, test);}
		
		Test xml = new Test();
		
		if(q.isSetId()){xml.setId(test.getId());}
		if(q.isSetCode()){xml.setCode(test.getCode());}
		if(q.isSetName()){xml.setName(test.getName());}
		if(q.isSetVisible()){xml.setVisible(test.getVisible());}
		if(q.isSetDuration())
		{
			if(test.getDuration()!=null){xml.setDuration(test.getDuration());}
			else{xml.setDuration(0);}
		}

		if(q.isSetReference() && test.getReference()!=null){xml.setReference(XmlReferenceFactory.build(test.getReference()));}
		if(q.isSetDescription() && test.getDescription()!=null){xml.setDescription(XmlDescriptionFactory.build(test.getDescription()));}
		if(q.isSetPreCondition() && test.getPreCondition()!=null){xml.setPreCondition(XmlPreConditionFactory.build(test.getPreCondition()));}
		if(q.isSetSteps() && test.getSteps()!=null){xml.setSteps(XmlStepsFactory.build(test.getSteps()));}
		if(q.isSetExpected() && test.getExpectedResult()!=null){xml.setExpected(XmlExpectedFactory.build(test.getExpectedResult()));}
		
		if(q.isSetStatement() && test.getClientStatus()!=null)
		{
			XmlStatementFactory<QATS,L,D> f = new XmlStatementFactory<QATS,L,D>(null,q.getStatement());
			xml.setStatement(f.build(test.getClientStatus()));
		}
		
		if(q.isSetStatus() && test.getDeveloperStatus()!=null)
		{
			xml.setStatus(xfDeveloperStatus.build(test.getDeveloperStatus()));
		}
		
		if(q.isSetResults())
		{
			XmlResultsFactory<L,D,C,R,V,U,A,AT,USER,STAFF,GROUP,QA,QASD,QASS,QAC,QAT,QAU,QAR,QASH,QATD,QATI,QATC,QATS,QARS,QAUS> f = new XmlResultsFactory<L,D,C,R,V,U,A,AT,USER,STAFF,GROUP,QA,QASD,QASS,QAC,QAT,QAU,QAR,QASH,QATD,QATI,QATC,QATS,QARS,QAUS>(q.getResults());
			xml.setResults(f.build(test));
		}
		
		if(q.isSetInfo() && test.getInfo()!=null)
		{
			XmlInfoFactory<L,D,C,R,V,U,A,AT,USER,STAFF,GROUP,QA,QASD,QASS,QAC,QAT,QAU,QAR,QASH,QATD,QATI,QATC,QATS,QARS,QAUS> f = new XmlInfoFactory<L,D,C,R,V,U,A,AT,USER,STAFF,GROUP,QA,QASD,QASS,QAC,QAT,QAU,QAR,QASH,QATD,QATI,QATC,QATS,QARS,QAUS>(q.getInfo());
			xml.setInfo(f.build(test.getInfo()));
		}
		
		if(q.isSetGroups())
		{
			XmlGroupsFactory<L,D,C,R,V,U,A,AT,USER,STAFF,GROUP,QA,QASD,QASS,QAC,QAT,QAU,QAR,QASH,QATD,QATI,QATC,QATS,QARS,QAUS> f = new XmlGroupsFactory<L,D,C,R,V,U,A,AT,USER,STAFF,GROUP,QA,QASD,QASS,QAC,QAT,QAU,QAR,QASH,QATD,QATI,QATC,QATS,QARS,QAUS>(q.getGroups());
			xml.setGroups(f.build(test));
		}
		
		return xml;
	}
}