package org.jeesl.controller.facade.module;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.jeesl.api.facade.module.JeeslWorkflowFacade;
import org.jeesl.factory.builder.module.WorkflowFactoryBuilder;
import org.jeesl.interfaces.model.module.workflow.action.JeeslWorkflowAction;
import org.jeesl.interfaces.model.module.workflow.action.JeeslWorkflowBot;
import org.jeesl.interfaces.model.module.workflow.action.JeeslWorkflowCommunication;
import org.jeesl.interfaces.model.module.workflow.instance.JeeslApprovalActivity;
import org.jeesl.interfaces.model.module.workflow.instance.JeeslApprovalLink;
import org.jeesl.interfaces.model.module.workflow.instance.JeeslApprovalWorkflow;
import org.jeesl.interfaces.model.module.workflow.instance.JeeslWithWorkflow;
import org.jeesl.interfaces.model.module.workflow.process.JeeslWorkflowContext;
import org.jeesl.interfaces.model.module.workflow.process.JeeslWorkflowProcess;
import org.jeesl.interfaces.model.module.workflow.stage.JeeslWorkflowModificationLevel;
import org.jeesl.interfaces.model.module.workflow.stage.JeeslWorkflowPermissionType;
import org.jeesl.interfaces.model.module.workflow.stage.JeeslWorkflowStage;
import org.jeesl.interfaces.model.module.workflow.stage.JeeslWorkflowStagePermission;
import org.jeesl.interfaces.model.module.workflow.stage.JeeslWorkflowStageType;
import org.jeesl.interfaces.model.module.workflow.transition.JeeslWorkflowTransition;
import org.jeesl.interfaces.model.module.workflow.transition.JeeslApprovalTransitionType;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslIoTemplate;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslTemplateChannel;
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

public class JeeslWorkflowFacadeBean<L extends UtilsLang, D extends UtilsDescription, LOC extends UtilsStatus<LOC,L,D>,
									AX extends JeeslWorkflowContext<AX,L,D,?>,
									AP extends JeeslWorkflowProcess<L,D,AX>,
									AS extends JeeslWorkflowStage<L,D,AP,AST>,
									AST extends JeeslWorkflowStageType<AST,?,?,?>,
									ASP extends JeeslWorkflowStagePermission<AS,APT,WML,SR>,
									APT extends JeeslWorkflowPermissionType<APT,L,D,?>,
									WML extends JeeslWorkflowModificationLevel<WML,?,?,?>,
									WT extends JeeslWorkflowTransition<L,D,AS,ATT,SR>,
									ATT extends JeeslApprovalTransitionType<ATT,L,D,?>,
									AC extends JeeslWorkflowCommunication<WT,MT,MC,SR,RE>,
									AA extends JeeslWorkflowAction<WT,AB,AO,RE,RA>,
									AB extends JeeslWorkflowBot<AB,L,D,?>,
									AO extends EjbWithId,
									MT extends JeeslIoTemplate<L,D,?,?,?,?>,
									MC extends JeeslTemplateChannel<L,D,MC,?>,
									SR extends JeeslSecurityRole<L,D,?,?,?,?,?>,
									RE extends JeeslRevisionEntity<L,D,?,?,RA>,
									RA extends JeeslRevisionAttribute<L,D,RE,?,?>,
									AL extends JeeslApprovalLink<AW,RE>,
									AW extends JeeslApprovalWorkflow<AP,AS,AY>,
									AY extends JeeslApprovalActivity<WT,AW,USER>,
									USER extends JeeslUser<SR>>
					extends UtilsFacadeBean
					implements JeeslWorkflowFacade<L,D,LOC,AX,AP,AS,AST,ASP,APT,WML,WT,ATT,AC,AA,AB,AO,MT,MC,SR,RE,RA,AL,AW,AY,USER>
{	
	private static final long serialVersionUID = 1L;

	final static Logger logger = LoggerFactory.getLogger(JeeslWorkflowFacadeBean.class);
	
	private final WorkflowFactoryBuilder<L,D,AX,AP,AS,AST,ASP,APT,WML,WT,ATT,AC,AA,AB,AO,MT,MC,SR,RE,RA,AL,AW,AY,USER> fbApproval;
	
	public JeeslWorkflowFacadeBean(EntityManager em, final WorkflowFactoryBuilder<L,D,AX,AP,AS,AST,ASP,APT,WML,WT,ATT,AC,AA,AB,AO,MT,MC,SR,RE,RA,AL,AW,AY,USER> fbApproval)
	{
		super(em);
		this.fbApproval=fbApproval;
	}

	@Override
	public WT fTransitionBegin(AP process)
	{
		logger.warn("Optimisation required here!!");
		List<AS> stages = this.allForParent(fbApproval.getClassStage(), process);
		if(!stages.isEmpty())
		{
			List<WT> transitions = this.allForParent(fbApproval.getClassTransition(), stages.get(0));
			for(WT t : transitions)
			{
				if(!t.getType().getCode().equals(JeeslApprovalTransitionType.Code.migration.toString())) {return t;}
			}
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