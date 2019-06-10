package org.jeesl.mail;

import org.jeesl.api.facade.io.JeeslIoMailFacade;
import org.jeesl.api.facade.io.JeeslIoTemplateFacade;
import org.jeesl.factory.xml.system.io.mail.XmlMailFactory;
import org.jeesl.factory.xml.system.io.mail.XmlMailsFactory;
import org.jeesl.interfaces.controller.JeeslMail;
import org.jeesl.interfaces.model.system.io.mail.core.JeeslIoMail;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslIoTemplate;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslIoTemplateDefinition;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslIoTemplateToken;
import org.jeesl.model.xml.system.io.mail.EmailAddress;
import org.jeesl.model.xml.system.io.mail.Mail;
import org.jeesl.model.xml.system.io.mail.Mails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class AbstractJeeslMail<L extends UtilsLang,D extends UtilsDescription,
								CATEGORY extends UtilsStatus<CATEGORY,L,D>,
								TYPE extends UtilsStatus<TYPE,L,D>,
								TEMPLATE extends JeeslIoTemplate<L,D,CATEGORY,SCOPE,DEFINITION,TOKEN>,
								SCOPE extends UtilsStatus<SCOPE,L,D>,
								DEFINITION extends JeeslIoTemplateDefinition<D,TYPE,TEMPLATE>,
								TOKEN extends JeeslIoTemplateToken<L,D,TEMPLATE,TOKENTYPE>,
								TOKENTYPE extends UtilsStatus<TOKENTYPE,L,D>,
								MAILCAT extends UtilsStatus<MAILCAT,L,D>,
								MAIL extends JeeslIoMail<L,D,MAILCAT,MAIL,STATUS,RETENTION>,
								STATUS extends UtilsStatus<STATUS,L,D>,
								RETENTION extends UtilsStatus<RETENTION,L,D>>
							implements JeeslMail
{
	final static Logger logger = LoggerFactory.getLogger(AbstractJeeslMail.class);
	
	protected final JeeslIoTemplateFacade<L,D,CATEGORY,TYPE,TEMPLATE,SCOPE,DEFINITION,TOKEN,TOKENTYPE> fTemplate;
	protected final JeeslIoMailFacade<L,D,MAILCAT,MAIL,STATUS,RETENTION> fMail;
	

	protected TEMPLATE template;
	protected MAILCAT categoryMail;
	protected EmailAddress mailFrom;
	
	protected final Mails mails;
	
	protected String subjectPreifx;
	
	public AbstractJeeslMail(JeeslIoTemplateFacade<L,D,CATEGORY,TYPE,TEMPLATE,SCOPE,DEFINITION,TOKEN,TOKENTYPE> fTemplate, JeeslIoMailFacade<L,D,MAILCAT,MAIL,STATUS,RETENTION> fMail)
	{
		this.fTemplate=fTemplate;
		this.fMail=fMail;
		
		subjectPreifx = "";
		mails = XmlMailsFactory.build();
	}
	
	protected void spool(Mail mail) throws UtilsConstraintViolationException, UtilsNotFoundException
	{
		fMail.queueMail(categoryMail,null,mail);
		logger.info("Spooled");
	}
	
	@Override public void overrideRecipients(EmailAddress email)
	{
		for(Mail mail : mails.getMail())
		{
			XmlMailFactory.overwriteRecipients(mail,email);
		}
	}
	
	@Override public void spool()
	{
		for(Mail mail : mails.getMail())
		{
			try {fMail.queueMail(categoryMail,null,mail);}
			catch (UtilsConstraintViolationException | UtilsNotFoundException e) {e.printStackTrace();}
		}
	}
}