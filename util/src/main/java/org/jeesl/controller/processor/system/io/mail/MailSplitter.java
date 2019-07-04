package org.jeesl.controller.processor.system.io.mail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.jeesl.api.facade.io.JeeslIoMailFacade;
import org.jeesl.controller.monitor.BucketSizeCounter;
import org.jeesl.factory.builder.io.IoMailFactoryBuilder;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileContainer;
import org.jeesl.interfaces.model.system.io.mail.core.JeeslIoMail;
import org.jeesl.interfaces.model.system.io.mail.core.JeeslMailRetention;
import org.jeesl.interfaces.model.system.io.mail.core.JeeslMailStatus;
import org.jeesl.model.xml.system.io.mail.Attachment;
import org.jeesl.model.xml.system.io.mail.Mail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.exlp.util.xml.JaxbUtil;

public class MailSplitter<L extends UtilsLang,D extends UtilsDescription,
						CATEGORY extends UtilsStatus<CATEGORY,L,D>,
						MAIL extends JeeslIoMail<L,D,CATEGORY,STATUS,RETENTION,FRC>,
						STATUS extends JeeslMailStatus<L,D,STATUS,?>,
						RETENTION extends JeeslMailRetention<L,D,RETENTION,?>,
						FRC extends JeeslFileContainer<?,?>>
{
	final static Logger logger = LoggerFactory.getLogger(MailSplitter.class);
	
	private final IoMailFactoryBuilder<L,D,CATEGORY,MAIL,STATUS,RETENTION,FRC> fbMail;
	private final JeeslIoMailFacade<L,D,CATEGORY,MAIL,STATUS,RETENTION,FRC> fMail;
	
	private final BucketSizeCounter bsc;
	
	public MailSplitter(IoMailFactoryBuilder<L,D,CATEGORY,MAIL,STATUS,RETENTION,FRC> fbMail,
						JeeslIoMailFacade<L,D,CATEGORY,MAIL,STATUS,RETENTION,FRC> fMail)
	{		
		this.fbMail=fbMail;
		this.fMail=fMail;
		bsc = new BucketSizeCounter(this.getClass().getSimpleName()); 
	}
	
	public void split(List<CATEGORY> categories, List<STATUS> status, Date upToDate)
	{
		bsc.clear();
		List<RETENTION> retentions = new ArrayList<>();
		
		try
		{
			retentions.add(fMail.fByCode(fbMail.getClassRetention(), JeeslMailRetention.Code.fully));
		}
		catch (UtilsNotFoundException e) {e.printStackTrace();}
		
		
		int size=1;
		while(size>0)
		{
			List<MAIL> mails = fMail.fMails(categories,status,retentions, new Date(0), upToDate, 5);
			size=mails.size();
			for(MAIL eMail : mails)
			{
				bsc.debugLoop(100);
				bsc.add("e-mails",1);
				try
				{
					Mail xMail = JaxbUtil.loadJAXB(IOUtils.toInputStream(eMail.getXml(), "UTF-8"), Mail.class);
					
					bsc.add("attachments",xMail.getAttachment().size());
					for(Attachment att : xMail.getAttachment())
					{
						bsc.add("size",att.getData().length);
					}
//					logger.info(eMail.getRecipient()+" "+eMail.getXml().length()+" "+xMail.getAttachment().size());
					
					eMail.setRetention(fMail.fByCode(fbMail.getClassRetention(), JeeslMailRetention.Code.split));
					fMail.save(eMail);
				}
				catch (IOException | UtilsNotFoundException | UtilsConstraintViolationException | UtilsLockingException e) {e.printStackTrace();}
			}
		}
		
		bsc.ofx(System.out);
	}
}