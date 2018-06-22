package org.jeesl.factory.builder.module;

import org.jeesl.factory.builder.AbstractFactoryBuilder;
import org.jeesl.factory.ejb.module.approval.EjbApprovalProcessFactory;
import org.jeesl.interfaces.model.module.approval.JeeslApprovalProcess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class ApprovalFactoryBuilder<L extends UtilsLang, D extends UtilsDescription,
									CAT extends UtilsStatus<CAT,L,D>,
									PROCESS extends JeeslApprovalProcess<L,D,CAT>>
				extends AbstractFactoryBuilder<L,D>
{
	final static Logger logger = LoggerFactory.getLogger(ApprovalFactoryBuilder.class);
	
	private final Class<CAT> cCategory; public Class<CAT> getClassCategory() {return cCategory;}
	private final Class<PROCESS> cProcess; public Class<PROCESS> getClassProcess() {return cProcess;}
	
	public ApprovalFactoryBuilder(final Class<L> cL, final Class<D> cD,
									final Class<CAT> cCategory,
									final Class<PROCESS> cProcess)
	{
		super(cL,cD);
		this.cCategory=cCategory;
		this.cProcess=cProcess;
	}
	
	public EjbApprovalProcessFactory<PROCESS> ejbProcess()
	{
		return new EjbApprovalProcessFactory<PROCESS>(cProcess);
	}
}