package org.jeesl.factory.xml.dev.qa;

import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityView;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityTemplate;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityAction;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityUsecase;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityCategory;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
import net.sf.ahtutils.xml.qa.Groups;

public class XmlGroupsFactory<L extends UtilsLang,
D extends UtilsDescription,
C extends JeeslSecurityCategory<L,D>,
R extends JeeslSecurityRole<L,D,C,R,V,U,A,AT,USER>,
V extends JeeslSecurityView<L,D,C,R,U,A>,
U extends JeeslSecurityUsecase<L,D,C,R,V,A>,
A extends JeeslSecurityAction<L,D,C,R,V,U,A,AT,USER>,
AT extends JeeslSecurityTemplate<L,D,C,R,V,U,A,AT,USER>,
USER extends JeeslUser<L,D,C,R,V,U,A,AT,USER>,
STAFF extends UtilsQaStaff<L,D,C,R,V,U,A,AT,USER,STAFF,GROUP,QA,QASD,QASS,QAC,QAT,QAU,QAR,QASH,QATD,QATI,QATC,QATS,QARS,QAUS>,
GROUP extends UtilsQaGroup<L,D,C,R,V,U,A,AT,USER,STAFF,GROUP,QA,QASD,QASS,QAC,QAT,QAU,QAR,QASH,QATD,QATI,QATC,QATS,QARS,QAUS>,
QA extends UtilsQualityAssurarance<L,D,C,R,V,U,A,AT,USER,STAFF,GROUP,QA,QASD,QASS,QAC,QAT,QAU,QAR,QASH,QATD,QATI,QATC,QATS,QARS,QAUS>,
QASD extends UtilsQaSchedule<L,D,C,R,V,U,A,AT,USER,STAFF,GROUP,QA,QASD,QASS,QAC,QAT,QAU,QAR,QASH,QATD,QATI,QATC,QATS,QARS,QAUS>,
QASS extends UtilsQaScheduleSlot<L,D,C,R,V,U,A,AT,USER,STAFF,GROUP,QA,QASD,QASS,QAC,QAT,QAU,QAR,QASH,QATD,QATI,QATC,QATS,QARS,QAUS>,
QAC extends UtilsQaCategory<L,D,C,R,V,U,A,AT,USER,STAFF,GROUP,QA,QASD,QASS,QAC,QAT,QAU,QAR,QASH,QATD,QATI,QATC,QATS,QARS,QAUS>,
QAT extends UtilsQaTest<L,D,C,R,V,U,A,AT,USER,STAFF,GROUP,QA,QASD,QASS,QAC,QAT,QAU,QAR,QASH,QATD,QATI,QATC,QATS,QARS,QAUS>,
QAU extends UtilsQaUsability<L,D,C,R,V,U,A,AT,USER,STAFF,GROUP,QA,QASD,QASS,QAC,QAT,QAU,QAR,QASH,QATD,QATI,QATC,QATS,QARS,QAUS>,
QAR extends UtilsQaResult<L,D,C,R,V,U,A,AT,USER,STAFF,GROUP,QA,QASD,QASS,QAC,QAT,QAU,QAR,QASH,QATD,QATI,QATC,QATS,QARS,QAUS>,
QASH extends UtilsQaStakeholder<L,D,C,R,V,U,A,AT,USER,STAFF,GROUP,QA,QASD,QASS,QAC,QAT,QAU,QAR,QASH,QATD,QATI,QATC,QATS,QARS,QAUS>,
QATD extends UtilsQaTestDiscussion<L,D,C,R,V,U,A,AT,USER,STAFF,GROUP,QA,QASD,QASS,QAC,QAT,QAU,QAR,QASH,QATD,QATI,QATC,QATS,QARS,QAUS>,
QATI extends UtilsQaTestInfo<L,D,C,R,V,U,A,AT,USER,STAFF,GROUP,QA,QASD,QASS,QAC,QAT,QAU,QAR,QASH,QATD,QATI,QATC,QATS,QARS,QAUS>,
QATC extends UtilsStatus<QATC,L,D>,
QATS extends UtilsStatus<QATS,L,D>,
QARS extends UtilsStatus<QARS,L,D>,
QAUS extends UtilsStatus<QAUS,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(XmlGroupsFactory.class);
		
	private Groups q;
	
	public XmlGroupsFactory(Groups q)
	{
		this.q=q;
	}
	
	public Groups build(QAT test)
	{
		Groups xml = new Groups();
		
		if(q.isSetGroup() && test.getGroups()!=null)
		{
			XmlGroupFactory<L,D,C,R,V,U,A,AT,USER,STAFF,GROUP,QA,QASD,QASS,QAC,QAT,QAU,QAR,QASH,QATD,QATI,QATC,QATS,QARS,QAUS> f = new XmlGroupFactory<L,D,C,R,V,U,A,AT,USER,STAFF,GROUP,QA,QASD,QASS,QAC,QAT,QAU,QAR,QASH,QATD,QATI,QATC,QATS,QARS,QAUS>(q.getGroup().get(0));
			for(GROUP g : test.getGroups())
			{
				xml.getGroup().add(f.build(g));
			}
		}
		
		return xml;
	}
	
	public static Groups build()
	{
		Groups xml = new Groups();
		
		return xml;
	}
	
}