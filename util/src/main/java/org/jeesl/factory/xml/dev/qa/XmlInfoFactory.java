package org.jeesl.factory.xml.dev.qa;

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
import net.sf.ahtutils.xml.qa.Comment;
import net.sf.ahtutils.xml.qa.Info;

public class XmlInfoFactory<L extends UtilsLang, D extends UtilsDescription,
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
{
	final static Logger logger = LoggerFactory.getLogger(XmlInfoFactory.class);
		
	private Info q;
	private XmlStatusFactory<QATC,L,D> xfCondition;
	
	public XmlInfoFactory(Info q)
	{
		this.q=q;
		if(q.isSetStatus()){xfCondition = new XmlStatusFactory<QATC,L,D>(null,q.getStatus());}
	}
	
	public Info build(QATI info)
	{
		Info xml = new Info();
	
		if(q.isSetStatus()){xml.setStatus(xfCondition.build(info.getCondition()));}
		
		if(q.isSetComment())
		{
			xml.setComment(new Comment());
			xml.getComment().setValue(info.getDescription());
			if(xml.getComment().getValue()==null){xml.getComment().setValue("");}
		}
		
		return xml;
	}
}