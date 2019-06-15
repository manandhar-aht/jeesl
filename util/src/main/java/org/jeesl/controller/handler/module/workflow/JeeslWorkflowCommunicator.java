package org.jeesl.controller.handler.module.workflow;

import java.io.IOException;
import java.io.StringWriter;
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
										AX extends JeeslWorkflowContext<AX,L,D,?>,
										AP extends JeeslWorkflowProcess<L,D,AX>,
										AS extends JeeslWorkflowStage<L,D,AP,AST>,
										AST extends JeeslWorkflowStageType<AST,?,?,?>,
										ASP extends JeeslWorkflowStagePermission<AS,APT,WML,SR>,
										APT extends JeeslWorkflowPermissionType<APT,L,D,?>,
										WML extends JeeslWorkflowModificationLevel<WML,?,?,?>,
										WT extends JeeslWorkflowTransition<L,D,AS,ATT,SR>,
										ATT extends JeeslApprovalTransitionType<ATT,L,D,?>,
										AC extends JeeslWorkflowCommunication<WT,MT,SR,RE>,
										AA extends JeeslWorkflowAction<WT,AB,AO,RE,RA>,
										AB extends JeeslWorkflowBot<AB,L,D,?>,
										AO extends EjbWithId,
										MT extends JeeslIoTemplate<L,D,?,?,MD,?>,
										MD extends JeeslIoTemplateDefinition<D,?,MT>,
										SR extends JeeslSecurityRole<L,D,?,?,?,?,USER>,
										RE extends JeeslRevisionEntity<L,D,?,?,RA>,
										RA extends JeeslRevisionAttribute<L,D,RE,?,?>,
										AW extends JeeslApprovalWorkflow<AP,AS,AY>,
										AY extends JeeslApprovalActivity<WT,AW,USER>,
										USER extends JeeslUser<SR>
										>
{
	final static Logger logger = LoggerFactory.getLogger(JeeslWorkflowCommunicator.class);
	
	private boolean debugOnInfo; public void setDebugOnInfo(boolean debugOnInfo) {this.debugOnInfo = debugOnInfo;}

	private final JeeslWorkflowMessageHandler<SR,MT,MD,AW,USER> messageHandler;
	private final FtlWorkflowModelFactory<L,D,AS,WT,AY,USER> fmFactory;
	
	private Configuration templateConfig;
	
	
	public JeeslWorkflowCommunicator(JeeslWorkflowMessageHandler<SR,MT,MD,AW,USER> messageHandler)
	{
		this.messageHandler=messageHandler;
		fmFactory = new FtlWorkflowModelFactory<>();
		templateConfig = new Configuration(Configuration.getVersion());
		debugOnInfo = false;
	}
	
	public void build(AY activity, JeeslWithWorkflow<AW> entity, List<AC> communications)
	{
		if(debugOnInfo) {logger.info("Buidling Messages for "+entity.toString());}
		Mails mails = XmlMailsFactory.build();
		for(AC communication : communications)
		{
			try {
				mails.getMail().addAll(build(activity, entity,communication).getMail());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
	
	private Mails build(AY activity, JeeslWithWorkflow<AW> entity, AC communication) throws IOException
	{
		List<MD> definitions = messageHandler.getDefinitions(communication.getTemplate());
		MD definition = definitions.get(0);
		
		Template templateHeader = new Template("name",definition.getHeader().get("en").getLang(),templateConfig);
		Template templateBody = new Template("name",definition.getDescription().get("en").getLang(),templateConfig);
		
		List<USER> recipients = messageHandler.getRecipients(entity,communication.getRole(),activity.getWorkflow());
		if(debugOnInfo) {logger.info("Building for "+recipients.size());}
		Mails mails = XmlMailsFactory.build();
		for(USER user : recipients)
		{
			try
			{
				mails.getMail().add(build(activity,entity,user,communication.getTemplate(),templateHeader,templateBody));
			}
			catch (TemplateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return mails;
	}
	
	private Mail build(AY activity, JeeslWithWorkflow<AW> entity, USER user, MT template, Template templateHeader, Template templateBody) throws TemplateException, IOException
	{
		Map<String,Object> model = fmFactory.build(activity,user);
		model.put("workflowInitiatorEmail", messageHandler.buildEmail(activity.getUser()).getEmail());
		
//				messageHandler.buildModel(template,user);
		
		StringWriter swHeader = new StringWriter();
		templateHeader.process(model,swHeader);
		swHeader.flush();
		
		StringWriter swBody = new StringWriter();
		templateBody.process(model, swBody);
		swBody.flush();
		
		EmailAddress to = messageHandler.buildEmail(user);
		Header header = XmlHeaderFactory.build(swHeader.toString(),to,to);

		return XmlMailFactory.build(header,swBody.toString());
	}
}