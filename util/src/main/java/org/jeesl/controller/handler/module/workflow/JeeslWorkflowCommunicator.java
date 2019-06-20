package org.jeesl.controller.handler.module.workflow;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jeesl.factory.ftl.FtlWorkflowModelFactory;
import org.jeesl.factory.xml.system.io.mail.XmlHeaderFactory;
import org.jeesl.factory.xml.system.io.mail.XmlMailFactory;
import org.jeesl.factory.xml.system.io.mail.XmlMailsFactory;
import org.jeesl.interfaces.controller.handler.module.workflow.JeeslWorkflowMessageHandler;
import org.jeesl.interfaces.model.module.workflow.action.JeeslWorkflowAction;
import org.jeesl.interfaces.model.module.workflow.action.JeeslWorkflowBot;
import org.jeesl.interfaces.model.module.workflow.action.JeeslWorkflowCommunication;
import org.jeesl.interfaces.model.module.workflow.instance.JeeslApprovalActivity;
import org.jeesl.interfaces.model.module.workflow.instance.JeeslApprovalWorkflow;
import org.jeesl.interfaces.model.module.workflow.instance.JeeslWithWorkflow;
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
import org.jeesl.interfaces.model.system.io.mail.template.JeeslIoTemplateDefinition;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslTemplateType;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionAttribute;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntity;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.jeesl.model.xml.system.io.mail.EmailAddress;
import org.jeesl.model.xml.system.io.mail.Header;
import org.jeesl.model.xml.system.io.mail.Mail;
import org.jeesl.model.xml.system.io.mail.Mails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class JeeslWorkflowCommunicator <L extends UtilsLang, D extends UtilsDescription, LOC extends UtilsStatus<LOC,L,D>,
										WX extends JeeslWorkflowContext<WX,L,D,?>,
										WP extends JeeslWorkflowProcess<L,D,WX>,
										AS extends JeeslWorkflowStage<L,D,WP,AST>,
										AST extends JeeslWorkflowStageType<AST,?,?,?>,
										ASP extends JeeslWorkflowStagePermission<AS,APT,WML,SR>,
										APT extends JeeslWorkflowPermissionType<APT,L,D,?>,
										WML extends JeeslWorkflowModificationLevel<WML,?,?,?>,
										WT extends JeeslWorkflowTransition<L,D,AS,ATT,SR>,
										ATT extends JeeslApprovalTransitionType<ATT,L,D,?>,
										WC extends JeeslWorkflowCommunication<WT,MT,MC,SR,RE>,
										AA extends JeeslWorkflowAction<WT,AB,AO,RE,RA>,
										AB extends JeeslWorkflowBot<AB,L,D,?>,
										AO extends EjbWithId,
										MT extends JeeslIoTemplate<L,D,?,?,MD,?>,
										MC extends JeeslTemplateType<L,D,MC,?>,
										MD extends JeeslIoTemplateDefinition<D,MC,MT>,
										SR extends JeeslSecurityRole<L,D,?,?,?,?,USER>,
										RE extends JeeslRevisionEntity<L,D,?,?,RA>,
										RA extends JeeslRevisionAttribute<L,D,RE,?,?>,
										WF extends JeeslApprovalWorkflow<WP,AS,WY>,
										WY extends JeeslApprovalActivity<WT,WF,USER>,
										USER extends JeeslUser<SR>
										>
{
	final static Logger logger = LoggerFactory.getLogger(JeeslWorkflowCommunicator.class);
	
	private boolean debugOnInfo; public void setDebugOnInfo(boolean debugOnInfo) {this.debugOnInfo = debugOnInfo;}

	private final JeeslWorkflowMessageHandler<WC,SR,RE,MT,MC,MD,WF,WY,USER> messageHandler;
	private final FtlWorkflowModelFactory<L,D,WP,AS,WT,WF,WY,USER> fmFactory;
	
	private Configuration templateConfig;
	
	
	public JeeslWorkflowCommunicator(JeeslWorkflowMessageHandler<WC,SR,RE,MT,MC,MD,WF,WY,USER> messageHandler)
	{
		this.messageHandler=messageHandler;
		fmFactory = new FtlWorkflowModelFactory<>();
		templateConfig = new Configuration(Configuration.getVersion());
		debugOnInfo = false;
	}
	
	public void build(WY activity, JeeslWithWorkflow<WF> entity, List<WC> communications)
	{
		if(debugOnInfo) {logger.info("Buidling Messages for "+entity.toString());}
		Mails mails = XmlMailsFactory.build();
		for(WC communication : communications)
		{
			try
			{
				mails.getMail().addAll(build(activity, entity,communication).getMail());
			}
			catch (IOException e) {e.printStackTrace();}
		}
		for(Mail mail : mails.getMail())
		{
			try
			{
				messageHandler.spool(mail);
			}
			catch (UtilsConstraintViolationException | UtilsNotFoundException e) {e.printStackTrace();}
		}
	}
	
	private Mails build(WY activity, JeeslWithWorkflow<WF> entity, WC communication) throws IOException
	{
		List<MD> definitions = messageHandler.getDefinitions(communication.getTemplate());
		MD definition = definitions.get(0);
		
		Map<String,Template> templates = new HashMap<>();
		for(String key : definition.getHeader().keySet())
		{
			String txt = definition.getHeader().get(key).getLang();
			if(txt!=null && txt.trim().length()>0) {templates.put("h:"+key, new Template("header-"+key,txt,templateConfig));}
		}
		for(String key : definition.getDescription().keySet())
		{
			String txt = definition.getDescription().get(key).getLang();
			if(txt!=null && txt.trim().length()>0) {templates.put("b:"+key, new Template("body-"+key,txt,templateConfig));}
		}
		
		List<USER> recipients = messageHandler.getRecipients(entity,activity,communication);
		if(debugOnInfo) {logger.info("Building for "+recipients.size());}
		Mails mails = XmlMailsFactory.build();
		for(USER user : recipients)
		{
			try
			{
				String localeCode = messageHandler.localeCode(user);
				mails.getMail().add(build(activity,entity,communication,user,templates,localeCode));
			}
			catch (TemplateException e) {e.printStackTrace();}
		}
		return mails;
	}
	
	private Mail build(WY activity, JeeslWithWorkflow<WF> entity,  WC communication, USER user,  Map<String,Template> templates, String localeCode) throws TemplateException, IOException
	{
		Map<String,Object> model = fmFactory.build(localeCode,activity,user);
		model.put("wfInitiatorEmail", messageHandler.recipientEmail(activity.getUser()).getEmail());
		messageHandler.completeModel(entity,activity,communication,localeCode,model);
		fmFactory.debug(model);
		
		Template templateHeader = null;
		if(templates.containsKey("h:"+localeCode)) {templateHeader = templates.get("h:"+localeCode);}
		else {templateHeader = templates.get("h:en");}
		
		Template templateBody = null;
		if(templates.containsKey("b:"+localeCode)) {templateBody = templates.get("b:"+localeCode);}
		else {templateBody = templates.get("b:en");}
		
		StringWriter swHeader = new StringWriter();
		if(templateHeader!=null) {templateHeader.process(model,swHeader);}
		else {swHeader.append("TEMPLATE MISSING");}
		swHeader.flush();
		
		StringWriter swBody = new StringWriter();
		if(templateBody!=null) {templateBody.process(model, swBody);}
		else {swBody.append("Please contact administrators");}
		swBody.flush();
		
		EmailAddress from = messageHandler.senderEmail(activity);
		EmailAddress to = messageHandler.recipientEmail(user);
		Header header = XmlHeaderFactory.build(messageHandler.headerPrefix()+""+swHeader.toString(),from,to);

		return XmlMailFactory.build(header,swBody.toString());
	}
}