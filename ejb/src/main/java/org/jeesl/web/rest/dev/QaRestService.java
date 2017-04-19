package org.jeesl.web.rest.dev;

import org.jeesl.api.facade.module.JeeslQaFacade;
import org.jeesl.factory.xml.system.security.XmlStaffFactory;
import org.jeesl.factory.xml.system.status.XmlStatusFactory;
import org.jeesl.factory.xml.system.status.XmlTypeFactory;
import org.jeesl.util.query.xml.module.QaQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.factory.xml.qa.XmlCategoryFactory;
import net.sf.ahtutils.factory.xml.qa.XmlGroupFactory;
import net.sf.ahtutils.factory.xml.qa.XmlGroupsFactory;
import net.sf.ahtutils.factory.xml.status.XmlResponsibleFactory;
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
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityAction;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityActionTemplate;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityCategory;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityRole;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityUsecase;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityView;
import net.sf.ahtutils.interfaces.model.system.security.UtilsUser;
import net.sf.ahtutils.xml.qa.Category;
import net.sf.ahtutils.xml.qa.Group;
import net.sf.ahtutils.xml.qa.Qa;
import net.sf.ahtutils.xml.qa.Test;
import net.sf.ahtutils.xml.security.Staff;

public class QaRestService <L extends UtilsLang,
							D extends UtilsDescription,
							C extends UtilsSecurityCategory<L,D,C,R,V,U,A,AT,USER>,
							R extends UtilsSecurityRole<L,D,C,R,V,U,A,AT,USER>,
							V extends UtilsSecurityView<L,D,C,R,V,U,A,AT,USER>,
							U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
							A extends UtilsSecurityAction<L,D,C,R,V,U,A,AT,USER>,
							AT extends UtilsSecurityActionTemplate<L,D,C,R,V,U,A,AT,USER>,
							USER extends UtilsUser<L,D,C,R,V,U,A,AT,USER>,
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
					//implements UtilsQualityAssuranceRest
{
	final static Logger logger = LoggerFactory.getLogger(QaRestService.class);
	
	private JeeslQaFacade<L,D,C,R,V,U,A,AT,USER,STAFF,GROUP,QA,QASD,QASS,QAC,QAT,QAU,QAR,QASH,QATD,QATI,QATC,QATS,QARS,QAUS> fQa;
		
	private final Class<GROUP> cGroup;
	private final Class<QA> cQa;
	
	private XmlStaffFactory<L,D,C,R,V,U,A,AT,USER,STAFF,QA,QA> xfStaff;
	private XmlGroupFactory<L,D,C,R,V,U,A,AT,USER,STAFF,GROUP,QA,QASD,QASS,QAC,QAT,QAU,QAR,QASH,QATD,QATI,QATC,QATS,QARS,QAUS> xfGroup;
	private XmlCategoryFactory<L,D,C,R,V,U,A,AT,USER,STAFF,GROUP,QA,QASD,QASS,QAC,QAT,QAU,QAR,QASH,QATD,QATI,QATC,QATS,QARS,QAUS> xfCategory;
	private XmlCategoryFactory<L,D,C,R,V,U,A,AT,USER,STAFF,GROUP,QA,QASD,QASS,QAC,QAT,QAU,QAR,QASH,QATD,QATI,QATC,QATS,QARS,QAUS> xfFrDuration;
	
	private QaRestService(JeeslQaFacade<L,D,C,R,V,U,A,AT,USER,STAFF,GROUP,QA,QASD,QASS,QAC,QAT,QAU,QAR,QASH,QATD,QATI,QATC,QATS,QARS,QAUS> fQa, final Class<L> cL, final Class<D> cD,final Class<GROUP> cGroup,final Class<QA> cQa,final Class<QAC> cQAC,final Class<QAT> cQAT)
	{
		this.fQa=fQa;
		this.cGroup=cGroup;
		this.cQa=cQa;
		
		xfStaff = new XmlStaffFactory<L,D,C,R,V,U,A,AT,USER,STAFF,QA,QA>(QaQuery.staff());
		xfGroup = new XmlGroupFactory<L,D,C,R,V,U,A,AT,USER,STAFF,GROUP,QA,QASD,QASS,QAC,QAT,QAU,QAR,QASH,QATD,QATI,QATC,QATS,QARS,QAUS>(QaQuery.group());
		xfCategory = new XmlCategoryFactory<L,D,C,R,V,U,A,AT,USER,STAFF,GROUP,QA,QASD,QASS,QAC,QAT,QAU,QAR,QASH,QATD,QATI,QATC,QATS,QARS,QAUS>(QaQuery.category());
		xfFrDuration = new XmlCategoryFactory<L,D,C,R,V,U,A,AT,USER,STAFF,GROUP,QA,QASD,QASS,QAC,QAT,QAU,QAR,QASH,QATD,QATI,QATC,QATS,QARS,QAUS>(QaQuery.frDuration());
		
		xfFrDuration.lazyLoader(fQa,cQAC,cQAT);
	}
	
	public static <L extends UtilsLang,
					D extends UtilsDescription,
					C extends UtilsSecurityCategory<L,D,C,R,V,U,A,AT,USER>,
					R extends UtilsSecurityRole<L,D,C,R,V,U,A,AT,USER>,
					V extends UtilsSecurityView<L,D,C,R,V,U,A,AT,USER>,
					U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
					A extends UtilsSecurityAction<L,D,C,R,V,U,A,AT,USER>,
					AT extends UtilsSecurityActionTemplate<L,D,C,R,V,U,A,AT,USER>,
					USER extends UtilsUser<L,D,C,R,V,U,A,AT,USER>,
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
		QaRestService<L,D,C,R,V,U,A,AT,USER,STAFF,GROUP,QA,QASD,QASS,QAC,QAT,QAU,QAR,QASH,QATD,QATI,QATC,QATS,QARS,QAUS>
			factory(JeeslQaFacade<L,D,C,R,V,U,A,AT,USER,STAFF,GROUP,QA,QASD,QASS,QAC,QAT,QAU,QAR,QASH,QATD,QATI,QATC,QATS,QARS,QAUS> fQa,final Class<L> cL,final Class<D> cD,final Class<GROUP> cGroup,final Class<QA> cQa,final Class<QAC> cQAC,final Class<QAT> cQAT)
	{
		return new QaRestService<L,D,C,R,V,U,A,AT,USER,STAFF,GROUP,QA,QASD,QASS,QAC,QAT,QAU,QAR,QASH,QATD,QATI,QATC,QATS,QARS,QAUS>(fQa,cL,cD,cGroup,cQa,cQAC,cQAT);
	}

	public Qa qaGroups(long qaId)
	{
		logger.info("Building QA Groups for QA="+qaId);
		Qa qa = new Qa();
		qa.setGroups(XmlGroupsFactory.build());
		try
		{
			QA eQa = fQa.find(cQa, qaId);
			
			for(GROUP group : fQa.fQaGroups(cGroup,eQa))
			{
				group = fQa.load(cGroup, group);
				Group xGroup = xfGroup.build(group);
				for(STAFF staff : group.getStaffs())
				{
					Staff xStaff = xfStaff.build(staff);
					
					if(staff.getDepartment()!=null){xStaff.setType(XmlTypeFactory.buildLabel(null,staff.getDepartment()));}
					if(staff.getStakeholder()!=null){xStaff.setStatus(XmlStatusFactory.buildLabel(null,staff.getStakeholder().getCode()));}
					if(staff.getResponsibilities()!=null){xStaff.setResponsible(XmlResponsibleFactory.buildLabel(null,staff.getResponsibilities()));}
					xGroup.getStaff().add(xStaff);
				}
						
				qa.getGroups().getGroup().add(xGroup);
			}
		}
		catch (UtilsNotFoundException e) {e.printStackTrace();}	
		return qa;
	}
	
	public Qa qaSchedule(long qaId)
	{
		logger.info("Building QA Groups for QA="+qaId);
		Qa qa = new Qa();
		qa.setGroups(XmlGroupsFactory.build());
		try
		{
			QA eQa = fQa.find(cQa, qaId);
			
			for(GROUP group : fQa.fQaGroups(cGroup,eQa))
			{
				group = fQa.load(cGroup, group);
				Group xGroup = xfGroup.build(group);
				for(STAFF staff : group.getStaffs())
				{
					Staff xStaff = xfStaff.build(staff);
					
					if(staff.getDepartment()!=null){xStaff.setType(XmlTypeFactory.buildLabel(null,staff.getDepartment()));}
					if(staff.getStakeholder()!=null){xStaff.setStatus(XmlStatusFactory.buildLabel(null,staff.getStakeholder().getCode()));}
					if(staff.getResponsibilities()!=null){xStaff.setResponsible(XmlResponsibleFactory.buildLabel(null,staff.getResponsibilities()));}
					xGroup.getStaff().add(xStaff);
				}
						
				qa.getGroups().getGroup().add(xGroup);
			}
		}
		catch (UtilsNotFoundException e) {e.printStackTrace();}	
		return qa;
	}
	
	public Qa qaCategories(long qaId)
	{
		Qa qa = new Qa();
		try
		{
			QA eQa = fQa.find(cQa, qaId);
			eQa = fQa.load(cQa,eQa);
			
			for(QAC category : eQa.getCategories())
			{
				qa.getCategory().add(xfCategory.build(category));
			}
		}
		catch (UtilsNotFoundException e) {e.printStackTrace();}	
		return qa;
	}
	
	public Qa qaFrDurations(long qaId)
	{
		Qa qa = new Qa();
		try
		{
			QA eQa = fQa.find(cQa, qaId);
			eQa = fQa.load(cQa,eQa);
			
			for(QAC category : eQa.getCategories())
			{
				Category c = xfFrDuration.build(category);
				if(logger.isTraceEnabled())
				{
//					logger.info(StringUtil.stars());
					for(Test t : c.getTest())
					{
						logger.info("\tVisible?"+t.isSetVisible());
					}
				}
				
				qa.getCategory().add(c);
			}
		}
		catch (UtilsNotFoundException e) {e.printStackTrace();}	
		return qa;
	}
	
}