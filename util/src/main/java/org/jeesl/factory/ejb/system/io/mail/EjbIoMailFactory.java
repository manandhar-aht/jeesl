package org.jeesl.factory.ejb.system.io.mail;

import java.util.Date;

import org.jeesl.interfaces.model.system.io.mail.core.JeeslIoMail;
import org.jeesl.model.xml.system.io.mail.Mail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.exlp.util.xml.JaxbUtil;

public class EjbIoMailFactory <L extends UtilsLang,D extends UtilsDescription,
								CATEGORY extends UtilsStatus<CATEGORY,L,D>,
								MAIL extends JeeslIoMail<L,D,CATEGORY,MAIL,STATUS,RETENTION>,
								STATUS extends UtilsStatus<STATUS,L,D>,
								RETENTION extends UtilsStatus<RETENTION,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbIoMailFactory.class);
	
	private final Class<MAIL> cMail;

	public EjbIoMailFactory(final Class<MAIL> cMail)
	{
        this.cMail = cMail;
	}
 
	public MAIL build(CATEGORY category, STATUS status, Mail mail, RETENTION retention)
	{
		MAIL ejb = null;
		try
		{
			ejb = cMail.newInstance();
			ejb.setCategory(category);
			ejb.setStatus(status);
			ejb.setRetention(retention);
			ejb.setCounter(0);
			ejb.setRecordCreation(new Date());
			if(mail.isSetHeader() && mail.getHeader().isSetTo() && mail.getHeader().getTo().isSetEmailAddress()){ejb.setRecipient(mail.getHeader().getTo().getEmailAddress().get(0).getEmail());}
			ejb.setXml(JaxbUtil.toString(mail));
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
}