package org.jeesl.factory.ftl;

import java.util.HashMap;
import java.util.Map;

import org.jeesl.interfaces.model.module.workflow.instance.JeeslApprovalActivity;
import org.jeesl.interfaces.model.module.workflow.stage.JeeslWorkflowStage;
import org.jeesl.interfaces.model.module.workflow.transition.JeeslWorkflowTransition;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public class FtlWorkflowModelFactory <L extends UtilsLang, D extends UtilsDescription,
										AS extends JeeslWorkflowStage<L,D,?,?>,
										AT extends JeeslWorkflowTransition<L,D,AS,?>,
										AY extends JeeslApprovalActivity<AT,?,USER>,
										USER extends JeeslUser<?>
>
{
	final static Logger logger = LoggerFactory.getLogger(FtlWorkflowModelFactory.class);
		
	public Map<String,Object> build(AY activity, USER recipient)
	{		
		Map<String,Object> model = new HashMap<String,Object>();
		
		initiator(model,activity);
		recipient(model,activity,recipient);
		
		return model;
	}
	
	private void recipient(Map<String,Object> model, AY activity, USER recipient)
	{
		model.put("workflowRecipientName", recipient.getFirstName()+" "+recipient.getLastName());
		model.put("workflowRecipientStage", activity.getTransition().getDestination().getName().get("en").getLang());
	}
	
	private void initiator(Map<String,Object> model, AY activity)
	{
		model.put("workflowInitiatorName", activity.getUser().getFirstName()+" "+activity.getUser().getLastName());
		model.put("workflowInitiatorStage", activity.getTransition().getSource().getName().get("en").getLang());
		model.put("workflowInitiatorRemark", activity.getRemark());
		model.put("workflowInitiatorDate", activity.getRecord().toString());
	}
}