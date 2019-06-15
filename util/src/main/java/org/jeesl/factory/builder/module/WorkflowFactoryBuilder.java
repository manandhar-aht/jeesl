package org.jeesl.factory.builder.module;

import org.jeesl.factory.builder.AbstractFactoryBuilder;
import org.jeesl.factory.ejb.module.workflow.EjbWorkflowActionFactory;
import org.jeesl.factory.ejb.module.workflow.EjbWorkflowActivityFactory;
import org.jeesl.factory.ejb.module.workflow.EjbWorkflowCommunicationFactory;
import org.jeesl.factory.ejb.module.workflow.EjbWorkflowLinkFactory;
import org.jeesl.factory.ejb.module.workflow.EjbWorkflowPermissionFactory;
import org.jeesl.factory.ejb.module.workflow.EjbWorkflowProcessFactory;
import org.jeesl.factory.ejb.module.workflow.EjbWorkflowStageFactory;
import org.jeesl.factory.ejb.module.workflow.EjbWorkflowTransitionFactory;
import org.jeesl.factory.ejb.module.workflow.EjbWorkflowFactory;
import org.jeesl.interfaces.model.module.workflow.action.JeeslWorkflowAction;
import org.jeesl.interfaces.model.module.workflow.action.JeeslWorkflowBot;
import org.jeesl.interfaces.model.module.workflow.action.JeeslWorkflowCommunication;
import org.jeesl.interfaces.model.module.workflow.instance.JeeslApprovalActivity;
import org.jeesl.interfaces.model.module.workflow.instance.JeeslApprovalLink;
import org.jeesl.interfaces.model.module.workflow.instance.JeeslApprovalWorkflow;
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
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionAttribute;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntity;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class WorkflowFactoryBuilder<L extends UtilsLang, D extends UtilsDescription,
									AX extends JeeslWorkflowContext<AX,L,D,?>,
									AP extends JeeslWorkflowProcess<L,D,AX>,
									AS extends JeeslWorkflowStage<L,D,AP,AST>,
									AST extends JeeslWorkflowStageType<AST,?,?,?>,
									ASP extends JeeslWorkflowStagePermission<AS,APT,WML,SR>,
									APT extends JeeslWorkflowPermissionType<APT,L,D,?>,
									WML extends JeeslWorkflowModificationLevel<WML,?,?,?>,
									AT extends JeeslWorkflowTransition<L,D,AS,ATT>,
									ATT extends JeeslApprovalTransitionType<ATT,L,D,?>,
									AC extends JeeslWorkflowCommunication<AT,MT,SR>,
									AA extends JeeslWorkflowAction<AT,AB,AO,RE,RA>,
									AB extends JeeslWorkflowBot<AB,L,D,?>,
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
	final static Logger logger = LoggerFactory.getLogger(WorkflowFactoryBuilder.class);
	
	private final Class<AX> cContext; public Class<AX> getClassContext() {return cContext;}
	private final Class<AP> cProcess; public Class<AP> getClassProcess() {return cProcess;}
	private final Class<AS> cStage; public Class<AS> getClassStage() {return cStage;}
	private final Class<AST> cStageType; public Class<AST> getClassStageType() {return cStageType;}
	private final Class<ASP> cPermission; public Class<ASP> getClassPermission() {return cPermission;}
	private final Class<APT> cPermissionType; public Class<APT> getClassPermissionType() {return cPermissionType;}
	private final Class<WML> cModificationLevel; public Class<WML> getClassModificationLevel() {return cModificationLevel;}
	private final Class<AT> cTransition; public Class<AT> getClassTransition() {return cTransition;}
	private final Class<ATT> cTransitionType; public Class<ATT> getClassTransitionType() {return cTransitionType;}
	private final Class<AC> cCommunication; public Class<AC> getClassCommunication() {return cCommunication;}
	private final Class<AA> cAction; public Class<AA> getClassAction() {return cAction;}
	private final Class<AB> cBot; public Class<AB> getClassBot() {return cBot;}
	private final Class<AL> cLink; public Class<AL> getClassLink() {return cLink;}
	private final Class<AW> cWorkflow; public Class<AW> getClassWorkflow() {return cWorkflow;}
	private final Class<AY> cActivity; public Class<AY> getClassActivity() {return cActivity;}
	
	public WorkflowFactoryBuilder(final Class<L> cL, final Class<D> cD,
									final Class<AX> cContext,
									final Class<AP> cProcess,
									final Class<AS> cStage,
									final Class<AST> cStageType,
									final Class<ASP> cPermission,
									final Class<APT> cPermissionType,
									final Class<WML> cModificationLevel,
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
		this.cModificationLevel=cModificationLevel;
		this.cTransition=cTransition;
		this.cTransitionType=cTransitionType;
		this.cCommunication=cCommunication;
		this.cAction=cAction;
		this.cBot=cBot;
		this.cLink=cLink;
		this.cWorkflow=cWorkflow;
		this.cActivity=cActivity;
	}
	
	public EjbWorkflowProcessFactory<AP> ejbProcess() {return new EjbWorkflowProcessFactory<>(cProcess);}
	public EjbWorkflowStageFactory<AP,AS> ejbStage() {return new EjbWorkflowStageFactory<>(cStage);}
	public EjbWorkflowPermissionFactory<AS,ASP,WML,SR> ejbPermission() {return new EjbWorkflowPermissionFactory<>(cPermission);}
	public EjbWorkflowTransitionFactory<AS,AT> ejbTransition() {return new EjbWorkflowTransitionFactory<>(cTransition);}
	public EjbWorkflowCommunicationFactory<AT,AC,MT,SR> ejbCommunication() {return new EjbWorkflowCommunicationFactory<>(cCommunication);}
	public EjbWorkflowActionFactory<AT,AA,AB,AO,RE,RA> ejbAction() {return new EjbWorkflowActionFactory<>(cAction);}
	public EjbWorkflowLinkFactory<RE,AL,AW> ejbLink() {return new EjbWorkflowLinkFactory<>(cLink);}
	public EjbWorkflowFactory<AP,AS,AW> ejbWorkflow() {return new EjbWorkflowFactory<>(cWorkflow);}
	public EjbWorkflowActivityFactory<AT,AW,AY,USER> ejbActivity() {return new EjbWorkflowActivityFactory<>(cActivity);}
}