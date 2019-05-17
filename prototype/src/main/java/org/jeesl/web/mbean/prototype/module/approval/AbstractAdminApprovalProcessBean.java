package org.jeesl.web.mbean.prototype.module.approval;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.jeesl.api.bean.JeeslTranslationBean;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.api.facade.module.JeeslApprovalFacade;
import org.jeesl.controller.handler.sb.SbSingleHandler;
import org.jeesl.factory.builder.io.IoTemplateFactoryBuilder;
import org.jeesl.factory.builder.module.ApprovalFactoryBuilder;
import org.jeesl.factory.builder.system.SecurityFactoryBuilder;
import org.jeesl.interfaces.bean.sb.SbSingleBean;
import org.jeesl.interfaces.model.module.approval.JeeslApprovalCommunication;
import org.jeesl.interfaces.model.module.approval.JeeslApprovalContext;
import org.jeesl.interfaces.model.module.approval.JeeslApprovalProcess;
import org.jeesl.interfaces.model.module.approval.JeeslApprovalStage;
import org.jeesl.interfaces.model.module.approval.JeeslApprovalTransition;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslIoTemplate;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
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
											AP extends JeeslApprovalProcess<L,D,CTX>,
											S extends JeeslApprovalStage<L,D,AP>,
											T extends JeeslApprovalTransition<L,D,S>,
											C extends JeeslApprovalCommunication<T,MT,SR>,
											MT extends JeeslIoTemplate<L,D,?,?,?,?>,
											SR extends JeeslSecurityRole<L,D,?,?,?,?,?>
											>
				extends AbstractAdminBean<L,D>
					implements Serializable,SbSingleBean
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminApprovalProcessBean.class);

	protected JeeslApprovalFacade<L,D,CTX,AP,S,T,C,MT,SR> fApproval;
	
	private final ApprovalFactoryBuilder<L,D,CTX,AP,S,T,C,MT,SR> fbApproval;
	private final IoTemplateFactoryBuilder<L,D,?,?,MT,?,?,?,?> fbTemplate;
	private final SecurityFactoryBuilder<L,D,?,SR,?,?,?,?,?,?,?> fbSecurity;
	
	private final SbSingleHandler<CTX> sbhContext; public SbSingleHandler<CTX> getSbhContext() {return sbhContext;}
	private final SbSingleHandler<AP> sbhProcess; public SbSingleHandler<AP> getSbhProcess() {return sbhProcess;}
	
	private final List<MT> templates; public List<MT> getTemplates() {return templates;}
	private final List<SR> roles; public List<SR> getRoles() {return roles;}
	private List<S> stages; public List<S> getStages() {return stages;} public void setStages(List<S> stages) {this.stages = stages;}
	private final List<T> transitions; public List<T> getTransitions() {return transitions;}
	private final List<C> communications; public List<C> getCommunications() {return communications;}
	
	
	protected AP process; public AP getProcess() {return process;} public void setProcess(AP process) {this.process = process;}
	private S stage; public S getStage() {return stage;} public void setStage(S stage) {this.stage = stage;}
	private T transition; public T getTransition() {return transition;} public void setTransition(T transition) {this.transition = transition;}
	private C communication; public C getCommunication() {return communication;} public void setCommunication(C communication) {this.communication = communication;}
	
	private boolean editStage; public boolean isEditStage() {return editStage;} public void toggleEditStage() {editStage=!editStage;}
	private boolean editTransition; public boolean isEditTransition() {return editTransition;} public void toggleEditTransition() {editTransition=!editTransition;}

	public AbstractAdminApprovalProcessBean(final ApprovalFactoryBuilder<L,D,CTX,AP,S,T,C,MT,SR> fbApproval,
											final SecurityFactoryBuilder<L,D,?,SR,?,?,?,?,?,?,?> fbSecurity,
											final IoTemplateFactoryBuilder<L,D,?,?,MT,?,?,?,?> fbTemplate)
	{
		super(fbApproval.getClassL(),fbApproval.getClassD());
		this.fbApproval=fbApproval;
		this.fbSecurity=fbSecurity;
		this.fbTemplate=fbTemplate;
		
		sbhContext = new SbSingleHandler<CTX>(fbApproval.getClassContext(),this);
		sbhProcess = new SbSingleHandler<AP>(fbApproval.getClassProcess(),this);
		
		roles = new ArrayList<>();
		templates = new ArrayList<>();
		transitions = new ArrayList<>();
		communications = new ArrayList<>();
		
		editStage = false;
		editTransition = false;
	}
	
	protected void postConstructProcess(JeeslTranslationBean<L,D,LOC> bTranslation, JeeslApprovalFacade<L,D,CTX,AP,S,T,C,MT,SR> fApproval, JeeslFacesMessageBean bMessage)
	{
		super.initJeeslAdmin(bTranslation, bMessage);
		this.fApproval=fApproval;
		
		initPageSettings();
		reloadProcesses();
		if(sbhProcess.isSelected())
		{
			process = fApproval.find(fbApproval.getClassProcess(),sbhProcess.getSelection());
			reloadStages();
		}
	}
	
	protected void initPageSettings()
	{
		templates.addAll(fApproval.all(fbTemplate.getClassTemplate()));
		roles.addAll(fApproval.allOrderedPositionVisible(fbSecurity.getClassRole()));
		
		sbhContext.setList(fApproval.allOrderedPositionVisible(fbApproval.getClassContext()));
		sbhContext.setDefault();
		if(debugOnInfo) {logger.info(AbstractLogMessage.reloaded(fbApproval.getClassContext(), sbhContext.getList()));}
	}
	
	public void cancelCommunication() {reset(false,false,false,false,true);}
	private void reset(boolean rStage, boolean rTransitions, boolean rTransition, boolean rCommunications, boolean rCommunication)
	{
		if(rStage) {stage=null;}
		if(rTransitions) {transitions.clear();}
		if(rTransition) {transition=null;}
		if(rCommunications) {communications.clear();}
		if(rCommunication) {communication=null;}
	}
	
	@Override
	public void selectSbSingle(EjbWithId item) throws UtilsLockingException, UtilsConstraintViolationException
	{
		if(item instanceof JeeslApprovalContext) {reloadProcesses();}
		else if(item instanceof JeeslApprovalProcess)
		{
			process = fApproval.find(fbApproval.getClassProcess(),sbhProcess.getSelection());
			reloadStages();
		}
	}
	
	public void reloadProcesses()
	{
		sbhProcess.update(fApproval.all(fbApproval.getClassProcess()),sbhProcess.getSelection());
		if(debugOnInfo){logger.info(AbstractLogMessage.reloaded(fbApproval.getClassProcess(), sbhProcess.getList(),sbhContext.getSelection()));}
	}
	
	public void reloadStages()
	{
		stages = fApproval.allForParent(fbApproval.getClassStage(), process);
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
		reloadStages();
	}
	
	public void saveProcess() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.saveEntity(process));
		process.setContext(fApproval.find(fbApproval.getClassContext(), process.getContext()));
		process = fApproval.save(process);
		reloadProcesses();
	}
	
	public void deleteProcess() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.rmEntity(process));
		fApproval.rm(process);
		reset(true,true,true,true,true);
		reloadProcesses();
	}
	
	public void addStage()
	{
		reset(true,true,true,true,true);
		logger.info(AbstractLogMessage.addEntity(fbApproval.getClassProcess()));
		stage = fbApproval.ejbStage().build(process,stages);
		stage.setName(efLang.createEmpty(localeCodes));
		stage.setDescription(efDescription.createEmpty(localeCodes));
		stage.setProcess(process);
		editStage = true;
	}
	
	public void saveStage() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.saveEntity(stage));
		stage.setProcess(fApproval.find(fbApproval.getClassProcess(), stage.getProcess()));
		stage = fApproval.save(stage);
		reloadStages();
	}
	
	public void selectStage() throws UtilsNotFoundException
	{
		reset(false,true,true,true,true);
		logger.info(AbstractLogMessage.selectEntity(stage));
		stage = fApproval.find(fbApproval.getClassStage(), stage);
		stage = efLang.persistMissingLangs(fApproval,localeCodes,stage);
		stage = efDescription.persistMissingLangs(fApproval,localeCodes,stage);
		editStage = false;
		reloadTransitions();
	}
	
	public void deleteStage() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.rmEntity(stage));
		fApproval.rm(stage);
		reset(true,false,true,true,true);
		reloadStages();
	}
	
	private void reloadTransitions()
	{
		transitions.clear();
		transitions.addAll(fApproval.allForParent(fbApproval.getClassTransition(), stage));
	}
	
	public void addTransition()
	{
		reset(false,false,true,true,true);
		logger.info(AbstractLogMessage.addEntity(fbApproval.getClassTransition()));
		transition = fbApproval.ejbTransition().build(stage,transitions);
		transition.setName(efLang.createEmpty(localeCodes));
		transition.setDescription(efDescription.createEmpty(localeCodes));
		editTransition = true;
	}
	
	public void saveTransition() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.saveEntity(transition));
		transition.setDestination(fApproval.find(fbApproval.getClassStage(), transition.getDestination()));
		transition = fApproval.save(transition);
		reloadTransitions();
		reloadCommunications();
	}
	
	public void selectTransition() throws UtilsNotFoundException
	{
		reset(false,false,false,true,true);
		logger.info(AbstractLogMessage.selectEntity(transition));
		transition = fApproval.find(fbApproval.getClassTransition(),transition);
		transition = efLang.persistMissingLangs(fApproval,localeCodes,transition);
		transition = efDescription.persistMissingLangs(fApproval,localeCodes,transition);
		editTransition = false;
		reloadCommunications();
	}
	
	public void deleteTransition() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.rmEntity(transition));
		fApproval.rm(transition);
		reset(false,false,true,true,true);
		reloadTransitions();
	}
	
	private void reloadCommunications()
	{
		communications.clear();
		communications.addAll(fApproval.allForParent(fbApproval.getClassCommunication(),transition));
	}
	
	public void addCommunication()
	{
		reset(false,false,false,false,true);
		logger.info(AbstractLogMessage.addEntity(fbApproval.getClassTransition()));
		communication = fbApproval.ejbCommunication().build(transition,communications);
	}
	
	public void saveCommunication() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.saveEntity(transition));
		communication.setTemplate(fApproval.find(fbTemplate.getClassTemplate(), communication.getTemplate()));
		communication.setRole(fApproval.find(fbSecurity.getClassRole(), communication.getRole()));
		communication = fApproval.save(communication);
		reloadCommunications();
	}
	
	public void selectCommunication() throws UtilsNotFoundException
	{
		reset(false,false,false,false,false);
		logger.info(AbstractLogMessage.selectEntity(transition));
		communication = fApproval.find(fbApproval.getClassCommunication(),communication);
	}
	
	public void deleteCommunication() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.rmEntity(communication));
		fApproval.rm(communication);
		reset(false,false,false,true,true);
		reloadCommunications();
	}
	
	public void reorderProcesses() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fApproval,sbhProcess.getList());}
	public void reorderStages() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fApproval,stages);}
}