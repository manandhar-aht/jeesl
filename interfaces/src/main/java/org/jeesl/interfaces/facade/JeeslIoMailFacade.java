package org.jeesl.interfaces.facade;

import org.jeesl.interfaces.model.system.io.mail.JeeslIoMail;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public interface JeeslIoMailFacade <L extends UtilsLang,D extends UtilsDescription,
									CATEGORY extends UtilsStatus<CATEGORY,L,D>,
									MAIL extends JeeslIoMail<L,D,CATEGORY,MAIL,STATUS>, STATUS extends UtilsStatus<STATUS,L,D>>
			extends UtilsFacade
{	
	void spoolMail(CATEGORY category, org.jeesl.model.xml.system.io.mail.Mail mail) throws UtilsConstraintViolationException, UtilsNotFoundException;
}