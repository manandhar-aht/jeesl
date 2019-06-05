package org.jeesl.factory.builder.module;

import org.jeesl.factory.builder.AbstractFactoryBuilder;
import org.jeesl.factory.ejb.module.workflow.EjbApprovalActionFactory;
import org.jeesl.factory.ejb.module.workflow.EjbApprovalActivityFactory;
import org.jeesl.factory.ejb.module.workflow.EjbApprovalCommunicationFactory;
import org.jeesl.factory.ejb.module.workflow.EjbApprovalLinkFactory;
import org.jeesl.factory.ejb.module.workflow.EjbApprovalPermissionFactory;
import org.jeesl.factory.ejb.module.workflow.EjbApprovalProcessFactory;
import org.jeesl.factory.ejb.module.workflow.EjbApprovalStageFactory;
import org.jeesl.factory.ejb.module.workflow.EjbApprovalTransitionFactory;
import org.jeesl.factory.ejb.module.workflow.EjbApprovalWorkflowFactory;
import org.jeesl.interfaces.model.module.workflow.action.JeeslApprovalAction;
import org.jeesl.interfaces.model.module.workflow.action.JeeslApprovalBot;
import org.jeesl.interfaces.model.module.workflow.action.JeeslApprovalCommunication;
import org.jeesl.interfaces.model.module.workflow.instance.JeeslApprovalActivity;
import org.jeesl.interfaces.model.module.workflow.instance.JeeslApprovalLink;
import org.jeesl.interfaces.model.module.workflow.instance.JeeslApprovalWorkflow;
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

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class ApprovalFactoryBuilder<L extends UtilsLang, D extends UtilsDescription,
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
				extends AbstractFactoryBuilder<L,D>
{
	final static Logger logger = LoggerFactory.getLogger(ApprovalFactoryBuilder.class);
	
	private final Class<AX> cContext; public Class<AX> getClassContext() {return cContext;}
	private final Class<AP> cProcess; public Class<AP> getClassProcess() {return cProcess;}
	private final Class<AS> cStage; public Class<AS> getClassStage() {return cStage;}
	private final Class<AST> cStageType; public Class<AST> getClassStageType() {return cStageType;}
	private final Class<ASP> cPermission; public Class<ASP> getClassPermission() {return cPermission;}
	private final Class<APT> cPermissionType; public Class<APT> getClassPermissionType() {return cPermissionType;}
	private final Class<AT> cTransition; public Class<AT> getClassTransition() {return cTransition;}
	private final Class<ATT> cTransitionType; public Class<ATT> getClassTransitionType() {return cTransitionType;}
	private final Class<AC> cCommunication; public Class<AC> getClassCommunication() {return cCommunication;}
	private final Class<AA> cAction; public Class<AA> getClassAction() {return cAction;}
	private final Class<AB> cBot; public Class<AB> getClassBot() {return cBot;}
	private final Class<AL> cLink; public Class<AL> getClassLink() {return cLink;}
	private final Class<AW> cWorkflow; public Class<AW> getClassWorkflow() {return cWorkflow;}
	private final Class<AY> cActivity; public Class<AY> getClassActivity() {return cActivity;}
	
	public ApprovalFactoryBuilder(final Class<L> cL, final Class<D> cD,
									final Class<AX> cContext,
									final Class<AP> cProcess,
									final Class<AS> cStage,
									final Class<AST> cStageType,
									final Class<ASP> cPermission,
									final Class<APT> cPermissionType,
									final Class<AT> cTransition,
									final Class<ATT> cTransitionType,
									final Class<AC> cCommunication,
									final Class<AA> cAction,
									final Class<AB> cBot,
									final Class<AL> cLink,
									final Class<AW> cWorkflow,
									final Class<AY> cActivity)
	{
		super(cL,cD);
		this.cContext=cContext;
		this.cProcess=cProcess;
		this.cStage=cStage;
		this.cStageType=cStageType;
		this.cPermission=cPermission;
		this.cPermissionType=cPermissionType;
		this.cTransition=cTransition;
		this.cTransitionType=cTransitionType;
		this.cCommunication=cCommunication;
		this.cAction=cAction;
		this.cBot=cBot;
		this.cLink=cLink;
		this.cWorkflow=cWorkflow;
		this.cActivity=cActivity;
	}
	
	public EjbApprovalProcessFactory<AP> ejbProcess() {return new EjbApprovalProcessFactory<>(cProcess);}
	public EjbApprovalStageFactory<AP,AS> ejbStage() {return new EjbApprovalStageFactory<>(cStage);}
	public EjbApprovalPermissionFactory<AS,ASP,SR> ejbPermission() {return new EjbApprovalPermissionFactory<>(cPermission);}
	public EjbApprovalTransitionFactory<AS,AT> ejbTransition() {return new EjbApprovalTransitionFactory<>(cTransition);}
	public EjbApprovalCommunicationFactory<AT,AC,MT,SR> ejbCommunication() {return new EjbApprovalCommunicationFactory<>(cCommunication);}
	public EjbApprovalActionFactory<AT,AA,AB,AO,RE,RA> ejbAction() {return new EjbApprovalActionFactory<>(cAction);}
	public EjbApprovalLinkFactory<RE,AL,AW> ejbLink() {return new EjbApprovalLinkFactory<>(cLink);}
	public EjbApprovalWorkflowFactory<AP,AS,AW> ejbWorkflow() {return new EjbApprovalWorkflowFactory<>(cWorkflow);}
	public EjbApprovalActivityFactory<AT,AW,AY,USER> ejbActivity() {return new EjbApprovalActivityFactory<>(cActivity);}
}