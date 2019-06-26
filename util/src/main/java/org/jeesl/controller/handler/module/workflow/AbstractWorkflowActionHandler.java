package org.jeesl.controller.handler.module.workflow;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.beanutils.BeanUtils;
import org.jeesl.exception.JeeslWorkflowException;
import org.jeesl.interfaces.controller.handler.module.workflow.JeeslWorkflowActionCallback;
import org.jeesl.interfaces.controller.handler.module.workflow.JeeslWorkflowActionHandler;
import org.jeesl.interfaces.controller.handler.module.workflow.JeeslWorkflowActionSingleHandler;
import org.jeesl.interfaces.model.module.workflow.action.JeeslWorkflowAction;
import org.jeesl.interfaces.model.module.workflow.action.JeeslWorkflowBot;
import org.jeesl.interfaces.model.module.workflow.instance.JeeslApprovalWorkflow;
import org.jeesl.interfaces.model.module.workflow.instance.JeeslWithWorkflow;
import org.jeesl.interfaces.model.system.constraint.JeeslConstraint;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionAttribute;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.exception.processing.UtilsProcessingException;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithName;

public class AbstractWorkflowActionHandler <WA extends JeeslWorkflowAction<?,AB,AO,RE,RA>,
										AB extends JeeslWorkflowBot<AB,?,?,?>,
										AO extends EjbWithId,
										RE extends JeeslRevisionEntity<?,?,?,?,RA>,
										RA extends JeeslRevisionAttribute<?,?,RE,?,?>,
										AW extends JeeslApprovalWorkflow<?,?,?>,
										WC extends JeeslConstraint<?,?,?,?,?,?,?,?>>
					implements JeeslWorkflowActionHandler<WA,AB,AO,RE,RA,AW,WC>
{
	final static Logger logger = LoggerFactory.getLogger(AbstractWorkflowActionHandler.class);
	
	private boolean debugOnInfo; @Override public void setDebugOnInfo(boolean debugOnInfo) {this.debugOnInfo = debugOnInfo;}
	private JeeslWorkflowActionCallback<WA,WC> callback;
	
	private final List<JeeslWorkflowActionSingleHandler<WA,AB,AO,RE,RA,AW,WC>> actionHandlers;

	public AbstractWorkflowActionHandler(JeeslWorkflowActionCallback<WA,WC> callback)
	{
		this.callback=callback;
		debugOnInfo = false;
		actionHandlers = new ArrayList<>();
	}
	
	public void add(JeeslWorkflowActionSingleHandler<WA,AB,AO,RE,RA,AW,WC> ah)
	{
		actionHandlers.add(ah);
	}
	
	@Override public <W extends JeeslWithWorkflow<AW>> void perform(JeeslWithWorkflow<AW> entity, List<WA> actions) throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException, UtilsProcessingException, JeeslWorkflowException
	{
		if(debugOnInfo) {logger.info("Performing Actions "+entity.toString());}
		for(WA action : actions)
		{
			perform(entity,action);
		}
		callback.workflowCallback(entity);
	}
	
	protected <W extends JeeslWithWorkflow<AW>> void perform(JeeslWithWorkflow<AW> entity, WA action) throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException, UtilsProcessingException, JeeslWorkflowException
	{
		if(debugOnInfo) {logger.info("Perform "+action.toString());}
		
		if(action.getBot().getCode().contentEquals("statusUpdate")) {statusUpdate(entity,action);}
		else if(action.getBot().getCode().contentEquals("appendRandomInt")) {appendRandomInt(entity,action);}
		else if(action.getBot().getCode().contentEquals("callbackCommand"))
		{
			for(JeeslWorkflowActionSingleHandler<WA,AB,AO,RE,RA,AW,WC> ah : actionHandlers)
			{
				ah.perform(entity, action);
			}
			callback.workflowCallback(entity,action);
		}
		else {logger.warn("Unknown Bot");}
		
	}
	
	private <W extends JeeslWithWorkflow<AW>> void statusUpdate(JeeslWithWorkflow<AW> entity, WA action)
	{
		if(debugOnInfo) {logger.info("statusUpdate "+entity.toString()+" option:"+action.getOption().toString());}
		try
		{
			BeanUtils.setProperty(entity,action.getAttribute().getCode(),action.getOption());
		}
		catch (IllegalAccessException | InvocationTargetException e)
		{
			e.printStackTrace();
		}
	}
	
	private <W extends JeeslWithWorkflow<AW>> void appendRandomInt(JeeslWithWorkflow<AW> entity, WA action)
	{
		if(entity instanceof EjbWithName)
		{
			Random rnd = new Random();
			EjbWithName ejb = (EjbWithName)entity;
			ejb.setName(ejb.getName()+" "+rnd.nextInt(10));
		}
		
	}

	@Override public <W extends JeeslWithWorkflow<AW>> void abort(JeeslWithWorkflow<AW> entity)
	{
		try
		{
			callback.workflowAbort(entity);
		}
		catch (UtilsConstraintViolationException | UtilsLockingException | UtilsNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<WC> workflowPreconditions(JeeslWithWorkflow<?> entity, List<WA> actions)
	{
		List<WC> constraints = new ArrayList<WC>();
		for(WA action : actions)
		{
			for(JeeslWorkflowActionSingleHandler<WA,AB,AO,RE,RA,AW,WC> ah : actionHandlers)
			{
				constraints.addAll(ah.workflowPreconditions(entity, action));
			}
		}
		return constraints;
	}
}