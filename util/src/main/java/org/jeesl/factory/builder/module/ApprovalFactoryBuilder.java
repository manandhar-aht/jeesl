package org.jeesl.factory.builder.module;

import org.jeesl.factory.builder.AbstractFactoryBuilder;
import org.jeesl.factory.ejb.module.approval.EjbApprovalActionFactory;
import org.jeesl.factory.ejb.module.approval.EjbApprovalCommunicationFactory;
import org.jeesl.factory.ejb.module.approval.EjbApprovalProcessFactory;
import org.jeesl.factory.ejb.module.approval.EjbApprovalStageFactory;
import org.jeesl.factory.ejb.module.approval.EjbApprovalTransitionFactory;
import org.jeesl.interfaces.model.module.approval.JeeslApprovalAction;
import org.jeesl.interfaces.model.module.approval.JeeslApprovalBot;
import org.jeesl.interfaces.model.module.approval.JeeslApprovalCommunication;
import org.jeesl.interfaces.model.module.approval.JeeslApprovalContext;
import org.jeesl.interfaces.model.module.approval.JeeslApprovalProcess;
import org.jeesl.interfaces.model.module.approval.JeeslApprovalStage;
import org.jeesl.interfaces.model.module.approval.JeeslApprovalTransition;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslIoTemplate;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionAttribute;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntity;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class ApprovalFactoryBuilder<L extends UtilsLang, D extends UtilsDescription,
									CTX extends JeeslApprovalContext<CTX,L,D,?>,
									AP extends JeeslApprovalProcess<L,D,CTX>,
									S extends JeeslApprovalStage<L,D,AP>,
									AT extends JeeslApprovalTransition<L,D,S>,
									AC extends JeeslApprovalCommunication<AT,MT,MR>,
									AA extends JeeslApprovalAction<AT,AB,AO,RE,RA>,
									AB extends JeeslApprovalBot<AB,L,D,?>,
									AO extends EjbWithId,
									MT extends JeeslIoTemplate<L,D,?,?,?,?>,
									MR extends JeeslSecurityRole<L,D,?,?,?,?,?>,
									RE extends JeeslRevisionEntity<L,D,?,?,RA>,
									RA extends JeeslRevisionAttribute<L,D,RE,?,?>>
				extends AbstractFactoryBuilder<L,D>
{
	final static Logger logger = LoggerFactory.getLogger(ApprovalFactoryBuilder.class);
	
	private final Class<CTX> cContext; public Class<CTX> getClassContext() {return cContext;}
	private final Class<AP> cProcess; public Class<AP> getClassProcess() {return cProcess;}
	private final Class<S> cStage; public Class<S> getClassStage() {return cStage;}
	private final Class<AT> cTransition; public Class<AT> getClassTransition() {return cTransition;}
	private final Class<AC> cCommunication; public Class<AC> getClassCommunication() {return cCommunication;}
	private final Class<AA> cAction; public Class<AA> getClassAction() {return cAction;}
	private final Class<AB> cBot; public Class<AB> getClassBot() {return cBot;}
	
	public ApprovalFactoryBuilder(final Class<L> cL, final Class<D> cD,
									final Class<CTX> cContext,
									final Class<AP> cProcess,
									final Class<S> cStage,
									final Class<AT> cTransition,
									final Class<AC> cCommunication,
									final Class<AA> cAction,
									final Class<AB> cBot)
	{
		super(cL,cD);
		this.cContext=cContext;
		this.cProcess=cProcess;
		this.cStage=cStage;
		this.cTransition=cTransition;
		this.cCommunication=cCommunication;
		this.cAction=cAction;
		this.cBot=cBot;
	}
	
	public EjbApprovalProcessFactory<AP> ejbProcess() {return new EjbApprovalProcessFactory<>(cProcess);}
	public EjbApprovalStageFactory<AP,S> ejbStage() {return new EjbApprovalStageFactory<>(cStage);}
	public EjbApprovalTransitionFactory<S,AT> ejbTransition() {return new EjbApprovalTransitionFactory<>(cTransition);}
	public EjbApprovalCommunicationFactory<AT,AC,MT,MR> ejbCommunication() {return new EjbApprovalCommunicationFactory<>(cCommunication);}
	public EjbApprovalActionFactory<AT,AA,AB,AO,RE,RA> ejbAction() {return new EjbApprovalActionFactory<>(cAction);}
}