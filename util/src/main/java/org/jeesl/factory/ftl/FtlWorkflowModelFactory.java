package org.jeesl.factory.ftl;

import java.util.HashMap;
import java.util.Map;

import org.jeesl.interfaces.model.module.workflow.instance.JeeslApprovalActivity;
import org.jeesl.interfaces.model.module.workflow.instance.JeeslApprovalWorkflow;
import org.jeesl.interfaces.model.module.workflow.process.JeeslWorkflowProcess;
import org.jeesl.interfaces.model.module.workflow.stage.JeeslWorkflowStage;
import org.jeesl.interfaces.model.module.workflow.transition.JeeslWorkflowTransition;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.exlp.util.io.StringUtil;

public class FtlWorkflowModelFactory <L extends UtilsLang, D extends UtilsDescription,
										WP extends JeeslWorkflowProcess<L,D,?>,
										AS extends JeeslWorkflowStage<L,D,WP,?>,
										AT extends JeeslWorkflowTransition<L,D,AS,?,?>,
										WF extends JeeslApprovalWorkflow<WP,AS,WY>,
										WY extends JeeslApprovalActivity<AT,WF,?,USER>,
										USER extends JeeslUser<?>
>
{
	final static Logger logger = LoggerFactory.getLogger(FtlWorkflowModelFactory.class);
		
	public Map<String,Object> build(String localeCode, WY activity, USER recipient)
	{		
		Map<String,Object> model = new HashMap<String,Object>();
		
		activity(localeCode,model,activity);
		recipient(localeCode,model,activity,recipient);
		process(localeCode,model,activity.getWorkflow().getProcess());
		
		return model;
	}
	
	private void recipient(String localeCode, Map<String,Object> model, WY activity, USER recipient)
	{
		model.put("emailRecipientName", recipient.getFirstName()+" "+recipient.getLastName());
		model.put("wfRecipientStage", activity.getTransition().getDestination().getName().get(localeCode).getLang());
	}
	
	private void activity(String localeCode, Map<String,Object> model, WY activity)
	{
		model.put("wfInitiatorName", activity.getUser().getFirstName()+" "+activity.getUser().getLastName());
		model.put("wfInitiatorStage", activity.getTransition().getSource().getName().get(localeCode).getLang());
		model.put("wfActivityRemark", activity.getRemark());
		model.put("wfActivityDate", activity.getRecord().toString());
		model.put("wfActivityTransition", activity.getTransition().getName().get(localeCode).getLang());
	}
	
	private void process(String localeCode, Map<String,Object> model, WP process)
	{
		model.put("wfProcess", process.getName().get(localeCode).getLang());
	}
	
	public void debug(Map<String,Object> model)
	{
		logger.info(StringUtil.stars());
		for(String key : model.keySet())
		{
			logger.info(key+": "+model.get(key).toString());
		}
	}
}