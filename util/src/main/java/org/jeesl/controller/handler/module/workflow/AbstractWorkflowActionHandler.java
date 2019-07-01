package org.jeesl.controller.handler.module.workflow;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.beanutils.BeanUtils;
import org.jeesl.api.bean.msg.JeeslConstraintsBean;
import org.jeesl.exception.JeeslWorkflowException;
import org.jeesl.interfaces.controller.handler.module.workflow.JeeslWorkflowActionCallback;
import org.jeesl.interfaces.controller.handler.module.workflow.JeeslWorkflowActionHandler;
import org.jeesl.interfaces.controller.handler.module.workflow.JeeslWorkflowActionsHandler;
import org.jeesl.interfaces.model.module.workflow.action.JeeslWorkflowAction;
import org.jeesl.interfaces.model.module.workflow.action.JeeslWorkflowBot;
import org.jeesl.interfaces.model.module.workflow.instance.JeeslApprovalWorkflow;
import org.jeesl.interfaces.model.module.workflow.instance.JeeslWithWorkflow;
import org.jeesl.interfaces.model.module.workflow.transition.JeeslWorkflowTransition;
import org.jeesl.interfaces.model.system.constraint.JeeslConstraint;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionAttribute;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntity;
import org.jeesl.util.comparator.pojo.BooleanComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.exception.processing.UtilsProcessingException;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithName;

public abstract class AbstractWorkflowActionHandler <WT extends JeeslWorkflowTransition<?,?,?,?,?>,
										WA extends JeeslWorkflowAction<?,AB,AO,RE,RA>,
										AB extends JeeslWorkflowBot<AB,?,?,?>,
										AO extends EjbWithId,
										RE extends JeeslRevisionEntity<?,?,?,?,RA>,
										RA extends JeeslRevisionAttribute<?,?,RE,?,?>,
										AW extends JeeslApprovalWorkflow<?,?,?>,
										WC extends JeeslConstraint<?,?,?,?,WC,?,?,?>>
					implements JeeslWorkflowActionsHandler<WT,WA,AB,AO,RE,RA,AW,WC>
{
	final static Logger logger = LoggerFactory.getLogger(AbstractWorkflowActionHandler.class);
	
	private boolean debugOnInfo; @Override public void setDebugOnInfo(boolean debugOnInfo) {this.debugOnInfo = debugOnInfo;}
	
	protected final JeeslConstraintsBean<WC> bConstraint;
	private final JeeslWorkflowActionCallback<WA> callback;

	private final List<JeeslWorkflowActionHandler<WA,AB,AO,RE,RA,AW,WC>> actionHandlers;

	public AbstractWorkflowActionHandler(JeeslConstraintsBean<WC> bConstraint,
									JeeslWorkflowActionCallback<WA> callback,
									JeeslWorkflowActionHandler<WA,AB,AO,RE,RA,AW,WC> actionHandler)
	{
		this.bConstraint=bConstraint;
		this.callback=callback;
		actionHandlers = new ArrayList<>();
		actionHandlers.add(actionHandler);
	}
	
	@Override public void checkRemark(List<WC> constraints, WT transition, String remark)
	{
		if(debugOnInfo) {logger.info("Checking remark for "+transition.getClass().getSimpleName()+" mandatory:"+transition.getRemarkMandatory());}
		if(BooleanComparator.active(transition.getRemarkMandatory()))
		{
			if(remark==null || remark.trim().isEmpty())
			{
				if(debugOnInfo) {logger.info("Adding a constraint");}
				try{constraints.add(bConstraint.get(JeeslWorkflowAction.class, JeeslWorkflowAction.Constraint.remarkEmpty));}
				catch (UtilsNotFoundException e) {constraints.add(this.getConstraintNotFound());}
			}
		}
	}
	
	@Override public <W extends JeeslWithWorkflow<AW>> JeeslWithWorkflow<AW> perform(JeeslWithWorkflow<AW> entity, List<WA> actions) throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException, UtilsProcessingException, JeeslWorkflowException
	{
		if(debugOnInfo) {logger.info("Performing Actions "+entity.toString());}
		for(WA action : actions)
		{
			perform(entity,action);
		}
		callback.workflowCallback(entity);
		return entity;
	}
	
	protected <W extends JeeslWithWorkflow<AW>> JeeslWithWorkflow<AW> perform(JeeslWithWorkflow<AW> entity, WA action) throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException, UtilsProcessingException, JeeslWorkflowException
	{
		if(debugOnInfo) {logger.info("Perform "+action.toString());}
		
		if(action.getBot().getCode().contentEquals("statusUpdate"))
		{
			statusUpdate(entity,action);
			for(JeeslWorkflowActionHandler<WA,AB,AO,RE,RA,AW,WC> ah : actionHandlers)
			{
				entity = ah.statusUpdated(entity);
			}
			
		}
		else if(action.getBot().getCode().contentEquals("callbackCommand"))
		{
			for(JeeslWorkflowActionHandler<WA,AB,AO,RE,RA,AW,WC> ah : actionHandlers)
			{
				entity = ah.perform(entity,action);
			}
		}
		else if(action.getBot().getCode().contentEquals("appendRandomInt")) {appendRandomInt(entity,action);}
		else {logger.warn("Unknown Bot");}
		return entity;
		
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

	@Override public void checkPreconditions(List<WC> constraints, JeeslWithWorkflow<?> entity, List<WA> actions)
	{
		for(JeeslWorkflowActionHandler<WA,AB,AO,RE,RA,AW,WC> ah : actionHandlers)
		{
			for(WA action : actions)
			{
				try
				{
					ah.workflowPreconditions(constraints,entity, action);
				}
				catch (UtilsNotFoundException e)
				{
					WC c = getConstraintNotFound();
					c.setContextMessage(e.getMessage());
					constraints.add(c);
				}
			}
		}
	}
	
	protected abstract WC getConstraintNotFound();
}