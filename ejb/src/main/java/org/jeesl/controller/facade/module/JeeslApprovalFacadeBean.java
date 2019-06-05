package org.jeesl.controller.facade.module;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.jeesl.api.facade.module.JeeslApprovalFacade;
import org.jeesl.factory.builder.module.WorkflowFactoryBuilder;
import org.jeesl.interfaces.model.module.workflow.action.JeeslApprovalAction;
import org.jeesl.interfaces.model.module.workflow.action.JeeslApprovalBot;
import org.jeesl.interfaces.model.module.workflow.action.JeeslApprovalCommunication;
import org.jeesl.interfaces.model.module.workflow.instance.JeeslApprovalActivity;
import org.jeesl.interfaces.model.module.workflow.instance.JeeslApprovalLink;
import org.jeesl.interfaces.model.module.workflow.instance.JeeslApprovalWorkflow;
import org.jeesl.interfaces.model.module.workflow.instance.JeeslWithWorkflow;
import org.jeesl.interfaces.model.module.workflow.process.JeeslApprovalContext;
import org.jeesl.interfaces.model.module.workflow.process.JeeslApprovalProcess;
import org.jeesl.interfaces.model.module.workflow.stage.JeeslApprovalPermissionType;
import org.jeesl.interfaces.model.module.workflow.stage.JeeslApprovalStage;
import org.jeesl.interfaces.model.module.workflow.stage.JeeslApprovalStagePermission;
import org.jeesl.interfaces.model.module.workflow.stage.JeeslApprovalStageType;
import org.jeesl.interfaces.model.module.workflow.transition.JeeslApprovalTransition;
import org.jeesl.interfaces.model.module.workflow.transition.JeeslApprovalTransitionType;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslIoTemplate;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionAttribute;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntity;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.controller.facade.UtilsFacadeBean;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class JeeslApprovalFacadeBean<L extends UtilsLang, D extends UtilsDescription, LOC extends UtilsStatus<LOC,L,D>,
									AX extends JeeslApprovalContext<AX,L,D,?>,
									AP extends JeeslApprovalProcess<L,D,AX>,
									AS extends JeeslApprovalStage<L,D,AP,AST>,
									AST extends JeeslApprovalStageType<AST,?,?,?>,
									ASP extends JeeslApprovalStagePermission<AS,APT,SR>,
									APT extends JeeslApprovalPermissionType<APT,L,D,?>,
									AT extends JeeslApprovalTransition<L,D,AS,ATT>,
									ATT extends JeeslApprovalTransitionType<ATT,L,D,?>,
									AC extends JeeslApprovalCommunication<AT,MT,SR>,
									AA extends JeeslApprovalAction<AT,AB,AO,RE,RA>,
									AB extends JeeslApprovalBot<AB,L,D,?>,
									AO extends EjbWithId,
									MT extends JeeslIoTemplate<L,D,?,?,?,?>,
									SR extends JeeslSecurityRole<L,D,?,?,?,?,?>,
									RE extends JeeslRevisionEntity<L,D,?,?,RA>,
									RA extends JeeslRevisionAttribute<L,D,RE,?,?>,
									AL extends JeeslApprovalLink<AW,RE>,
									AW extends JeeslApprovalWorkflow<AP,AS,AY>,
									AY extends JeeslApprovalActivity<AT,AW,USER>,
									USER extends JeeslUser<SR>>
					extends UtilsFacadeBean
					implements JeeslApprovalFacade<L,D,LOC,AX,AP,AS,AST,ASP,APT,AT,ATT,AC,AA,AB,AO,MT,SR,RE,RA,AL,AW,AY,USER>
{	
	final static Logger logger = LoggerFactory.getLogger(JeeslApprovalFacadeBean.class);
	
	private final WorkflowFactoryBuilder<L,D,AX,AP,AS,AST,ASP,APT,AT,ATT,AC,AA,AB,AO,MT,SR,RE,RA,AL,AW,AY,USER> fbApproval;
	
	public JeeslApprovalFacadeBean(EntityManager em, final WorkflowFactoryBuilder<L,D,AX,AP,AS,AST,ASP,APT,AT,ATT,AC,AA,AB,AO,MT,SR,RE,RA,AL,AW,AY,USER> fbApproval)
	{
		super(em);
		this.fbApproval=fbApproval;
	}

	@Override
	public AT fTransitionBegin(AP process)
	{
		logger.warn("Optimisation required here!!");
		List<AS> stages = this.allForParent(fbApproval.getClassStage(), process);
		if(!stages.isEmpty())
		{
			List<AT> transitions = this.allForParent(fbApproval.getClassTransition(), stages.get(0));
			if(!transitions.isEmpty()) {return transitions.get(0);}
		}
		return null;
	}

	@Override
	public <W extends JeeslWithWorkflow<AW>> AL fLink(AP process, W owner) throws UtilsNotFoundException
	{
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<AL> cQ = cB.createQuery(fbApproval.getClassLink());
		Root<AL> link = cQ.from(fbApproval.getClassLink());
		
		Join<AL,AW> jWorkflow = link.join(JeeslApprovalLink.Attributes.workflow.toString());
		Path<AP> pProcess = jWorkflow.get(JeeslApprovalWorkflow.Attributes.process.toString());
		Path<Long> pRefId = link.get(JeeslApprovalLink.Attributes.refId.toString());
		
		cQ.where(cB.and(cB.equal(pRefId,owner.getId()),cB.equal(pProcess,process)));
		cQ.select(link);
		
		List<AL> links = em.createQuery(cQ).getResultList();
		
		if(!links.isEmpty())
		{
			if(links.size()==1) {return links.get(0);}
			else
			{
				logger.warn("NYI Multiple links");
				return links.get(0);
			}
		}
		else
		{
			{throw new UtilsNotFoundException("No "+fbApproval.getClassLink()+" found for "+owner);}
		}
	}
}