package org.jeesl.web.mbean.prototype.module.approval;

import java.io.Serializable;

import org.jeesl.api.bean.JeeslTranslationBean;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.api.facade.module.JeeslApprovalFacade;
import org.jeesl.controller.handler.sb.SbSingleHandler;
import org.jeesl.factory.builder.module.ApprovalFactoryBuilder;
import org.jeesl.interfaces.bean.sb.SbSingleBean;
import org.jeesl.interfaces.model.module.approval.JeeslApprovalContext;
import org.jeesl.interfaces.model.module.approval.JeeslApprovalProcess;
import org.jeesl.web.mbean.prototype.admin.AbstractAdminBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.jsf.util.PositionListReorderer;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractAdminApprovalProcessBean <L extends UtilsLang, D extends UtilsDescription, LOC extends UtilsStatus<LOC,L,D>,
											CTX extends JeeslApprovalContext<CTX,L,D,?>,
											PROCESS extends JeeslApprovalProcess<L,D,CTX>
											>
				extends AbstractAdminBean<L,D>
					implements Serializable,SbSingleBean
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminApprovalProcessBean.class);

	protected JeeslApprovalFacade<L,D,CTX,PROCESS> fApproval;
	protected final ApprovalFactoryBuilder<L,D,CTX,PROCESS> fbApproval;
	
	private final SbSingleHandler<CTX> sbhContext; public SbSingleHandler<CTX> getSbhContext() {return sbhContext;}
	private final SbSingleHandler<PROCESS> sbhProcess; public SbSingleHandler<PROCESS> getSbhProcess() {return sbhProcess;}
		
	protected PROCESS process; public PROCESS getProcess() {return process;} public void setProcess(PROCESS process) {this.process = process;}

	public AbstractAdminApprovalProcessBean(final ApprovalFactoryBuilder<L,D,CTX,PROCESS> fbApproval)
	{
		super(fbApproval.getClassL(),fbApproval.getClassD());
		this.fbApproval=fbApproval;
		sbhContext = new SbSingleHandler<CTX>(fbApproval.getClassContext(),this);
		sbhProcess = new SbSingleHandler<PROCESS>(fbApproval.getClassProcess(),this);
	}
	
	protected void postConstructProcess(JeeslTranslationBean<L,D,LOC> bTranslation, JeeslApprovalFacade<L,D,CTX,PROCESS> fApproval, JeeslFacesMessageBean bMessage)
	{
		super.initJeeslAdmin(bTranslation, bMessage);
		this.fApproval=fApproval;
		reloadContexts();
		reloadProcesses();
	}
	
	private void reloadContexts()
	{
		sbhContext.setList(fApproval.allOrderedPositionVisible(fbApproval.getClassContext()));
		sbhContext.setDefault();
		if(debugOnInfo) {logger.info(AbstractLogMessage.reloaded(fbApproval.getClassContext(), sbhContext.getList()));}
	}
	
	public void reloadProcesses()
	{
		sbhProcess.update(fApproval.all(fbApproval.getClassProcess()),sbhProcess.getSelection());
		if(debugOnInfo){logger.info(AbstractLogMessage.reloaded(fbApproval.getClassProcess(), sbhProcess.getList(),sbhContext.getSelection()));}
	}
	
	@Override
	public void selectSbSingle(EjbWithId item) throws UtilsLockingException, UtilsConstraintViolationException
	{
		if(item instanceof JeeslApprovalContext) {reloadProcesses();}
		else if(item instanceof JeeslApprovalProcess)
		{
			process = fApproval.find(fbApproval.getClassProcess(),sbhProcess.getSelection());
		}
		
	}
	
	private void reset(boolean rProcess)
	{
		if(rProcess) {process=null;}
	}
	

	public void addProcess() throws UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.addEntity(fbApproval.getClassProcess()));
		process = fbApproval.ejbProcess().build();
		process.setName(efLang.createEmpty(localeCodes));
		process.setDescription(efDescription.createEmpty(localeCodes));
		process.setContext(sbhContext.getSelection());
	}
	
	public void selectProcess() throws UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.selectEntity(process));
		process = fApproval.find(fbApproval.getClassProcess(), process);
		process = efLang.persistMissingLangs(fApproval,localeCodes,process);
		process = efDescription.persistMissingLangs(fApproval,localeCodes,process);
	}
	
	public void saveProcess() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.saveEntity(process));
//		process.setCategory(fTs.find(fbTs.getClassCategory(), scope.getCategory()));
		process = fApproval.save(process);
		reloadProcesses();
	}
	
	public void deleteProcess() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.rmEntity(process));
		fApproval.rm(process);
		reset(true);
		reloadProcesses();
	}
	
	protected void reorderProcesses() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fApproval,sbhProcess.getList());}
}