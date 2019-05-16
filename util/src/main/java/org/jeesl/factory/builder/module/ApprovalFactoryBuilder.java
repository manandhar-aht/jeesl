package org.jeesl.factory.builder.module;

import org.jeesl.factory.builder.AbstractFactoryBuilder;
import org.jeesl.factory.ejb.module.approval.EjbApprovalCommunicationFactory;
import org.jeesl.factory.ejb.module.approval.EjbApprovalProcessFactory;
import org.jeesl.factory.ejb.module.approval.EjbApprovalStageFactory;
import org.jeesl.factory.ejb.module.approval.EjbApprovalTransitionFactory;
import org.jeesl.interfaces.model.module.approval.JeeslApprovalCommunication;
import org.jeesl.interfaces.model.module.approval.JeeslApprovalContext;
import org.jeesl.interfaces.model.module.approval.JeeslApprovalProcess;
import org.jeesl.interfaces.model.module.approval.JeeslApprovalStage;
import org.jeesl.interfaces.model.module.approval.JeeslApprovalTransition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public class ApprovalFactoryBuilder<L extends UtilsLang, D extends UtilsDescription,
									CTX extends JeeslApprovalContext<CTX,L,D,?>,
									P extends JeeslApprovalProcess<L,D,CTX>,
									S extends JeeslApprovalStage<L,D,P>,
									T extends JeeslApprovalTransition<L,D,S>,
									C extends JeeslApprovalCommunication<T>>
				extends AbstractFactoryBuilder<L,D>
{
	final static Logger logger = LoggerFactory.getLogger(ApprovalFactoryBuilder.class);
	
	private final Class<CTX> cContext; public Class<CTX> getClassContext() {return cContext;}
	private final Class<P> cProcess; public Class<P> getClassProcess() {return cProcess;}
	private final Class<S> cStage; public Class<S> getClassStage() {return cStage;}
	private final Class<T> cTransition; public Class<T> getClassTransition() {return cTransition;}
	private final Class<C> cCommunication; public Class<C> getClassCommunication() {return cCommunication;}
	
	public ApprovalFactoryBuilder(final Class<L> cL, final Class<D> cD,
									final Class<CTX> cContext,
									final Class<P> cProcess,
									final Class<S> cStage,
									final Class<T> cTransition,
									final Class<C> cCommunication)
	{
		super(cL,cD);
		this.cContext=cContext;
		this.cProcess=cProcess;
		this.cStage=cStage;
		this.cTransition=cTransition;
		this.cCommunication=cCommunication;
	}
	
	public EjbApprovalProcessFactory<P> ejbProcess() {return new EjbApprovalProcessFactory<>(cProcess);}
	public EjbApprovalStageFactory<P,S> ejbStage() {return new EjbApprovalStageFactory<>(cStage);}
	public EjbApprovalTransitionFactory<S,T> ejbTransition() {return new EjbApprovalTransitionFactory<>(cTransition);}
	public EjbApprovalCommunicationFactory<T,C> ejbCommunication() {return new EjbApprovalCommunicationFactory<>(cCommunication);}
}