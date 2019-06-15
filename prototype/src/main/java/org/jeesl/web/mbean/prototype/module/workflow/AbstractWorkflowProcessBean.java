package org.jeesl.web.mbean.prototype.module.workflow;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.jeesl.api.bean.JeeslTranslationBean;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.api.facade.io.JeeslIoRevisionFacade;
import org.jeesl.api.facade.module.JeeslWorkflowFacade;
import org.jeesl.controller.handler.module.workflow.WorkflowProcesslResetHandler;
import org.jeesl.controller.handler.sb.SbSingleHandler;
import org.jeesl.factory.builder.io.IoRevisionFactoryBuilder;
import org.jeesl.factory.builder.io.IoTemplateFactoryBuilder;
import org.jeesl.factory.builder.module.WorkflowFactoryBuilder;
import org.jeesl.factory.builder.system.SecurityFactoryBuilder;
import org.jeesl.interfaces.bean.sb.SbSingleBean;
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
import org.jeesl.interfaces.model.module.workflow.transition.JeeslApprovalTransitionType;
import org.jeesl.interfaces.model.module.workflow.transition.JeeslWorkflowTransition;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslIoTemplate;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionAttribute;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntity;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.jeesl.util.comparator.ejb.system.security.SecurityRoleComparator;
import org.jeesl.web.mbean.prototype.admin.AbstractAdminBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPosition;
import net.sf.ahtutils.jsf.util.PositionListReorderer;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public abstract class AbstractWorkflowProcessBean <L extends UtilsLang, D extends UtilsDescription, LOC extends UtilsStatus<LOC,L,D>,
											AX extends JeeslWorkflowContext<AX,L,D,?>,
											AP extends JeeslWorkflowProcess<L,D,AX>,
											AS extends JeeslWorkflowStage<L,D,AP,AST>,
											AST extends JeeslWorkflowStageType<AST,?,?,?>,
											ASP extends JeeslWorkflowStagePermission<AS,APT,WML,SR>,
											APT extends JeeslWorkflowPermissionType<APT,L,D,?>,
											WML extends JeeslWorkflowModificationLevel<WML,?,?,?>,
											AT extends JeeslWorkflowTransition<L,D,AS,ATT,SR>,
											ATT extends JeeslApprovalTransitionType<ATT,L,D,?>,
											AC extends JeeslWorkflowCommunication<AT,MT,SR,RE>,
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
				extends AbstractAdminBean<L,D>
					implements Serializable,SbSingleBean
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractWorkflowProcessBean.class);

	private JeeslWorkflowFacade<L,D,LOC,AX,AP,AS,AST,ASP,APT,WML,AT,ATT,AC,AA,AB,AO,MT,SR,RE,RA,AL,AW,AY,USER> fWorkflow;
	private JeeslIoRevisionFacade<L,D,?,?,?,?,?,RE,?,RA,?,?> fRevision;
	
	private final WorkflowFactoryBuilder<L,D,AX,AP,AS,AST,ASP,APT,WML,AT,ATT,AC,AA,AB,AO,MT,SR,RE,RA,AL,AW,AY,USER> fbWorkflow;
	private final IoTemplateFactoryBuilder<L,D,?,?,MT,?,?,?,?> fbTemplate;
	private final IoRevisionFactoryBuilder<L,D,?,?,?,?,?,RE,?,RA,?,?> fbRevision;
	private final SecurityFactoryBuilder<L,D,?,SR,?,?,?,?,?,?,?> fbSecurity;
	
	private final SbSingleHandler<AX> sbhContext; public SbSingleHandler<AX> getSbhContext() {return sbhContext;}
	private final SbSingleHandler<AP> sbhProcess; public SbSingleHandler<AP> getSbhProcess() {return sbhProcess;}
	
	private final List<MT> templates; public List<MT> getTemplates() {return templates;}
	private final List<SR> roles; public List<SR> getRoles() {return roles;}
	private List<AS> stages; public List<AS> getStages() {return stages;} public void setStages(List<AS> stages) {this.stages = stages;}
	private final List<AST> stageTypes; public List<AST> getStageTypes() {return stageTypes;}
	private final List<ASP> permissions; public List<ASP> getPermissions() {return permissions;}
	private final List<APT> permissionTypes; public List<APT> getPermissionTypes() {return permissionTypes;}
	private final List<WML> modificationLevels; public List<WML> getModificationLevels() {return modificationLevels;}
	private final List<AT> transitions; public List<AT> getTransitions() {return transitions;}
	private final List<ATT> transitionTypes; public List<ATT> getTransitionTypes() {return transitionTypes;}
	private final List<AC> communications; public List<AC> getCommunications() {return communications;}
	private final List<AA> actions; public List<AA> getActions() {return actions;}
	private final List<AB> bots; public List<AB> getBots() {return bots;}
	protected final List<RE> entities; public List<RE> getEntities() {return entities;}
	protected final List<RE> scopes; public List<RE> getScopes() {return scopes;}
	private final List<RA> attributes; public List<RA> getAttributes() {return attributes;}
	private final List<EjbWithId> options; public List<EjbWithId> getOptions() {return options;}
	
	protected AP process; public AP getProcess() {return process;} public void setProcess(AP process) {this.process = process;}
	private AS stage; public AS getStage() {return stage;} public void setStage(AS stage) {this.stage = stage;}
	private ASP permission; public ASP getPermission() {return permission;} public void setPermission(ASP permission) {this.permission = permission;}
	private AT transition; public AT getTransition() {return transition;} public void setTransition(AT transition) {this.transition = transition;}
	private AC communication; public AC getCommunication() {return communication;} public void setCommunication(AC communication) {this.communication = communication;}
	private AA action; public AA getAction() {return action;} public void setAction(AA action) {this.action = action;}
	
	private Class<EjbWithPosition> cOption;
	private Long option; public Long getOption() {return option;} public void setOption(Long option) {this.option = option;}
	
	private boolean editStage; public boolean isEditStage() {return editStage;} public void toggleEditStage() {editStage=!editStage;reloadStageSelectOne();}
	private boolean editTransition; public boolean isEditTransition() {return editTransition;} public void toggleEditTransition() {editTransition=!editTransition;}

	private final Comparator<SR> cpRole;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public AbstractWorkflowProcessBean(final WorkflowFactoryBuilder<L,D,AX,AP,AS,AST,ASP,APT,WML,AT,ATT,AC,AA,AB,AO,MT,SR,RE,RA,AL,AW,AY,USER> fbApproval,
											final IoRevisionFactoryBuilder<L,D,?,?,?,?,?,RE,?,RA,?,?> fbRevision,
											final SecurityFactoryBuilder<L,D,?,SR,?,?,?,?,?,?,?> fbSecurity,
											final IoTemplateFactoryBuilder<L,D,?,?,MT,?,?,?,?> fbTemplate)
	{
		super(fbApproval.getClassL(),fbApproval.getClassD());
		this.fbWorkflow=fbApproval;
		this.fbRevision=fbRevision;
		this.fbSecurity=fbSecurity;
		this.fbTemplate=fbTemplate;
		
		sbhContext = new SbSingleHandler<AX>(fbApproval.getClassContext(),this);
		sbhProcess = new SbSingleHandler<AP>(fbApproval.getClassProcess(),this);
		
		roles = new ArrayList<>();
		templates = new ArrayList<>();
		stageTypes = new ArrayList<>();
		permissions = new ArrayList<>();
		permissionTypes = new ArrayList<>();
		modificationLevels = new ArrayList<>();
		transitions = new ArrayList<>();
		transitionTypes = new ArrayList<>();
		communications = new ArrayList<>();
		actions = new ArrayList<>();
		bots = new ArrayList<>();
		entities = new ArrayList<>();
		scopes = new ArrayList<>();
		attributes = new ArrayList<>();
		options = new ArrayList<>();
		
		editStage = false;
		editTransition = false;
		
		cpRole = (new SecurityRoleComparator()).factory(SecurityRoleComparator.Type.position);
	}
	
	protected void postConstructProcess(JeeslTranslationBean<L,D,LOC> bTranslation, JeeslFacesMessageBean bMessage,
										JeeslWorkflowFacade<L,D,LOC,AX,AP,AS,AST,ASP,APT,WML,AT,ATT,AC,AA,AB,AO,MT,SR,RE,RA,AL,AW,AY,USER> fApproval,
										JeeslIoRevisionFacade<L,D,?,?,?,?,?,RE,?,RA,?,?> fRevision)
	{
		super.initJeeslAdmin(bTranslation,bMessage);
		this.fWorkflow=fApproval;
		this.fRevision=fRevision;
		
		stageTypes.addAll(fApproval.allOrderedPositionVisible(fbWorkflow.getClassStageType()));
		transitionTypes.addAll(fApproval.allOrderedPositionVisible(fbWorkflow.getClassTransitionType()));
		permissionTypes.addAll(fApproval.allOrderedPositionVisible(fbWorkflow.getClassPermissionType()));
		modificationLevels.addAll(fApproval.allOrderedPositionVisible(fbWorkflow.getClassModificationLevel()));
		
		bots.addAll(fApproval.allOrderedPositionVisible(fbWorkflow.getClassBot()));
		try{initEntities();} catch (UtilsNotFoundException e) {e.printStackTrace();}
		initPageSettings();
		Collections.sort(roles,cpRole);
		
		reloadProcesses();
		if(sbhProcess.isSelected())
		{
			process = fApproval.find(fbWorkflow.getClassProcess(),sbhProcess.getSelection());
			reloadStages();
		}
	}
	
	protected abstract void initEntities() throws UtilsNotFoundException;
	
	protected void initPageSettings()
	{
		templates.addAll(fWorkflow.all(fbTemplate.getClassTemplate()));
		roles.addAll(fWorkflow.allOrderedPositionVisible(fbSecurity.getClassRole()));
		
		sbhContext.setList(fWorkflow.allOrderedPositionVisible(fbWorkflow.getClassContext()));
		sbhContext.setDefault();
		if(debugOnInfo) {logger.info(AbstractLogMessage.reloaded(fbWorkflow.getClassContext(), sbhContext.getList()));}
	}
	
	public void cancelPermission() {reset(WorkflowProcesslResetHandler.build().none().permission(true));}
	public void cancelCommunication() {reset(WorkflowProcesslResetHandler.build().none().communication(true));}
	public void cancelAction() {reset(WorkflowProcesslResetHandler.build().none().action(true));}
	private void reset(WorkflowProcesslResetHandler arh)
	{
		if(arh.isStage()) {stage=null;}
		if(arh.isPermissions()) {permissions.clear();;}
		if(arh.isPermission()) {permission=null;}
		if(arh.isTransistions()) {transitions.clear();}
		if(arh.isTransistion()) {transition=null;}
		if(arh.isCommunications()) {communications.clear();}
		if(arh.isCommunication()) {communication=null;}
		if(arh.isActions()) {actions.clear();}
		if(arh.isAction()) {action=null;}
	}
	
	@Override
	public void selectSbSingle(EjbWithId item) throws UtilsLockingException, UtilsConstraintViolationException
	{
		if(item instanceof JeeslWorkflowContext) {reloadProcesses();}
		else if(item instanceof JeeslWorkflowProcess)
		{
			process = fWorkflow.find(fbWorkflow.getClassProcess(),sbhProcess.getSelection());
			reloadStages();
		}
	}
	
	public void reloadProcesses()
	{
		sbhProcess.update(fWorkflow.all(fbWorkflow.getClassProcess()),sbhProcess.getSelection());
		if(debugOnInfo){logger.info(AbstractLogMessage.reloaded(fbWorkflow.getClassProcess(), sbhProcess.getList(),sbhContext.getSelection()));}
	}
	
	public void reloadStages()
	{
		stages = fWorkflow.allForParent(fbWorkflow.getClassStage(), process);
	}

	public void addProcess() throws UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.addEntity(fbWorkflow.getClassProcess()));
		process = fbWorkflow.ejbProcess().build();
		process.setName(efLang.createEmpty(localeCodes));
		process.setDescription(efDescription.createEmpty(localeCodes));
		process.setContext(sbhContext.getSelection());
	}
	
	public void selectProcess() throws UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.selectEntity(process));
		process = fWorkflow.find(fbWorkflow.getClassProcess(), process);
		process = efLang.persistMissingLangs(fWorkflow,localeCodes,process);
		process = efDescription.persistMissingLangs(fWorkflow,localeCodes,process);
		reloadStages();
	}
	
	public void saveProcess() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.saveEntity(process));
		process.setContext(fWorkflow.find(fbWorkflow.getClassContext(), process.getContext()));
		process = fWorkflow.save(process);
		reloadProcesses();
	}
	
	public void deleteProcess() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.rmEntity(process));
		fWorkflow.rm(process);
		reset(WorkflowProcesslResetHandler.build().all());
		reloadProcesses();
	}
	
	public void addStage()
	{
		reset(WorkflowProcesslResetHandler.build().all());
		logger.info(AbstractLogMessage.addEntity(fbWorkflow.getClassProcess()));
		stage = fbWorkflow.ejbStage().build(process,stages);
		stage.setName(efLang.createEmpty(localeCodes));
		stage.setDescription(efDescription.createEmpty(localeCodes));
		stage.setProcess(process);
		editStage = true;
	}
	
	public void saveStage() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.saveEntity(stage));
		reloadStageSelectOne();
		stage = fWorkflow.save(stage);
		reloadStages();
	}
	
	private void reloadStageSelectOne()
	{
		stage.setProcess(fWorkflow.find(fbWorkflow.getClassProcess(), stage.getProcess()));
		if(stage.getType()!=null) {stage.setType(fWorkflow.find(fbWorkflow.getClassStageType(),stage.getType()));}
	}
	
	public void selectStage() throws UtilsNotFoundException
	{
		reset(WorkflowProcesslResetHandler.build().all().stage(false));
		logger.info(AbstractLogMessage.selectEntity(stage));
		stage = fWorkflow.find(fbWorkflow.getClassStage(), stage);
		stage = efLang.persistMissingLangs(fWorkflow,localeCodes,stage);
		stage = efDescription.persistMissingLangs(fWorkflow,localeCodes,stage);
		editStage = false;
		reloadTransitions();
		reloadPermissions();
	}
	
	public void deleteStage() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.rmEntity(stage));
		fWorkflow.rm(stage);
		reset(WorkflowProcesslResetHandler.build().all());
		reloadStages();
	}
	
	private void reloadPermissions()
	{
		reset(WorkflowProcesslResetHandler.build().none().permissions(true));
		permissions.addAll(fWorkflow.allForParent(fbWorkflow.getClassPermission(),stage));
	}
	
	public void addPermission()
	{
		reset(WorkflowProcesslResetHandler.build().none().permission(true));
		logger.info(AbstractLogMessage.addEntity(fbWorkflow.getClassPermission()));
		permission = fbWorkflow.ejbPermission().build(stage,permissions);
	}
	
	public void savePermission() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.saveEntity(permission));
		permission.setRole(fWorkflow.find(fbSecurity.getClassRole(), permission.getRole()));
		permission.setType(fWorkflow.find(fbWorkflow.getClassPermissionType(), permission.getType()));
		permission.setModificationLevel(fWorkflow.find(fbWorkflow.getClassModificationLevel(), permission.getModificationLevel()));
		permission = fWorkflow.save(permission);
		reloadPermissions();
	}
	
	public void selectPermission() throws UtilsNotFoundException
	{
		reset(WorkflowProcesslResetHandler.build().none());
		logger.info(AbstractLogMessage.selectEntity(permission));
		permission = fWorkflow.find(fbWorkflow.getClassPermission(),permission);
	}
	
	public void deletePermission() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.rmEntity(permission));
		fWorkflow.rm(permission);
		reset(WorkflowProcesslResetHandler.build().none().permission(true));
		reloadPermissions();
	}
	
	private void reloadTransitions()
	{
		transitions.clear();
		transitions.addAll(fWorkflow.allForParent(fbWorkflow.getClassTransition(), stage));
	}
	
	public void addTransition()
	{
		reset(WorkflowProcesslResetHandler.build().none().transistion(true));
		logger.info(AbstractLogMessage.addEntity(fbWorkflow.getClassTransition()));
		transition = fbWorkflow.ejbTransition().build(stage,transitions);
		transition.setName(efLang.createEmpty(localeCodes));
		transition.setDescription(efDescription.createEmpty(localeCodes));
		editTransition = true;
	}
	
	public void saveTransition() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.saveEntity(transition));
		transition.setDestination(fWorkflow.find(fbWorkflow.getClassStage(), transition.getDestination()));
		transition.setType((fWorkflow.find(fbWorkflow.getClassTransitionType(), transition.getType())));
		if(transition.getRole()!=null) {transition.setRole(fWorkflow.find(fbSecurity.getClassRole(),transition.getRole()));}
		transition = fWorkflow.save(transition);
		reloadTransitions();
		reloadActions();
		reloadCommunications();
	}
	
	public void selectTransition() throws UtilsNotFoundException
	{
		reset(WorkflowProcesslResetHandler.build().none().action(true).actions(true).communication(true).communications(true));
		logger.info(AbstractLogMessage.selectEntity(transition));
		transition = fWorkflow.find(fbWorkflow.getClassTransition(),transition);
		transition = efLang.persistMissingLangs(fWorkflow,localeCodes,transition);
		transition = efDescription.persistMissingLangs(fWorkflow,localeCodes,transition);
		editTransition = false;
		reloadActions();
		reloadCommunications();
	}
	
	public void deleteTransition() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.rmEntity(transition));
		fWorkflow.rm(transition);
		reset(WorkflowProcesslResetHandler.build().none().transistion(true));
		reloadTransitions();
	}
	
	private void reloadCommunications()
	{
		reset(WorkflowProcesslResetHandler.build().none().communications(true));
		communications.addAll(fWorkflow.allForParent(fbWorkflow.getClassCommunication(),transition));
	}
	
	public void addCommunication()
	{
		reset(WorkflowProcesslResetHandler.build().none().communication(true));
		logger.info(AbstractLogMessage.addEntity(fbWorkflow.getClassTransition()));
		communication = fbWorkflow.ejbCommunication().build(transition,communications);
	}
	
	public void saveCommunication() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.saveEntity(transition));
		communication.setTemplate(fWorkflow.find(fbTemplate.getClassTemplate(), communication.getTemplate()));
		communication.setRole(fWorkflow.find(fbSecurity.getClassRole(), communication.getRole()));
		communication.setScope(fWorkflow.find(fbRevision.getClassEntity(), communication.getScope()));
		communication = fWorkflow.save(communication);
		reloadCommunications();
	}
	
	public void selectCommunication() throws UtilsNotFoundException
	{
		reset(WorkflowProcesslResetHandler.build().none());
		logger.info(AbstractLogMessage.selectEntity(transition));
		communication = fWorkflow.find(fbWorkflow.getClassCommunication(),communication);
	}
	
	public void deleteCommunication() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.rmEntity(communication));
		fWorkflow.rm(communication);
		reset(WorkflowProcesslResetHandler.build().none().communication(true));
		reloadCommunications();
	}

	private void reloadActions()
	{
		reset(WorkflowProcesslResetHandler.build().none().actions(true));
		actions.addAll(fWorkflow.allForParent(fbWorkflow.getClassAction(),transition));
	}
	
	public void addAction()
	{
		reset(WorkflowProcesslResetHandler.build().none().action(true));
		logger.info(AbstractLogMessage.addEntity(fbWorkflow.getClassAction()));
		action = fbWorkflow.ejbAction().build(transition,actions);
	}
	
	@SuppressWarnings("unchecked")
	public void saveAction() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.saveEntity(action));
		action.setBot(fWorkflow.find(fbWorkflow.getClassBot(), action.getBot()));
		
		if(action.getEntity()!=null) {action.setEntity(fWorkflow.find(fbRevision.getClassEntity(),action.getEntity()));}
		if(action.getAttribute()!=null)
		{
			action.setAttribute(fWorkflow.find(fbRevision.getClassAttribute(),action.getAttribute()));
			
			if(debugOnInfo) {logger.info("cOption!=null?"+(cOption!=null));}
			
			logger.info("Saving option:"+option+" for "+cOption.getName());
			AO id = (AO)fWorkflow.find(cOption,option);
			action.setOption(id);
		}	
		
		action = fWorkflow.save(action);
		reloadActions();
	}
	
	public void selectAction() throws UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.selectEntity(action));
		action = fWorkflow.find(fbWorkflow.getClassAction(),action);
		changeEntity();
	}
	
	public void deleteAction() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.rmEntity(action));
		fWorkflow.rm(action);
		reset(WorkflowProcesslResetHandler.build().none().action(true));
		reloadActions();
	}
	
	public void changeEntity()
	{
		logger.info(AbstractLogMessage.selectOneMenuChange(action.getEntity()));
		attributes.clear();
		if(action.getEntity()==null) {action.setAttribute(null);}
		else
		{
			action.setEntity(fWorkflow.find(fbRevision.getClassEntity(),action.getEntity()));
			action.setEntity(fRevision.load(fbRevision.getClassEntity(),action.getEntity()));
			attributes.addAll(action.getEntity().getAttributes());
			
			if(attributes.isEmpty()) {action.setAttribute(null);}
			else
			{
				if(action.getAttribute()==null) {action.setAttribute(attributes.get(0));}
				else
				{
					if(!attributes.contains(action.getAttribute())){action.setAttribute(attributes.get(0));}
				}
			}
		}
		changeAttribute();
	}
	
	@SuppressWarnings("unchecked")
	public void changeAttribute()
	{
		logger.info(AbstractLogMessage.selectOneMenuChange(action.getAttribute()));
		options.clear();
		
		if(action.getAttribute()!=null)
		{
			logger.info("The following attribute is selected: "+action.getAttribute().toString());
			logger.info("Now checking for a entity, is there something? "+(action.getAttribute().getEntity()!=null));
			action.setAttribute(fWorkflow.find(fbRevision.getClassAttribute(),action.getAttribute()));
			logger.info("After find, is there something? "+(action.getAttribute().getEntity()!=null));
		}
		
		if(action.getAttribute()!=null && action.getAttribute().getEntity()!=null)
		{
			logger.info("Evaluating "+action.getAttribute().getEntity().getCode());
			try
			{
				cOption = (Class<EjbWithPosition>)Class.forName(action.getAttribute().getEntity().getCode()).asSubclass(EjbWithPosition.class);
				options.addAll(fWorkflow.allOrderedPosition(cOption));
			}
			catch (ClassNotFoundException e) {e.printStackTrace();}
			logger.info("Options: "+options.size());
			if(action.getOption()!=null) {option = action.getOption().getId();}
			else {option=null;}
		}
	}
	
	public void reorderProcesses() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fWorkflow,sbhProcess.getList());}
	public void reorderStages() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fWorkflow,stages);}
	public void reorderPermissions() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fWorkflow,permissions);}
}