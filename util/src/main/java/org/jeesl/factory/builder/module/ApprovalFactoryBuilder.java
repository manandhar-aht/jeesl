package org.jeesl.factory.builder.module;

import org.jeesl.factory.builder.AbstractFactoryBuilder;
import org.jeesl.factory.ejb.module.approval.EjbApprovalProcessFactory;
import org.jeesl.interfaces.model.module.approval.JeeslApprovalContext;
import org.jeesl.interfaces.model.module.approval.JeeslApprovalProcess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public class ApprovalFactoryBuilder<L extends UtilsLang, D extends UtilsDescription,
									CTX extends JeeslApprovalContext<CTX,L,D,?>,
									PROCESS extends JeeslApprovalProcess<L,D,CTX>>
				extends AbstractFactoryBuilder<L,D>
{
	final static Logger logger = LoggerFactory.getLogger(ApprovalFactoryBuilder.class);
	
	private final Class<CTX> cContext; public Class<CTX> getClassContext() {return cContext;}
	private final Class<PROCESS> cProcess; public Class<PROCESS> getClassProcess() {return cProcess;}
	
	public ApprovalFactoryBuilder(final Class<L> cL, final Class<D> cD,
									final Class<CTX> cContext,
									final Class<PROCESS> cProcess)
	{
		super(cL,cD);
		this.cContext=cContext;
		this.cProcess=cProcess;
	}
	
	public EjbApprovalProcessFactory<PROCESS> ejbProcess()
	{
		return new EjbApprovalProcessFactory<PROCESS>(cProcess);
	}
}