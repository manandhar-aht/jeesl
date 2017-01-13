package org.jeesl.factory.factory;

import org.jeesl.factory.ejb.system.io.mail.EjbIoMailFactory;
import org.jeesl.interfaces.model.system.io.mail.JeeslIoMail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class MailFactoryFactory<L extends UtilsLang,D extends UtilsDescription,
								CATEGORY extends UtilsStatus<CATEGORY,L,D>,
								MAIL extends JeeslIoMail<L,D,CATEGORY,MAIL,STATUS>, STATUS extends UtilsStatus<STATUS,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(MailFactoryFactory.class);
	
//	final Class<L> cL;
//	final Class<D> cD;
//	final Class<CATEGORY> cCategory;
	final Class<MAIL> cMail;
	
	private MailFactoryFactory(final Class<MAIL> cMail)
	{       
		this.cMail = cMail;
	}
	
	public static <L extends UtilsLang,D extends UtilsDescription,
					CATEGORY extends UtilsStatus<CATEGORY,L,D>,
					MAIL extends JeeslIoMail<L,D,CATEGORY,MAIL,STATUS>, STATUS extends UtilsStatus<STATUS,L,D>>
		MailFactoryFactory<L,D,CATEGORY,MAIL,STATUS> factory(final Class<MAIL> cMail)
	{
		return new MailFactoryFactory<L,D,CATEGORY,MAIL,STATUS>(cMail);
	}
	
	public EjbIoMailFactory<L,D,CATEGORY,MAIL,STATUS> mail()
	{
		return new EjbIoMailFactory<L,D,CATEGORY,MAIL,STATUS>(cMail);
	}
}