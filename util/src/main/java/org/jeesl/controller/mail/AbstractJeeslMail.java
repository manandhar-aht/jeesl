package org.jeesl.controller.mail;

import org.jeesl.api.facade.io.JeeslIoMailFacade;
import org.jeesl.interfaces.model.system.io.mail.core.JeeslIoMail;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslIoTemplate;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslIoTemplateDefinition;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslIoTemplateToken;
import org.jeesl.model.xml.system.io.mail.EmailAddress;
import org.jeesl.model.xml.system.io.mail.Mail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.exlp.util.xml.JaxbUtil;

public class AbstractJeeslMail<L extends UtilsLang,D extends UtilsDescription,
								CATEGORY extends UtilsStatus<CATEGORY,L,D>,
								TYPE extends UtilsStatus<TYPE,L,D>,
								TEMPLATE extends JeeslIoTemplate<L,D,CATEGORY,SCOPE,DEFINITION,TOKEN>,
								SCOPE extends UtilsStatus<SCOPE,L,D>,
								DEFINITION extends JeeslIoTemplateDefinition<D,TYPE,TEMPLATE>,
								TOKEN extends JeeslIoTemplateToken<L,D,TEMPLATE,TOKENTYPE>,
								TOKENTYPE extends UtilsStatus<TOKENTYPE,L,D>,
								
								MC extends UtilsStatus<MC,L,D>,
								MAIL extends JeeslIoMail<L,D,MC,MAIL,STATUS,RETENTION>,
								STATUS extends UtilsStatus<STATUS,L,D>,
								RETENTION extends UtilsStatus<RETENTION,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(AbstractJeeslMail.class);
	
	public enum Action {spool,debugXml}
	
	private final JeeslIoMailFacade<L,D,MC,MAIL,STATUS,RETENTION> fMail;
//	protected final FreemarkerIoTemplateEngine<L,D,CATEGORY,TYPE,TEMPLATE,SCOPE,DEFINITION,TOKEN,TOKENTYPE> fmEngine;
	protected MC category;
	
	protected final boolean spoolMail;
	protected final boolean debugMail;
	
	protected EmailAddress mailFrom;
	
	public AbstractJeeslMail(JeeslIoMailFacade<L,D,MC,MAIL,STATUS,RETENTION> fMail, Action action)
	{
		this.fMail=fMail;
		switch (action)
		{
			case spool:	spoolMail=true; debugMail=false; break;
			case debugXml:	spoolMail=false; debugMail=true; break;
			default: spoolMail=false; debugMail=false;
		}
	}
	
	protected void spool(Mail mail) throws UtilsConstraintViolationException, UtilsNotFoundException
	{
		logger.info("spool "+debugMail+"."+spoolMail);
		if(debugMail)
		{
			JaxbUtil.info(mail);
		}
		if(spoolMail)
		{
			fMail.queueMail(category,mail);
			logger.info("Spooled");
		}
	}
}