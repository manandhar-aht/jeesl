package org.jeesl.factory.ejb.system.io.mail;

import java.util.Date;

import org.jeesl.interfaces.model.system.io.mail.JeeslIoMail;
import org.jeesl.model.xml.system.io.mail.Mail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.exlp.util.xml.JaxbUtil;

public class EjbIoMailFactory <L extends UtilsLang,D extends UtilsDescription,
								CATEGORY extends UtilsStatus<CATEGORY,L,D>,
								MAIL extends JeeslIoMail<L,D,CATEGORY,MAIL>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbIoMailFactory.class);
	
	private final Class<MAIL> cMail;

	public EjbIoMailFactory(final Class<MAIL> cMail)
	{
        this.cMail = cMail;
	}
 
	public MAIL build(CATEGORY category, Mail mail)
	{
		MAIL ejb = null;
		try
		{
			ejb = cMail.newInstance();
			ejb.setCategory(category);
			ejb.setRecordCreation(new Date());
			ejb.setXml(JaxbUtil.toString(mail));
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
}