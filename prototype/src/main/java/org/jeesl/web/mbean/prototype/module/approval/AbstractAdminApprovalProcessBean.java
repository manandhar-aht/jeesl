package org.jeesl.web.mbean.prototype.module.approval;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import org.jeesl.api.bean.JeeslTranslationBean;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.api.facade.module.JeeslApprovalFacade;
import org.jeesl.factory.builder.module.ApprovalFactoryBuilder;
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
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractAdminApprovalProcessBean <L extends UtilsLang, D extends UtilsDescription,
											CAT extends UtilsStatus<CAT,L,D>,
											PROCESS extends JeeslApprovalProcess<L,D,CAT>
											>
				extends AbstractAdminApprovalBean<L,D,CAT,PROCESS>
					implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminApprovalProcessBean.class);

	
	protected List<PROCESS> processes; public List<PROCESS> getProcesses() {return processes;}
	
	protected PROCESS process; public PROCESS getProcess() {return process;} public void setProcess(PROCESS process) {this.process = process;}

	public AbstractAdminApprovalProcessBean(final ApprovalFactoryBuilder<L,D,CAT,PROCESS> fbApproval)
	{
		super(fbApproval);
	}
	
	protected void postConstructProcess(JeeslTranslationBean bTranslation, JeeslApprovalFacade<L,D,CAT,PROCESS> fApproval, JeeslFacesMessageBean bMessage)
	{
		super.postConstructApproval(bTranslation,fApproval,bMessage);
		reloadProcesses();
	}
	
	private void reset(boolean rProcess)
	{
		if(rProcess) {process=null;}
	}
	
	public void reloadProcesses()
	{
		if(debugOnInfo){logger.info("reloadScopes");}
		processes = fApproval.all(fbApproval.getClassProcess());
	}
	
	public void add() throws UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.addEntity(fbApproval.getClassProcess()));
		process = fbApproval.ejbProcess().build();
		process.setName(efLang.createEmpty(localeCodes));
		process.setDescription(efDescription.createEmpty(localeCodes));
	}
	
	public void select() throws UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.selectEntity(process));
		process = fApproval.find(fbApproval.getClassProcess(), process);
		process = efLang.persistMissingLangs(fApproval,localeCodes,process);
		process = efDescription.persistMissingLangs(fApproval,localeCodes,process);
	}
	
	public void save() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.saveEntity(process));
//		process.setCategory(fTs.find(fbTs.getClassCategory(), scope.getCategory()));
		process = fApproval.save(process);
		reloadProcesses();
	}
	
	public void rm() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.rmEntity(process));
		fApproval.rm(process);
		reset(true);
		reloadProcesses();
	}


	
//	protected void reorderProcesses() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fTs, fbTs.getClassScope(), scopes);Collections.sort(scopes, comparatorScope);}
}