package org.jeesl.controller.facade.module;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.jeesl.api.facade.module.JeeslApprovalFacade;
import org.jeesl.factory.builder.module.ApprovalFactoryBuilder;
import org.jeesl.interfaces.model.module.approval.JeeslApprovalAction;
import org.jeesl.interfaces.model.module.approval.JeeslApprovalBot;
import org.jeesl.interfaces.model.module.approval.JeeslApprovalCommunication;
import org.jeesl.interfaces.model.module.approval.JeeslApprovalContext;
import org.jeesl.interfaces.model.module.approval.JeeslApprovalProcess;
import org.jeesl.interfaces.model.module.approval.JeeslApprovalTransition;
import org.jeesl.interfaces.model.module.approval.JeeslApprovalTransitionType;
import org.jeesl.interfaces.model.module.approval.instance.JeeslApprovalActivity;
import org.jeesl.interfaces.model.module.approval.instance.JeeslApprovalLink;
import org.jeesl.interfaces.model.module.approval.instance.JeeslApprovalWorkflow;
import org.jeesl.interfaces.model.module.approval.instance.JeeslWithWorkflow;
import org.jeesl.interfaces.model.module.approval.stage.JeeslApprovalPermissionType;
import org.jeesl.interfaces.model.module.approval.stage.JeeslApprovalStage;
import org.jeesl.interfaces.model.module.approval.stage.JeeslApprovalStagePermission;
import org.jeesl.interfaces.model.module.approval.stage.JeeslApprovalStageType;
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
	
	private final ApprovalFactoryBuilder<L,D,AX,AP,AS,AST,ASP,APT,AT,ATT,AC,AA,AB,AO,MT,SR,RE,RA,AL,AW,AY,USER> fbApproval;
	
	public JeeslApprovalFacadeBean(EntityManager em, final ApprovalFactoryBuilder<L,D,AX,AP,AS,AST,ASP,APT,AT,ATT,AC,AA,AB,AO,MT,SR,RE,RA,AL,AW,AY,USER> fbApproval)
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
	public <W extends JeeslWithWorkflow<AW>> AW fWorkflow(Class<W> cWith, W with) throws UtilsNotFoundException
	{
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<AW> cQ = cB.createQuery(fbApproval.getClassWorkflow());
		Root<W> w = cQ.from(cWith);
		
		Path<AW> pWorkflow = w.get(JeeslWithWorkflow.Attributes.workflow.toString());
		Path<Long> pId = w.get("id");
		
		cQ.where(cB.equal(pId,with.getId()));
		cQ.select(pWorkflow);
		
		try	{return em.createQuery(cQ).getSingleResult();}
		catch (NoResultException ex){throw new UtilsNotFoundException("No Graphic found for status.id"+with);}
		catch (NonUniqueResultException ex){throw new UtilsNotFoundException("Multiple Results for status.id"+with);}
	}
}